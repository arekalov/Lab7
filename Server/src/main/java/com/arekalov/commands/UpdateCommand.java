package com.arekalov.commands;


import com.arekalov.core.CommandManager;
import com.arekalov.entities.Product;

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
    public void execute(String[] commandParts, Product product) {
        manager.updateCommand(commandParts, product);
    }
}
