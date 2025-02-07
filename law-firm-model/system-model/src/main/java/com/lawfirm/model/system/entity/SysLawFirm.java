package com.lawfirm.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 律师事务所实体
 */
@Data
@TableName("sys_law_firm")
@EqualsAndHashCode(callSuper = true)
public class SysLawFirm extends BaseEntity {

    /**
     * 事务所名称
     */
    private String name;

    /**
     * 统一社会信用代码
     */
    private String creditCode;

    /**
     * 法定代表人
     */
    private String legalRepresentative;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 状态（0-待审核 1-正常 2-禁用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
} 