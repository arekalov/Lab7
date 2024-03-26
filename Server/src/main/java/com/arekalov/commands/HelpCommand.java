package com.arekalov.commands;

import arekalov.com.proga5.core.CommandManager;

/**
 * Class of command Help.
 * This command shows all available commands.
 */
public class HelpCommand implements Command{
    private final CommandManager commandManager;

    /**
     * Constructor for class HelpCommand.
     * @param commandManager
     */
    public HelpCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    /**
     * Method for executing this command.
     * @param commandParts
     */
    @Override
    public void execute(String[] commandParts) {
        commandManager.helpCommand(commandParts);
    }
}
