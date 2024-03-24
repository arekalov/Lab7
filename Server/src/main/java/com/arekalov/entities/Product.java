package com.arekalov.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class for creating product
 */
public class Product implements Comparable<Product>, Serializable {
    /**
     * Constructor for creating product
     * @param id
     * @param name
     * @param coordinates
     * @param creationDate
     * @param price
     * @param partNumber
     * @param manufactureCost
     * @param unitOfMeasure
     * @param manufacturer
     */
    public Product(Long id, String name, Coordinates coordinates,
                   LocalDateTime creationDate, Long price, String partNumber,
                   Integer manufactureCost, UnitOfMeasure unitOfMeasure, Organization manufacturer) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.partNumber = partNumber;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.manufacturer = manufacturer;
    }

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long price; //Поле может быть null, Значение поля должно быть больше 0
    private String partNumber; //Строка не может быть пустой, Длина строки не должна быть больше 56, Поле может быть null
    private Integer manufactureCost; //Поле не может быть null
    private UnitOfMeasure unitOfMeasure; //Поле может быть null
    private Organization manufacturer; //Поле не может быть null
    /**
     * Method to get id
     * @return
     */
    public Long getId() {
        return id;
    }
    /**
     * Method to set id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Method to get name
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     * Method to set name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Method to get coordinates
     * @return
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }
    /**
     * Method to set coordinates
     * @param coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    /**
     * Method to get creation date
     * @return
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    /**
     * Method to set creation date
     * @param creationDate
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    /**
     * Method to get price
     * @return
     */
    public Long getPrice() {
        return price;
    }
    /**
     * Method to set price
     * @param price
     */
    public void setPrice(Long price) {
        this.price = price;
    }
    /**
     * Method to get part number
     * @return
     */
    public String getPartNumber() {
        return partNumber;
    }
    /**
     * Method to set part number
     * @param partNumber
     */
    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }
    /**
     * Method to get manufacture cost
     * @return
     */
    public Integer getManufactureCost() {
        return manufactureCost;
    }
    /**
     * Method to set manufacture cost
     * @param manufactureCost
     */
    public void setManufactureCost(Integer manufactureCost) {
        this.manufactureCost = manufactureCost;
    }

    /**
     * Method to get unit of measure
     * @return
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Method to set unit of measure
     * @param unitOfMeasure
     */
    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    /**
     * Method to get manufacturer
     * @return
     */
    public Organization getManufacturer() {
        return manufacturer;
    }

    /**
     * Method to set manufacturer
     * @param manufacturer
     */
    public void setManufacturer(Organization manufacturer) {
        this.manufacturer = manufacturer;
    }
    /**
     * Method to represent product as string
     * @return string representation of product
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", partNumber='" + partNumber + '\'' +
                ", manufactureCost=" + manufactureCost +
                ", unitOfMeasure=" + unitOfMeasure +
                ", manufacturer=" + manufacturer +
                '}';
    }

    /**
     * Method to compare products
     * @param o
     * @return true if products are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(coordinates, product.coordinates) && Objects.equals(creationDate, product.creationDate) && Objects.equals(price, product.price) && Objects.equals(partNumber, product.partNumber) && Objects.equals(manufactureCost, product.manufactureCost) && unitOfMeasure == product.unitOfMeasure && Objects.equals(manufacturer, product.manufacturer);
    }

    /**
     * Method to get hash code of product
     * @return hash code of product
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, price, partNumber, manufactureCost, unitOfMeasure, manufacturer);
    }


    /**
     * Method to compare products
     * @param o
     */
    @Override
    public int compareTo(Product o) {
        return (int) (price - o.price);
    }
}
