package com.lawfirm.model.client.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 联系人视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ContactVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    private Long clientId;

    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人类型
     */
    private Integer contactType;

    /**
     * 联系人类型名称
     */
    private String contactTypeName;

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
     * 重要程度名称
     */
    private String importanceName;

    /**
     * 是否默认联系人 0-否 1-是
     */
    private Integer isDefault;

    /**
     * 是否默认联系人名称
     */
    private String isDefaultName;

    /**
     * 联系人状态 0-无效 1-有效
     */
    private Integer status;

    /**
     * 联系人状态名称
     */
    private String statusName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 
