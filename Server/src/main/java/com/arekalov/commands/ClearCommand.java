package com.arekalov.commands;

import arekalov.com.proga5.core.CommandManager;

/**
 * ClearCommand class realizes Command interface and execute method
 * for clearing the collection

 */
public class ClearCommand implements Command{
    /**
     * Manager for command

     */
    private CommandManager manager;

    /**
     * Constructor for set manager field

     * @param manager - manager for command
     */
    public ClearCommand(CommandManager manager) {
        this.manager = manager;
    }

    /**
     * Method for executing command
     * @param commandParts
     */
    @Override
    public void execute(String[] commandParts) {
        manager.clearCommand(commandParts);
    }
}
