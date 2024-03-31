package com.arekalov.commands;


import com.arekalov.core.CommandManager;
import com.arekalov.entities.Product;

/**
 * PrintUniqueManufactureCommand class
 * the class that calls the manager to execute the printUniqueManufactureCommand
 */
public class PrintUniqueManufactureCommand implements Command{
    private CommandManager manager;
    /**
     * constructor for the class
     * @param manager the manager that the command will be executed on
     */
    public PrintUniqueManufactureCommand(CommandManager manager) {
        this.manager = manager;
    }

    /**
     * calls the manager to execute the printUniqueManufactureCommand
     * @param commandParts
     */

    @Override
    public String execute(String[] commandParts, Product product) {
        return manager.printUniqueManufactureCommand(commandParts);
    }
}
