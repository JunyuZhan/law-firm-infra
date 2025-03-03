package com.lawfirm.model.organization.entity.department;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.organization.constants.DepartmentFieldConstants;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 职能部门实体
 */
@Data
@TableName("org_functional_dept")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class FunctionalDept extends Department {

    private static final long serialVersionUID = 1L;


    /**
     * 职能类型
     */
    @TableField(value = "functional_type")
    @Size(max = DepartmentFieldConstants.Functional.TYPE_MAX_LENGTH, message = "职能类型长度不能超过{max}")
    private String functionalType;

    /**
     * 服务范围
     */
    @TableField(value = "service_scope")
    @Size(max = DepartmentFieldConstants.Functional.SCOPE_MAX_LENGTH, message = "服务范围长度不能超过{max}")
    private String serviceScope;
} 
