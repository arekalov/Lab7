package com.arekalov.commands;


import com.arekalov.managers.ClientCommandManager;
import com.arekalov.entities.Product;

/**
 * Class of command CountLessThenPrice.
 * This command counts the number of elements whose price is less than the specified one.
 */
public class CountLessThenPriceCommand implements Command{
    private ClientCommandManager manager;
    /**
     * Constructor for creating a new command.
     * @param manager set manager for command
     */
    public CountLessThenPriceCommand(ClientCommandManager manager) {
        this.manager = manager;
    }
    /**
     * Method for executing this command
     * @param commandParts string array of command parameters
     */

    @Override
    public String execute(String[] commandParts, Product product) {
        return manager.countLessThenPriceCommand(commandParts);
    }
}
