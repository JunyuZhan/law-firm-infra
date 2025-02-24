package com.lawfirm.model.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 配置视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ConfigVO extends BaseDTO {

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 配置键名
     */
    private String configKey;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 配置类型
     */
    private String configType;

    /**
     * 配置类型名称
     */
    private String configTypeName;

    /**
     * 配置描述
     */
    private String description;

    /**
     * 状态（0-启用，1-禁用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
} 