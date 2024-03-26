package com.arekalov.commands;


import com.arekalov.core.CommandManager;
import com.arekalov.entities.Product;

/**
 * SaveCommand class is a command to save the current state of the system.
 */
public class SaveCommand implements Command {
    private CommandManager manager;

    /**
     * Constructor for SaveCommand.
     *
     * @param manager The command manager that handles this command.
     */
    public SaveCommand(CommandManager manager) {
        this.manager = manager;
        ;
    }

    /**
     * Executes the save command.
     *
     * @param commandParts The parts of the command.
     */

    @Override
    public void execute(String[] commandParts, Product product) {
        manager.saveCommand(commandParts);
    }
}
