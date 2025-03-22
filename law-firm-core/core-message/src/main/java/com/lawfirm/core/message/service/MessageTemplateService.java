package com.lawfirm.core.message.service;

/**
 * 消息模板服务接口
 * 提供消息模板的注册和获取功能
 */
public interface MessageTemplateService {
    
    /**
     * 注册消息模板
     * 
     * @param templateKey 模板键
     * @param templateContent 模板内容
     * @return 是否注册成功
     */
    boolean registerTemplate(String templateKey, String templateContent);
    
    /**
     * 获取消息模板
     * 
     * @param templateKey 模板键
     * @return 模板内容
     */
    String getTemplate(String templateKey);
    
    /**
     * 删除消息模板
     * 
     * @param templateKey 模板键
     * @return 是否删除成功
     */
    boolean removeTemplate(String templateKey);
} 