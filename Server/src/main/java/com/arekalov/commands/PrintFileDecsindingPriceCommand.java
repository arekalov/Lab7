package com.arekalov.commands;

import arekalov.com.proga5.core.CommandManager;

/**
 * Class of command PrintFileDecsindingPriceCommand.
 * This command print file in descending order of price.
 */
public class PrintFileDecsindingPriceCommand implements Command{
    private CommandManager manager;
    /**
     * Constructor for class PrintFileDecsindingPriceCommand.
     * @param manager
     */
    public PrintFileDecsindingPriceCommand(CommandManager manager) {
        this.manager = manager;
    }
    /**
     * Method for executing this command
     * @param commandParts
     */
    @Override
    public void execute(String[] commandParts) {
        manager.printFiledDescendingPriceCommand(commandParts);
    }
}
