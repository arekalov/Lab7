package com.arekalov.commands;


import com.arekalov.core.CommandManager;
import com.arekalov.entities.Product;

/**
 * Class of command RemoveLower.
 * This command removes all elements from the collection that are less than the given one.
 */
public class RemoveLowerCommand implements Command{
    private CommandManager manager;

    /**
     * Constructor for class RemoveLowerCommand.
     * @param manager
     */
    public RemoveLowerCommand(CommandManager manager) {
        this.manager = manager;
    }
    /**
     * Method for executing this command.
     * @param commandParts
     */


    @Override
    public void execute(String[] commandParts, Product product) {
        manager.removeLowerCommand(commandParts, product);
    }
}
