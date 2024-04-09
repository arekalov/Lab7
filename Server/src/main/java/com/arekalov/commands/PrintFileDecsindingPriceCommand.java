package com.arekalov.commands;


import com.arekalov.entities.CommandWithProduct;
import com.arekalov.managers.ClientCommandManager;
import com.arekalov.entities.Product;

/**
 * Class of command PrintFileDecsindingPriceCommand.
 * This command print file in descending order of price.
 */
public class PrintFileDecsindingPriceCommand implements Command {
    private ClientCommandManager manager;

    /**
     * Constructor for class PrintFileDecsindingPriceCommand.
     *
     * @param manager
     */
    public PrintFileDecsindingPriceCommand(ClientCommandManager manager) {
        this.manager = manager;
    }

    /**
     * Method for executing this command
     *
     * @param commandParts
     */


    @Override
    public String execute(String[] commandParts, CommandWithProduct product) {
        return manager.printFiledDescendingPriceCommand(commandParts, product);
    }
}
