package com.lawfirm.model.document.dto.template;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 模板更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TemplateUpdateDTO extends TemplateCreateDTO {
    private static final long serialVersionUID = 1L;
    // 继承了TemplateCreateDTO的所有字段
    // 如果有特殊的更新字段，可以在这里添加
} 