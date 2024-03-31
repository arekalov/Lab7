package com.arekalov.commands;


import com.arekalov.core.CommandManager;
import com.arekalov.entities.Product;

/**
 * Class of command PrintFileDecsindingPriceCommand.
 * This command print file in descending order of price.
 */
public class PrintFileDecsindingPriceCommand implements Command {
    private CommandManager manager;

    /**
     * Constructor for class PrintFileDecsindingPriceCommand.
     *
     * @param manager
     */
    public PrintFileDecsindingPriceCommand(CommandManager manager) {
        this.manager = manager;
    }

    /**
     * Method for executing this command
     *
     * @param commandParts
     */


    @Override
    public String execute(String[] commandParts, Product product) {
        return manager.printFiledDescendingPriceCommand(commandParts);
    }
}
