package com.arekalov.managers;

import com.arekalov.entities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayDeque;
import java.util.concurrent.ConcurrentLinkedDeque;

import static com.arekalov.core.Server.logger;


public class DBCommandManager {
    private static final Logger log = LogManager.getLogger(DBCommandManager.class);
    private Connection connection;

    public DBCommandManager(Connection connection) {
        this.connection = connection;
    }


    public void removeProduct(Product product) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from product where id = ?");
        statement.setLong(1, product.getId());
        statement.executeUpdate();
    }

    public void removeLowerWithLogin(String login, Product product) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from product where price < ? and creatorlogin = ?");
        statement.setLong(1, product.getPrice());
        statement.setString(2, login);
        statement.executeUpdate();
    }
    
    public void clearWithLogin(String login) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from product where creatorlogin = ?");
        statement.setString(1, login);
        statement.executeUpdate();
    }

    public void updateProduct(Product product, Integer productId) {
        try {

            // Обновляем координаты
            Coordinates coordinates = product.getCoordinates();
            PreparedStatement coordinateStatement = connection.prepareStatement("UPDATE coordinate SET x = ?, y = ? WHERE id = ?");
            coordinateStatement.setFloat(1, coordinates.getX());
            coordinateStatement.setFloat(2, coordinates.getY());
            coordinateStatement.setInt(3, productId);
            coordinateStatement.executeUpdate();

            // Обновляем местоположение
            Location location = product.getManufacturer().getPostalAddress().getTown();
            PreparedStatement locationStatement = connection.prepareStatement("UPDATE locationp SET x = ?, y = ?, name = ? WHERE id = ?");
            locationStatement.setFloat(1, location.getX());
            locationStatement.setDouble(2, location.getY());
            locationStatement.setString(3, location.getName());
            locationStatement.setInt(4, productId);
            locationStatement.executeUpdate();

            // Обновляем адрес
            Address address = product.getManufacturer().getPostalAddress();
            PreparedStatement addressStatement = connection.prepareStatement("UPDATE adress SET street = ? WHERE id = ?");
            addressStatement.setString(1, address.getStreet());
            addressStatement.setInt(2, productId);
            addressStatement.executeUpdate();

            // Обновляем организацию
            Organization organization = product.getManufacturer();
            PreparedStatement organizationStatement = connection.prepareStatement("UPDATE organization SET name = ?, annualturnover = ?, type = ? WHERE id = ?");
            organizationStatement.setString(1, organization.getName());
            organizationStatement.setDouble(2, organization.getAnnualTurnover());
            organizationStatement.setObject(3, organization.getType(), Types.OTHER);
            organizationStatement.setInt(4, productId);
            organizationStatement.executeUpdate();

            // Обновляем продукт
            PreparedStatement productStatement = connection.prepareStatement("UPDATE product SET name = ?, localdate = ?, price = ?, partnumber = ?, manufacturecost = ?, unitofmeasure = ?, creatorlogin = ? WHERE id = ?");
            productStatement.setString(1, product.getName());
            productStatement.setTimestamp(2, Timestamp.valueOf(product.getCreationDate()));
            productStatement.setLong(3, product.getPrice());
            productStatement.setString(4, product.getPartNumber());
            productStatement.setInt(5, product.getManufactureCost());
            productStatement.setObject(6, product.getUnitOfMeasure(), Types.OTHER);
            productStatement.setString(7, product.getCreator());
            productStatement.setInt(8, productId);
            productStatement.executeUpdate();

        } catch (SQLException ex) {
            logger.error("Failed to update product", ex);
        }
    }

    public int insertProduct(Product product) throws SQLException {
        PreparedStatement idStatement = connection.prepareStatement("select nextval('product_id_seq')");
        idStatement.execute();
        ResultSet idRes = idStatement.getResultSet();
        idRes.next();
        int id = idRes.getInt("nextval");

        Coordinates coordinates = product.getCoordinates();
        Organization organization = product.getManufacturer();
        Address address = organization.getPostalAddress();
        Location location = address.getTown();

        PreparedStatement coordinateStatement = connection.prepareStatement("insert into coordinate (id,x, y) " +
                "VALUES (?, ?, ?)");
        coordinateStatement.setInt(1, id);
        coordinateStatement.setFloat(2, coordinates.getX());
        coordinateStatement.setFloat(3, coordinates.getY());
        coordinateStatement.executeUpdate();


        PreparedStatement locationStatement = connection.prepareStatement("insert into locationp (id, x, y, name)\n" +
                "VALUES (?,?, ?, ?)");
        locationStatement.setInt(1, id);
        locationStatement.setFloat(2, location.getX());
        locationStatement.setDouble(3, location.getY());
        locationStatement.setString(4, location.getName());
        locationStatement.executeUpdate();

        PreparedStatement addressStatement = connection.prepareStatement("insert into adress (id, street, location)\n" +
                "VALUES (?, ?, ?)");
        addressStatement.setInt(1, id);
        addressStatement.setString(2, address.getStreet());
        addressStatement.setInt(3, id);
        addressStatement.executeUpdate();

        PreparedStatement organizationStatement = connection.prepareStatement("insert into organization (id, name, annualturnover, type, postaladress) VALUES " +
                "(?, ?, ?, ?, ?)");
        organizationStatement.setInt(1, id);
        organizationStatement.setString(2, organization.getName());
        organizationStatement.setDouble(3, organization.getAnnualTurnover());
        organizationStatement.setObject(4, organization.getType(), Types.OTHER);
        organizationStatement.setInt(5, id);
        organizationStatement.executeUpdate();

        PreparedStatement productStatement = connection.prepareStatement("insert into product (id, name, coordinates, localdate, price, partnumber, manufacturecost, unitofmeasure, manufacturer, creatorlogin) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        productStatement.setInt(1, id);
        productStatement.setString(2, product.getName());
        productStatement.setInt(3, id);
        productStatement.setTimestamp(4, Timestamp.valueOf(product.getCreationDate()));
        productStatement.setLong(5, product.getPrice());
        productStatement.setString(6, product.getPartNumber());
        productStatement.setInt(7, product.getManufactureCost());
        productStatement.setObject(8,  product.getUnitOfMeasure(), Types.OTHER);
        productStatement.setInt(9, id);
        productStatement.setString(10, product.getCreator());
        productStatement.executeUpdate();
        return id;
    }

    public ConcurrentLinkedDeque<Product> getProducts() {
        ConcurrentLinkedDeque<Product> arrayDeque = new ConcurrentLinkedDeque<>();
        try {
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
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            return arrayDeque;
        }
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
