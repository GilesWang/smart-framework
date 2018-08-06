package org.smart4j.framework.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();

    public static <T> String toJson(T obj){
        String json;
        try {
            json=OBJECT_MAPPER.writeValueAsString(obj);
        }catch (Exception ex){
            LOGGER.error("serialize failure", ex);
            throw new RuntimeException(ex);
        }
        return json;
    }

    public static <T> T fromJson(String json,Class<T> type){
        T pojo;
        try {
            pojo=OBJECT_MAPPER.readValue(json,type);
        }catch (Exception ex){
            LOGGER.error("deserialize failure", ex);
            throw new RuntimeException(ex);
        }
        return pojo;
    }
}
