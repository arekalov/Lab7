//package com.arekalov.commands;
//
//import java.util.HashMap; /**
// * Invoker for pattern Command
// */
//public class CommandInvoker {
//    HashMap<String, Command> commands;
//
//    /**
//     * \
//     * Constructor for CommandInvoker
//     * @param commands - HashMap<String, Command>
//     *
//     */
//    public CommandInvoker(HashMap<String, Command> commands) {
//        this.commands = commands;
//    }
//
//    /**
//     * Execute the command help
//     *
//     * @param commandParts
//     */
//    public void help(String[] commandParts) {
//        commands.get("help").execute(commandParts);
//    }
//
//    /**
//     * Execute the command info
//     *
//     * @param commandParts
//     */
//    public void info(String[] commandParts) {
//        commands.get("info").execute(commandParts);
//    }
//
//    public void show(String[] commandParts) {
//        commands.get("show").execute(commandParts);
//    }
//
//    /**
//     * Execute the command add
//     *
//     * @param commandParts
//     */
//    public void add(String[] commandParts) {
//        commands.get("add").execute(commandParts);
//    }
//
//    /**
//     * Execute the command update
//     *
//     * @param commandParts
//     */
//    public void update(String[] commandParts) {
//        commands.get("update").execute(commandParts);
//    }
//
//    /**
//     * Execute the command remove_by_id
//     *
//     * @param commandParts
//     */
//
//    public void removeById(String[] commandParts) {
//        commands.get("removeById").execute(commandParts);
//    }
//
//    /**
//     * Execute the command clear
//     *
//     * @param commandParts
//     */
//
//    public void clear(String[] commandParts) {
//        commands.get("clear").execute(commandParts);
//    }
//
//    /**
//     * Execute the command save
//     * @param commandParts
//     */
//    public void save(String[] commandParts) {
//        commands.get("save").execute(commandParts);
//    }
//
//    /**
//     * Execute the command execute_script
//     * @param commandParts
//     */
//    public void executeScript(String[] commandParts) {
//        commands.get("executeScript").execute(commandParts);
//    }
//
//    /**
//     * Execute the command exit
//     * @param commandParts
//     */
//    public void exit(String[] commandParts) {
//        commands.get("exit").execute(commandParts);
//    }
//
//    /**
//     * Execute the command remove_first
//     * @param commandParts
//     */
//    public void removeFirst(String[] commandParts) {
//        commands.get("removeFirst").execute(commandParts);
//    }
//
//    /**
//     * Execute the command remove_head
//     * @param commandParts
//     */
//    public void removeHead(String[] commandParts) {
//        commands.get("removeHead").execute(commandParts);
//    }
//
//    /**
//     * Execute the command remove_lower
//     * @param commandParts
//     */
//    public void removeLower(String[] commandParts) {
//        commands.get("removeLower").execute(commandParts);
//    }
//
//    /**
//     * Execute the command count_less_than_price
//     * @param commandParts
//     */
//    public void countLessThenPrice(String[] commandParts) {
//        commands.get("countLessThenPrice").execute(commandParts);
//    }
//
//    /**
//     * Execute the command print_unique_manufacturer
//     * @param commandParts
//     */
//    public void printUniqueManufacture(String[] commandParts) {
//        commands.get("printUniqueManufacture").execute(commandParts);
//    }
//
//    /**
//     * Execute the command print_field_descending_price
//     * @param commandParts
//     */
//    public void printFiledDescendingPrice(String[] commandParts) {
//        commands.get("printFiledDescendingPrice").execute(commandParts);
//    }
//}
