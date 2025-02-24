package com.lawfirm.model.log.entity.business;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.log.entity.base.AuditableLog;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 业务日志实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_business_log")
public class BusinessLog extends AuditableLog {

    /**
     * 业务ID
     */
    @TableField("business_id")
    private Long businessId;

    /**
     * 业务编号
     */
    @TableField("business_no")
    private String businessNo;

    /**
     * 业务名称
     */
    @TableField("business_name")
    private String businessName;

    /**
     * 业务阶段
     */
    @TableField("business_stage")
    private String businessStage;

    /**
     * 业务状态
     */
    @TableField("business_status")
    private String businessStatus;

    /**
     * 处理人ID
     */
    @TableField("handler_id")
    private Long handlerId;

    /**
     * 处理人名称
     */
    @TableField("handler_name")
    private String handlerName;

    /**
     * 处理意见
     */
    @TableField("handle_opinion")
    private String handleOpinion;

    /**
     * 处理时间
     */
    @TableField("handle_time")
    private String handleTime;

    /**
     * 相关附件
     */
    @TableField("attachments")
    private String attachments;
} 