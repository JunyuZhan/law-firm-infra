package com.lawfirm.model.personnel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.personnel.constant.PersonnelConstants;
import com.lawfirm.model.personnel.enums.EmployeeStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 员工信息实体
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(PersonnelConstants.Table.EMPLOYEE)
public class Employee extends Person {

    private static final long serialVersionUID = 1L;

    /**
     * 工号
     */
    @NotBlank(message = "工号不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "工号只能包含字母和数字")
    @TableField(PersonnelConstants.Field.Employee.WORK_NUMBER)
    private String workNumber;

    /**
     * 关联的用户ID（auth模块）
     */
    @TableField(PersonnelConstants.Field.Employee.USER_ID)
    private Long userId;

    /**
     * 员工状态
     */
    @TableField("employee_status")
    private EmployeeStatusEnum employeeStatus;

    /**
     * 所属部门ID
     */
    @TableField(PersonnelConstants.Field.Employee.DEPARTMENT_ID)
    private Long departmentId;

    /**
     * 职位ID (引用自organization-model的Position实体)
     */
    @TableField(PersonnelConstants.Field.Employee.POSITION_ID)
    private Long positionId;

    /**
     * 入职日期
     */
    @TableField(PersonnelConstants.Field.Employee.ENTRY_DATE)
    private LocalDate entryDate;

    /**
     * 转正日期
     */
    @TableField(PersonnelConstants.Field.Employee.REGULAR_DATE)
    private LocalDate regularDate;

    /**
     * 离职日期
     */
    @TableField(PersonnelConstants.Field.Employee.RESIGN_DATE)
    private LocalDate resignDate;

    /**
     * 工作邮箱
     */
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "工作邮箱格式不正确")
    @TableField(PersonnelConstants.Field.Employee.WORK_EMAIL)
    private String workEmail;

    /**
     * 工作电话
     */
    @Pattern(regexp = "^\\d{3,4}-\\d{7,8}$", message = "工作电话格式不正确")
    @TableField(PersonnelConstants.Field.Employee.WORK_PHONE)
    private String workPhone;

    /**
     * 办公地点
     */
    @TableField(PersonnelConstants.Field.Employee.OFFICE_LOCATION)
    private String officeLocation;

    /**
     * 直属上级ID
     */
    @TableField(PersonnelConstants.Field.Employee.SUPERVISOR_ID)
    private Long supervisorId;

    /**
     * 工作年限
     */
    @TableField(PersonnelConstants.Field.Employee.WORK_YEARS)
    private Integer workYears;

    /**
     * 学历（1-专科 2-本科 3-硕士 4-博士 5-其他）
     */
    @TableField(PersonnelConstants.Field.Employee.EDUCATION)
    private Integer education;

    /**
     * 毕业院校
     */
    @TableField(PersonnelConstants.Field.Employee.GRADUATE_SCHOOL)
    private String graduateSchool;

    /**
     * 专业
     */
    @TableField(PersonnelConstants.Field.Employee.MAJOR)
    private String major;

    /**
     * 毕业年份
     */
    @TableField(PersonnelConstants.Field.Employee.GRADUATE_YEAR)
    private Integer graduateYear;

    /**
     * 员工类型（1-全职 2-兼职 3-实习 4-外包）
     */
    @TableField(PersonnelConstants.Field.Employee.EMPLOYEE_TYPE)
    private Integer employeeType;

    /**
     * 合同状态（0-未签 1-已签 2-过期 3-终止）
     */
    @TableField(PersonnelConstants.Field.Employee.CONTRACT_STATUS)
    private Integer contractStatus;

    /**
     * 当前合同ID
     */
    @TableField(PersonnelConstants.Field.Employee.CURRENT_CONTRACT_ID)
    private Long currentContractId;

    /**
     * 获取用户名
     */
    public String getUsername() {
        // 默认返回工号作为用户名
        return this.workNumber;
    }

    /**
     * 是否启用
     */
    public boolean getEnabled() {
        return this.employeeStatus != null && 
               (this.employeeStatus.getCode() == PersonnelConstants.Status.ON_JOB || 
                this.employeeStatus.getCode() == PersonnelConstants.Status.PROBATION);
    }
} 