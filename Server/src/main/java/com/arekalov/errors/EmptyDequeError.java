package com.arekalov.errors;

/**
 * Class for throwing error when deque is empty
 */
public class EmptyDequeError extends RuntimeException{

    /**
     * Method to throw error when deque is empty
     */
    public EmptyDequeError() {
        super("Error: deque is already empty");
    }
}
