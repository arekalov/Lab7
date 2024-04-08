package com.arekalov.managers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBCommandManager {
    private Connection connection;

    public DBCommandManager(Connection connection) {
        this.connection = connection;
    }

    public void executeCommand() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("SELECT * FROM employee WHERE id = 1");
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            System.out.println(resultSet.getString("name"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
