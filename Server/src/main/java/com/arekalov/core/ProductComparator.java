package com.arekalov.core;

import arekalov.com.proga5.entities.Product;

import java.util.Comparator;

/**
 * Class for comparing products
 */
public class ProductComparator implements Comparator<Product> {
    /**
     * Method to compare products
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     */
    @Override
    public int compare(Product o1, Product o2) {
        return o1.compareTo(o2);
    }
}
