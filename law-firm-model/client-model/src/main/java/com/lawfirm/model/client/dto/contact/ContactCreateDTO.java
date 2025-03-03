package com.lawfirm.model.client.dto.contact;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 联系人创建DTO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ContactCreateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

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
     * 重要程度
     */
    private Integer importance;

    /**
     * 是否默认联系人
     */
    private Integer isDefault;
} 
