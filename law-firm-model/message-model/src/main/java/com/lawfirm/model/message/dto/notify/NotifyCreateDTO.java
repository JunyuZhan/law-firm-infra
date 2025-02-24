package com.lawfirm.model.message.dto.notify;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 通知创建DTO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class NotifyCreateDTO extends BaseDTO {

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 通知渠道
     */
    private Integer channel;

    /**
     * 发送者ID
     */
    private Long senderId;

    /**
     * 发送者名称
     */
    private String senderName;

    /**
     * 接收者ID
     */
    private Long receiverId;

    /**
     * 接收者名称
     */
    private String receiverName;

    /**
     * 接收地址
     */
    private String receiverAddress;

    /**
     * 计划发送时间
     */
    private LocalDateTime planTime;

    /**
     * 最大重试次数
     */
    private Integer maxRetryCount;

    /**
     * 通知模板ID
     */
    private Long templateId;

    /**
     * 模板参数（JSON格式）
     */
    private String templateParams;

    /**
     * 通知配置（JSON格式）
     */
    private String notifyConfig;

    /**
     * 关联业务ID
     */
    private Long businessId;

    /**
     * 关联业务类型
     */
    private String businessType;
} 