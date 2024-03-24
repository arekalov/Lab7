package com.arekalov.errors;

/**
 * Class for throwing error when wrong command is passed
 */
public class IncorrectCommandError extends RuntimeException {
    /**
     * Method to throw error when wrong command is passed
     *
     * @param message
     */
    public IncorrectCommandError(String message) {

        super("Error: incorrect command \"" + message + "\"");
    }
}
