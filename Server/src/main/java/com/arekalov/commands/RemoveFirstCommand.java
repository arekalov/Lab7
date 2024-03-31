package com.arekalov.commands;


import com.arekalov.core.CommandManager;
import com.arekalov.entities.Product;

/**
 * class for the remove first command
 */
public class RemoveFirstCommand implements Command {
    private CommandManager manager;

    /**
     * constructor for the remove first command
     *
     * @param manager the manager of the command
     */
    public RemoveFirstCommand(CommandManager manager) {
        this.manager = manager;
    }

    /**
     * execute the remove first command
     *
     * @param commandParts the parts of the command
     */
    @Override
    public String execute(String[] commandParts, Product product) {
        return manager.removeFirstCommand(commandParts);
    }
}
