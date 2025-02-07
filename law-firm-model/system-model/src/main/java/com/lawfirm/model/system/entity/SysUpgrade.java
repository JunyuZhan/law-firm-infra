package com.lawfirm.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统升级实体类
 */
@Data
@TableName("sys_upgrade")
@EqualsAndHashCode(callSuper = true)
public class SysUpgrade extends BaseEntity {

    /**
     * 升级包名称
     */
    private String name;

    /**
     * 升级包版本
     */
    private String version;

    /**
     * 升级包路径
     */
    private String path;

    /**
     * 升级包大小（字节）
     */
    private Long size;

    /**
     * 升级包MD5
     */
    private String md5;

    /**
     * 升级包描述
     */
    private String description;

    /**
     * 升级状态（0-未升级，1-升级中，2-升级成功，3-升级失败）
     */
    private Integer status;

    /**
     * 升级日志
     */
    private String log;

    /**
     * 升级时间
     */
    private Long upgradeTime;

    /**
     * 备注
     */
    private String remark;
} 