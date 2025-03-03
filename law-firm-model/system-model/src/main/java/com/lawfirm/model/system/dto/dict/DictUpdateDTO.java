package com.lawfirm.model.system.dto.dict;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 字典更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DictUpdateDTO extends DictCreateDTO {
    // 继承了DictCreateDTO的所有字段
    // 如果有特殊的更新字段，可以在这里添加
    
    private static final long serialVersionUID = 1L;
} 