package com.lawfirm.model.personnel.entity;

import com.lawfirm.model.base.entity.BaseEntity;
import com.lawfirm.model.personnel.enums.EmployeeTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "employee")
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends BaseEntity {

    @NotNull(message = "律所ID不能为空")
    @Column(nullable = false)
    private Long lawFirmId;  // 所属律所ID

    private Long branchId;   // 所属分支机构ID

    private Long departmentId;  // 所属部门ID

    private Long positionId;    // 职位ID

    @NotBlank(message = "员工姓名不能为空")
    @Size(max = 50, message = "员工姓名长度不能超过50个字符")
    @Column(nullable = false, length = 50)
    private String name;  // 姓名

    @Size(max = 20, message = "员工编号长度不能超过20个字符")
    @Column(length = 20)
    private String employeeCode;  // 员工编号

    @NotNull(message = "员工类型不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EmployeeTypeEnum employeeType;  // 员工类型

    @Size(max = 18, message = "身份证号长度不能超过18个字符")
    @Column(length = 18)
    private String idNumber;  // 身份证号

    private LocalDate birthDate;  // 出生日期

    @Size(max = 10, message = "性别长度不能超过10个字符")
    @Column(length = 10)
    private String gender;  // 性别

    @Size(max = 200, message = "地址长度不能超过200个字符")
    @Column(length = 200)
    private String address;  // 地址

    @Size(max = 20, message = "手机号长度不能超过20个字符")
    @Column(length = 20)
    private String mobile;  // 手机号

    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    @Column(length = 100)
    private String email;  // 邮箱

    private LocalDate entryDate;  // 入职日期

    private LocalDate leaveDate;  // 离职日期

    @Size(max = 20, message = "在职状态长度不能超过20个字符")
    @Column(length = 20)
    private String employmentStatus;  // 在职状态

    private Boolean enabled = true;  // 是否启用

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Column(length = 500)
    private String remark;  // 备注
} 