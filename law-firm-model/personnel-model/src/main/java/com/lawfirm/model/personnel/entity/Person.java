package com.lawfirm.model.personnel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import com.lawfirm.model.personnel.constant.PersonnelConstants;
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
@TableName(PersonnelConstants.Table.PERSON)
public class Person extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 人员编号
     */
    @NotBlank(message = "人员编号不能为空")
    @Size(max = 32, message = "人员编号长度不能超过32个字符")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "人员编号只能包含字母和数字")
    @TableField(PersonnelConstants.Field.Person.PERSON_CODE)
    private String personCode;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Size(max = 64, message = "姓名长度不能超过64个字符")
    @TableField(PersonnelConstants.Field.Person.NAME)
    private String name;

    /**
     * 英文名
     */
    @Size(max = 64, message = "英文名长度不能超过64个字符")
    @Pattern(regexp = "^[A-Za-z\\s]*$", message = "英文名只能包含英文字母和空格")
    @TableField(PersonnelConstants.Field.Person.ENGLISH_NAME)
    private String englishName;

    /**
     * 性别（0-未知 1-男 2-女）
     */
    @TableField(PersonnelConstants.Field.Person.GENDER)
    private Integer gender;

    /**
     * 出生日期
     */
    @TableField(PersonnelConstants.Field.Person.BIRTH_DATE)
    private LocalDate birthDate;

    /**
     * 证件类型（1-身份证 2-护照 3-其他）
     */
    @TableField(PersonnelConstants.Field.Person.ID_TYPE)
    private Integer idType;

    /**
     * 证件号码
     */
    @TableField(PersonnelConstants.Field.Person.ID_NUMBER)
    private String idNumber;

    /**
     * 手机号码
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号码格式不正确")
    @TableField(PersonnelConstants.Field.Person.MOBILE)
    private String mobile;

    /**
     * 电子邮箱
     */
    @Email(message = "邮箱格式不正确")
    @TableField(PersonnelConstants.Field.Person.EMAIL)
    private String email;

    /**
     * 人员类型
     */
    @TableField(PersonnelConstants.Field.Person.TYPE)
    private PersonTypeEnum type;

    /**
     * 所属律所ID
     */
    @TableField(PersonnelConstants.Field.Person.FIRM_ID)
    private Long firmId;

    /**
     * 紧急联系人
     */
    @Size(max = 32, message = "紧急联系人姓名长度不能超过32个字符")
    @TableField(PersonnelConstants.Field.Person.EMERGENCY_CONTACT)
    private String emergencyContact;

    /**
     * 紧急联系人电话
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "紧急联系人电话格式不正确")
    @TableField(PersonnelConstants.Field.Person.EMERGENCY_MOBILE)
    private String emergencyMobile;

    /**
     * 照片URL
     */
    @TableField(PersonnelConstants.Field.Person.PHOTO_URL)
    private String photoUrl;
} 