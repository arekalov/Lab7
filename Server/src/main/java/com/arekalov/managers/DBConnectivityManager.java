package com.arekalov.managers;

import com.arekalov.core.Server;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectivityManager {
    private Session session;
    private Connection connection;
    private Logger logger = Server.logger;

    public DBConnectivityManager() {
        connect();
    }

    public Connection getConnection() {
        return connection;
    }

    public void connect() {
        String sshHost = "se.ifmo.ru";
        String sshUsername = "s409449";
        String sshPassword = "CXef&0424";
        String dbHost = "localhost";
        int sshPort = 2222;
        int localPort = 54321;
        int dbPort = 5432;
        String dbName = "studs";
        String dbUsername = "s409449";
        String dbPassword = "7ChhsEMmi3l6rp3x";

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(sshUsername, sshHost, sshPort);
            session.setPassword(sshPassword);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            int tunnelLocalPort = localPort;
            int tunnelRemotePort = dbPort;
            session.setPortForwardingL(tunnelLocalPort, dbHost, tunnelRemotePort);

            String dbUrl = "jdbc:postgresql://localhost:" + tunnelLocalPort + "/" + dbName;
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            logger.info("Соединение c "+ dbUrl + " установлено успешно.");

        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public void disconnect() {
        try {
            connection.close();
            logger.info("Соединение c бд закрыто.");

            session.disconnect();
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
