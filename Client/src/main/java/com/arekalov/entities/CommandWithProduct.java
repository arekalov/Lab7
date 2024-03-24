package com.arekalov.entities;

import java.io.Serializable;
import java.util.Arrays;

public class CommandWithProduct implements Serializable {
    String command;
    String[] args;
    Product product;

    public CommandWithProduct(String command, String[] args, Product product) {
        this.command = command;
        this.args = args;
        this.product = product;
    }

    @Override
    public String toString() {
        return "CommandWithProduct{" +
                "command='" + command + '\'' +
                ", args=" + Arrays.toString(args) +
                ", product=" + product +
                '}';
    }
}
