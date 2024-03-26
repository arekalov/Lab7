package com.arekalov.errors;

/**
 * Class for throwing error when recursion is not allowed
 */
public class RecursionError extends RuntimeException {
    /**
     * Method to throw error when recursion is not allowed
     */
    public RecursionError() {

        super("Error: recursion is not allowed");
    }
}
