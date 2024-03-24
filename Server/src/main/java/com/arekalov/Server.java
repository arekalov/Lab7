package com.arekalov;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        List<Socket> clients = new ArrayList<>(); // список для хранения подключенных клиентов

        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Создание серверного сокета на порту 12345
            System.out.println("Сервер запущен. Ожидание подключения...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Ожидание подключения клиента
                System.out.println("Клиент подключен");
                clients.add(clientSocket); // Добавление клиента в список

                // Обработка клиента
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Получено от клиента: " + inputLine);
                    out.println("Ответ от сервера: " + inputLine); // Отправка ответа клиенту
                }

                clientSocket.close(); // Закрытие соединения с клиентом
                clients.remove(clientSocket); // Удаление клиента из списка
                System.out.println("Соединение с клиентом закрыто.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}