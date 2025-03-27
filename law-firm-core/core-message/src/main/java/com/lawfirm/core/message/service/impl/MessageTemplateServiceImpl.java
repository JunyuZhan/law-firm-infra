package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.service.MessageTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息模板服务实现类
 */
@Slf4j
@Service("messageTemplateServiceImpl")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MessageTemplateServiceImpl implements MessageTemplateService {
    
    /**
     * 模板存储
     */
    private final Map<String, String> templateMap = new ConcurrentHashMap<>();
    
    @Override
    public boolean registerTemplate(String templateKey, String templateContent) {
        if (templateKey == null || templateContent == null) {
            return false;
        }
        
        templateMap.put(templateKey, templateContent);
        log.info("注册消息模板: {}", templateKey);
        return true;
    }
    
    @Override
    public String getTemplate(String templateKey) {
        if (templateKey == null) {
            return null;
        }
        
        return templateMap.get(templateKey);
    }
    
    @Override
    public boolean removeTemplate(String templateKey) {
        if (templateKey == null) {
            return false;
        }
        
        String removed = templateMap.remove(templateKey);
        if (removed != null) {
            log.info("删除消息模板: {}", templateKey);
            return true;
        }
        return false;
    }
} 