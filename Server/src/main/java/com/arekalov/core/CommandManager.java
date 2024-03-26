package com.arekalov.core;


import com.arekalov.commands.Command;
import com.arekalov.commands.Validators;
import com.arekalov.entities.Product;
import com.arekalov.errors.ArgumentError;
import com.arekalov.errors.EmptyDequeError;
import com.arekalov.errors.EnvNotFoundError;
import com.arekalov.errors.ReadFromFileError;
import com.arekalov.parsing.JsonParser;
import javafx.util.Pair;
//import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * This class is responsible for executing commands
 * There are methods for executing commands and working with them
 */
public class CommandManager {

    private ServerExecutionManager runner;
    private IOManager ioManager;
    private JsonParser parser;
    private HashMap<String, Command> hashMapCommands;
    private CollectionManager collectionManager;
    private HashSet<String> executableFiles;
    private ObjectOutputStream out;

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    /**
     * Constructor for CommandManager
     *
     * @param ioManager
     * @param runner
     * @param parser
     */
    public CommandManager(IOManager ioManager, ServerExecutionManager runner, JsonParser parser, CollectionManager collectionManager) {
        this.runner = runner;
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
    public void printFiledDescendingPriceCommand(String[] commandParts) {

        ArrayList<Product> arrayList = new ArrayList<>(collectionManager.getArrayDeque());
        arrayList.sort(Collections.reverseOrder(new ProductComparator()));
        String res = "";
        for (Product i : arrayList) {
            res += i + "\n";
        }
        try {
            out.writeObject(res);
            out.flush();
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }

    /**
     * This method prints unique manufacturers
     *
     * @param commandParts - command parts
     */
    public void printUniqueManufactureCommand(String[] commandParts) {
        HashSet<Integer> set = new HashSet<>();
        for (Product i : collectionManager.getArrayDeque()) {
            set.add(i.getManufactureCost());
        }
        StringBuilder unique = new StringBuilder();
        for (Integer i : set) {
            unique.append(i.toString()).append(" ");
        }
        try {
            out.writeObject("Unique manufacturer: " + unique);
            out.flush();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    /**
     * This method counts the number of products with a price less than the specified one
     *
     * @param commandParts - command parts
     */
    public void countLessThenPriceCommand(String[] commandParts) {
        Long price = Long.valueOf(commandParts[1]);
        int count = 0;
        for (Product i : collectionManager.getArrayDeque()) {
            if (i.getPrice() < price) {
                count++;
            }
        }
        try {
            out.writeObject("Count: " + count);
            out.flush();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    /**
     * This method removes products with a price less than the specified one
     *
     * @param commandParts - command parts
     */
    public void removeLowerCommand(String[] commandParts, Product product) {
        collectionManager.removeLower(product);
        try {
            out.writeObject("Success");
            out.flush();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        saveCommand(commandParts);
    }

    /**
     * This method removes the first product
     *
     * @param commandParts - command parts
     */
    public void removeHeadCommand(String[] commandParts) {
        if (!collectionManager.isEmpty()) {
            try {
                out.writeObject(collectionManager.removeHead() + "\n" + "Successfully deleted");
                out.flush();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        } else throw new EmptyDequeError();
        saveCommand(commandParts);
    }

    /**
     * This method removes the first product
     *
     * @param commandParts - command parts
     */
    public void removeFirstCommand(String[] commandParts) {

        if (!collectionManager.isEmpty()) {
            collectionManager.removeHead();
            try {
                out.writeObject("Successfully deleted first element");
                out.flush();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        } else {
            throw new EmptyDequeError();
        }
        saveCommand(commandParts);
    }

    /**
     * This method exits the program
     *
     * @param commandParts - command parts
     */
    public void exitCommand(String[] commandParts) {
        try {
            out.writeObject("Bye");
            out.flush();
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }

    /**
     * This method executes the script, during the script execution, we change the scanner from the console to the file
     * It help us to read commands from the file and check recursion
     *
     * @param commandParts - command parts
     */
//    public void executeScriptCommand(String[] commandParts, HashMap<String, Integer> files){
//        try {
//            File inputFile = new File(commandParts[1]);
//            Scanner scanner = new Scanner(inputFile);
//            ioManager.setScanner(scanner);
//            if (true) {
//                System.out.println("Start executing script");
//                do {
//                    String line = scanner.nextLine();
//                    System.out.println("__");
//                    System.out.println(line);
//                    if (files.get(commandParts[1]) > 1) {
//                        throw new RecursionError();
//                    }
//                    else {
//                        runner.executeCommand(line);
//                    }
//                } while (scanner.hasNextLine());
//                ioManager.setScanner(new Scanner(System.in));
//                System.out.println("End executing script");
//            } else throw new RecursionError();
//        } catch (FileNotFoundException e) {
//            System.err.println("Error: can't read this file");
//        } catch (RecursionError recursionError) {
//            System.err.println(recursionError.getMessage());
//            runner.setRunning(false);
//        }


    /**
     * This method saves the collection to a file, it can throw an exception if the file is not found
     *
     * @param commandParts - command parts
     */
    public void saveCommand(String[] commandParts) {

        try {
            String json = parser.dequeOfProductsToJson(collectionManager.getArrayDeque());
            File file = ioManager.getFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter output = new OutputStreamWriter(fileOutputStream);
            output.write(json);
            output.close();
            System.out.println("Success");
        } catch (EnvNotFoundError envNotFoundError) {
            System.out.println(envNotFoundError.getMessage());
        } catch (ReadFromFileError readFromFileError) {
            System.out.println(readFromFileError.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
        } catch (Exception exception) {
            System.out.println("Unexpected error! (" + exception + ")");
        }
    }

    /**
     * This method clears the collection
     *
     * @param commandParts - command parts
     */
    public void clearCommand(String[] commandParts) {

        collectionManager.clear();
        try {
            out.writeObject("Successfully cleared");
            out.flush();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        saveCommand(commandParts);
    }

    /**
     * This method removes the product by id, in this method we can throw an exception if the product is not found
     *
     * @param commandParts - command parts
     */
    public void removeByIdCommand(String[] commandParts) {

        Product toRemove = null;
        Long id = Validators.checkLong(commandParts[1]);
        for (Product i : collectionManager.getArrayDeque()) {
            if (i.getId().equals(id)) {
                toRemove = i;
            }
        }
        if (toRemove != null) {
            collectionManager.remove(toRemove);
            try {
                out.writeObject("Successfully deleted");
                out.flush();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        } else {
            throw new ArgumentError("Error: No product with id " + id);
        }
        saveCommand(commandParts);
    }

    /**
     * This method updates the product by id, in this method we can throw an exception if the product is not found
     *
     * @param commandParts - command parts
     */
    public void updateCommand(String[] commandParts, Product product) {

        Long id = Validators.checkLong(commandParts[1]);
        System.out.println("get id " + id);
        Product changeProduct = product;
        for (Product i : collectionManager.getArrayDeque()) {
            if (Objects.equals(i.getId(), id)) {
                System.out.println("Changed product id " + i);
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
        try {
            out.writeObject("Success");
            out.flush();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        saveCommand(commandParts);
    }

    /**
     * This method adds a product to the collection, it calls the method from the ProductReader class to input the product
     *
     * @param commandParts - command parts
     */
    public void addCommand(String[] commandParts, Product product) {
        collectionManager.add(product);
        try {
            out.writeObject("Success");
            out.flush();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        saveCommand(commandParts);
    }

    /**
     * This method prints the collection
     *
     * @param commandParts - command parts
     */
    public void showCommand(String[] commandParts) {
        try {
            if (collectionManager.isEmpty()) {
                out.writeObject("Empty");
                out.flush();
            } else {
                String res = "";
                for (Product i : collectionManager.getArrayDeque()) {
                    res += i + "\n\n";
                }
                out.writeObject(res);
                out.flush();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    /**
     * This method prints the information about the collection
     *
     * @param commandParts - command parts
     */
    public void infoCommand(String[] commandParts) {
        try {
            out.writeObject(collectionManager.getArrayDeque().getClass() + "\n" + LocalDate.now() + "\n" + collectionManager.getArrayDeque().size());
            out.flush();
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }


    public void helpCommand(String[] commandsParts) {
        try {
            out.writeObject("""
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
                    print_field_descending_price : вывести значения поля price всех элементов в порядке убывания""");
            out.flush();
        } catch (IOException ex) {
            System.err.println(ex);
        }
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
