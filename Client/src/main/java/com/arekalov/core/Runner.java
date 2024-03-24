package com.arekalov.core;



import com.arekalov.entities.Product;
import com.arekalov.errors.ArgsCountError;
import com.arekalov.errors.IncorrectCommandError;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

/**
 * Class for running the program
 */
public class Runner {
    public static final String ENV_NAME = "PROGA";
    private final PrintWriter out;
    private final BufferedReader in;
    private IOManager ioManager = new IOManager(ENV_NAME);
    private Boolean isRunning = true;


    public Runner(PrintWriter out, BufferedReader in) {
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
                sendCommand(input);
                System.out.println(in.readLine());
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
    protected void sendCommand(String command) {
        try {
            String[] commandParts = validateCommand(command.toLowerCase());
            Product product = null;
            if (CommandsInfoArrays.commandsWithInputing.contains(command)) {
                product = ProductRe
            }
            out.println(command + " / " + commandParts);
        } catch (IncorrectCommandError icr) {
            System.err.println("Error command: " + command);
        }
        catch (ArgsCountError ace) {
            System.err.println("Error args count for command: " + command);
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
