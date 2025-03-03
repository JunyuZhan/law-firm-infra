package com.lawfirm.model.organization.dto.firm;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.organization.constants.FirmFieldConstants;
import com.lawfirm.model.organization.constants.OrganizationFieldConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 分所数据传输对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BranchDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;


    /**
     * 分所编码
     */
    @NotBlank(message = "分所编码不能为空")
    @Pattern(regexp = OrganizationFieldConstants.Code.PATTERN, message = "编码格式不正确")
    @Size(min = OrganizationFieldConstants.Code.MIN_LENGTH, max = OrganizationFieldConstants.Code.MAX_LENGTH, message = "编码长度必须在{min}到{max}之间")
    private String code;

    /**
     * 分所名称
     */
    @NotBlank(message = "分所名称不能为空")
    @Size(max = FirmFieldConstants.Branch.NAME_MAX_LENGTH, message = "名称长度不能超过{max}")
    private String name;

    /**
     * 总所ID
     */
    @NotNull(message = "总所ID不能为空")
    private Long headOfficeId;

    /**
     * 执业许可证号
     */
    @NotBlank(message = "执业许可证号不能为空")
    @Pattern(regexp = FirmFieldConstants.License.NUMBER_PATTERN, message = "执业许可证号格式不正确")
    private String licenseNumber;

    /**
     * 执业许可证有效期
     */
    @NotNull(message = "执业许可证有效期不能为空")
    private transient LocalDate licenseExpireDate;

    /**
     * 统一社会信用代码
     */
    @NotBlank(message = "统一社会信用代码不能为空")
    @Pattern(regexp = FirmFieldConstants.Business.CREDIT_CODE_PATTERN, message = "统一社会信用代码格式不正确")
    private String creditCode;

    /**
     * 负责人
     */
    @NotBlank(message = "负责人不能为空")
    @Size(max = FirmFieldConstants.Business.LEGAL_REPRESENTATIVE_MAX_LENGTH, message = "负责人姓名长度不能超过{max}")
    private String manager;

    /**
     * 成立日期
     */
    @NotNull(message = "成立日期不能为空")
    private transient LocalDate establishDate;

    /**
     * 所在省份
     */
    @NotBlank(message = "所在省份不能为空")
    private String province;

    /**
     * 所在城市
     */
    @NotBlank(message = "所在城市不能为空")
    private String city;

    /**
     * 所在区县
     */
    private String district;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
    @Size(max = OrganizationFieldConstants.Contact.PHONE_MAX_LENGTH, message = "联系电话长度不能超过{max}")
    private String phone;

    /**
     * 联系邮箱
     */
    @Size(max = OrganizationFieldConstants.Contact.EMAIL_MAX_LENGTH, message = "联系邮箱长度不能超过{max}")
    private String email;

    /**
     * 详细地址
     */
    @NotBlank(message = "详细地址不能为空")
    @Size(max = OrganizationFieldConstants.Contact.ADDRESS_MAX_LENGTH, message = "详细地址长度不能超过{max}")
    private String address;

    /**
     * 描述
     */
    @Size(max = OrganizationFieldConstants.Description.MAX_LENGTH, message = "描述长度不能超过{max}")
    private String description;
} 
