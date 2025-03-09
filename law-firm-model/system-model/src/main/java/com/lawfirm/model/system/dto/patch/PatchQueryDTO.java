package com.lawfirm.model.system.dto.patch;

import com.lawfirm.model.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 系统补丁查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PatchQueryDTO extends BaseQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 补丁版本号
     */
    private String version;

    /**
     * 补丁名称
     */
    private String name;
    
    /**
     * 补丁状态
     */
    private String status;
    
    /**
     * 是否强制安装
     */
    private Boolean forceInstall;
    
    /**
     * 计划安装开始时间
     */
    private LocalDateTime plannedStartTime;
    
    /**
     * 计划安装结束时间
     */
    private LocalDateTime plannedEndTime;
    
    /**
     * 实际安装开始时间
     */
    private LocalDateTime actualStartTime;
    
    /**
     * 实际安装结束时间
     */
    private LocalDateTime actualEndTime;
} 