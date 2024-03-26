package com.arekalov.core;



import com.arekalov.entities.CommandWithProduct;
import com.arekalov.entities.Product;
import com.arekalov.errors.ArgsCountError;
import com.arekalov.errors.IncorrectCommandError;
import com.arekalov.readers.ProductReader;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.NoSuchElementException;
import java.util.Objects;

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
                sendCommand(input);
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
            if (CommandsInfoArrays.commandsWithInputing.contains(commandParts[0])) {
                product = productReader.getProduct();
            }
            CommandWithProduct commandWithProduct = new CommandWithProduct(command, commandParts, product);
            out.writeObject(commandWithProduct);
            out.flush();

            String ans = (String) in.readObject();
            if (Objects.equals(ans, "Bye")) {
                isRunning = false;
            }
            else {
                System.out.println(ans);
            }
        } catch (IncorrectCommandError icr) {
            System.err.println("Error command: " + command);
        }
        catch (ArgsCountError ace) {
            System.err.println("Error args count for command: " + command);
        }
        catch (Exception e) {
            System.err.println(e);
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
