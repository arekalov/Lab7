package com.arekalov.commands;

import arekalov.com.proga5.core.CommandManager;

/**
 * class for the remove head command
 */
public class RemoveHeadCommand implements Command{
    private CommandManager manager;
    /**
     * constructor for the remove head command
     * @param manager the manager of the command
     */
    public RemoveHeadCommand(CommandManager manager) {
        this.manager = manager;
    }
    /**
     * execute the remove head command
     * @param commandParts the command in parts
     */
    @Override
    public void execute(String[] commandParts) {
        manager.removeHeadCommand(commandParts);
    }
}
