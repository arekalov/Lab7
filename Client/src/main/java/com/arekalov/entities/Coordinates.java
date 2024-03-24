package com.arekalov.entities;

import java.util.Objects;

public class Coordinates {
    /**
     * Class for creating coordinates
     *
     * @param x
     * @param y
     */
    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    private float x;
    private float y; //Максимальное значение поля: 557

    /**
     * Method to get x coordinate
     *
     * @return x coordinate
     */
    public float getX() {

        return x;
    }

    /**
     * Method to set x coordinate
     *
     * @param x
     */
    public void setX(float x) {

        this.x = x;
    }

    /**
     * Method to get y coordinate
     *
     * @return y coordinate
     */
    public float getY() {

        return y;
    }

    /**
     * Method to set y coordinate
     *
     * @param y
     */
    public void setY(float y) {

        this.y = y;
    }

    /**
     * Method to represent coordinates as string
     *
     * @return string representation of coordinates
     */
    @Override
    public String toString() {

        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Method to compare coordinates
     *
     * @param o
     * @return true if coordinates are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    /**
     * Method to get hash code of coordinates
     *
     * @return hash code of coordinates
     */
    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }
}
