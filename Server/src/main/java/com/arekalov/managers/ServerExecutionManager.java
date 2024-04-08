package com.arekalov.managers;

import com.arekalov.commands.*;
import com.arekalov.core.IOManager;
import com.arekalov.core.Server;
import com.arekalov.entities.AuthMode;
import com.arekalov.entities.CommandWithProduct;
import com.arekalov.entities.Product;
import com.arekalov.errors.*;
import com.arekalov.parsing.JsonParser;
import javafx.util.Pair;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ServerExecutionManager {
    public static final String ENV_NAME = "PROGA";
    ConcurrentLinkedDeque<Product> arrayDeque;
    CollectionManager collectionManager;
    private JsonParser parser = new JsonParser();
    private IOManager ioManager = new IOManager(ENV_NAME);
    protected Boolean isRunning = true;
    HashMap<String, Command> commandHashMap;
    Logger logger = Server.logger;
    ClientCommandManager commandManager;
    private DBAuthenticateManager dbAuthenticateManager;
    private Connection connection;
    DBCommandManager dbCommandManager;

    public ServerExecutionManager(ConcurrentLinkedDeque arrayDeque, DBAuthenticateManager dbAuthenticateManager, Connection connection, DBCommandManager dbCommandManager) throws SQLException {
        this.dbAuthenticateManager = dbAuthenticateManager;
        this.connection = connection;
        this.dbCommandManager = dbCommandManager;
        this.arrayDeque = arrayDeque;
        collectionManager = new CollectionManager(arrayDeque);
        commandManager = new ClientCommandManager(ioManager, this, parser, collectionManager);
        commandHashMap = commandManager.getHashMapCommands();
        initCommands();
    }



    public void setRunning(Boolean running) {
        isRunning = running;
    }

    public void authenticate(CommandWithProduct commandWithProduct) throws SQLException, IOException, UserAlreadyExistError, IncorrectPasswordError, HaveNotAccauntError {
        if (commandWithProduct.getUserInfo().getAuthMode().equals(AuthMode.SignUp)) {
            dbAuthenticateManager.signUp(commandWithProduct.getUserInfo().getLogin(), commandWithProduct.getUserInfo().getPassword());
        }
        if (commandWithProduct.getUserInfo().getAuthMode().equals(AuthMode.LogIn)) {
            dbAuthenticateManager.logIn(commandWithProduct.getUserInfo().getLogin(), commandWithProduct.getUserInfo().getPassword());
        }
    }

    public void executeCommand(CommandWithProduct commandWithProduct, SocketChannel client) {
        try {
            System.out.println(commandWithProduct);
            if(commandWithProduct.getProduct() != null) {
                Product product = commandWithProduct.getProduct();
                product.setCreator(commandWithProduct.getUserInfo().getLogin());
                commandWithProduct.setProduct(product);
            }
            String answer = commandHashMap.get(commandWithProduct.getArgs()[0]).execute(commandWithProduct.getArgs(), commandWithProduct.getProduct());
            byte[] data = serialize(answer);
            ByteBuffer buffer = ByteBuffer.allocate(data.length);
            buffer.put(data);
            buffer.flip();
            while (buffer.hasRemaining()) {
                client.write(buffer);
            }
            logger.info("OK\n");
        } catch (RuntimeException runtimeException) {
            logger.error(runtimeException.getMessage());
        } catch (IOException e) {
            logger.error("Serialization error");
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    static byte[] serialize(String obj) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        }
    }

    public void save(String consoleInput) {
        try {
            if (consoleInput.toLowerCase().equals("save")) {
                CommandWithProduct commandWithProduct = new CommandWithProduct("save", new String[]{"save"}, null, null);
                commandHashMap.get(commandWithProduct.getArgs()[0]).execute(commandWithProduct.getArgs(), commandWithProduct.getProduct());
                logger.info("OK\n");
            } else {
                logger.error("Error: Incorrect server command");
            }
        } catch (RuntimeException runtimeException) {
            logger.error(runtimeException.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
