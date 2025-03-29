package com.lawfirm.core.ai.service.impl;

import com.lawfirm.common.security.crypto.SensitiveDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * AI模块敏感数据服务实现
 * 提供针对AI场景的特殊脱敏处理
 */
@Component("aiSensitiveDataService")
public class AISensitiveDataServiceImpl implements SensitiveDataService {

    private static final Logger logger = LoggerFactory.getLogger(AISensitiveDataServiceImpl.class);
    
    // AI模型可能会泄露的关键词匹配模式
    private static final Pattern API_KEY_PATTERN = Pattern.compile("(sk-[a-zA-Z0-9]{20,})");
    private static final Pattern PROMPT_PATTERN = Pattern.compile("(system:|user:|assistant:)");
    
    @Override
    public String maskApiKey(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        
        // 特殊处理API Key格式
        return API_KEY_PATTERN.matcher(text).replaceAll(mr -> {
            String key = mr.group(1);
            return mask(key, 5, 0, '*');
        });
    }
    
    @Override
    public String maskPrompt(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        
        // 特殊处理提示词格式
        return PROMPT_PATTERN.matcher(text).replaceAll(mr -> "***:");
    }
    
    /**
     * AI场景下的自定义脱敏
     * 比通用实现做了更严格的处理
     */
    @Override
    public String mask(String text, int prefixLength, int suffixLength, char maskChar) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        
        // AI场景下，对敏感信息做更严格处理
        if (text.contains("password") || text.contains("secret") || 
            text.contains("key") || text.contains("token")) {
            // 敏感字段全部遮蔽
            return "*".repeat(text.length());
        }
        
        // 其他情况使用默认实现
        return SensitiveDataService.super.mask(text, prefixLength, suffixLength, maskChar);
    }
    
    // 其他方法使用接口的默认实现
} 