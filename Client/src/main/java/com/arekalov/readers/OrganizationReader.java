package com.arekalov.readers;


import com.arekalov.core.IOManager;
import com.arekalov.entities.Address;
import com.arekalov.entities.Organization;
import com.arekalov.entities.OrganizationType;

import java.time.LocalDateTime;

/**
 * This class is responsible for reading Organization from console
 */
public class OrganizationReader implements Reader {
    private IOManager ioManager;


    /**
     * Constructor for OrganizationReader
     *
     * @param ioManager
     */
    public OrganizationReader(IOManager ioManager) {

        this.ioManager = ioManager;
    }

    /**
     * This method is responsible for reading Organization from console
     *
     * @return Organization
     */
    public Organization getOrganization() {

        System.out.println("Start inputting Organization");
        Long id = (long) Math.abs(LocalDateTime.now().hashCode());
        String name = inputName();
        Float inputAnnualTurnover = inputAnnualTurnover();
        OrganizationType organizationType = inputOrganizationType();
        Address address = new PostalAddressReader(ioManager).getPostalAddress();
        System.out.println("End inputting Organization");
        return new Organization(id, name, inputAnnualTurnover, organizationType, address);
    }

    /**
     * This method is responsible for reading OrganizationType from console
     *
     * @return OrganizationType
     */
    private OrganizationType inputOrganizationType() {

        System.out.println("Input OrganizationType <type> (choose one of: COMMERCIAL, TRUST\n PRIVATE_LIMITED_COMPANY OPEN_JOINT_STOCK_COMPANY)");
        do {
            String input = ioManager.consoleRead();
            try {
                OrganizationType n = Validators.checkOrganizationType(input);
                return n;
            } catch (Exception exception) {
                errorPrinter();
            }
        } while (true);
    }

    /**
     * This method is responsible for reading Float from console
     *
     * @return Float
     */
    private Float inputAnnualTurnover() {

        System.out.println("Input Float <price> (\"\" to null, >0)");
        do {
            String input = ioManager.consoleRead();
            try {
                if (input.isEmpty()) {
                    return null;
                }
                Float name = Validators.checkUpperThenZero(Validators.checkFloat(input));
                return name;
            } catch (Exception exception) {
                errorPrinter();
            }
        } while (true);
    }

    /**
     * This method is responsible for reading String from console
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
