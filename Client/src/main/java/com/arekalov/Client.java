package com.arekalov;

import com.arekalov.core.ClientRunner;

import java.io.*;
import java.net.*;

public class Client {
    final public static int PORT = 12345;
    final public static String HOST = "localhost";

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            try {
                ClientRunner runner = new ClientRunner(out, in);
                runner.startInteractiveMode();
            } catch (Exception ex) {}
            finally {
                in.close();
                out.close();
                socket.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
