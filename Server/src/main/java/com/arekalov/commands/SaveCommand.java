package com.arekalov.commands;

import arekalov.com.proga5.core.CommandManager;

/**
 * SaveCommand class is a command to save the current state of the system.
 */
public class SaveCommand implements Command{
    private CommandManager manager;
    /**
     * Constructor for SaveCommand.
     * @param manager The command manager that handles this command.
     */
    public SaveCommand(CommandManager manager) {
        this.manager = manager;;
    }
    /**
     * Executes the save command.
     * @param commandParts The parts of the command.
     */
    @Override
    public void execute(String[] commandParts) {
        manager.saveCommand(commandParts);
    }
}
