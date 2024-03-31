package com.arekalov.core;


import com.arekalov.entities.CommandWithProduct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Server {
    final public static Integer PORT = 12345;
    public static final Logger logger = LogManager.getLogger(Server.class);

    ServerConnectivityManager serverConnectivityManager = new ServerConnectivityManager(PORT, logger);
    private final ServerExecutionManager serverExecutionManager = new ServerExecutionManager();

    public void run() {
        logger.info("Сервер запущен. Ожидание подключения...");
        ServerSocketChannel socketChannel = serverConnectivityManager.getServerSocketChannel();

        Thread consoleInputThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                if (scanner.hasNextLine()) {
                    String consoleInput = scanner.nextLine();
                    serverExecutionManager.save(consoleInput);
                }
            }
        });
        consoleInputThread.start();
        try {
            while (true) {
                SocketChannel clientSocketChannel = socketChannel.accept();
                logger.info("Клиент подключен");

                readData(clientSocketChannel);

                clientSocketChannel.close();
                logger.info("Соединение с клиентом закрыто.");
            }


        } catch (IOException e) {
        } catch (Exception exception) {
            logger.error("Произошла ошибка " + exception);
        } finally {
            serverConnectivityManager.close();
        }
    }

    private void readData(SocketChannel socketChannel) {
        try (ObjectInputStream in = new ObjectInputStream(socketChannel.socket().getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socketChannel.socket().getOutputStream())) {
            serverExecutionManager.addOutStream(out);
            while (true) {
                Object obj = in.readObject();
                if (obj instanceof CommandWithProduct) {
                    CommandWithProduct commandWithProduct = (CommandWithProduct) obj;
                    logger.info("Получено от клиента: " + commandWithProduct);

                    serverExecutionManager.executeCommand(commandWithProduct);

                }
            }
        } catch (EOFException eofException) {
            logger.info("");
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
