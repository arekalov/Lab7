package com.arekalov.commands;

import arekalov.com.proga5.core.CommandManager;

/**
 * UpdateCommand class is a command that updates the command in the command manager
 */
public class UpdateCommand implements Command{
    private CommandManager manager;
    /**
     * Constructor for UpdateCommand
     * @param manager CommandManager object
     */
    public UpdateCommand(CommandManager manager) {
        this.manager = manager;
    }
    /**
     * Method that updates the command in the command manager
     * @param commandParts String array of command parts
     */
    @Override
    public void execute(String[] commandParts) {
        manager.updateCommand(commandParts);
    }
}
