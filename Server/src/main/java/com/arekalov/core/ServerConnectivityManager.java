package com.arekalov.core;

import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class ServerConnectivityManager {
    private Integer port;
    Selector selector;

    Logger logger;
    private ServerSocketChannel serverSocketChannel;

    public ServerConnectivityManager(Integer port, Logger logger) {
        this.logger = logger;
        try {
            this.selector = Selector.open();
            this.port = port;
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("localhost", Server.PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception ex) {
            logger.error(ex);
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
            logger.error(ioException);
        }
    }
}
