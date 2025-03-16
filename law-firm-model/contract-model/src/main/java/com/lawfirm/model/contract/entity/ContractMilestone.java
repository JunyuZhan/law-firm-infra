package com.lawfirm.model.contract.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 合同里程碑实体类
 * 用于记录合同执行过程中的关键节点和时间点
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("contract_milestone")
public class ContractMilestone extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    
    @TableField("contract_id")
    private Long contractId;        // 关联的合同ID
    
    @TableField("milestone_name")
    private String milestoneName;   // 里程碑名称
    
    @TableField("description")
    private String description;     // 里程碑描述
    
    @TableField("plan_date")
    private Date planDate;          // 计划完成日期
    
    @TableField("actual_date")
    private Date actualDate;        // 实际完成日期
    
    @TableField("status")
    private Integer status;         // 状态(0-未开始，1-进行中，2-已完成，3-已延期)
    
    @TableField("completion_percent")
    private Integer completionPercent; // 完成百分比
    
    @TableField("responsible_id")
    private Long responsibleId;     // 负责人ID
    
    @TableField("linked_fee_id")
    private Long linkedFeeId;       // 关联的收费项ID
    
    @TableField("is_key_point")
    private Boolean isKeyPoint;     // 是否重要节点
    
    @TableField("is_reminder_sent")
    private Boolean isReminderSent; // 是否已发送提醒
    
    @TableField("reminder_days")
    private Integer reminderDays;   // 提前提醒天数
    
    @TableField("sequence")
    private Integer sequence;       // 序列号，用于排序
    
    @TableField("remarks")
    private String remarks;         // 备注说明
} 