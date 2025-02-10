package com.lawfirm.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 律师事务所实体类
 */
@Getter
@Setter
@TableName("sys_law_firm")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysLawFirm extends ModelBaseEntity<SysLawFirm> {

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
     * 备注
     */
    private String remark;
} 