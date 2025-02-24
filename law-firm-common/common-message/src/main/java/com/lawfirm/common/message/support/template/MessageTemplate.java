package com.lawfirm.common.message.support.template;

import java.util.Map;

/**
 * 消息模板基类
 * 提供模板解析的基础功能
 */
public abstract class MessageTemplate {
    
    /**
     * 模板ID
     */
    protected String templateId;
    
    /**
     * 模板内容
     */
    protected String content;
    
    /**
     * 解析模板
     *
     * @param params 模板参数
     * @return 解析后的内容
     */
    public abstract String parse(Map<String, Object> params);
    
    /**
     * 验证模板参数
     *
     * @param params 模板参数
     * @return 是否验证通过
     */
    public abstract boolean validate(Map<String, Object> params);
    
    /**
     * 获取模板所需的参数列表
     *
     * @return 参数列表
     */
    public abstract String[] getRequiredParams();
} 