package com.arekalov.core;

import arekalov.com.proga5.entities.Product;

import java.util.ArrayDeque;

/**
 * Class for managing the collection
 */
public class CollectionManager {
    private ArrayDeque<Product> arrayDeque;
    /**
     * Constructor for CollectionManager
     * @param arrayDeque
     * @see ArrayDeque
     */
    public CollectionManager(ArrayDeque<Product> arrayDeque) {
        this.arrayDeque = arrayDeque;
    }
    /**
     * Method to get the collection
     * @return ArrayDeque
     */
    public ArrayDeque<Product> getArrayDeque() {
        return arrayDeque;
    }

    /**
     * Method to get the size of the collection
     * @return boolean
     */
    public Boolean isEmpty() {
        return arrayDeque.isEmpty();
    }

    /**
     * Method to get the size of the collection
     * @return int
     */
    public Product removeHead() {
        return arrayDeque.pop();
    }

    /**
     * Method to get the size of the collection
     * @return int
     */
    public void removeLower(Product product) {
            ArrayDeque<Product> copy = arrayDeque.clone();
            for (Product i : arrayDeque) {
                if (i.compareTo(product) < 0) {
                    copy.remove(i);
                }
            }
            arrayDeque = copy;
    }

    /**
     * Method to get the size of the collection
     * @return int
     */
    public void clear(){
        arrayDeque.clear();
    }

    /**
     * Method to get the size of the collection
     * @return int
     */
    public void remove(Product product){
        arrayDeque.remove(product);
    }

    /**
     * Method to get the size of the collection
     * @return int
     */
    public void add(Product product){
        arrayDeque.add(product);
    }

}
