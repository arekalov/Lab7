package com.arekalov.core;

import com.arekalov.commands.*;
import com.arekalov.entities.CommandWithProduct;
import com.arekalov.entities.Product;
import com.arekalov.errors.EnvNotFoundError;
import com.arekalov.errors.ReadFromFileError;
import com.arekalov.parsing.JsonParser;
import javafx.util.Pair;

import java.util.ArrayDeque;

public class ServerExecutionManager {
    public static final String ENV_NAME = "PROGA";
    private ArrayDeque<Product> arrayDeque;
    private CollectionManager collectionManager;
    private JsonParser parser = new JsonParser();
    private IOManager ioManager = new IOManager(ENV_NAME);
    protected Boolean isRunning = false;
    CommandManager commandManager;
    {
        initFromFile();
        collectionManager = new CollectionManager(arrayDeque);
        commandManager = new CommandManager(ioManager, this, parser, collectionManager);
    }


    public void setRunning(Boolean running) {
        isRunning = running;

    }

    public void executeCommand(CommandWithProduct commandWithProduct){

    }

    public void initFromFile() {
        try {
            String json = ioManager.getJsonFromEnv();
            arrayDeque = parser.jsonToDequeOfProducts(json);
        } catch (EnvNotFoundError envNotFoundError) {
            System.err.println(envNotFoundError.getMessage());
            setRunning(false);
        } catch (ReadFromFileError readFromFileError) {
            System.err.println(readFromFileError.getMessage());
            setRunning(false);
        } catch (Exception exception) {
            System.err.println("Unexpected error! (" + exception + ")");
            setRunning(false);
        }
    }
    private void initCommands() {
        commandManager.initHashMap(
                new Pair<>("help", new HelpCommand(commandManager)),
                new Pair<>("info", new InfoCommand(commandManager)),
                new Pair<>("show", new ShowCommand(commandManager)),
                new Pair<>("add", new AddCommand(commandManager)),
                new Pair<>("update", new UpdateCommand(commandManager)),
                new Pair<>("remove_by_id", new RemoveByIdCommand(commandManager)),
                new Pair<>("clear", new ClearCommand(commandManager)),
                new Pair<>("save", new SaveCommand(commandManager)),
//                new Pair<>("execute_script", new ExecuteScriptCommand(commandManager)),
                new Pair<>("exit", new ExitCommand(commandManager)),
                new Pair<>("remove_first", new RemoveFirstCommand(commandManager)),
                new Pair<>("remove_head", new RemoveHeadCommand(commandManager)),
                new Pair<>("remove_lower", new RemoveLowerCommand(commandManager)),
                new Pair<>("count_less_than_price", new CountLessThenPriceCommand(commandManager)),
                new Pair<>("print_unique_manufacturer", new PrintUniqueManufactureCommand(commandManager)),
                new Pair<>("print_field_descending_price", new PrintFileDecsindingPriceCommand(commandManager))
        );
    }
}
