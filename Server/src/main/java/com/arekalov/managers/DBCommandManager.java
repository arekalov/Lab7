package com.arekalov.managers;

import com.arekalov.entities.*;

import java.sql.*;
import java.util.ArrayDeque;

public class DBCommandManager {
    private Connection connection;

    public DBCommandManager(Connection connection) {
        this.connection = connection;
    }

    public ArrayDeque<Product> getProducts() throws SQLException {
        ArrayDeque<Product> arrayDeque = new ArrayDeque<Product>();
        PreparedStatement statement = connection.prepareStatement("select *\n" +
                "from product\n" +
                "         natural inner join coordinate\n" +
                "         inner join organization on manufacturer = organization.id\n" +
                "         inner join adress on organization.postaladress = adress.id\n" +
                "         inner join locationp on adress.location = locationp.id\n");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            arrayDeque.add(getProduct(resultSet));
        }
        return arrayDeque;
    }

    public Product getProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setCoordinates(getCoordinateFromResultSet(resultSet));

        product.setCreationDate(resultSet.getTimestamp("localDate").toLocalDateTime());
        product.setPrice(resultSet.getLong("price"));
        product.setPartNumber(resultSet.getString("partNumber"));
        product.setManufactureCost(resultSet.getInt("manufactureCost"));
        product.setUnitOfMeasure(UnitOfMeasure.valueOf(resultSet.getString("unitOfMeasure")));
        product.setManufacturer(getOrganizationFromResultSet(resultSet));

        product.setCreator(resultSet.getString("creatorLogin"));

        return product;
    }

    public Coordinates getCoordinateFromResultSet(ResultSet resultSet) throws SQLException {
        Coordinates coordinate = new Coordinates();
        coordinate.setX(resultSet.getFloat("x"));
        coordinate.setY(resultSet.getFloat("y"));
        return coordinate;
    }

    public Organization getOrganizationFromResultSet(ResultSet resultSet) throws SQLException {
        Organization organization = new Organization();
        organization.setName(resultSet.getString("name"));
        organization.setAnnualTurnover(resultSet.getFloat("annualTurnover"));
        organization.setType(OrganizationType.valueOf(resultSet.getString("type")));
        organization.setPostalAddress(getAddressFromResultSet(resultSet));
        return organization;
    }

    public Address getAddressFromResultSet(ResultSet resultSet) throws SQLException {
        Address address = new Address();
        address.setStreet(resultSet.getString("street"));
        address.setTown(getLocationFromResultSet(resultSet));

        return address;
    }

    public Location getLocationFromResultSet(ResultSet resultSet) throws SQLException {
        Location location = new Location();
        location.setX(resultSet.getFloat("x"));
        location.setY(resultSet.getDouble("y"));
        location.setName(resultSet.getString("name"));
        return location;
    }


}
