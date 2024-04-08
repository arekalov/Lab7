package com.arekalov.readers;


import com.arekalov.core.IOManager;
import com.arekalov.entities.Coordinates;
import com.arekalov.entities.Organization;
import com.arekalov.entities.Product;
import com.arekalov.entities.UnitOfMeasure;

import java.time.LocalDateTime;

/**
 * Class for reading Product from console
 */
public class ProductReader implements Reader {

    private IOManager ioManager;

    /**
     * Constructor for ProductReader
     *
     * @param ioManager - IOManager
     */
    public ProductReader(IOManager ioManager) {

        this.ioManager = ioManager;
    }

    private Product product;


    /**
     * Method to get Product
     *
     * @return Product
     */
    public Product getProduct() {

        System.out.println("Start inputting Product");
        Long id = (long) Math.abs(LocalDateTime.now().hashCode());
        String name = inputName();
        Coordinates coordinates = new CoordinatesReader(ioManager).getCoordinates();
        LocalDateTime localDateTime = LocalDateTime.now();
        Long price = inputPrice();
        String partNumber = inputPartNumber();
        Integer manufactureCost = manufactureCostInput();
        UnitOfMeasure unitOfMeasure = unitOfMeasureInput();
        Organization organization = new OrganizationReader(ioManager).getOrganization();
        System.out.println("End inputting Product");
        return new Product(id, name, coordinates, localDateTime, price,
                partNumber, manufactureCost, unitOfMeasure, organization, null);
    }

    /**
     * Method to input UnitOfMeasure
     *
     * @return UnitOfMeasure
     */
    private UnitOfMeasure unitOfMeasureInput() {

        System.out.println("Input UnitOfMeasure <unitOfMeasureInput> (choose one of: KILOGRAMS, SQUARE_METERS\n PCS MILLIGRAMS)");
        do {
            String input = ioManager.consoleRead();
            try {
                UnitOfMeasure n = Validators.checkUnitOfMeasure(input);
                return n;
            } catch (Exception exception) {
                errorPrinter();
            }
        } while (true);
    }

    /**
     * Method to input manufactureCost
     *
     * @return Integer
     */
    private Integer manufactureCostInput() {

        System.out.println("Input Integer <manufactureCost> (not null)");
        do {
            String input = ioManager.consoleRead();
            try {
                Integer n = Validators.checkInt(input);
                return n;
            } catch (Exception exception) {
                errorPrinter();
            }
        } while (true);
    }

    /**
     * Method to input partNumber
     *
     * @return String
     */
    private String inputPartNumber() {

        System.out.println("Input String <partNumber> (partNumber can't be more than" +
                "56 letters and partNumber can bee null(type \"\" for it))");
        do {
            String input = ioManager.consoleRead();
            try {
                String n = Validators.checkLength(input, 56);
                if (n.isEmpty()) {
                    return null;
                }
                return n;
            } catch (Exception exception) {
                errorPrinter();
            }
        } while (true);
    }

    /**
     * Method to input price
     *
     * @return Long
     */
    private Long inputPrice() {

        System.out.println("Input Long <price> (>0)");
        do {
            String input = ioManager.consoleRead();
            try {
                Long name = Validators.checkUpperThenZero(Validators.checkLong(input));
                return name;
            } catch (Exception exception) {
                errorPrinter();
            }
        } while (true);
    }

    /**
     * Method to input name
     *
     * @return String
     */
    private String inputName() {

        System.out.println("Input String <name> (name can't bee null or \"\")");
        do {
            String input = ioManager.consoleRead();
            try {
                String name = Validators.isNotNullAndEmpty(input);
                return name;
            } catch (Exception exception) {
                errorPrinter();
            }
        } while (true);
    }

}
