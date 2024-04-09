package com.arekalov.commands;


import com.arekalov.entities.CommandWithProduct;
import com.arekalov.managers.ClientCommandManager;
import com.arekalov.entities.Product;

import java.sql.SQLException;

/**
 * ClearCommand class realizes Command interface and execute method
 * for clearing the collection

 */
public class ClearCommand implements Command{
    /**
     * Manager for command

     */
    private ClientCommandManager manager;

    /**
     * Constructor for set manager field

     * @param manager - manager for command
     */
    public ClearCommand(ClientCommandManager manager) {
        this.manager = manager;
    }

    /**
     * Method for executing command
     * @param commandParts
     */


    @Override
    public String execute(String[] commandParts, CommandWithProduct product) throws SQLException {
        return manager.clearCommand(commandParts, product);
    }
}
