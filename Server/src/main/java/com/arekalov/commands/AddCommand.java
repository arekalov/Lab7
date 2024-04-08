package com.arekalov.commands;


import com.arekalov.managers.ClientCommandManager;
import com.arekalov.entities.Product;

import java.sql.SQLException;

/**
 * AddCommand class is a command that adds a new command to the command manager.
 */
public class AddCommand implements Command {
    /**
     * The command manager that the command will be added to.
     *
     * @see ClientCommandManager
     */
    private ClientCommandManager manager;

    /**
     * Constructor for the AddCommand class.
     *
     * @param manager
     */
    public AddCommand(ClientCommandManager manager) {
        this.manager = manager;
    }

    /**
     * Adds a new command to the command manager.
     *
     * @param commandParts
     */

    @Override
    public String execute(String[] commandParts, Product product) throws SQLException {
        return  manager.addCommand(commandParts, product);
    }
}
