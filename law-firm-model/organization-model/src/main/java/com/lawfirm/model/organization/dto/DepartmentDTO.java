package com.lawfirm.model.organization.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.organization.enums.DepartmentTypeEnum;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class DepartmentDTO extends BaseDTO {

    private Long lawFirmId;  // 所属律所ID

    private Long branchId;  // 所属分支机构ID

    private Long parentId;  // 上级部门ID

    private String name;  // 部门名称

    private String departmentCode;  // 部门编号

    private DepartmentTypeEnum departmentType;  // 部门类型

    private String manager;  // 负责人

    private String phone;  // 联系电话

    private String duty;  // 职责说明

    private Integer sortOrder;  // 排序号

    private Boolean enabled;  // 是否启用

    private String remark;  // 备注

    private List<DepartmentDTO> children;  // 子部门列表
} 