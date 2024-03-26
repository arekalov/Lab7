package com.arekalov.commands;

/**
 * Interface for pattern Command
 */
public interface Command {
    /**
     * Execute the command
     * @param commandParts
     */
    void execute(String[] commandParts);
}
