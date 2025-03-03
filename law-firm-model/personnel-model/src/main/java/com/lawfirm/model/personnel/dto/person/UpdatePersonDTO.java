package com.lawfirm.model.personnel.dto.person;

import com.lawfirm.model.personnel.enums.PersonTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 更新人员的数据传输对象
 */
@Data
public class UpdatePersonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 姓名
     */
    @Size(max = 64, message = "姓名长度不能超过64个字符")
    private String name;

    /**
     * 英文名
     */
    @Size(max = 64, message = "英文名长度不能超过64个字符")
    @Pattern(regexp = "^[A-Za-z\\s]*$", message = "英文名只能包含英文字母和空格")
    private String englishName;

    /**
     * 性别（0-未知 1-男 2-女）
     */
    private Integer gender;

    /**
     * 出生日期
     */
    private LocalDate birthDate;

    /**
     * 证件类型（1-身份证 2-护照 3-其他）
     */
    private Integer idType;

    /**
     * 证件号码
     */
    private String idNumber;

    /**
     * 手机号码
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号码格式不正确")
    private String mobile;

    /**
     * 电子邮箱
     */
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 人员类型
     */
    private PersonTypeEnum type;

    /**
     * 所属律所ID
     */
    private Long firmId;

    /**
     * 紧急联系人
     */
    @Size(max = 32, message = "紧急联系人姓名长度不能超过32个字符")
    private String emergencyContact;

    /**
     * 紧急联系人电话
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "紧急联系人电话格式不正确")
    private String emergencyMobile;

    /**
     * 照片URL
     */
    private String photoUrl;

    /**
     * 状态（0-启用 1-禁用）
     */
    private Integer status;

    /**
     * 版本号
     */
    @NotNull(message = "版本号不能为空")
    private Integer version;
} 