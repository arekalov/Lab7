package com.arekalov.entities;

import java.io.Serializable;
import java.util.Objects;

public class Organization implements Serializable {
    /**
     * Class for creating organization
     *
     * @param id
     * @param name
     * @param annualTurnover
     * @param type
     * @param postalAddress
     */
    public Organization(Long id, String name, Float annualTurnover, OrganizationType type, Address postalAddress) {
        this.id = id;
        this.name = name;
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.postalAddress = postalAddress;
    }

    public Organization() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(Float annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public Address getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
    }

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Float annualTurnover; //Поле может быть null, Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    Address postalAddress; //Поле не может быть null

    public String getName() {
        return name;
    }

    /**
     * Method to represent organization as string
     *
     * @return string representation of organization
     */
    @Override
    public String toString() {

        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", annualTurnover=" + annualTurnover +
                ", type=" + type +
                ", postalAddress=" + postalAddress +
                '}';
    }

    /**
     * Method to compare organizations
     *
     * @param o
     * @return true if organizations are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(annualTurnover, that.annualTurnover) && type == that.type && Objects.equals(postalAddress, that.postalAddress);
    }

    /**
     * Method to get hash code of organization
     *
     * @return hash code of organization
     */
    @Override
    public int hashCode() {

        return Objects.hash(id, name, annualTurnover, type, postalAddress);
    }
}
