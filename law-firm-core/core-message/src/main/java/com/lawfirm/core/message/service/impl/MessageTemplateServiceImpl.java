package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.service.MessageTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息模板服务实现类
 * 提供消息模板的管理功能
 */
@Slf4j
@Service("coreMessageTemplateServiceImpl")
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MessageTemplateServiceImpl implements MessageTemplateService {

    /**
     * 模板存储，使用线程安全的ConcurrentHashMap
     */
    private final Map<String, String> templates = new ConcurrentHashMap<>();

    /**
     * 注册消息模板
     *
     * @param templateKey     模板键
     * @param templateContent 模板内容
     * @return 是否注册成功
     */
    @Override
    public boolean registerTemplate(String templateKey, String templateContent) {
        if (templateKey == null || templateKey.isEmpty() || templateContent == null) {
            log.warn("模板注册失败：键或内容为空，key={}", templateKey);
            return false;
        }

        templates.put(templateKey, templateContent);
        log.info("模板注册成功：key={}", templateKey);
        return true;
    }

    /**
     * 获取消息模板
     *
     * @param templateKey 模板键
     * @return 模板内容，如果模板不存在则返回null
     */
    @Override
    public String getTemplate(String templateKey) {
        String template = templates.get(templateKey);
        if (template == null) {
            log.warn("模板不存在：key={}", templateKey);
        }
        return template;
    }

    /**
     * 删除消息模板
     *
     * @param templateKey 模板键
     * @return 是否删除成功
     */
    @Override
    public boolean removeTemplate(String templateKey) {
        if (templateKey == null || templateKey.isEmpty()) {
            log.warn("模板删除失败：键为空");
            return false;
        }

        String removed = templates.remove(templateKey);
        boolean success = removed != null;
        if (success) {
            log.info("模板删除成功：key={}", templateKey);
        } else {
            log.warn("模板删除失败：模板不存在，key={}", templateKey);
        }
        return success;
    }
} 