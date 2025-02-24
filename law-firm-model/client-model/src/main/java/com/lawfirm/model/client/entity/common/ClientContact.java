package com.lawfirm.model.client.entity.common;

import com.lawfirm.model.base.BaseModel;
import com.lawfirm.model.client.enums.ContactTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 客户联系人
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ClientContact extends BaseModel {

    /**
     * 客户ID
     */
    private Long clientId;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人类型
     * @see ContactTypeEnum
     */
    private Integer contactType;

    /**
     * 所属部门
     */
    private String department;

    /**
     * 职务职位
     */
    private String position;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 固定电话
     */
    private String telephone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 重要程度 1-普通 2-重要 3-非常重要
     */
    private Integer importance;

    /**
     * 是否默认联系人 0-否 1-是
     */
    private Integer isDefault;

    /**
     * 联系人状态 0-无效 1-有效
     */
    private Integer status;
} 