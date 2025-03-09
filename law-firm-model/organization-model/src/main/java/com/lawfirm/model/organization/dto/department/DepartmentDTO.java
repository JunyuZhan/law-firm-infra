package com.lawfirm.model.organization.dto.department;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.organization.constants.DepartmentFieldConstants;
import com.lawfirm.model.organization.constants.OrganizationFieldConstants;
import com.lawfirm.model.organization.enums.DepartmentTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 部门数据传输对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DepartmentDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;


    /**
     * 部门编码
     */
    @NotBlank(message = "部门编码不能为空")
    @Pattern(regexp = OrganizationFieldConstants.Code.PATTERN, message = "编码格式不正确")
    @Size(min = OrganizationFieldConstants.Code.MIN_LENGTH, max = OrganizationFieldConstants.Code.MAX_LENGTH, message = "编码长度必须在{min}到{max}之间")
    private String code;

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    @Size(min = DepartmentFieldConstants.Name.MIN_LENGTH, max = DepartmentFieldConstants.Name.MAX_LENGTH, message = "名称长度必须在{min}到{max}之间")
    private String name;

    /**
     * 所属律所ID
     */
    @NotNull(message = "所属律所ID不能为空")
    private Long firmId;

    /**
     * 父级部门ID
     */
    private Long parentId;

    /**
     * 部门类型
     */
    @NotNull(message = "部门类型不能为空")
    private DepartmentTypeEnum type;

    /**
     * 负责人ID
     */
    private Long managerId;

    /**
     * 负责人姓名
     */
    @Size(max = DepartmentFieldConstants.Name.MAX_LENGTH, message = "负责人姓名长度不能超过{max}")
    private String managerName;

    /**
     * 联系电话
     */
    @Size(max = OrganizationFieldConstants.Contact.PHONE_MAX_LENGTH, message = "联系电话长度不能超过{max}")
    private String phone;

    /**
     * 联系邮箱
     */
    @Size(max = OrganizationFieldConstants.Contact.EMAIL_MAX_LENGTH, message = "联系邮箱长度不能超过{max}")
    private String email;

    /**
     * 职能描述
     */
    @Size(max = 500, message = "职能描述长度不能超过{max}")
    private String functionDescription;

    /**
     * 办公地点
     */
    @Size(max = 200, message = "办公地点长度不能超过{max}")
    private String officeLocation;

    /**
     * 业务领域（仅业务部门）
     */
    @Size(max = DepartmentFieldConstants.Business.DOMAIN_MAX_LENGTH, message = "业务领域长度不能超过{max}")
    private String businessDomain;

    /**
     * 案件类型列表（仅业务部门）
     */
    private transient List<String> caseTypes;

    /**
     * 职能类型（仅职能部门）
     */
    @Size(max = DepartmentFieldConstants.Functional.TYPE_MAX_LENGTH, message = "职能类型长度不能超过{max}")
    private String functionalType;

    /**
     * 服务范围（仅职能部门）
     */
    @Size(max = DepartmentFieldConstants.Functional.SCOPE_MAX_LENGTH, message = "服务范围长度不能超过{max}")
    private String serviceScope;

    /**
     * 描述
     */
    @Size(max = OrganizationFieldConstants.Description.MAX_LENGTH, message = "描述长度不能超过{max}")
    private String description;

    /**
     * 排序
     */
    private Integer sortOrder;
} 
