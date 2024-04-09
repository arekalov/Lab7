package com.arekalov.commands;


import com.arekalov.entities.CommandWithProduct;
import com.arekalov.managers.ClientCommandManager;
import com.arekalov.entities.Product;

/**
 * Class of command Exit.
 */
public class ExitCommand implements Command{
    private ClientCommandManager manager;

    /**
     * Constructor for class ExitCommand.
     * @param manager
     */
    public ExitCommand(ClientCommandManager manager) {
        this.manager = manager;
    }
    /**
     * Method for executing this command.
     * @param commandParts
     */


    @Override
    public String execute(String[] commandParts, CommandWithProduct product) {
        return manager.exitCommand(commandParts);
    }
}
