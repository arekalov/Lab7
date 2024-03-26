package com.arekalov.commands;

import arekalov.com.proga5.core.CommandManager;

/**
 * PrintUniqueManufactureCommand class
 * the class that calls the manager to execute the printUniqueManufactureCommand
 */
public class PrintUniqueManufactureCommand implements Command{
    private CommandManager manager;
    /**
     * constructor for the class
     * @param manager the manager that the command will be executed on
     */
    public PrintUniqueManufactureCommand(CommandManager manager) {
        this.manager = manager;
    }

    /**
     * calls the manager to execute the printUniqueManufactureCommand
     * @param commandParts
     */
    @Override
    public void execute(String[] commandParts) {
        manager.printUniqueManufactureCommand(commandParts);
    }
}
