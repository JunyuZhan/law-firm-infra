package com.lawfirm.model.log.entity.business;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.log.entity.base.AuditableLog;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 操作日志实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_operation_log")
public class OperationLog extends AuditableLog {

    private static final long serialVersionUID = 1L;

    /**
     * 操作对象ID
     */
    @TableField("target_id")
    private Long targetId;

    /**
     * 操作对象类型
     */
    @TableField("target_type")
    private String targetType;

    /**
     * 操作对象名称
     */
    @TableField("target_name")
    private String targetName;

    /**
     * 操作方法
     */
    @TableField("method")
    private String method;

    /**
     * 操作参数
     */
    @TableField("params")
    private String params;

    /**
     * 操作结果
     */
    @TableField("result")
    private String result;

    /**
     * 操作耗时(毫秒)
     */
    @TableField("cost_time")
    private Long costTime;

    /**
     * 操作来源（如：PC端、移动端等）
     */
    @TableField("source")
    private String source;
} 