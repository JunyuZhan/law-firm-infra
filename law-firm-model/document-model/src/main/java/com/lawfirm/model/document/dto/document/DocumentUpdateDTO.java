package com.lawfirm.model.document.dto.document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文档更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DocumentUpdateDTO extends DocumentCreateDTO {
    // 继承了DocumentCreateDTO的所有字段
    // 如果有特殊的更新字段，可以在这里添加
} 