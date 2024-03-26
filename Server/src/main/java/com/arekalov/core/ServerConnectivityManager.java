package com.arekalov.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class ServerConnectivityManager {
    private Integer port;
    private Selector selector;

    public Selector getSelector() {
        return selector;
    }

    private ServerSocketChannel serverSocketChannel;

    public ServerConnectivityManager(Integer port) {
        try {
            this.port = port;
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
        } catch (Exception ex) {
            System.out.println(ex);
            serverSocketChannel = null;
        }
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public ServerSocketChannel getServerSocketChannel() {
        return serverSocketChannel;
    }

    public void close() {
        try {
            if (serverSocketChannel != null) {
                serverSocketChannel.close();

            }
        } catch (IOException ioException) {

        }
    }
}
