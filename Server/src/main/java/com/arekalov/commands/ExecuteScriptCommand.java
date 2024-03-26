//package com.arekalov.commands;
//
//
//
//import java.util.HashMap;
//
///**
// * Class of command Exit.
// * This command executes script from file.
// */
//public class ExecuteScriptCommand implements Command{
//    private CommandManager manager;
//    private HashMap<String, Integer> files = new HashMap<>();
//    /**
//     * Constructor for this command.
//     * @param manager - command manager.
//     */
//    public ExecuteScriptCommand(CommandManager manager) {
//        this.manager = manager;
//    }
//    /**
//     * Method for executing this command.
//     * @param commandParts - command parts.
//     */
//    @Override
//    public void execute(String[] commandParts) {
//        if (files.containsKey(commandParts[1])){
//            files.replace(commandParts[1], files.get(commandParts[1]) + 1);
//        }
//        else {
//            files.put(commandParts[1], 1);
//        }
//        manager.executeScriptCommand(commandParts, files);
//    }
//
//    /**
//     * Method to reset files.
//     */
//    public void resetFiles(){
//        files.clear();
//    }
//}
//
//
