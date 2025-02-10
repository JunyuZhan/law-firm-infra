package com.lawfirm.model.system.entity;

import com.lawfirm.common.core.enums.StatusEnum;
import com.lawfirm.common.core.status.StatusAware;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 系统升级日志实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "sys_upgrade_log")
@EqualsAndHashCode(callSuper = true)
public class SysUpgradeLog extends ModelBaseEntity<SysUpgradeLog> {
    
    /**
     * 升级包ID
     */
    @Column(name = "package_id")
    private Long packageId;
    
    /**
     * 升级状态
     */
    @Column(name = "status")
    private Integer status;
    
    /**
     * 升级时间
     */
    @Column(name = "upgrade_time")
    private Long upgradeTime;
    
    /**
     * 升级日志
     */
    @Column(name = "log")
    private String log;
    
    /**
     * 错误信息
     */
    @Column(name = "error_message")
    private String errorMessage;

    @Override
    public StatusEnum getStatus() {
        return super.getStatus();
    }

    public Integer getStatusValue() {
        return status;
    }
} 