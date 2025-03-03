package com.lawfirm.model.organization.entity.department;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.organization.constants.DepartmentFieldConstants;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 业务部门实体
 */
@Data
@TableName("org_business_dept")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BusinessDept extends Department {

    private static final long serialVersionUID = 1L;


    /**
     * 业务领域
     */
    @TableField(value = "business_domain")
    @Size(max = DepartmentFieldConstants.Business.DOMAIN_MAX_LENGTH, message = "业务领域长度不能超过{max}")
    private String businessDomain;

    /**
     * 案件类型列表
     */
    @TableField(value = "case_types")
    private transient List<String> caseTypes;
} 
