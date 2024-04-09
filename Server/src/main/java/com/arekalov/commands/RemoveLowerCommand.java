package com.arekalov.commands;


import com.arekalov.entities.CommandWithProduct;
import com.arekalov.managers.ClientCommandManager;
import com.arekalov.entities.Product;

import java.sql.SQLException;

/**
 * Class of command RemoveLower.
 * This command removes all elements from the collection that are less than the given one.
 */
public class RemoveLowerCommand implements Command{
    private ClientCommandManager manager;

    /**
     * Constructor for class RemoveLowerCommand.
     * @param manager
     */
    public RemoveLowerCommand(ClientCommandManager manager) {
        this.manager = manager;
    }
    /**
     * Method for executing this command.
     * @param commandParts
     */


    @Override
    public String execute(String[] commandParts, CommandWithProduct product) throws SQLException {
        return manager.removeLowerCommand(commandParts, product);
    }
}
