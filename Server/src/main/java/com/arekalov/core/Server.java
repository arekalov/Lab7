package com.arekalov.core;


import com.arekalov.entities.CommandWithProduct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
    final public static Integer PORT = 12345;
    public static final Logger logger = LogManager.getLogger(Server.class);
    ServerConnectivityManager serverConnectivityManager = new ServerConnectivityManager(PORT, logger);
    ServerExecutionManager serverExecutionManager = new ServerExecutionManager();

    public void run() {
        logger.info("Сервер запущен. Ожидание подключения...");

//        Thread consoleInputThread = new Thread(() -> {
//            Scanner scanner = new Scanner(System.in);
//            while (true) {
//                if (scanner.hasNextLine()) {
//                    String consoleInput = scanner.nextLine();
//                    serverExecutionManager.save(consoleInput);
//                }
//            }
//        });
//        consoleInputThread.start();
        ByteBuffer buffer = ByteBuffer.allocate(16384);
        Selector selector = serverConnectivityManager.selector;
        try {
            while (true) {
                selector.select();

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    if (key.isAcceptable()) {
                        logger.info("Клиент подключен");
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        buffer.clear();
                        int bytesRead = client.read(buffer);
                        if (bytesRead == -1) {
                            logger.info("Соединение с клиентом закрыто!");
                            client.close();
                            key.cancel();
                        } else if (bytesRead > 0) {
                            buffer.flip();
                            byte[] data = new byte[buffer.remaining()];
                            buffer.get(data);
                            CommandWithProduct obj = deserialize(data);
                            readData(obj, client);
                        }
                }
            }
            }
        } catch (IOException e) {
        } catch (Exception exception) {
            logger.error("Произошла ошибка " + exception);
        } finally {
            serverConnectivityManager.close();
        }
    }

    private void readData(CommandWithProduct commandWithProduct, SocketChannel client) {
        try {
            serverExecutionManager.executeCommand(commandWithProduct, client);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    private static CommandWithProduct deserialize(byte[] data) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return (CommandWithProduct) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
