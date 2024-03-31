package com.arekalov.commands;

import com.arekalov.entities.Product;

/**
 * Interface for pattern Command
 */
public interface Command {
    /**
     * Execute the command
     * @param commandParts
     */
    String execute(String[] commandParts, Product product);
}
