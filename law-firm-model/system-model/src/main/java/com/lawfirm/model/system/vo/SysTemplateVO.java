package com.lawfirm.model.system.vo;

import com.lawfirm.common.data.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 系统模板VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysTemplateVO extends BaseVO {

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板编码
     */
    private String code;

    /**
     * 模板类型
     */
    private String type;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 模板参数
     */
    private String params;

    /**
     * 状态（0停用 1正常）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
} 