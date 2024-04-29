package com.arekalov.commands;


import com.arekalov.entities.CommandWithProduct;
import com.arekalov.managers.ClientCommandManager;

/**
 * Class of command Info.
 * This command shows information about the collection.
 * implements Command
 */
public class CheckUpdateCommand implements Command {
    private final ClientCommandManager commandManager;

    /**
     * Constructor for this class
     *
     * @param commandManager - for this class
     */
    public CheckUpdateCommand(ClientCommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Method for executing this command
     *
     * @param commandParts - command
     */

    @Override
    public String execute(String[] commandParts, CommandWithProduct product) {
        return commandManager.checkupdate(commandParts, product);
    }
}
