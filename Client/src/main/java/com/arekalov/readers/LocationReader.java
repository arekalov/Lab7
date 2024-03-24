package com.arekalov.readers;

import arekalov.com.proga5.core.IOManager;
import arekalov.com.proga5.entities.Location;

/**
 * Class for reading Location from console
 *
 * @see IOManager
 * @see Location
 * @see Reader
 */
public class LocationReader implements Reader {
    /**
     * Constructor for LocationReader
     *
     * @param ioManager - IOManager for reading from console
     * @see IOManager
     */
    private IOManager ioManager;

    public LocationReader(IOManager ioManager) {

        this.ioManager = ioManager;
    }

    /**
     * Method for reading Location from console
     *
     * @return Location
     * @see Location
     */
    public Location getLocation() {
        System.out.println("Start inputting Location");
        Float x = inputX();
        Float y = inputY();
        String name = inputName();
        System.out.println("End inputting Location");
        return new Location(x, y, name);
    }

    /**
     * Method for reading String from console
     *
     * @return String
     */
    private String inputName() {

        System.out.println("Input String <name> (type \"\" for null)");
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

    /**
     * Method for reading Float from console
     *
     * @return Float
     */
    private Float inputY() {

        System.out.println("Input Float <y> (\"\" to null)");
        do {
            String input = ioManager.consoleRead();
            try {
                if (input.isEmpty()) {
                    return null;
                }
                Float name = Validators.checkFloat(input);
                return name;
            } catch (Exception exception) {
                errorPrinter();
            }
        } while (true);
    }

    /**
     * Method for reading Float from console
     *
     * @return Float
     */
    private Float inputX() {

        System.out.println("Input Float <x> (can't be null)");
        do {
            String input = ioManager.consoleRead();
            try {

                Float name = Validators.checkFloat(input);
                return name;
            } catch (Exception exception) {
                errorPrinter();
            }
        } while (true);
    }
}
