package com.lawfirm.model.client.vo.tag;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 客户标签视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ClientTagVO extends BaseVO {
    
    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签颜色
     */
    private String color;

    /**
     * 标签类型
     */
    private String tagType;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 状态（0-禁用 1-启用）
     */
    private Integer status;
} 