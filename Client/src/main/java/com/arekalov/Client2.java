package com.arekalov;

import com.arekalov.core.ClientRunner;
import com.arekalov.entities.CommandWithProduct;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client2 {
    final public static int PORT = 12345;
    final public static String HOST = "localhost";

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false);

            try {
                ClientRunner runner = new ClientRunner(socketChannel);
                runner.startInteractiveMode();
            } catch (Exception ex) {}
            finally {
                socketChannel.close();
            }
        } catch (IOException e) {
            System.err.println(e);
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
