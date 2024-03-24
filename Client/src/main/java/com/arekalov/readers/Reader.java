package com.arekalov.readers;


import com.arekalov.core.IOManager;

/**
 * Interface for reading from console
 */
public interface Reader {
    IOManager iomanager = null;

    /**
     * Method to print error message
     */
    default void errorPrinter() {
        System.out.println("\u001B[31m" + "Error: input incorrect, try again" + "\u001B[0m");
    }
}
