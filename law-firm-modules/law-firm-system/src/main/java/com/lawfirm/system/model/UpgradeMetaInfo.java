package com.lawfirm.system.model;

import lombok.Data;

/**
 * 升级包元信息
 */
@Data
public class UpgradeMetaInfo {
    
    /**
     * 版本号
     */
    private String version;
    
    /**
     * 升级类型(1:整体升级 2:补丁升级)
     */
    private Integer type;
    
    /**
     * 是否需要备份
     */
    private Boolean needBackup;
    
    /**
     * 升级说明
     */
    private String description;
    
    /**
     * 依赖版本
     */
    private String dependVersion;
    
    /**
     * 发布时间
     */
    private String releaseTime;
} 