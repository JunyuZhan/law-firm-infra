package com.lawfirm.model.system.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "sys_upgrade_package")
@EqualsAndHashCode(callSuper = true)
public class SysUpgradePackage extends ModelBaseEntity<SysUpgradePackage> {
    
    /**
     * 升级包名称
     */
    @Column(name = "name")
    private String name;
    
    /**
     * 升级说明
     */
    @Column(name = "description")
    private String description;
    
    /**
     * 升级包路径
     */
    @Column(name = "path")
    private String path;
    
    /**
     * 文件MD5
     */
    @Column(name = "md5")
    private String md5;
    
    /**
     * 文件大小(字节)
     */
    @Column(name = "size")
    private Long size;
    
    /**
     * 错误信息
     */
    @Column(name = "error_message")
    private String errorMessage;

    /**
     * 是否需要备份
     */
    @Column(name = "need_backup")
    private Boolean needBackup;

    /**
     * 备份路径
     */
    @Column(name = "backup_path")
    private String backupPath;
} 