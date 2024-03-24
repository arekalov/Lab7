package com.arekalov.readers;

import arekalov.com.proga5.core.IOManager;

/**
 * Interface for reading from console
 */
public interface Reader {
    IOManager iomanager = null;

    /**
     * Method to print error message
     */
    default void errorPrinter() {
        System.err.println("Error: input incorrect, try again");
    }
}
