package com.lawfirm.model.cases.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 案件提醒实体类
 */
@Data
@TableName("case_reminder")
public class CaseReminder {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 提醒类型（1-开庭 2-截止日期 3-重要节点）
     */
    private Integer type;

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
     * 接收人ID
     */
    private Long receiverId;

    /**
     * 接收人姓名
     */
    private String receiverName;

    /**
     * 提醒状态（0-未提醒 1-已提醒 2-已确认）
     */
    private Integer status;

    /**
     * 确认时间
     */
    private LocalDateTime confirmTime;

    /**
     * 确认备注
     */
    private String confirmRemark;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;
} 