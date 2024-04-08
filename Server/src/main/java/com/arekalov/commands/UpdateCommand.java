package com.arekalov.commands;


import com.arekalov.managers.ClientCommandManager;
import com.arekalov.entities.Product;

/**
 * UpdateCommand class is a command that updates the command in the command manager
 */
public class UpdateCommand implements Command{
    private ClientCommandManager manager;
    /**
     * Constructor for UpdateCommand
     * @param manager CommandManager object
     */
    public UpdateCommand(ClientCommandManager manager) {
        this.manager = manager;
    }
    /**
     * Method that updates the command in the command manager
     * @param commandParts String array of command parts
     */
    @Override
    public String execute(String[] commandParts, Product product) {
        return manager.updateCommand(commandParts, product);
    }
}
