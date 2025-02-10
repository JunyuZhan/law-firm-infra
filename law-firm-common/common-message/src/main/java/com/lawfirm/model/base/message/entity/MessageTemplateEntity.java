package com.lawfirm.model.base.message.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 消息模板实体
 */
@Data
@Entity
@Table(name = "message_template")
@Accessors(chain = true)
public class MessageTemplateEntity {
    
    @Id
    private String id;
    
    /**
     * 模板编码
     */
    private String code;
    
    /**
     * 模板类型
     */
    private String type;
    
    /**
     * 标题模板
     */
    private String title;
    
    /**
     * 内容模板
     */
    private String content;
    
    /**
     * 优先级
     */
    private Integer priority;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 