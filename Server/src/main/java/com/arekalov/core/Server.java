package com.arekalov.core;


import com.arekalov.entities.CommandWithProduct;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    final public static Integer PORT = 12345;

    ServerConnectivityManager serverConnectivityManager = new ServerConnectivityManager(PORT);
    private final ServerExecutionManager serverExecutionManager = new ServerExecutionManager();
    public void run() {
        System.out.println("Сервер запущен. Ожидание подключения...");
        ServerSocketChannel socketChannel = serverConnectivityManager.getServerSocketChannel();
        try {
            while (true) {
                SocketChannel clientSocketChannel = socketChannel.accept();
                System.out.println("Клиент подключен");

                readData(clientSocketChannel);

                clientSocketChannel.close();
                System.out.println("Соединение с клиентом закрыто.");
            }

        } catch (IOException e) {

        } catch (Exception exception) {
            System.err.println("Произошла ошибка " + exception);
        }
        finally {
            serverConnectivityManager.close();
        }
    }

    private void readData(SocketChannel socketChannel) {
        try (ObjectInputStream in = new ObjectInputStream(socketChannel.socket().getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socketChannel.socket().getOutputStream())) {
            while (true) {
                Object obj = in.readObject();
                if (obj instanceof CommandWithProduct) {
                    CommandWithProduct commandWithProduct = (CommandWithProduct) obj;
                    System.out.println("Получено от клиента: " + commandWithProduct);

//                    serverExecutionManager.

                    out.writeObject(commandWithProduct);
                    out.flush();
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
