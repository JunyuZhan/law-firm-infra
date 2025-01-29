package com.lawfirm.model.organization.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.organization.enums.DepartmentTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "org_department")
@EqualsAndHashCode(callSuper = true)
public class Department extends ModelBaseEntity {

    @NotNull(message = "律所ID不能为空")
    @Column(nullable = false)
    private Long lawFirmId;  // 所属律所ID

    private Long branchId;  // 所属分支机构ID

    private Long parentId;  // 上级部门ID

    @NotBlank(message = "部门名称不能为空")
    @Size(max = 100, message = "部门名称长度不能超过100个字符")
    @Column(nullable = false, length = 100)
    private String name;  // 部门名称

    @Size(max = 50, message = "部门编号长度不能超过50个字符")
    @Column(length = 50)
    private String departmentCode;  // 部门编号

    @NotNull(message = "部门类型不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DepartmentTypeEnum departmentType;  // 部门类型

    @Size(max = 50, message = "负责人长度不能超过50个字符")
    @Column(length = 50)
    private String manager;  // 负责人

    @Size(max = 50, message = "联系电话长度不能超过50个字符")
    @Column(length = 50)
    private String phone;  // 联系电话

    @Size(max = 500, message = "职责说明长度不能超过500个字符")
    @Column(length = 500)
    private String duty;  // 职责说明

    private Integer sortOrder = 0;  // 排序号

    private Boolean enabled = true;  // 是否启用

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Column(length = 500)
    private String remark;  // 备注
} 