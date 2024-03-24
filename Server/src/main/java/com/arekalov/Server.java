package com.arekalov;

import com.arekalov.entities.CommandWithProduct;
import com.arekalov.entities.Product;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Создание серверного сокета на порту 12345
            System.out.println("Сервер запущен. Ожидание подключения...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Ожидание подключения клиента
                System.out.println("Клиент подключен");

                // Обработка клиента
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                CommandWithProduct commandWithProduct;
                while ((commandWithProduct = (CommandWithProduct) in.readObject()) != null) {

                    System.out.println("Получено от клиента: " + commandWithProduct);
                    out.println("Ответ от сервера: " + commandWithProduct); // Отправка ответа клиенту
                }

                clientSocket.close(); // Закрытие соединения с клиентом
                System.out.println("Соединение с клиентом закрыто.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}