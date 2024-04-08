package com.arekalov.entities;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Serializable {
    /**
     * Class for creating location
     *
     * @param x
     * @param y
     * @param name
     */
    public Location(Float x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Location() {
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Float x; //Поле не может быть null
    private double y;
    private String name; //Строка не может быть пустой, Поле может быть null

    /**
     * Method to represent location as string
     *
     * @return string representation of location
     */
    @Override
    public String toString() {

        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * Method to compare locations
     *
     * @param o
     * @return true if locations are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(y, location.y) == 0 && Objects.equals(x, location.x) && Objects.equals(name, location.name);
    }

    /**
     * Method to get hash code of location
     *
     * @return hash code of location
     */
    @Override
    public int hashCode() {

        return Objects.hash(x, y, name);
    }
}
