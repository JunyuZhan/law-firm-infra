package com.lawfirm.model.system.dto.config;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 配置查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ConfigQueryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 配置键名
     */
    private String configKey;

    /**
     * 配置类型
     */
    private String configType;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    private Integer pageSize = 10;
} 