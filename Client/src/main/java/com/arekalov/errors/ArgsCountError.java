package com.arekalov.errors;

public class ArgsCountError extends RuntimeException{
    /**
     * Class for throwing error when wrong number of arguments is passed
     */
    public ArgsCountError() {
    }
    /**
     * Method to throw error when wrong number of arguments is passed
     * @param message
     */
    public ArgsCountError(String message) {

        super(message);
    }
}
