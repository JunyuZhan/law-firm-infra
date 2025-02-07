package com.lawfirm.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统模板实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_template")
public class SysTemplate extends BaseEntity {

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
     * 状态（0-禁用，1-启用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
} 