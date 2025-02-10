package com.lawfirm.model.system.query;

import com.lawfirm.common.data.query.PageQuery;
import com.lawfirm.model.system.enums.ConfigStatusEnum;
import com.lawfirm.model.system.enums.ConfigTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigQuery extends PageQuery {

    private Long lawFirmId;  // 所属律所ID

    private String name;  // 配置名称

    private String configKey;  // 配置键

    private ConfigTypeEnum configType;  // 配置类型

    private ConfigStatusEnum status;  // 配置状态

    private Boolean isSystem;  // 是否系统内置

    private Boolean isEncrypted;  // 是否加密存储

    private Boolean enabled;  // 是否启用
} 