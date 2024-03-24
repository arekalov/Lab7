package com.arekalov.readers;


import com.arekalov.core.IOManager;
import com.arekalov.entities.Address;
import com.arekalov.entities.Location;

/**
 * The IOManager to be used for reading
 *
 * @see IOManager
 * @see Address
 * @see Location
 */
public class PostalAddressReader implements Reader {

    private IOManager ioManager;

    /**
     * The IOManager to be used for reading
     */
    public PostalAddressReader(IOManager ioManager) {
        this.ioManager = ioManager;
    }

    /**
     * Reads the street and location of the postal address
     *
     * @return the postal address
     * @see Address
     * @see Location
     */
    public Address getPostalAddress() {

        System.out.println("Start inputting postalAddress");
        String street = inputStreet();
        Location location = new LocationReader(ioManager).getLocation();
        System.out.println("End inputting postalAddress");
        return new Address(street, location);
    }

    /**
     * Reads the street of the postal address
     *
     * @return the street of the postal address
     */
    private String inputStreet() {

        System.out.println("Input String <street> (type \"\" for null)");
        do {
            String input = ioManager.consoleRead();
            try {
                if (input.isEmpty()) {
                    return null;
                }
                return input;
            } catch (Exception exception) {
                errorPrinter();
            }
        } while (true);
    }
}
