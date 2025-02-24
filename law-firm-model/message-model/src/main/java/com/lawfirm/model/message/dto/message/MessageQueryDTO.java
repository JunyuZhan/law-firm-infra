package com.lawfirm.model.message.dto.message;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 消息查询DTO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MessageQueryDTO extends BaseDTO {

    /**
     * 消息编号
     */
    private String messageNo;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息类型
     */
    private Integer messageType;

    /**
     * 消息状态
     */
    private Integer status;

    /**
     * 发送者ID
     */
    private Long senderId;

    /**
     * 发送者类型
     */
    private Integer senderType;

    /**
     * 接收者ID
     */
    private Long receiverId;

    /**
     * 接收者类型
     */
    private Integer receiverType;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 是否需要确认
     */
    private Integer needConfirm;

    /**
     * 是否已确认
     */
    private Integer isConfirmed;

    /**
     * 关联业务ID
     */
    private Long businessId;

    /**
     * 关联业务类型
     */
    private String businessType;

    /**
     * 发送时间-开始
     */
    private String sendTimeStart;

    /**
     * 发送时间-结束
     */
    private String sendTimeEnd;

    /**
     * 阅读时间-开始
     */
    private String readTimeStart;

    /**
     * 阅读时间-结束
     */
    private String readTimeEnd;
} 