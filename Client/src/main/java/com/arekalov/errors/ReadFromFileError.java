package com.arekalov.errors;

/**
 * Class for throwing error when file is not found
 */
public class ReadFromFileError extends RuntimeException {
    /**
     * Method to th
     */
    public ReadFromFileError() {

        super("Error while reading the file");
    }
}
