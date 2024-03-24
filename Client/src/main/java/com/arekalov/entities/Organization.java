package com.arekalov.entities;

import java.util.Objects;

public class Organization {
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

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Float annualTurnover; //Поле может быть null, Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    Address postalAddress; //Поле не может быть null

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
