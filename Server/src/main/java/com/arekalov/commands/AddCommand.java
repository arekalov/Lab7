package com.arekalov.commands;

import arekalov.com.proga5.core.CommandManager;

/**
 * AddCommand class is a command that adds a new command to the command manager.
 */
public class AddCommand implements Command{
    /**
     * The command manager that the command will be added to.
     * @see CommandManager
     */
    private CommandManager manager;

    /**
     * Constructor for the AddCommand class.
     * @param manager
     */
    public AddCommand(CommandManager manager) {
        this.manager = manager;
    }

    /**
     * Adds a new command to the command manager.
     * @param commandParts
     */
    @Override
    public void execute(String[] commandParts) {
        manager.addCommand(commandParts);
    }
}

// Рекурсия
// collection manager
// модульность