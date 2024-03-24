package com.arekalov.core;


import com.arekalov.errors.EnvNotFoundError;
import com.arekalov.errors.ReadFromFileError;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
/**
 * Class for working with input/output
 **/
public class IOManager {

    private String envName;
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructor for IOManager
     * @param envName - String
     */
    public IOManager(String envName) {
        this.envName = envName;
    }

    /**
     * Method to get JSON from environment variable
     */
    public String getJsonFromEnv() {

        File file = getFile();
        return readFromFile(file);
    }
    /**
     * Method to set Scanner
     * @param scanner - Scanner
     */
    public void setScanner(Scanner scanner) {

        this.scanner = scanner;
    }
    /**
     * Method to get File
     * @return File
     */
    public File getFile() {

        String filePath = System.getenv(envName);
        if (filePath != null) {
            return new File(filePath);
        } else throw new EnvNotFoundError(envName);
    }

    /**
     * Method to read from file
     * @param file - File
     * @return String
     */
    public String readFromFile(File file) {

        try {
            StringBuilder text = new StringBuilder();
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            while (bis.available() > 0) {
                text.append((char) bis.read());
            }
            fis.close();
            bis.close();
            return text.toString();
        } catch (IOException e) {
            throw new ReadFromFileError();
        }
    }

    /**
     * Method to read from console
     * @return String
     */
    public String consoleRead() {

        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        setScanner(new Scanner(System.in));
        return scanner.nextLine();
    }


}
