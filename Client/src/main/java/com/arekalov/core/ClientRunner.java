package com.arekalov.core;


import com.arekalov.entities.CommandWithProduct;
import com.arekalov.entities.Product;
import com.arekalov.errors.ArgsCountError;
import com.arekalov.errors.IncorrectCommandError;
import com.arekalov.errors.RecursionError;
import com.arekalov.readers.ProductReader;

import java.io.*;
import java.util.*;

/**
 * Class for running the program
 */
public class ClientRunner {
    public static final String ENV_NAME = "PROGA";
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private IOManager ioManager = new IOManager(ENV_NAME);
    private ProductReader productReader = new ProductReader(ioManager);
    private Boolean isRunning = true;
    private HashSet<String> files = new HashSet<String>();


    public ClientRunner(ObjectOutputStream out, ObjectInputStream in) {
        this.out = out;
        this.in = in;
    }

    /**
     * Method to start interactive mode
     */

    public void startInteractiveMode() {
        startWorkingPrinter();
        try {
            while (isRunning) {
                printDelimiter();
                String input = ioManager.consoleRead();
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

    private void executeCommand(String command) {
        try {
            String[] commandParts = validateCommand(command);
            commandParts[0] = commandParts[0].toLowerCase();
            if (commandParts[0].equals("execute_script")) {
                executeScript(commandParts[1]);
            } else {
                sendCommand(command);
            }
        } catch (Exception ex) {
            System.out.println("\u001B[31m" + ex.getMessage() + "\u001B[0m");
        }
    }

    private void executeScript(String path) {
        try {
//          execute_script  /home/arekalov/Subjects/II/Proga/Lab6/Client/src/main/data/simple.txt
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
                    } else {
                        sendCommand(line);
                    }
                    sendCommand(line);
                } while (scanner.hasNextLine());
                files.add(path);
                ioManager.setScanner(new Scanner(System.in));
                System.out.println("End executing script");
            } else throw new RecursionError();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void sendCommand(String command) throws IOException, ClassNotFoundException {
        String[] commandParts = validateCommand(command.toLowerCase());
        Product product = null;
        if (CommandsInfoArrays.commandsWithInputing.contains(commandParts[0])) {
            product = productReader.getProduct();
        }
        CommandWithProduct commandWithProduct = new CommandWithProduct(commandParts[0], commandParts, product);
        out.writeObject(commandWithProduct);
        out.flush();

        String ans = (String) in.readObject();
        if (Objects.equals(ans, "Bye")) {
            isRunning = false;
        } else {
            System.out.println(ans);
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
}
