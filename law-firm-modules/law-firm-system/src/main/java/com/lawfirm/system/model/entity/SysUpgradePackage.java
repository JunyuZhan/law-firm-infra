package com.lawfirm.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.core.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统升级包
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_upgrade_package")
public class SysUpgradePackage extends BaseEntity {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 升级包名称
     */
    private String packageName;
    
    /**
     * 目标版本号
     */
    private String version;
    
    /**
     * 升级说明
     */
    private String description;
    
    /**
     * 升级包路径
     */
    private String filePath;
    
    /**
     * 文件MD5
     */
    private String md5;
    
    /**
     * 文件大小(字节)
     */
    private Long fileSize;
    
    /**
     * 状态(0:待升级 1:升级中 2:升级成功 3:升级失败)
     */
    private Integer status;
    
    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 是否需要备份
     */
    private Boolean needBackup;

    /**
     * 备份路径
     */
    private String backupPath;
} 