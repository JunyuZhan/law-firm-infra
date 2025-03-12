package com.lawfirm.model.document.dto.tag;

import com.lawfirm.model.base.dto.BaseDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文档标签更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TagUpdateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 标签ID
     */
    @NotNull(message = "标签ID不能为空")
    private Long id;

    /**
     * 标签名称
     */
    @Size(max = 50, message = "标签名称不能超过50个字符")
    private String tagName;

    /**
     * 标签编码
     */
    @Size(max = 50, message = "标签编码不能超过50个字符")
    private String tagCode;

    /**
     * 标签类型
     */
    @Size(max = 20, message = "标签类型不能超过20个字符")
    private String tagType;

    /**
     * 标签颜色
     */
    @Size(max = 20, message = "标签颜色不能超过20个字符")
    private String color;

    /**
     * 标签描述
     */
    @Size(max = 200, message = "标签描述不能超过200个字符")
    private String description;

    /**
     * 是否启用
     */
    private Boolean isEnabled;
} 