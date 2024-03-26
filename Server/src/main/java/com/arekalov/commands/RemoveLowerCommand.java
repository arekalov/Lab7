package com.arekalov.commands;

import arekalov.com.proga5.core.CommandManager;

/**
 * Class of command RemoveLower.
 * This command removes all elements from the collection that are less than the given one.
 */
public class RemoveLowerCommand implements Command{
    private CommandManager manager;

    /**
     * Constructor for class RemoveLowerCommand.
     * @param manager
     */
    public RemoveLowerCommand(CommandManager manager) {
        this.manager = manager;
    }
    /**
     * Method for executing this command.
     * @param commandParts
     */
    @Override
    public void execute(String[] commandParts) {
        manager.removeLowerCommand(commandParts);
    }
}
