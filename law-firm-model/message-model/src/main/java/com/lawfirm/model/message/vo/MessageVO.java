package com.lawfirm.model.message.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 消息视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MessageVO extends BaseVO {

    private static final long serialVersionUID = 1L;

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
     */
    private Integer messageType;

    /**
     * 消息类型名称
     */
    private String messageTypeName;

    /**
     * 消息状态
     */
    private Integer status;

    /**
     * 消息状态名称
     */
    private String statusName;

    /**
     * 发送者ID
     */
    private Long senderId;

    /**
     * 发送者名称
     */
    private String senderName;

    /**
     * 发送者类型
     */
    private Integer senderType;

    /**
     * 发送者类型名称
     */
    private String senderTypeName;

    /**
     * 接收者ID
     */
    private Long receiverId;

    /**
     * 接收者名称
     */
    private String receiverName;

    /**
     * 接收者类型
     */
    private Integer receiverType;

    /**
     * 接收者类型名称
     */
    private String receiverTypeName;

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
     * 优先级
     */
    private Integer priority;

    /**
     * 优先级名称
     */
    private String priorityName;

    /**
     * 是否需要确认
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