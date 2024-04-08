package com.arekalov.managers;

import com.arekalov.core.Server;
import com.arekalov.entities.UserInfo;
import com.arekalov.errors.UserAlreadyExistError;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.*;

import static com.arekalov.managers.ServerExecutionManager.serialize;

public class DBCommandManager {
    private Connection connection;
    private Logger logger = Server.logger;
    private SocketChannel client;

    public DBCommandManager(Connection connection, SocketChannel client) {
        this.connection = connection;
        this.client = client;
    }

    public void signUp(String login, String password) throws SQLException, IOException, UserAlreadyExistError {

            String countQuery = "SELECT COUNT(*) FROM USERS WHERE (login = ?)";
            PreparedStatement countStatement = connection.prepareStatement(countQuery);
            countStatement.setString(1, login);
            countStatement.execute();
            ResultSet countResult = countStatement.getResultSet();
            countResult.next();
            int count = countResult.getInt("count");
            if (count == 0) {
                String insertQuery = "INSERT INTO users (login, password) VALUES (?, ?);";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, login);
                insertStatement.setString(2, password);
                insertStatement.execute();
            }
            else {
                String answer = "Пользователь с таким логином уже существует!";
                sendToClient(answer);
                throw new UserAlreadyExistError(answer);
            }

    }

    private void sendToClient(String answer) throws IOException {
            byte[] data = serialize(answer);
            ByteBuffer buffer = ByteBuffer.allocate(data.length);
            buffer.put(data);
            buffer.flip();
            while (buffer.hasRemaining()) {
                client.write(buffer);
            }
    }
}
