package com.irfan.workflowmanager.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Irfan Manakkat<irfan.manakkat@pearldatadirect.com>
 * Created On : April 29, 2023
 */
@Slf4j
public class MapperUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private MapperUtils() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T toObject(Object obj, Class<T> tClass) {
        if (obj != null && obj.getClass().equals(tClass))
            return (T) obj;
        return convert(obj, tClass);
    }

    public static String toString(Object obj) {
        return toString(obj, false);
    }

    public static String toString(Object obj, boolean prettify) {
        if (obj instanceof String string)
            return string;

        try {
            return prettify ?
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj) :
                    mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Error converting toString. {}", e.getMessage());
            return null;
        }
    }

    public static <T> T toObject(Object obj, TypeReference<T> ref) {
        return convert(obj, ref);
    }

    @SuppressWarnings("unchecked")
    private static <T> T convert(Object obj, Object ref) {
        try {
            if (obj == null) return null;

            String val = obj instanceof String string ? string : MapperUtils.toString(obj);

            if (ref instanceof TypeReference)
                return mapper.readValue(val, (TypeReference<T>) ref);
            else {
                return mapper.readValue(val, (Class<T>) ref);
            }

        } catch (JsonProcessingException e) {
            log.info("Error converting toObject. {}", e.getLocalizedMessage());
            return null;
        }
    }

}
