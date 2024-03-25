package com.arekalov;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

public class ServerConnectivityManager {
    private Integer port;
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
}
