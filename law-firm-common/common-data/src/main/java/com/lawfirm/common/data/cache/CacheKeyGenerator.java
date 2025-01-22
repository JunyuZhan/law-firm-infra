package com.lawfirm.common.data.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 自定义缓存键生成器
 */
@Component
public class CacheKeyGenerator implements KeyGenerator {

    private static final String DELIMITER = ":";

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder key = new StringBuilder();
        key.append(target.getClass().getSimpleName()).append(DELIMITER);
        key.append(method.getName());
        
        if (params.length > 0) {
            key.append(DELIMITER);
            key.append(Arrays.deepToString(params));
        }
        
        return key.toString();
    }
} 