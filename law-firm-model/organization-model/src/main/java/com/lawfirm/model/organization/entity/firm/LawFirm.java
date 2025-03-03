package com.lawfirm.model.organization.entity.firm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.organization.constants.FirmFieldConstants;
import com.lawfirm.model.organization.constants.OrganizationFieldConstants;
import com.lawfirm.model.organization.entity.base.BaseOrganizationEntity;
import com.lawfirm.model.organization.enums.FirmTypeEnum;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 律师事务所实体
 */
@Data
@TableName("org_law_firm")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class LawFirm extends BaseOrganizationEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 律所类型
     */
    @TableField(value = "type")
    private FirmTypeEnum type;

    /**
     * 执业许可证号
     */
    @TableField(value = "license_number")
    @Pattern(regexp = FirmFieldConstants.License.NUMBER_PATTERN, message = "执业许可证号格式不正确")
    private String licenseNumber;

    /**
     * 执业许可证有效期
     */
    @TableField(value = "license_expire_date")
    private transient LocalDate licenseExpireDate;

    /**
     * 统一社会信用代码
     */
    @TableField(value = "credit_code")
    @Pattern(regexp = FirmFieldConstants.Business.CREDIT_CODE_PATTERN, message = "统一社会信用代码格式不正确")
    private String creditCode;

    /**
     * 法定代表人
     */
    @TableField(value = "legal_representative")
    @Size(max = FirmFieldConstants.Business.LEGAL_REPRESENTATIVE_MAX_LENGTH, message = "法定代表人姓名长度不能超过{max}")
    private String legalRepresentative;

    /**
     * 成立日期
     */
    @TableField(value = "establish_date")
    private transient LocalDate establishDate;

    /**
     * 是否总所
     */
    @TableField(value = "is_head_office")
    private Boolean headOffice = true;

    /**
     * 总所ID（如果是分所则关联总所）
     */
    @TableField(value = "head_office_id")
    private Long headOfficeId;

    /**
     * 联系电话
     */
    @TableField(value = "phone")
    @Size(max = OrganizationFieldConstants.Contact.PHONE_MAX_LENGTH, message = "联系电话长度不能超过{max}")
    private String phone;

    /**
     * 联系邮箱
     */
    @TableField(value = "email")
    @Size(max = OrganizationFieldConstants.Contact.EMAIL_MAX_LENGTH, message = "联系邮箱长度不能超过{max}")
    private String email;

    /**
     * 详细地址
     */
    @TableField(value = "address")
    @Size(max = OrganizationFieldConstants.Contact.ADDRESS_MAX_LENGTH, message = "详细地址长度不能超过{max}")
    private String address;
} 
