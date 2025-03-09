package com.lawfirm.model.system.entity.upgrade;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 系统补丁实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_patch")
public class Patch extends ModelBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 所属升级ID
     */
    @TableField("upgrade_id")
    private Long upgradeId;

    /**
     * 补丁文件路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 补丁描述
     */
    @TableField("description")
    private String description;

    /**
     * 补丁类型：SQL-SQL补丁，SCRIPT-脚本补丁，FILE-文件补丁
     */
    @TableField("patch_type")
    private String patchType;

    /**
     * 执行顺序
     */
    @TableField("execute_order")
    private Integer executeOrder;

    /**
     * 补丁状态：PENDING-待执行，EXECUTING-执行中，SUCCESS-执行成功，FAILED-执行失败，ROLLBACK-已回滚
     */
    @TableField("patch_status")
    private String patchStatus;

    /**
     * 执行时间
     */
    @TableField("execute_time")
    private Long executeTime;

    /**
     * 回滚时间
     */
    @TableField("rollback_time")
    private Long rollbackTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
} 