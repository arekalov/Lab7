package com.arekalov.entities;

import java.io.Serializable;
import java.util.Arrays;

public class CommandWithProduct implements Serializable {
    UserInfo userInfo;
    String command;
    String[] args;
    Product product;

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

    public CommandWithProduct(String command, String[] args, Product product, UserInfo userInfo) {
        this.command = command;
        this.args = args;
        this.product = product;
        this.userInfo = userInfo;
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
