package com.arekalov;

import com.arekalov.core.ClientRunner;
import com.arekalov.entities.CommandWithProduct;

import java.io.*;
import java.net.*;
import java.nio.channels.SocketChannel;

public class Client {
    final public static int PORT = 54375;
    final public static String HOST = "localhost";

    public static void main(String[] args) throws InterruptedException {
        while (true) {
//            Thread.sleep(1000);
            try {
                SocketChannel socketChannel = SocketChannel.open();
                socketChannel.connect(new InetSocketAddress(HOST, PORT));
                socketChannel.configureBlocking(false);
                System.out.println("Подключено");

                try {
                    ClientRunner runner = new ClientRunner(socketChannel);
                    runner.startInteractiveMode();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    socketChannel.close();
                }
            } catch (IOException e) {
                Thread.sleep(2000);
                System.err.println("Не удалось подключиться к серверу. Повторная попытка...");
            }
        }
    }

    public static byte[] serialize(CommandWithProduct obj) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        }
    }
}
