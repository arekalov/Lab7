package com.arekalov;

import com.arekalov.core.Runner;

import java.io.*;
import java.net.*;

public class Client {
    final public static int PORT = 12345;
    final public static String HOST = "localhost";

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            try {
                Runner runner = new Runner(out, in);
                runner.startInteractiveMode();
            } catch (Exception ex) {}
            finally {
                in.close();
                out.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
