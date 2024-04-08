package com.arekalov.commands;


import com.arekalov.managers.ClientCommandManager;
import com.arekalov.entities.Product;

/**
 * Class of command Help.
 * This command shows all available commands.
 */
public class HelpCommand implements Command{
    private final ClientCommandManager commandManager;

    /**
     * Constructor for class HelpCommand.
     * @param commandManager
     */
    public HelpCommand(ClientCommandManager commandManager) {
        this.commandManager = commandManager;
    }
    /**
     * Method for executing this command.
     * @param commandParts
     */
    @Override
    public String execute(String[] commandParts, Product product) {
        return commandManager.helpCommand(commandParts);
    }
}
