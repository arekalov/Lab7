package com.arekalov.commands;


import com.arekalov.managers.ClientCommandManager;
import com.arekalov.entities.Product;

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
    public String execute(String[] commandParts, Product product) {
        return manager.clearCommand(commandParts);
    }
}
