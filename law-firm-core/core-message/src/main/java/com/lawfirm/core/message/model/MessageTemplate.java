package com.lawfirm.core.message.model;

import com.lawfirm.model.base.message.enums.MessageType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageTemplate {
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
     * 消息类型
     */
    private MessageType type;

    /**
     * 标题模板
     */
    private String title;

    /**
     * 内容模板
     */
    private String content;

    /**
     * 是否启用
     */
    private boolean enabled;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 