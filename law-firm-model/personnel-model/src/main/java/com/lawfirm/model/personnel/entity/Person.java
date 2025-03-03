package com.lawfirm.model.personnel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import com.lawfirm.model.personnel.constant.PersonnelConstant;
import com.lawfirm.model.personnel.enums.PersonTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * 人员基础信息实体
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(PersonnelConstant.Table.PERSON)
public class Person extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 人员编号
     */
    @NotBlank(message = "人员编号不能为空")
    @Size(max = 32, message = "人员编号长度不能超过32个字符")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "人员编号只能包含字母和数字")
    @TableField(PersonnelConstant.Field.PERSON_CODE)
    private String personCode;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Size(max = 64, message = "姓名长度不能超过64个字符")
    @TableField("name")
    private String name;

    /**
     * 英文名
     */
    @Size(max = 64, message = "英文名长度不能超过64个字符")
    @Pattern(regexp = "^[A-Za-z\\s]*$", message = "英文名只能包含英文字母和空格")
    @TableField("english_name")
    private String englishName;

    /**
     * 性别（0-未知 1-男 2-女）
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 出生日期
     */
    @TableField("birth_date")
    private LocalDate birthDate;

    /**
     * 证件类型（1-身份证 2-护照 3-其他）
     */
    @TableField("id_type")
    private Integer idType;

    /**
     * 证件号码
     */
    @TableField("id_number")
    private String idNumber;

    /**
     * 手机号码
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号码格式不正确")
    @TableField("mobile")
    private String mobile;

    /**
     * 电子邮箱
     */
    @Email(message = "邮箱格式不正确")
    @TableField("email")
    private String email;

    /**
     * 人员类型
     */
    @TableField("type")
    private PersonTypeEnum type;

    /**
     * 所属律所ID
     */
    @TableField("firm_id")
    private Long firmId;

    /**
     * 紧急联系人
     */
    @Size(max = 32, message = "紧急联系人姓名长度不能超过32个字符")
    @TableField("emergency_contact")
    private String emergencyContact;

    /**
     * 紧急联系人电话
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "紧急联系人电话格式不正确")
    @TableField("emergency_mobile")
    private String emergencyMobile;

    /**
     * 照片URL
     */
    @TableField("photo_url")
    private String photoUrl;
} 