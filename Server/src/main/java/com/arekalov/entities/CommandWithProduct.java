package com.arekalov.entities;

import java.io.Serializable;
import java.util.Arrays;

public class CommandWithProduct implements Serializable {
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

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
