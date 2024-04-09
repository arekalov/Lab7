package com.arekalov.core;


import com.arekalov.entities.CommandWithProduct;
import com.arekalov.errors.HaveNotAccauntError;
import com.arekalov.errors.IncorrectPasswordError;
import com.arekalov.errors.UserAlreadyExistError;
import com.arekalov.managers.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentLinkedDeque;

import static com.arekalov.managers.ServerExecutionManager.serialize;

public class Server {
    final public static Integer PORT = 54376;
    public static final Logger logger = LogManager.getLogger(Server.class);
    ServerConnectivityManager serverConnectivityManager = new ServerConnectivityManager(PORT, logger);
    DBConnectivityManager dbManager = new DBConnectivityManager();
    public HashMap<Integer, ServerExecutionManager> clientsHashSet = new HashMap<>();
    ConcurrentLinkedDeque arrayDeque = new DBCommandManager(dbManager.getConnection()).getProducts();

    public void run() {
        logger.info("Сервер запущен. Ожидание подключения...");

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
                        ServerExecutionManager serverExecutionManager = new ServerExecutionManager(arrayDeque, new DBAuthenticateManager(dbManager.getConnection(), client), dbManager.getConnection(), new DBCommandManager(dbManager.getConnection()));
                        clientsHashSet.put(client.hashCode(), serverExecutionManager);
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        buffer.clear();
                        int bytesRead = client.read(buffer);
                        if (bytesRead == -1) {
                            logger.info("Соединение с клиентом закрыто!");
                            clientsHashSet.remove(client.hashCode());
                            client.close();
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
            clientsHashSet.get(client.hashCode()).authenticate(commandWithProduct);
            clientsHashSet.get(client.hashCode()).executeCommand(commandWithProduct, client);
        } catch (UserAlreadyExistError userAlreadyExistError) {
            logger.info("Логин уже занят.");
        } catch (HaveNotAccauntError haveNotAccauntError) {
            logger.info("У пользователя нет аккаунта.");
        } catch (IncorrectPasswordError incorrectPasswordError) {
            logger.info("Неправильный пароль.");
        } catch (Exception ex) {
            sendToClient(ex.getMessage(), client);
            logger.error(ex);
        }
    }

    private void sendToClient(String answer, SocketChannel client) {
        try {
            byte[] data = serialize(answer);
            ByteBuffer buffer = ByteBuffer.allocate(data.length);
            buffer.put(data);
            buffer.flip();
            while (buffer.hasRemaining()) {
                client.write(buffer);
            }
        } catch (Exception ex) {
            logger.error("Can't send answer to client!");
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
