package com.lawfirm.model.message.dto.notify;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 通知查询DTO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class NotifyQueryDTO extends BaseDTO {

    /**
     * 通知编号
     */
    private String notifyNo;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知渠道
     */
    private Integer channel;

    /**
     * 通知状态
     */
    private Integer status;

    /**
     * 发送者ID
     */
    private Long senderId;

    /**
     * 接收者ID
     */
    private Long receiverId;

    /**
     * 通知模板ID
     */
    private Long templateId;

    /**
     * 关联业务ID
     */
    private Long businessId;

    /**
     * 关联业务类型
     */
    private String businessType;

    /**
     * 计划发送时间-开始
     */
    private String planTimeStart;

    /**
     * 计划发送时间-结束
     */
    private String planTimeEnd;

    /**
     * 发送时间-开始
     */
    private String sendTimeStart;

    /**
     * 发送时间-结束
     */
    private String sendTimeEnd;

    /**
     * 送达时间-开始
     */
    private String deliveryTimeStart;

    /**
     * 送达时间-结束
     */
    private String deliveryTimeEnd;
} 