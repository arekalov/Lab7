package com.arekalov.parsing;


import com.arekalov.entities.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;


/**
 * Class for arekalov.com.parsing json
 */
public class JsonParser {
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
            .setPrettyPrinting()
            .create();

    /**
     * Method to parse deque of products from json
     *
     * @param json
     */
    public ArrayDeque<Product> jsonToDequeOfProducts(String json) {
        Type itemsListType = new TypeToken<Deque<Product>>() {
        }.getType();
        return gson.fromJson(json, itemsListType);
    }

    /**
     * Method to parse deque of products to json
     *
     * @param deque
     */
    public String dequeOfProductsToJson(ArrayDeque<Product> deque) {
        return gson.toJson(deque);
    }

}
