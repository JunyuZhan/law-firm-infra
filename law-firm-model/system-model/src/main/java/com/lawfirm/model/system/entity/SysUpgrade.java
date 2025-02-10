package com.lawfirm.model.system.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 系统升级实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "sys_upgrade")
@EqualsAndHashCode(callSuper = true)
public class SysUpgrade extends ModelBaseEntity<SysUpgrade> {

    /**
     * 升级包ID
     */
    @Column(name = "package_id")
    private Long packageId;

    /**
     * 升级包名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 升级包路径
     */
    @Column(name = "path")
    private String path;

    /**
     * 升级包大小（字节）
     */
    @Column(name = "size")
    private Long size;

    /**
     * 升级包MD5
     */
    @Column(name = "md5")
    private String md5;

    /**
     * 升级包描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 升级日志
     */
    @Column(name = "log")
    private String log;

    /**
     * 升级时间
     */
    @Column(name = "upgrade_time")
    private Long upgradeTime;
} 