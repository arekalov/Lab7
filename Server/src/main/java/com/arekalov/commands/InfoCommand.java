package com.arekalov.commands;

import arekalov.com.proga5.core.CommandManager;

/**
 * Class of command Info.
 * This command shows information about the collection.
 * implements Command

 */
public class InfoCommand implements Command{
    private final CommandManager commandManager;

    /**
     * Constructor for this class
     * @param commandManager - for this class
     */
    public InfoCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    /**
     * Method for executing this command
     * @param commandParts - command
     */
    @Override
    public void execute(String[] commandParts) {
        commandManager.infoCommand(commandParts);
    }
}
