package com.arekalov.commands;


import com.arekalov.core.CommandManager;
import com.arekalov.entities.Product;

/**
 * Class of command Exit.
 */
public class ExitCommand implements Command{
    private CommandManager manager;

    /**
     * Constructor for class ExitCommand.
     * @param manager
     */
    public ExitCommand(CommandManager manager) {
        this.manager = manager;
    }
    /**
     * Method for executing this command.
     * @param commandParts
     */


    @Override
    public String execute(String[] commandParts, Product product) {
        return manager.exitCommand(commandParts);
    }
}
