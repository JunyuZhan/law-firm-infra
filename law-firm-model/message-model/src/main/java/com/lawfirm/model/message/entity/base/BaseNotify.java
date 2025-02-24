package com.lawfirm.model.message.entity.base;

import com.lawfirm.model.base.BaseModel;
import com.lawfirm.model.message.enums.NotifyChannelEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 通知基类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BaseNotify extends BaseModel {

    /**
     * 通知编号
     */
    private String notifyNo;

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
     * @see NotifyChannelEnum
     */
    private Integer channel;

    /**
     * 通知状态 0-待发送 1-发送中 2-发送成功 3-发送失败
     */
    private Integer status;

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
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 送达时间
     */
    private LocalDateTime deliveryTime;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 最大重试次数
     */
    private Integer maxRetryCount;

    /**
     * 失败原因
     */
    private String failReason;

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