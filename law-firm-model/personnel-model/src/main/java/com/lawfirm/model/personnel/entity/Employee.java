package com.lawfirm.model.personnel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.personnel.constant.EmployeeConstant;
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
@TableName(EmployeeConstant.Table.EMPLOYEE)
public class Employee extends Person {

    private static final long serialVersionUID = 1L;

    /**
     * 工号
     */
    @NotBlank(message = "工号不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "工号只能包含字母和数字")
    @TableField(EmployeeConstant.Field.WORK_NUMBER)
    private String workNumber;

    /**
     * 员工状态
     */
    @TableField("employee_status")
    private EmployeeStatusEnum employeeStatus;

    /**
     * 所属部门ID
     */
    @TableField("department_id")
    private Long departmentId;

    /**
     * 职位ID
     */
    @TableField("position_id")
    private Long positionId;

    /**
     * 入职日期
     */
    @TableField(EmployeeConstant.Field.ENTRY_DATE)
    private LocalDate entryDate;

    /**
     * 转正日期
     */
    @TableField(EmployeeConstant.Field.REGULAR_DATE)
    private LocalDate regularDate;

    /**
     * 离职日期
     */
    @TableField(EmployeeConstant.Field.RESIGN_DATE)
    private LocalDate resignDate;

    /**
     * 工作邮箱
     */
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "工作邮箱格式不正确")
    @TableField("work_email")
    private String workEmail;

    /**
     * 工作电话
     */
    @Pattern(regexp = "^\\d{3,4}-\\d{7,8}$", message = "工作电话格式不正确")
    @TableField("work_phone")
    private String workPhone;

    /**
     * 办公地点
     */
    @TableField("office_location")
    private String officeLocation;

    /**
     * 直属上级ID
     */
    @TableField("supervisor_id")
    private Long supervisorId;

    /**
     * 工作年限
     */
    @TableField("work_years")
    private Integer workYears;

    /**
     * 最高学历（1-高中 2-专科 3-本科 4-硕士 5-博士）
     */
    @TableField("education")
    private Integer education;

    /**
     * 毕业院校
     */
    @TableField("graduate_school")
    private String graduateSchool;

    /**
     * 专业
     */
    @TableField("major")
    private String major;

    /**
     * 毕业年份
     */
    @TableField("graduate_year")
    private Integer graduateYear;

    /**
     * 员工类型（1-全职 2-兼职 3-实习 4-外包）
     */
    @TableField("employee_type")
    private Integer employeeType;

    /**
     * 合同签订状态（0-未签订 1-已签订）
     */
    @TableField("contract_status")
    private Integer contractStatus;

    /**
     * 当前合同ID
     */
    @TableField("current_contract_id")
    private Long currentContractId;
} 