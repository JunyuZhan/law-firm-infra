package com.lawfirm.core.message.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 消息模板
 */
@Data
@Accessors(chain = true)
public class MessageTemplate implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 模板ID
     */
    private String id;
    
    /**
     * 模板编码
     */
    private String code;
    
    /**
     * 模板名称
     */
    private String name;
    
    /**
     * 模板标题
     */
    private String title;
    
    /**
     * 模板内容
     */
    private String content;
    
    /**
     * 参数定义
     */
    private Map<String, String> params;
    
    /**
     * 是否启用
     */
    private Boolean enabled = true;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 租户ID
     */
    private Long tenantId;
} 