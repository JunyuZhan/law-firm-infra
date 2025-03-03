package com.lawfirm.model.system.dto.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 配置更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ConfigUpdateDTO extends ConfigCreateDTO {
    // 继承了ConfigCreateDTO的所有字段
    // 如果有特殊的更新字段，可以在这里添加
    
    private static final long serialVersionUID = 1L;
} 