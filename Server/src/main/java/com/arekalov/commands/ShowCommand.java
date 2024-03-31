package com.arekalov.commands;


import com.arekalov.core.CommandManager;
import com.arekalov.entities.Product;

/**
 * Class of the command to show the collection
 */
public class ShowCommand implements Command{
    CommandManager manager;
    /**
     * Constructor for the command
     * @param manager - the manager of the command
     */
    public ShowCommand(CommandManager manager) {
        this.manager = manager;
    }
    /**
     * Method to execute the command
     * @param commandParts - the parts of the command
     */

    @Override
    public String execute(String[] commandParts, Product product) {
        return manager.showCommand(commandParts);
    }
}
