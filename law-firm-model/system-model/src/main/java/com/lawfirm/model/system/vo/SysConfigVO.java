package com.lawfirm.model.system.vo;

import com.lawfirm.model.system.enums.ConfigStatusEnum;
import com.lawfirm.model.system.enums.ConfigTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysConfigVO {

    private Long id;  // 配置ID

    private Long lawFirmId;  // 所属律所ID
    private String lawFirmName;  // 所属律所名称

    private String name;  // 配置名称
    private String configKey;  // 配置键
    private String configValue;  // 配置值
    private ConfigTypeEnum configType;  // 配置类型
    private ConfigStatusEnum status;  // 配置状态

    private String validationRule;  // 验证规则
    private String description;  // 描述

    private Boolean isSystem;  // 是否系统内置
    private Boolean isEncrypted;  // 是否加密存储
    private Integer sortOrder;  // 排序号

    private String remark;  // 备注

    private LocalDateTime createTime;  // 创建时间
    private String createBy;  // 创建人
    private LocalDateTime updateTime;  // 更新时间
    private String updateBy;  // 更新人
} 