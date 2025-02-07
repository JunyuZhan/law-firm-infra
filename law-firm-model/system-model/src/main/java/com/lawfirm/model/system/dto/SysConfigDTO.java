package com.lawfirm.model.system.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 配置键
     */
    private String configKey;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 配置分组
     */
    private String configGroup;

    /**
     * 是否系统内置
     */
    private Boolean isSystem;

    /**
     * 备注
     */
    private String remark;
} 