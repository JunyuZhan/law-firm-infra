package com.lawfirm.model.personnel.dto.person;

import com.lawfirm.model.personnel.enums.PersonTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 创建人员的数据传输对象
 */
@Data
public class CreatePersonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
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
    @NotNull(message = "性别不能为空")
    private Integer gender;

    /**
     * 出生日期
     */
    @NotNull(message = "出生日期不能为空")
    private LocalDate birthDate;

    /**
     * 证件类型（1-身份证 2-护照 3-其他）
     */
    @NotNull(message = "证件类型不能为空")
    private Integer idType;

    /**
     * 证件号码
     */
    @NotBlank(message = "证件号码不能为空")
    private String idNumber;

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空")
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
    @NotNull(message = "人员类型不能为空")
    private PersonTypeEnum type;

    /**
     * 所属律所ID
     */
    @NotNull(message = "所属律所不能为空")
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
} 