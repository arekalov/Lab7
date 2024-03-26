package com.arekalov.parsing;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * Class for deserializing localimplementation("com.google.code.gson:gson:2.10.1") date time
 */
public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    /**
     * Method to deserialize local date time
     *
     * @param jsonElement
     */
    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        return LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString());
    }
}
