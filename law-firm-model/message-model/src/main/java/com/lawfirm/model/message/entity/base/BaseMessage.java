package com.lawfirm.model.message.entity.base;

import com.lawfirm.model.base.BaseModel;
import com.lawfirm.model.message.enums.MessageStatusEnum;
import com.lawfirm.model.message.enums.MessageTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 消息基类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BaseMessage extends BaseModel {

    /**
     * 消息编号
     */
    private String messageNo;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息类型
     * @see MessageTypeEnum
     */
    private Integer messageType;

    /**
     * 消息状态
     * @see MessageStatusEnum
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
     * 发送者类型 1-系统 2-用户
     */
    private Integer senderType;

    /**
     * 接收者ID
     */
    private Long receiverId;

    /**
     * 接收者名称
     */
    private String receiverName;

    /**
     * 接收者类型 1-用户 2-角色 3-部门 4-全部
     */
    private Integer receiverType;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 阅读时间
     */
    private LocalDateTime readTime;

    /**
     * 处理时间
     */
    private LocalDateTime processTime;

    /**
     * 优先级 1-低 2-中 3-高
     */
    private Integer priority;

    /**
     * 是否需要确认 0-否 1-是
     */
    private Integer needConfirm;

    /**
     * 确认时间
     */
    private LocalDateTime confirmTime;

    /**
     * 消息配置（JSON格式）
     */
    private String messageConfig;

    /**
     * 关联业务ID
     */
    private Long businessId;

    /**
     * 关联业务类型
     */
    private String businessType;
} 