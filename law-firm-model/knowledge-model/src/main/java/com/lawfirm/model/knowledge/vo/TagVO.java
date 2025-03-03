package com.lawfirm.model.knowledge.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 标签视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TagVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签编码
     */
    private String code;

    /**
     * 标签描述
     */
    private String description;

    /**
     * 使用次数
     */
    private Long useCount;

    /**
     * 排序权重
     */
    private Integer weight;

    /**
     * 是否推荐
     */
    private Boolean recommended;

    /**
     * 最近使用时间
     */
    private Long lastUseTime;
} 