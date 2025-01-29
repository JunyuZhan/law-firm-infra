package com.lawfirm.model.system.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.base.enums.StatusEnum;
import com.lawfirm.model.base.status.StatusAware;
import com.lawfirm.model.system.enums.ConfigStatusEnum;
import com.lawfirm.model.system.enums.ConfigTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "sys_config")
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends ModelBaseEntity implements StatusAware {

    private Long lawFirmId;  // 所属律所ID，为空表示全局配置

    @NotBlank(message = "配置名称不能为空")
    @Size(max = 100, message = "配置名称长度不能超过100个字符")
    @Column(nullable = false, length = 100)
    private String name;  // 配置名称

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String configKey;

    @NotBlank
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String configValue;

    @Column(length = 50)
    private ConfigTypeEnum configType;

    @Column(length = 50)
    private ConfigStatusEnum configStatus;

    @Column(length = 50)
    private StatusEnum status = StatusEnum.ENABLED;

    @Size(max = 200, message = "验证规则长度不能超过200个字符")
    @Column(length = 200)
    private String validationRule;  // 验证规则

    @Size(max = 500, message = "描述长度不能超过500个字符")
    @Column(length = 500)
    private String description;  // 描述

    private Boolean isSystem = false;  // 是否系统内置

    private Boolean isEncrypted = false;  // 是否加密存储

    private Integer sortOrder = 0;  // 排序号

    @Size(max = 500)
    @Column(length = 500)
    private String remark;

    @Override
    public StatusEnum getStatus() {
        return status;
    }

    @Override
    public void setStatus(StatusEnum status) {
        this.status = status;
        if (status == StatusEnum.ENABLED) {
            this.configStatus = ConfigStatusEnum.ENABLED;
        } else if (status == StatusEnum.DISABLED) {
            this.configStatus = ConfigStatusEnum.DISABLED;
        }
    }
} 