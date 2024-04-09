package com.arekalov.commands;


import com.arekalov.entities.CommandWithProduct;
import com.arekalov.managers.ClientCommandManager;
import com.arekalov.entities.Product;

/**
 * class for the remove first command
 */
public class RemoveFirstCommand implements Command {
    private ClientCommandManager manager;

    /**
     * constructor for the remove first command
     *
     * @param manager the manager of the command
     */
    public RemoveFirstCommand(ClientCommandManager manager) {
        this.manager = manager;
    }

    /**
     * execute the remove first command
     *
     * @param commandParts the parts of the command
     */
    @Override
    public String execute(String[] commandParts, CommandWithProduct product) {
        return manager.removeFirstCommand(commandParts, product);
    }
}
