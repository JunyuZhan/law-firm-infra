package com.lawfirm.model.system.dto.upgrade;

import com.lawfirm.model.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 系统升级查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UpgradeQueryDTO extends BaseQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 升级版本号
     */
    private String version;

    /**
     * 升级标题
     */
    private String title;

    /**
     * 升级类型：PATCH-补丁升级，MINOR-小版本升级，MAJOR-大版本升级
     */
    private String upgradeType;

    /**
     * 升级状态：PENDING-待升级，UPGRADING-升级中，SUCCESS-升级成功，FAILED-升级失败，ROLLBACK-已回滚
     */
    private String status;

    /**
     * 是否强制升级
     */
    private Boolean forceUpgrade;
    
    /**
     * 计划升级开始时间
     */
    private LocalDateTime plannedStartTime;
    
    /**
     * 计划升级结束时间
     */
    private LocalDateTime plannedEndTime;
    
    /**
     * 实际升级开始时间
     */
    private LocalDateTime actualStartTime;
    
    /**
     * 实际升级结束时间
     */
    private LocalDateTime actualEndTime;
} 