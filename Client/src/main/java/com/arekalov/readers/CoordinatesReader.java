package com.arekalov.readers;


import com.arekalov.core.IOManager;
import com.arekalov.entities.Coordinates;

/**
 * Class for reading Coordinates
 * Inherited from Reader
 *
 * @see Reader
 * @see Coordinates
 * @see IOManager
 * @see Validators
 */
public class CoordinatesReader implements Reader {

    private IOManager ioManager;

    /**
     * Constructor for CoordinatesReader
     *
     * @param ioManager - IOManager
     **/
    public CoordinatesReader(IOManager ioManager) {

        this.ioManager = ioManager;
    }

    /**
     * Method for reading Coordinates
     *
     * @return Coordinates
     */
    public Coordinates getCoordinates() {

        System.out.println("Start inputting Coordinates");
        float x = readX();
        float y = readY();
        System.out.println("End inputting Coordinates");
        return new Coordinates(x, y);
    }

    /**
     * Method for reading X
     *
     * @return int
     */

    private float readX() {

        System.out.println("Input float <x>");
        do {
            String input = ioManager.consoleRead();
            try {
                float x = Validators.checkFloat(input);
                return x;
            } catch (Exception exception) {
                errorPrinter();
            }
        } while (true);
    }

    /**
     * Method for reading Y
     *
     * @return int
     */
    private float readY() {

        System.out.println("Input float <y>");
        do {
            String input = ioManager.consoleRead();
            try {
                float y = Validators.checkNotUpperFloat(Validators.checkFloat(input), 557);
                return y;
            } catch (Exception exception) {
                errorPrinter();
            }
        } while (true);
    }
}
