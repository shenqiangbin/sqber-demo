package com.sqber.commonTool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class JSONUtil {

    public static String toString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    public static <T> T toObject(String content, Class<T> valueType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, valueType);
    }

    public static List<Object> toListObject(String content) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<List<Object>>() {
        });

    }

    public static String filterJson(String enumStr) {
        enumStr = enumStr.replace("“", "\"");
        enumStr = enumStr.replace("：", ":");
        enumStr = enumStr.replace("<br/>", "");
        enumStr = enumStr.replace("\\\"", "\"");
        enumStr = enumStr.replace("\n", "");
        enumStr = enumStr.replace("\",,\"", "\",\"");
        return enumStr;
    }

}
