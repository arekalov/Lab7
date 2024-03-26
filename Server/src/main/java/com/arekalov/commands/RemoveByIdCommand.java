package com.arekalov.commands;

import arekalov.com.proga5.core.CommandManager;

/**
 * Class of command PrintFileDecsindingPriceCommand.
 * implements Command
 */
public class RemoveByIdCommand implements Command{
    private CommandManager manager;
    /**
     * Constructor for creating a new command
     * @param manager set manager for command
     */
    public RemoveByIdCommand(CommandManager manager) {
        this.manager = manager;
    }
    /**
     * Method for executing this command
     * @param commandParts string array of command parameters
     */
    @Override
    public void execute(String[] commandParts) {
        manager.removeByIdCommand(commandParts);
    }
}
