package com.lawfirm.model.personnel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.personnel.constant.PersonnelConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 联系方式实体
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(PersonnelConstant.Table.CONTACT)
public class Contact extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 人员ID
     */
    @TableField("person_id")
    private Long personId;

    /**
     * 联系类型（1-个人 2-工作 3-其他）
     */
    @TableField("type")
    private Integer type;

    /**
     * 手机号码
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号码格式不正确")
    @TableField("mobile")
    private String mobile;

    /**
     * 电话号码
     */
    @Pattern(regexp = "^\\d{3,4}-\\d{7,8}$", message = "电话号码格式不正确")
    @TableField("phone")
    private String phone;

    /**
     * 电子邮箱
     */
    @Email(message = "邮箱格式不正确")
    @TableField("email")
    private String email;

    /**
     * 微信号
     */
    @TableField("wechat")
    private String wechat;

    /**
     * QQ号
     */
    @TableField("qq")
    private String qq;

    /**
     * 国家
     */
    @TableField("country")
    private String country;

    /**
     * 省份
     */
    @TableField("province")
    private String province;

    /**
     * 城市
     */
    @TableField("city")
    private String city;

    /**
     * 区县
     */
    @TableField("district")
    private String district;

    /**
     * 详细地址
     */
    @Size(max = 256, message = "详细地址长度不能超过256个字符")
    @TableField("address")
    private String address;

    /**
     * 邮政编码
     */
    @Pattern(regexp = "^\\d{6}$", message = "邮政编码格式不正确")
    @TableField("postal_code")
    private String postalCode;

    /**
     * 是否默认联系方式
     */
    @TableField("is_default")
    private Boolean isDefault;

    /**
     * 备注
     */
    @Size(max = 256, message = "备注长度不能超过256个字符")
    @TableField("remark")
    private String remark;
} 