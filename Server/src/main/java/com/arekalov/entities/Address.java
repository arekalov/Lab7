package com.arekalov.entities;

import java.io.Serializable;
import java.util.Objects;

public class Address implements Serializable {
    /**
     * Class for creating address
     *
     * @param street
     * @param town
     */
    public Address(String street, Location town) {
        this.street = street;
        this.town = town;
    }

    private String street; //Строка не может быть пустой, Поле может быть null
    private Location town; //Поле не может быть null

    /**
     * Method to represent address as string
     *
     * @return string representation of address
     */
    @Override
    public String toString() {

        return "Address{" +
                "street='" + street + '\'' +
                ", town=" + town +
                '}';
    }

    /**
     * Method to compare addresses
     *
     * @param o
     * @return true if addresses are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(town, address.town);
    }

    /**
     * Method to get hash code of address
     *
     * @return hash code of address
     */
    @Override
    public int hashCode() {

        return Objects.hash(street, town);
    }
}
