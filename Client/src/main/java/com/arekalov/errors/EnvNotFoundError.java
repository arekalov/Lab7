package com.arekalov.errors;
/**
 * Class for throwing error when env is not found
 */
public class EnvNotFoundError extends RuntimeException{
    /**
     * Method to throw error when env is not found
     * @param envName - String
     */
    public EnvNotFoundError(String envName) {

        super("Error: Not found env " + envName);
    }
}
