package com.arekalov.readers;


import com.arekalov.entities.OrganizationType;
import com.arekalov.entities.UnitOfMeasure;
import com.arekalov.errors.ArgumentError;

/**
 * Class for checking data
 *
 **/
public class Validators {

    /**
     * Method to check OrganizationType
     *
     * @param arg - String
     * @return OrganizationType
     */
    public static OrganizationType checkOrganizationType(String arg) {

        try {
            if (arg.toUpperCase().equals("COMMERCIAL")) {
                return OrganizationType.COMMERCIAL;
            } else if (arg.toUpperCase().equals("TRUST")) {
                return OrganizationType.TRUST;
            } else if (arg.toUpperCase().equals("PRIVATE_LIMITED_COMPANY")) {
                return OrganizationType.PRIVATE_LIMITED_COMPANY;
            } else if (arg.toUpperCase().equals("OPEN_JOINT_STOCK_COMPANY")) {
                return OrganizationType.OPEN_JOINT_STOCK_COMPANY;
            }
            throw new ArgumentError();
        } catch (Exception numberFormatException) {
            throw new ArgumentError();
        }
    }

    /**
     * Method to check UnitOfMeasure
     *
     * @param arg - String
     * @return UnitOfMeasure
     */
    public static UnitOfMeasure checkUnitOfMeasure(String arg) {

        try {
            if (arg.toUpperCase().equals("KILOGRAMS")) {
                return UnitOfMeasure.KILOGRAMS;
            } else if (arg.toUpperCase().equals("SQUARE_METERS")) {
                return UnitOfMeasure.SQUARE_METERS;
            } else if (arg.toUpperCase().equals("PCS")) {
                return UnitOfMeasure.PCS;
            } else if (arg.toUpperCase().equals("MILLIGRAMS")) {
                return UnitOfMeasure.MILLIGRAMS;
            }
            throw new ArgumentError();
        } catch (Exception numberFormatException) {
            throw new ArgumentError();
        }
    }

    /**
     * Method to check Long
     *
     * @param arg - String
     * @return Long
     */
    public static Long checkLong(String arg) {

        try {
            return Long.parseLong(arg);
        } catch (Exception numberFormatException) {
            throw new ArgumentError();
        }
    }

    /**
     * Method to check Float
     *
     * @param arg - String
     * @return Float
     */
    public static Float checkFloat(String arg) {

        try {
            return Float.parseFloat(arg);
        } catch (Exception numberFormatException) {
            throw new ArgumentError();
        }
    }

    /**
     * Method to check Integer
     *
     * @param arg - String
     * @return Integer
     */
    public static int checkInt(String arg) {

        try {
            return Integer.parseInt(arg);
        } catch (Exception numberFormatException) {
            throw new ArgumentError();
        }
    }

    /**
     * Method to check length of String
     *
     * @param arg  - String
     * @param maxL - int
     * @return String
     */
    public static String checkLength(String arg, int maxL) {

        try {
            if (arg.length() <= maxL) {
                return arg;
            }
            throw new ArgumentError();
        } catch (Exception numberFormatException) {
            throw new ArgumentError();
        }
    }

    /**
     * Method to check Integer
     *
     * @param arg
     * @param x
     * @return
     */
    public static int checkNotUpperInt(int arg, int x) {
        try {
            if (arg <= x) {
                return arg;
            }
            throw new ArgumentError();
        } catch (Exception numberFormatException) {
            throw new ArgumentError();
        }
    }

    public static float checkNotUpperFloat(float arg, int x) {
        try {
            if (arg <= x) {
                return arg;
            }
            throw new ArgumentError();
        } catch (Exception numberFormatException) {
            throw new ArgumentError();
        }
    }

    /**
     * Method to check Long
     *
     * @param arg - Long
     * @return Long
     */
    public static Long checkUpperThenZero(Long arg) {

        if (arg > 0) {
            return arg;
        }
        throw new ArgumentError();
    }

    /**
     * Method to check Float
     *
     * @param arg - Float
     * @return Float
     */
    public static Float checkUpperThenZero(Float arg) {

        if (arg > 0) {
            return arg;
        }
        throw new ArgumentError();
    }

    /**
     * Method to check Integer
     *
     * @param arg - Integer
     * @return Integer
     */
    public static Integer checkUpperThenZero(Integer arg) {

        if (arg > 0) {
            return arg;
        }
        throw new ArgumentError();
    }

    /**
     * Method to check String
     *
     * @param arg - String
     * @return String
     */
    public static String isNotNullAndEmpty(String arg) {

        if (arg != null && !arg.isEmpty()) {
            return arg;
        }
        throw new ArgumentError();
    }

}
