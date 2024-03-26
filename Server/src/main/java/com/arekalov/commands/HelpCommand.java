package com.arekalov.commands;


import com.arekalov.core.CommandManager;
import com.arekalov.entities.Product;

/**
 * Class of command Help.
 * This command shows all available commands.
 */
public class HelpCommand implements Command{
    private final CommandManager commandManager;

    /**
     * Constructor for class HelpCommand.
     * @param commandManager
     */
    public HelpCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    /**
     * Method for executing this command.
     * @param commandParts
     */
    @Override
    public void execute(String[] commandParts, Product product) {
        commandManager.helpCommand(commandParts);
    }
}
