package com.arekalov.managers;


import com.arekalov.entities.Product;

import java.util.ArrayDeque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

/**
 * Class for managing the collection
 */
public class CollectionManager {
    private ConcurrentLinkedDeque<Product> arrayDeque;

    /**
     * Constructor for CollectionManager
     *
     * @param arrayDeque
     * @see ArrayDeque
     */
    public CollectionManager(ConcurrentLinkedDeque<Product> arrayDeque) {
        this.arrayDeque = arrayDeque;
    }

    /**
     * Method to get the collection
     *
     * @return ArrayDeque
     */
    public ConcurrentLinkedDeque<Product> getArrayDeque() {
        return arrayDeque;
    }

    /**
     * Method to get the size of the collection
     *
     * @return boolean
     */
    public Boolean isEmpty() {
        return arrayDeque.isEmpty();
    }

    /**
     * Method to get the size of the collection
     *
     * @return int
     */
    public Product removeHead(String login) {
        Product product = null;
        for (Product pr: arrayDeque) {
            if (pr.getCreator().equals(login)) {
                product = pr;
                break;
            }
        }
        if (product != null) {
            arrayDeque.remove(product);
        }
        return product;
    }

    /**
     * Method to get the size of the collection
     *
     * @return int
     */
    public void removeLower(Product product, String login) {
        ConcurrentLinkedDeque<Product> copy = new ConcurrentLinkedDeque<>(arrayDeque);
        for (Product i : arrayDeque) {
            if (i.getPrice()< product.getPrice() && i.getCreator().equals(login)) {
                copy.remove(i);
            }
        }
        arrayDeque = copy;
    }

    /**
     * Method to get the size of the collection
     *
     * @return int
     */
    public void clear(String login) {
        arrayDeque = arrayDeque.stream().filter(x -> !x.getCreator().equals(login))
                .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
    }

    /**
     * Method to get the size of the collection
     *
     * @return int
     */
    public void remove(Product product) {
        arrayDeque.remove(product);
    }

    /**
     * Method to get the size of the collection
     *
     * @return int
     */
    public void add(Product product) {
        arrayDeque.add(product);
    }

}
