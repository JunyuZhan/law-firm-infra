package com.lawfirm.common.util.json;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

public class JsonPathUtils {
    
    public static Object read(String json, String path) {
        ReadContext context = JsonPath.parse(json);
        return context.read(path);
    }
    
    public static <T> T read(String json, String path, Class<T> type) {
        ReadContext context = JsonPath.parse(json);
        return context.read(path, type);
    }
    
    public static boolean exists(String json, String path) {
        try {
            ReadContext context = JsonPath.parse(json);
            return context.read(path) != null;
        } catch (Exception e) {
            return false;
        }
    }
} 