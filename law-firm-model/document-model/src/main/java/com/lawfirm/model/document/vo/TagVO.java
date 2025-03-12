package com.lawfirm.model.document.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 文档标签视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TagVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签编码
     */
    private String tagCode;

    /**
     * 标签类型
     */
    private String tagType;

    /**
     * 标签颜色
     */
    private String color;

    /**
     * 标签描述
     */
    private String description;

    /**
     * 是否系统标签
     */
    private Boolean isSystem;

    /**
     * 是否启用
     */
    private Boolean isEnabled;

    /**
     * 使用次数
     */
    private Long useCount;

    /**
     * 最近使用时间
     */
    private Long lastUseTime;
} 