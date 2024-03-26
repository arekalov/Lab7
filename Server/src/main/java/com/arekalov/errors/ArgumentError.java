package com.arekalov.errors;

public class ArgumentError extends RuntimeException {
    /**
     * Class for throwing error when wrong argument is passed
     */
    /**
     * Method to throw error when wrong argument is passed
     */
    public ArgumentError() {
        super("Error: incorrect command's arg");
    }

    /**
     * Method to throw error when wrong argument is passed
     *
     * @param message
     */
    public ArgumentError(String message) {
        super(message);
    }
}
