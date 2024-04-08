package com.arekalov.commands;


import com.arekalov.managers.ClientCommandManager;
import com.arekalov.entities.Product;

/**
 * SaveCommand class is a command to save the current state of the system.
 */
public class SaveCommand implements Command {
    private ClientCommandManager manager;

    /**
     * Constructor for SaveCommand.
     *
     * @param manager The command manager that handles this command.
     */
    public SaveCommand(ClientCommandManager manager) {
        this.manager = manager;
        ;
    }

    /**
     * Executes the save command.
     *
     * @param commandParts The parts of the command.
     */

    @Override
    public String execute(String[] commandParts, Product product) {
        return manager.saveCommand(commandParts);
    }
}
