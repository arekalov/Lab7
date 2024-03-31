package com.arekalov.commands;


import com.arekalov.core.CommandManager;
import com.arekalov.entities.Product;

/**
 * class for the remove head command
 */
public class RemoveHeadCommand implements Command {
    private CommandManager manager;

    /**
     * constructor for the remove head command
     *
     * @param manager the manager of the command
     */
    public RemoveHeadCommand(CommandManager manager) {
        this.manager = manager;
    }

    /**
     * execute the remove head command
     *
     * @param commandParts the command in parts
     */

    @Override
    public String execute(String[] commandParts, Product product) {
        return manager.removeHeadCommand(commandParts);
    }
}
