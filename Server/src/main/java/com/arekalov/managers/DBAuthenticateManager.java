package com.arekalov.managers;

import com.arekalov.core.Server;
import com.arekalov.errors.HaveNotAccauntError;
import com.arekalov.errors.IncorrectPasswordError;
import com.arekalov.errors.UserAlreadyExistError;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import static com.arekalov.managers.ServerExecutionManager.serialize;

public class DBAuthenticateManager {
    private Connection connection;
    private Logger logger = Server.logger;
    private SocketChannel client;

    public DBAuthenticateManager(Connection connection, SocketChannel client) {
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
            insertStatement.setString(2, hashString(password));
            insertStatement.execute();
        } else {
            String answer = "Пользователь с таким логином уже существует! Введите команду login и повторите попытку!";
            sendToClient(answer);
            throw new UserAlreadyExistError(answer);
        }

    }

    public void logIn(String login, String password) throws SQLException, IncorrectPasswordError, IOException, HaveNotAccauntError {
        String usersInTableQuery = "SELECT * FROM USERS WHERE (login = ?)";
        PreparedStatement usersStatement = connection.prepareStatement(usersInTableQuery);
        usersStatement.setString(1, login);
        usersStatement.execute();
        ResultSet usersStatementResult = usersStatement.getResultSet();
        if (usersStatementResult.next()) {
            String pass = usersStatementResult.getString("password");
            if (!verifyHash(password, pass)){
                sendToClient("Неправильный пароль. Введите команду login и повторите попытку.");
                throw new IncorrectPasswordError();
            }
        }
        else {
            sendToClient("У вас нет аккаунта. Введите команду login и пройдите регистрацию.");
            throw new HaveNotAccauntError();
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

    public static String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hashedBytes = digest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Функция для проверки соответствия хэш-значения исходному тексту
    public static boolean verifyHash(String input, String hash) {
        return hashString(input).equals(hash);
    }

    public static void main(String[] args) {
        String input = "Hello World";
        String hashed = hashString(input);
        System.out.println("Hashed value: " + hashed);
        System.out.println("Is Hash Verified: " + verifyHash(input, hashed)); // Должно быть true
    }


}
