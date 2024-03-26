package com.arekalov.commands;

import arekalov.com.proga5.core.CommandManager;

/**
 * Class of the command to show the collection
 */
public class ShowCommand implements Command{
    CommandManager manager;
    /**
     * Constructor for the command
     * @param manager - the manager of the command
     */
    public ShowCommand(CommandManager manager) {
        this.manager = manager;
    }
    /**
     * Method to execute the command
     * @param commandParts - the parts of the command
     */
    @Override
    public void execute(String[] commandParts) {
        manager.showCommand(commandParts);
    }
}
