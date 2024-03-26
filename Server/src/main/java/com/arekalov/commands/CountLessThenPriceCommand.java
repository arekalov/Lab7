package com.arekalov.commands;

import arekalov.com.proga5.core.CommandManager;

/**
 * Class of command CountLessThenPrice.
 * This command counts the number of elements whose price is less than the specified one.
 */
public class CountLessThenPriceCommand implements Command{
    private CommandManager manager;
    /**
     * Constructor for creating a new command.
     * @param manager set manager for command
     */
    public CountLessThenPriceCommand(CommandManager manager) {
        this.manager = manager;
    }
    /**
     * Method for executing this command
     * @param commandParts string array of command parameters
     */
    @Override
    public void execute(String[] commandParts) {
        manager.countLessThenPriceCommand(commandParts);
    }
}
