package com.lawfirm.core.audit.util;

import com.lawfirm.core.audit.annotation.AuditIgnore;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 字段变更工具类
 */
@UtilityClass
public class FieldChangeUtils {

    /**
     * 比较指定字段的变更
     *
     * @param before 变更前对象
     * @param after  变更后对象
     * @param fields 需要比较的字段
     * @return 变更的字段及其值 Map<字段名, [旧值, 新值]>
     */
    public Map<String, Object[]> compareFields(Object before, Object after, String... fields) {
        Map<String, Object[]> changes = new HashMap<>(fields.length);
        
        if (before == null || after == null) {
            return changes;
        }

        Arrays.stream(fields).forEach(fieldName -> {
            try {
                Field field = ReflectionUtils.findField(before.getClass(), fieldName);
                if (field != null && !field.isAnnotationPresent(AuditIgnore.class)) {
                    field.setAccessible(true);
                    Object beforeValue = field.get(before);
                    Object afterValue = field.get(after);
                    
                    if (!Objects.equals(beforeValue, afterValue)) {
                        changes.put(fieldName, new Object[]{beforeValue, afterValue});
                    }
                }
            } catch (Exception e) {
                // 忽略反射异常
            }
        });

        return changes;
    }

    /**
     * 获取对象指定字段的值
     */
    public Object getFieldValue(Object target, String fieldName) {
        try {
            Field field = ReflectionUtils.findField(target.getClass(), fieldName);
            if (field != null) {
                field.setAccessible(true);
                return field.get(target);
            }
        } catch (Exception e) {
            // 忽略反射异常
        }
        return null;
    }
} 