package com.lawfirm.model.knowledge.dto.article;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotNull;

/**
 * 文章更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ArticleUpdateDTO extends ArticleCreateDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @NotNull(message = "文章ID不能为空")
    private Long id;
} 