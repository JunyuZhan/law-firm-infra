package com.lawfirm.model.base.message.entity;

import com.lawfirm.model.base.message.enums.MessageType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消息模板实体
 */
@Data
@Entity
@Table(name = "message_template", indexes = {
    @Index(name = "idx_code", columnList = "code", unique = true)
})
public class MessageTemplateEntity {

    @Id
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 模板编码
     */
    @Column(name = "code", nullable = false)
    private String code;

    /**
     * 模板名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 模板内容
     */
    @Column(name = "content", length = 4000, nullable = false)
    private String content;

    /**
     * 消息类型
     */
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MessageType type;

    /**
     * 是否启用
     */
    @Column(name = "enabled")
    private Boolean enabled = true;

    /**
     * 参数说明（JSON格式）
     */
    @Column(name = "param_desc")
    private String paramDesc;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;
} 