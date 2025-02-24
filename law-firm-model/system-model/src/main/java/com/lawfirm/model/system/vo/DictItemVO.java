package com.lawfirm.model.system.vo;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 字典项视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DictItemVO extends BaseDTO {

    /**
     * 字典ID
     */
    private Long dictId;

    /**
     * 字典项标签
     */
    private String label;

    /**
     * 字典项值
     */
    private String value;

    /**
     * 字典项描述
     */
    private String description;

    /**
     * 是否默认（0-否，1-是）
     */
    private Integer isDefault;

    /**
     * 颜色类型
     */
    private String colorType;

    /**
     * CSS样式
     */
    private String cssClass;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 状态（0-启用，1-禁用）
     */
    private Integer status;
} 