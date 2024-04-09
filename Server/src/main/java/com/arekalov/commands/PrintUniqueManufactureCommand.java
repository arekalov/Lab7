package com.arekalov.commands;


import com.arekalov.entities.CommandWithProduct;
import com.arekalov.managers.ClientCommandManager;
import com.arekalov.entities.Product;

/**
 * PrintUniqueManufactureCommand class
 * the class that calls the manager to execute the printUniqueManufactureCommand
 */
public class PrintUniqueManufactureCommand implements Command{
    private ClientCommandManager manager;
    /**
     * constructor for the class
     * @param manager the manager that the command will be executed on
     */
    public PrintUniqueManufactureCommand(ClientCommandManager manager) {
        this.manager = manager;
    }

    /**
     * calls the manager to execute the printUniqueManufactureCommand
     * @param commandParts
     */

    @Override
    public String execute(String[] commandParts, CommandWithProduct product) {
        return manager.printUniqueManufactureCommand(commandParts, product);
    }
}
