package com.lawfirm.model.system.dto.dict;

import com.lawfirm.model.base.dto.BaseDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 字典项更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DictItemUpdateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 字典ID
     */
    @NotNull(message = "字典ID不能为空")
    private Long dictId;

    /**
     * 字典项标签
     */
    @Size(max = 100, message = "字典项标签长度不能超过100个字符")
    private String dictLabel;

    /**
     * 字典项值
     */
    @Size(max = 100, message = "字典项值长度不能超过100个字符")
    private String dictValue;

    /**
     * 字典项描述
     */
    @Size(max = 500, message = "字典项描述长度不能超过500个字符")
    private String description;

    /**
     * 是否默认 0-否 1-是
     */
    private Integer isDefault;

    /**
     * 颜色类型
     */
    @Size(max = 50, message = "颜色类型长度不能超过50个字符")
    private String colorType;

    /**
     * CSS样式
     */
    @Size(max = 200, message = "CSS样式长度不能超过200个字符")
    private String cssClass;

    /**
     * 列表样式
     */
    @Size(max = 200, message = "列表样式长度不能超过200个字符")
    private String listClass;

    /**
     * 排序号
     */
    private Integer dictSort;

    /**
     * 状态（0-启用，1-禁用）
     */
    private Integer status;
} 