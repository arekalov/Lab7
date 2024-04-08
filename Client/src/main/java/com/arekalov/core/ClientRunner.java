package com.arekalov.core;


import com.arekalov.Client;
import com.arekalov.entities.AuthMode;
import com.arekalov.entities.CommandWithProduct;
import com.arekalov.entities.Product;
import com.arekalov.entities.UserInfo;
import com.arekalov.errors.ArgsCountError;
import com.arekalov.errors.IncorrectCommandError;
import com.arekalov.errors.RecursionError;
import com.arekalov.readers.ProductReader;

import java.io.*;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * Class for running the program
 */
public class ClientRunner {
    public static final String ENV_NAME = "PROGA";
    private final SocketChannel socketChanel;
    private IOManager ioManager = new IOManager(ENV_NAME);
    private ProductReader productReader = new ProductReader(ioManager);
    private Boolean isRunning = true;
    private HashSet<String> files = new HashSet<String>();
    private UserInfo userInfo = null;


    public ClientRunner(SocketChannel socketChannel) {
        this.socketChanel = socketChannel;
    }

    /**
     * Method to start interactive mode
     */

    public void startInteractiveMode() {
        startWorkingPrinter();
        try {
            while (isRunning) {
                if (userInfo == null) {
                    signUpOrLogin();
                }
                printDelimiter();
                String input = ioManager.consoleRead();
                files.clear();
                executeCommand(input);
            }
            stopWorkingPrinter();
        } catch (NoSuchElementException noSuchElementException) {
            System.err.println("Exit");
            isRunning = false;
            stopWorkingPrinter();
        } catch (Exception exception) {
            System.err.println("Unexpected error!");
            isRunning = false;
            stopWorkingPrinter();
        }
    }

    private void signUpOrLogin() {
        while (userInfo == null) {
            System.out.println("Необходимо войти или зарегистрироваться. Введите р или л!");
            String input = ioManager.consoleRead();
            if (input.equalsIgnoreCase("р") || input.equalsIgnoreCase("r")) {
                registerNewUser();
            } else if (input.equalsIgnoreCase("л") || input.equalsIgnoreCase("l")) {
                logIn();
            } else {
                System.out.println("Неверная команда");
            }
        }
        System.out.println("Регистрация пройдена успешно!");
    }

    private void logIn() {
        System.out.println("Введите логин");
        String login = ioManager.consoleRead();
        System.out.println("Введите пароль");
        String pass = ioManager.consoleRead();
        System.out.println("Проверка логина и пароля происходит при каждой отправке команды," +
                "если логин или пароль окажутся неверными, выполнение команд будет невозможно. \n" +
                "В этом случае введите слово login!");
        userInfo = new UserInfo(login, pass, AuthMode.LogIn);
    }

    private void registerNewUser() {
        System.out.println("Введите логин");
        String login = ioManager.consoleRead();
        while (login.isEmpty()) {
            System.out.println("Логин не должен быть пустым, попробуйте еще раз!");
            login = ioManager.consoleRead();
        }

        System.out.println("Введите пароль");
        String pass = ioManager.consoleRead();
        while (pass.isEmpty()) {
            System.out.println("Пароль не должен быть пустым, попробуйте еще раз!");
            pass = ioManager.consoleRead();
        }
        userInfo = new UserInfo(login, pass, AuthMode.SignUp);

    }

    private void executeCommand(String command) {
        try {
            String[] commandParts = validateCommand(command);
            commandParts[0] = commandParts[0].toLowerCase();
            if (commandParts[0].equals("execute_script")) {
                executeScript(commandParts[1]);
            } else if (commandParts[0].equals("login")) {
                userInfo = null;
                signUpOrLogin();
            } else {
                System.out.println(userInfo);
                sendCommand(command);
            }
        } catch (SocketException socketException) {
            System.out.println("\u001B[31m" + "Сервер временно недоступен" + "\u001B[0m");
            isRunning = false;
        } catch (Exception ex) {
            System.out.println("\u001B[31m" + ex.getMessage() + "\u001B[0m");
        }
    }

    private void executeScript(String path) {
        try {
            File inputFile = new File(path);
            Scanner scanner = new Scanner(inputFile);
            ioManager.setScanner(scanner);
            if (true) {
                System.out.println("Start executing script");
                files.add(path);
                do {
                    String line = scanner.nextLine();
                    System.out.println("__");
                    System.out.println(line);
                    if (files.contains(path)) {
                        throw new RecursionError();
                    }
                    executeCommand(line);
                } while (scanner.hasNextLine());
                ioManager.setScanner(new Scanner(System.in));
                System.out.println("End executing script");
            } else throw new RecursionError();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void sendCommand(String command) throws IOException, ClassNotFoundException {
        String[] commandParts = validateCommand(command.toLowerCase());
        Product product = null;
        if (CommandsInfoArrays.commandsWithInputing.contains(commandParts[0])) {
            product = productReader.getProduct();
        }
        CommandWithProduct commandWithProduct = new CommandWithProduct(commandParts[0], commandParts, product, userInfo);
        byte[] data = Client.serialize(commandWithProduct);
        ByteBuffer buffer = ByteBuffer.allocate(data.length);
        buffer.put(data);
        buffer.flip();
        // Отправляем данные на сервер
        while (buffer.hasRemaining()) {
            socketChanel.write(buffer);
        }

        ByteBuffer bufferNew = ByteBuffer.allocate(16384);
        while (true) {
            bufferNew.clear();
            int bytesRead = socketChanel.read(bufferNew);
            if (bytesRead == -1) {
                break;
            } else if (bytesRead > 0) {
                bufferNew.flip();
                byte[] answer = new byte[bufferNew.remaining()];
                bufferNew.get(answer);
                String obj = deserialize(answer);
                if (obj.equals("Bye")) {
                    isRunning = false;
                }
                System.out.println(obj);
                break;
            }
        }
    }


    /**
     * This method validates the command using regular expressions, can throw an exception if the command is incorrect or has the wrong number of arguments
     *
     * @param command - command to validate
     * @return commandParts - command parts
     */
    private String[] validateCommand(String command) {

        String[] commandParts = command.split(" +");
        if (commandParts.length == 0) {
            throw new IncorrectCommandError("empty");
        }
        if (!CommandsInfoArrays.commandsList.contains(commandParts[0].toLowerCase())) {
            throw new IncorrectCommandError(commandParts[0]);
        } else if (CommandsInfoArrays.commandListWithoutArgs.contains(commandParts[0]) && commandParts.length != 1) {
            throw new ArgsCountError("Error: too many arguments for command " + "\"" + commandParts[0] + "\"");
        } else if (CommandsInfoArrays.commandsListWithArg.contains(commandParts[0]) && commandParts.length != 2) {
            throw new ArgsCountError("Error: Incorrect arguments count for command " + "\"" + commandParts[0] + "\"");
        }
        return commandParts;
    }

    /**
     * This method executes the command
     *
     * @param command - command to execute
     **/
    /**
     * Method to print that the program has started
     */
    private void startWorkingPrinter() {

        System.out.println("The program has started");
    }

    /**
     * Method to print that the program has stopped
     */
    private void stopWorkingPrinter() {

        System.out.println("The program has stopped");
    }

    /**
     * Method to print delimiter
     */
    public void printDelimiter() {

        System.out.println("_________________________");
    }

    /**
     * Method to set running
     *
     * @param running - Boolean
     */
    public void setRunning(Boolean running) {

        isRunning = running;
    }

    private static String deserialize(byte[] data) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("deserialize error!");
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }
}
