package com.arekalov.commands;


import com.arekalov.entities.CommandWithProduct;
import com.arekalov.managers.ClientCommandManager;
import com.arekalov.entities.Product;

import java.sql.SQLException;

/**
 * Class of command PrintFileDecsindingPriceCommand.
 * implements Command
 */
public class RemoveByIdCommand implements Command{
    private ClientCommandManager manager;
    /**
     * Constructor for creating a new command
     * @param manager set manager for command
     */
    public RemoveByIdCommand(ClientCommandManager manager) {
        this.manager = manager;
    }
    /**
     * Method for executing this command
     * @param commandParts string array of command parameters
     */


    @Override
    public String execute(String[] commandParts, CommandWithProduct product) throws SQLException {
        return manager.removeByIdCommand(commandParts, product);
    }
}
