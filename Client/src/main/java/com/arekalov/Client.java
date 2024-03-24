package com.arekalov;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345); // Подключение к серверу
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String message = "";

            while (!message.equals("0")) {
                System.out.print("Введите строку для отправки на сервер (для выхода введите 0): ");
                message = userInput.readLine(); // Чтение строки с консоли

                out.println(message); // Отправка строки на сервер

                String response = in.readLine(); // Получение ответа от сервера
                System.out.println("Сервер ответил: " + response);
            }

            socket.close(); // Закрытие соединения
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
