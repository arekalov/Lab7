package com.arekalov.commands;


import com.arekalov.entities.CommandWithProduct;
import com.arekalov.managers.ClientCommandManager;
import com.arekalov.entities.Product;

/**
 * Class of the command to show the collection
 */
public class ShowCommand implements Command{
    ClientCommandManager manager;
    /**
     * Constructor for the command
     * @param manager - the manager of the command
     */
    public ShowCommand(ClientCommandManager manager) {
        this.manager = manager;
    }
    /**
     * Method to execute the command
     * @param commandParts - the parts of the command
     */

    @Override
    public String execute(String[] commandParts, CommandWithProduct product) {
        return manager.showCommand(commandParts);
    }
}
