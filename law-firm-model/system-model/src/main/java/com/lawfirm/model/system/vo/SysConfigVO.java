package com.lawfirm.model.system.vo;

import com.lawfirm.common.data.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigVO extends BaseVO {
    private static final long serialVersionUID = 1L;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 配置键
     */
    private String configKey;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 配置分组
     */
    private String configGroup;

    /**
     * 是否系统内置
     */
    private Boolean isSystem;

    /**
     * 备注
     */
    private String remark;
} 