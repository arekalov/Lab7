package com.arekalov.managers;

import com.arekalov.core.Server;
import com.arekalov.entities.Product;
import com.arekalov.errors.ArgsCountError;
import com.arekalov.errors.IncorrectCommandError;
import javafx.util.Pair;

import java.util.ArrayDeque;

public class ServerCommandManager {
    Server server;

    public ServerCommandManager(Server server) {
        this.server = server;
    }

    public void executeCommand(String input) {
        try {
            Pair<String, Integer> pair = validateCommand(input);
            if (pair != null) {
                if (pair.getKey().equals("save")) {
                    server.clientsHashSet.get(pair.getValue()).save("save");
                    System.out.println("success saved");
                }
                if (pair.getKey().equals("check")) {
                    for (Integer k : server.clientsHashSet.keySet()) {
                        System.out.println(k);
                        ArrayDeque<Product> deque = server.clientsHashSet.get(k).arrayDeque;
                        if (deque.isEmpty()) {
                            System.out.println("Empty");
                        } else {
                            String res = "";
                            for (Product i : deque) {
                                res += i + "\n\n";
                            }
                            System.out.println(res);
                        }
                        System.out.println();
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("" + ex);
        }

    }

    private Pair<String, Integer> validateCommand(String input) {
        try {
            String[] commandParts = input.split(" +");
            if (commandParts.length == 0) {
                throw new IncorrectCommandError("empty");
            }
            String commandName = commandParts[0].toLowerCase();
            if (!commandName.equals("save") && !commandName.equals("check")) {
                throw new IncorrectCommandError("incorrect command");
            }

            if (commandName.equals("save")) {
                if (commandParts.length != 2) {
                    throw new ArgsCountError("error args count!");
                } else {
                    int clientId;
                    try {
                        clientId = Integer.parseInt(commandParts[1]);
                    } catch (NumberFormatException e) {
                        throw new IncorrectCommandError("incorrect client id");
                    }
                    if (!server.clientsHashSet.containsKey(clientId)) {
                        throw new IncorrectCommandError("incorrect client id");
                    } else {
                        return new Pair<>(commandName, clientId);
                    }
                }
            } else if (commandName.equals("check")) {
                if (commandParts.length != 1) {
                    throw new ArgsCountError("error args count!");
                } else {
                    return new Pair<>(commandName, null);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
            return null;
        }
        return null;
    }

}
