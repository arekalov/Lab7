package com.arekalov.managers;


import com.arekalov.commands.Command;
import com.arekalov.commands.Validators;
import com.arekalov.core.IOManager;
import com.arekalov.entities.Product;
import com.arekalov.errors.ArgumentError;
import com.arekalov.errors.EmptyDequeError;
import com.arekalov.errors.EnvNotFoundError;
import com.arekalov.errors.ReadFromFileError;
import com.arekalov.parsing.JsonParser;
import javafx.util.Pair;
//import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is responsible for executing commands
 * There are methods for executing commands and working with them
 */
public class ClientCommandManager {

    private ServerExecutionManager serverExecutionManager;
    private IOManager ioManager;
    private JsonParser parser;
    private HashMap<String, Command> hashMapCommands;
    private CollectionManager collectionManager;

    /**
     * Constructor for CommandManager
     *
     * @param ioManager
     * @param runner
     * @param parser
     */
    public ClientCommandManager(IOManager ioManager, ServerExecutionManager runner, JsonParser parser, CollectionManager collectionManager) {
        this.serverExecutionManager = runner;
        this.ioManager = ioManager;
        this.parser = parser;
        this.hashMapCommands = new HashMap<>();
        this.collectionManager = collectionManager;
    }

    public HashMap<String, Command> getHashMapCommands() {
        return hashMapCommands;
    }

    /**
     * This method prints the field in descending order
     *
     * @param commandParts - command parts
     */
    public String printFiledDescendingPriceCommand(String[] commandParts) {
        String stream = collectionManager.getArrayDeque().stream().sorted(Comparator.reverseOrder())
                .map(Product::toString).collect(Collectors.joining("\n"));
        return stream;

    }

    /**
     * This method prints unique manufacturers
     *
     * @param commandParts - command parts
     */
    public String printUniqueManufactureCommand(String[] commandParts) {
        String stream = collectionManager.getArrayDeque().stream().map(x -> x.getManufacturer().getName())
                .map(Object::toString).distinct().collect(Collectors.joining(" "));
        return "Unique manufacturers: " + stream;

    }

    /**
     * This method counts the number of products with a price less than the specified one
     *
     * @param commandParts - command parts
     */
    public String countLessThenPriceCommand(String[] commandParts) {
        Long price = Long.valueOf(commandParts[1]);
        Long stream = collectionManager.getArrayDeque().stream().filter(x -> x.getPrice() < price).count();
        return "Count: " + stream;

    }

    /**
     * This method removes products with a price less than the specified one
     *
     * @param commandParts - command parts
     */
//    todo 1
    public String removeLowerCommand(String[] commandParts, Product product) {
        collectionManager.removeLower(product);
        return "Success";
    }

    /**
     * This method removes the first product
     *
     * @param commandParts - command parts
     */
//    todo 2
    public String removeHeadCommand(String[] commandParts) {
        if (!collectionManager.isEmpty()) {
            return collectionManager.removeHead() + "\n" + "Successfully deleted";
        } else throw new EmptyDequeError();
    }

    /**
     * This method removes the first product
     *
     * @param commandParts - command parts
     */
//    todo 3
    public String removeFirstCommand(String[] commandParts) {

        if (!collectionManager.isEmpty()) {
            collectionManager.removeHead();
            return "Success";
        } else {
            throw new EmptyDequeError();
        }
    }

    /**
     * This method exits the program
     *
     * @param commandParts - command parts
     */
    public String exitCommand(String[] commandParts) {
        return "Bye";
    }



    /**
     * This method clears the collection
     *
     * @param commandParts - command parts
     */
//    todo 3
    public String clearCommand(String[] commandParts) {
        collectionManager.clear();
        return ("Successfully cleared");

    }

    /**
     * This method removes the product by id, in this method we can throw an exception if the product is not found
     *
     * @param commandParts - command parts
     */
//    todo 4
    public String removeByIdCommand(String[] commandParts) {
        Product toRemove = null;
        Long id = Validators.checkLong(commandParts[1]);
        for (Product i : collectionManager.getArrayDeque()) {
            if (i.getId().equals(id)) {
                toRemove = i;
            }
        }
        if (toRemove != null) {
            collectionManager.remove(toRemove);
            return "Success";
        } else {
            throw new ArgumentError("Error: No product with id " + id);
        }
    }

    /**
     * This method updates the product by id, in this method we can throw an exception if the product is not found
     *
     * @param commandParts - command parts
     */
//    todo 5
    public String updateCommand(String[] commandParts, Product product) {

        Long id = Validators.checkLong(commandParts[1]);
        Product changeProduct = product;
        for (Product i : collectionManager.getArrayDeque()) {
            if (Objects.equals(i.getId(), id)) {
                changeProduct = i;
            }
        }
        changeProduct.setName(product.getName());
        changeProduct.setCoordinates(product.getCoordinates());
        changeProduct.setCreationDate(product.getCreationDate());
        changeProduct.setPrice(product.getPrice());
        changeProduct.setPartNumber(product.getPartNumber());
        changeProduct.setManufactureCost(product.getManufactureCost());
        changeProduct.setUnitOfMeasure(product.getUnitOfMeasure());
        changeProduct.setManufacturer(product.getManufacturer());
        return "Success";
    }

    /**
     * This method adds a product to the collection, it calls the method from the ProductReader class to input the product
     *
     * @param commandParts - command parts
     */
    public String addCommand(String[] commandParts, Product product) throws SQLException {
        Long id = (long) serverExecutionManager.dbCommandManager.insertProduct(product);
        product.setId(id);
        collectionManager.add(product);
        return "Success";
    }

    /**
     * This method prints the collection
     *
     * @param commandParts - command parts
     */
    public String showCommand(String[] commandParts) {
        if (collectionManager.isEmpty()) {
            return "Empty";
        } else {
            String res = "";
            for (Product i : collectionManager.getArrayDeque()) {
                res += i + "\n\n";
            }
            return res;
        }
    }

    /**
     * This method prints the information about the collection
     *
     * @param commandParts - command parts
     */
    public String infoCommand(String[] commandParts) {
        return collectionManager.getArrayDeque().getClass() + "\n" + LocalDate.now() + "\n" + collectionManager.getArrayDeque().size();
    }


    public String helpCommand(String[] commandsParts) {
        return """
                help : вывести справку по доступным командам
                info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                add {element} : добавить новый элемент в коллекцию
                update id {element} : обновить значение элемента коллекции, id которого равен заданному
                remove_by_id id : удалить элемент из коллекции по его id
                clear : очистить коллекцию
                save : сохранить коллекцию в файл
                execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                exit : завершить программу (без сохранения в файл)
                remove_first : удалить первый элемент из коллекции
                remove_head : вывести первый элемент коллекции и удалить его
                remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный
                count_less_than_price price : вывести количество элементов, значение поля price которых меньше заданного
                print_unique_manufacturer : вывести уникальные значения поля manufacturer всех элементов в коллекции
                print_field_descending_price : вывести значения поля price всех элементов в порядке убывания""";

    }

    /**
     * This method initializes the collection from a file, it creates a new collection from the file and catches exceptions like file not found or incorrect json or permission denied
     */
    public HashMap<String, Command> initHashMap(Pair<String, Command>... commands) {
        for (Pair<String, Command> command : commands) {
            hashMapCommands.put(command.getKey(), command.getValue());
        }
        return hashMapCommands;
    }
}
