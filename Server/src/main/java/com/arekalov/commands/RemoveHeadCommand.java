package com.arekalov.commands;


import com.arekalov.entities.CommandWithProduct;
import com.arekalov.managers.ClientCommandManager;
import com.arekalov.entities.Product;

import java.sql.SQLException;

/**
 * class for the remove head command
 */
public class RemoveHeadCommand implements Command {
    private ClientCommandManager manager;

    /**
     * constructor for the remove head command
     *
     * @param manager the manager of the command
     */
    public RemoveHeadCommand(ClientCommandManager manager) {
        this.manager = manager;
    }

    /**
     * execute the remove head command
     *
     * @param commandParts the command in parts
     */

    @Override
    public String execute(String[] commandParts, CommandWithProduct product) throws SQLException {
        return manager.removeHeadCommand(commandParts, product);
    }
}
