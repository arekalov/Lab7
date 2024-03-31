package com.arekalov.commands;


import com.arekalov.core.CommandManager;
import com.arekalov.entities.Product;

/**
 * Class of command PrintFileDecsindingPriceCommand.
 * implements Command
 */
public class RemoveByIdCommand implements Command{
    private CommandManager manager;
    /**
     * Constructor for creating a new command
     * @param manager set manager for command
     */
    public RemoveByIdCommand(CommandManager manager) {
        this.manager = manager;
    }
    /**
     * Method for executing this command
     * @param commandParts string array of command parameters
     */


    @Override
    public String execute(String[] commandParts, Product product) {
        return manager.removeByIdCommand(commandParts);
    }
}
