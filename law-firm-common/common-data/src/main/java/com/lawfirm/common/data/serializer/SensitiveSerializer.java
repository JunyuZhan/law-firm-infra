package com.lawfirm.common.data.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.lawfirm.common.data.annotation.Sensitive;
import com.lawfirm.common.data.enums.SensitiveType;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * 敏感数据序列化器
 */
public class SensitiveSerializer extends JsonSerializer<String> implements ContextualSerializer {
    
    private SensitiveType type;
    
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (StringUtils.isBlank(value)) {
            gen.writeString(value);
            return;
        }
        gen.writeString(type.desensitize(value));
    }
    
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property == null) {
            return prov.findNullValueSerializer(null);
        }
        
        Sensitive annotation = property.getAnnotation(Sensitive.class);
        if (annotation == null) {
            return prov.findValueSerializer(property.getType(), property);
        }
        
        this.type = annotation.type();
        return this;
    }
} 