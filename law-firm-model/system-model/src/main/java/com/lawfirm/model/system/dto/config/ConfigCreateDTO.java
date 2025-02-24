package com.lawfirm.model.system.dto.config;

import com.lawfirm.model.base.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 配置创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ConfigCreateDTO extends BaseDTO {

    /**
     * 配置名称
     */
    @NotBlank(message = "配置名称不能为空")
    @Size(max = 50, message = "配置名称长度不能超过50个字符")
    private String configName;

    /**
     * 配置键名
     */
    @NotBlank(message = "配置键名不能为空")
    @Size(max = 100, message = "配置键名长度不能超过100个字符")
    private String configKey;

    /**
     * 配置值
     */
    @NotBlank(message = "配置值不能为空")
    private String configValue;

    /**
     * 配置类型
     */
    @NotBlank(message = "配置类型不能为空")
    private String configType;

    /**
     * 配置描述
     */
    @Size(max = 500, message = "配置描述长度不能超过500个字符")
    private String description;
} 