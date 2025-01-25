package com.lawfirm.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.core.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统升级日志
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_upgrade_log")
public class SysUpgradeLog extends BaseEntity {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 升级包ID
     */
    private Long packageId;
    
    /**
     * 操作类型
     */
    private String operation;
    
    /**
     * 详细信息
     */
    private String detail;
    
    /**
     * 是否成功
     */
    private Boolean success;
    
    /**
     * 错误信息
     */
    private String errorMessage;
} 