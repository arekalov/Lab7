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
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.arekalov.managers.ServerExecutionManager.serialize;
import static org.apache.commons.lang3.SerializationUtils.deserialize;

public class Server {
    final public static Integer PORT = 54375;
    public static final Logger logger = LogManager.getLogger(Server.class);
    ServerConnectivityManager serverConnectivityManager = new ServerConnectivityManager(PORT, logger);
    DBConnectivityManager dbManager = new DBConnectivityManager();
    public HashMap<Integer, ServerExecutionManager> clientsHashSet = new HashMap<>();
    ConcurrentLinkedDeque arrayDeque = new DBCommandManager(dbManager.getConnection()).getProducts();
    Selector selector = serverConnectivityManager.selector;

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
    public void run() {
        logger.info("Сервер запущен. Ожидание подключения...");
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
                        handleClientConnection(key);
                    } else if (key.isReadable()) {
                        handleClientRead(key);
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

    private void handleClientConnection(SelectionKey key) throws IOException, SQLException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        SocketChannel client = server.accept();
        client.configureBlocking(false);
        ServerExecutionManager serverExecutionManager = new ServerExecutionManager(arrayDeque, new DBAuthenticateManager(dbManager.getConnection(), client), dbManager.getConnection(), new DBCommandManager(dbManager.getConnection()));
                        clientsHashSet.put(client.hashCode(), serverExecutionManager);
        client.register(selector, SelectionKey.OP_READ);

        // Создаем новый поток для обработки подключения
        Thread clientThread = new Thread(() -> {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(16384);
                while (client.isOpen() && client.isConnected()) {
                    buffer.clear();
                    int bytesRead = client.read(buffer);
                    if (bytesRead == -1) {
                        logger.info("Соединение с клиентом закрыто!");
                        client.close();
                        break;
                    } else if (bytesRead > 0) {
                        buffer.flip();
                        byte[] data = new byte[buffer.remaining()];
                        buffer.get(data);
                        CommandWithProduct obj = deserialize(data);
                        executeCommand(obj, client);
                    }
                }
            } catch (IOException ex) {
                clientsHashSet.remove(client.hashCode());
                logger.error("Ошибка ввода-вывода в потоке клиента: " + ex.getMessage());
            } finally {
                // Удаление клиента из списка при закрытии соединения
                clientsHashSet.remove(client.hashCode());
            }
        });
        clientThread.start();
    }

    private void handleClientRead(SelectionKey key) throws IOException {
        cachedThreadPool.execute(() -> {
            try {
                SocketChannel client = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(16384);
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
                    executeCommand(obj, client);
                }
            } catch (IOException ex) {
                logger.error("Ошибка ввода-вывода в потоке клиента: " + ex.getMessage());
            }
        });
    }


    private void executeCommand(CommandWithProduct commandWithProduct, SocketChannel client) {
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
        fixedThreadPool.execute(() -> {
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
        });
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
