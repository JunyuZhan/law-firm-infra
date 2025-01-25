package com.lawfirm.cases.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.cases.enums.ReminderTypeEnum;
import com.lawfirm.cases.enums.ReminderStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 案件提醒实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("case_reminder")
public class CaseReminder extends BaseEntity {

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 提醒类型
     */
    private ReminderTypeEnum type;

    /**
     * 提醒标题
     */
    private String title;

    /**
     * 提醒内容
     */
    private String content;

    /**
     * 提醒时间
     */
    private LocalDateTime reminderTime;

    /**
     * 提前提醒时间(分钟)
     */
    private Integer advanceTime;

    /**
     * 提醒状态
     */
    private ReminderStatusEnum status;

    /**
     * 接收人ID
     */
    private Long receiverId;

    /**
     * 接收人姓名
     */
    private String receiverName;

    /**
     * 确认时间
     */
    private LocalDateTime confirmTime;

    /**
     * 确认备注
     */
    private String confirmRemark;
} 