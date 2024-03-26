package com.arekalov.core;

import java.util.ArrayList;
/**
 * Class for storing commands list
 */
public class CommandsInfoArrays {

    public static ArrayList<String> commandsList = new ArrayList<>();
    static {
        commandsList.add("help");
        commandsList.add("info");
        commandsList.add("show");
        commandsList.add("add");
        commandsList.add("update");
        commandsList.add("remove_by_id");
        commandsList.add("clear");
        commandsList.add("clear");
        commandsList.add("execute_script");
        commandsList.add("exit");
        commandsList.add("remove_first");
        commandsList.add("remove_head");
        commandsList.add("remove_lower");
        commandsList.add("count_less_than_price");
        commandsList.add("print_unique_manufacturer");
        commandsList.add("print_field_descending_price");
    }

    public static ArrayList<String> commandListWithoutArgs = new ArrayList<>();

    static {
        commandListWithoutArgs.add("help");
        commandListWithoutArgs.add("info");
        commandListWithoutArgs.add("show");
        commandListWithoutArgs.add("clear");
        commandListWithoutArgs.add("exit");
        commandListWithoutArgs.add("remove_first");
        commandListWithoutArgs.add("remove_head");
        commandListWithoutArgs.add("print_unique_manufacturer");
        commandListWithoutArgs.add("print_field_descending_price");

        commandListWithoutArgs.add("add");
        commandListWithoutArgs.add("remove_lower");
    }

    public static ArrayList<String> commandsListWithArg = new ArrayList<>();
    static {
        commandsListWithArg.add("update");
        commandsListWithArg.add("remove_by_id");
        commandsListWithArg.add("execute_script");
        commandsListWithArg.add("count_less_than_price");
    }
    public static ArrayList<String> commandsWithInputing = new ArrayList<>();
    static {
        commandsWithInputing.add("update");
        commandsWithInputing.add("add");
        commandsWithInputing.add("remove_lower");
    }
}
