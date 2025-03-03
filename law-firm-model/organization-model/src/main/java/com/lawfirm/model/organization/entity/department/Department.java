package com.lawfirm.model.organization.entity.department;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TreeEntity;
import com.lawfirm.model.organization.constants.DepartmentFieldConstants;
import com.lawfirm.model.organization.constants.OrganizationFieldConstants;
import com.lawfirm.model.organization.enums.DepartmentTypeEnum;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 部门基础实体
 */
@Data
@TableName("org_department")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Department extends TreeEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 所属律所ID
     */
    @TableField(value = "firm_id")
    private Long firmId;

    /**
     * 部门类型
     */
    @TableField(value = "type")
    private DepartmentTypeEnum type;

    /**
     * 负责人ID
     */
    @TableField(value = "manager_id")
    private Long managerId;

    /**
     * 负责人姓名
     */
    @TableField(value = "manager_name")
    @Size(max = DepartmentFieldConstants.Name.MAX_LENGTH, message = "负责人姓名长度不能超过{max}")
    private String managerName;

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
     * 职能描述
     */
    @TableField(value = "function_desc")
    @Size(max = 500, message = "职能描述长度不能超过{max}")
    private String functionDesc;

    /**
     * 办公地点
     */
    @TableField(value = "office_location")
    @Size(max = 200, message = "办公地点长度不能超过{max}")
    private String officeLocation;
} 
