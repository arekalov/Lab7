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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("hui");
            exception.printStackTrace();
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
        System.out.println("Вы будете авторизованы после выполнения любой команды.");
    }

    private void logIn() {
        System.out.println("Введите логин");
        String login = ioManager.consoleRead();
        System.out.println("Введите пароль");
        String pass = ioManager.consoleRead();
        userInfo = new UserInfo(login, hashString(pass), AuthMode.LogIn);
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
        userInfo = new UserInfo(login, hashString(pass), AuthMode.SignUp);

    }

    public static String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hashedBytes = digest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void executeCommand(String command) throws IOException {
        try {
            String[] commandParts = validateCommand(command);
            commandParts[0] = commandParts[0].toLowerCase();
            if (commandParts[0].equals("execute_script")) {
                executeScript(commandParts[1]);
            } else if (commandParts[0].equals("login")) {
                userInfo = null;
                signUpOrLogin();
            } else {
                sendCommand(command);
            }
        } catch (SocketException socketException) {
            System.out.println("\u001B[31m" + "Сервер временно недоступен" + "\u001B[0m");
            isRunning = false;
        }catch (IncorrectCommandError incorrectCommandError) {
            System.out.println("\u001B[31m" + "Неверная команда" + "\u001B[0m");
        }
        catch (IOException ex) {
            throw new IOException();
        }
    }

    private void executeScript(String path) throws IOException {
        File inputFile = new File(path);
        Scanner scanner = new Scanner(inputFile);
        ioManager.setScanner(scanner);
        if (true) {
            System.out.println("Start executing script");
            do {
                String line = scanner.nextLine();
                System.out.println("__");
                System.out.println(line);
                if (files.contains(path)) {
                    throw new RecursionError();
                }
                files.add(path);
                executeCommand(line);
            } while (scanner.hasNextLine());
            ioManager.setScanner(new Scanner(System.in));
            System.out.println("End executing script");
        } else throw new RecursionError();

    }

    protected void sendCommand(String command) {
        try {
            String[] commandParts = validateCommand(command.toLowerCase());
            Product product = null;
            if (commandParts[0].equals("update")) {
                commandParts[0] = "checkupdate";
                CommandWithProduct commandWithProduct = new CommandWithProduct("checkupdate", commandParts, product, userInfo);
//                System.out.println(commandWithProduct);
                byte[] newData = Client.serialize(commandWithProduct);
                ByteBuffer buffer2 = ByteBuffer.allocate(newData.length);
                buffer2.put(newData);
                buffer2.flip();
                while (buffer2.hasRemaining()) {
                    socketChanel.write(buffer2);
                }

                ByteBuffer bufferNew2 = ByteBuffer.allocate(16384);
                while (true) {
                    bufferNew2.clear();
                    int bytesRead = socketChanel.read(bufferNew2);
                    if (bytesRead == -1) {
                        break;
                    } else if (bytesRead > 0) {
                        bufferNew2.flip();
                        byte[] answer = new byte[bufferNew2.remaining()];
                        bufferNew2.get(answer);
                        String obj = deserialize(answer);
                        System.out.println(obj);
                        if (Objects.equals(obj, "ok")) {
                            commandParts[0] = "update";
                            break;
                        } else {
                            return;
                        }
                    }
                }
            }
            if (CommandsInfoArrays.commandsWithInputing.contains(commandParts[0])) {
                product = productReader.getProduct();
            }
            if (commandParts[0].equals("update")) {
                product.setCreator(userInfo.getLogin());
            }
            CommandWithProduct commandWithProduct = new CommandWithProduct(commandParts[0], commandParts, product, userInfo);
//            System.out.println(commandWithProduct);
            byte[] data = Client.serialize(commandWithProduct);
            ByteBuffer buffer = ByteBuffer.allocate(data.length);
            buffer.put(data);
            buffer.flip();
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
                    if (obj.equals("")) {
                        System.out.println(obj);
                    }
                    if (!obj.equals("Пользователь с таким логином уже существует!")) {
                        userInfo.setAuthMode(AuthMode.LogIn);
                    }
                    System.out.println(obj);
                    break;
                }
            }
        } catch (Exception ex) {
            isRunning = false;
            System.out.println("\u001B[31m" + ex.getMessage() + "\u001B[0m");
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
