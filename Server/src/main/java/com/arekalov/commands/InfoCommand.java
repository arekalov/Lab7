package com.arekalov.commands;


import com.arekalov.managers.ClientCommandManager;
import com.arekalov.entities.Product;

/**
 * Class of command Info.
 * This command shows information about the collection.
 * implements Command
 */
public class InfoCommand implements Command {
    private final ClientCommandManager commandManager;

    /**
     * Constructor for this class
     *
     * @param commandManager - for this class
     */
    public InfoCommand(ClientCommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Method for executing this command
     *
     * @param commandParts - command
     */

    @Override
    public String execute(String[] commandParts, Product product) {
        return commandManager.infoCommand(commandParts);
    }
}
