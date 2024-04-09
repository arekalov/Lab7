package com.arekalov.commands;

import com.arekalov.entities.CommandWithProduct;
import com.arekalov.entities.Product;

import java.sql.SQLException;

/**
 * Interface for pattern Command
 */
public interface Command {
    /**
     * Execute the command
     * @param commandParts
     */
    String execute(String[] commandParts, CommandWithProduct commandWithProduct) throws SQLException;
}
