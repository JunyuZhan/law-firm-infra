package com.lawfirm.model.message.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.message.enums.NotifyChannelEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 通知基类
 */
@Data
@TableName("base_notify")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BaseNotify extends BaseMessage {

    private static final long serialVersionUID = 1L;

    /**
     * 通知编号
     */
    @TableField("notify_no")
    private String notifyNo;

    /**
     * 通知标题
     */
    @TableField("title")
    private String title;

    /**
     * 通知内容
     */
    @TableField("content")
    private String content;

    /**
     * 通知渠道
     * @see NotifyChannelEnum
     */
    @TableField("channel")
    private NotifyChannelEnum channel;

    /**
     * 接收地址
     */
    @TableField("receiver_address")
    private String receiverAddress;

    /**
     * 计划发送时间
     */
    @TableField("plan_time")
    private LocalDateTime planTime;

    /**
     * 送达时间
     */
    @TableField("delivery_time")
    private LocalDateTime deliveryTime;

    /**
     * 重试次数
     */
    @TableField("retry_count")
    private Integer retryCount;

    /**
     * 最大重试次数
     */
    @TableField("max_retry_count")
    private Integer maxRetryCount;

    /**
     * 失败原因
     */
    @TableField("fail_reason")
    private String failReason;

    /**
     * 通知模板ID
     */
    @TableField("template_id")
    private Long templateId;

    /**
     * 模板参数（JSON格式）
     */
    @TableField("template_params")
    private String templateParams;

    /**
     * 通知配置（JSON格式）
     */
    @TableField("notify_config")
    private String notifyConfig;

    public void setChannel(NotifyChannelEnum channel) {
        this.channel = channel;
    }

    public NotifyChannelEnum getChannel() {
        return this.channel;
    }
} 