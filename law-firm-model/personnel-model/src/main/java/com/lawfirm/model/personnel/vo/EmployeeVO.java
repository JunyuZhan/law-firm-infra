package com.lawfirm.model.personnel.vo;

import com.lawfirm.model.personnel.enums.EmployeeStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 员工视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class EmployeeVO extends PersonVO {

    private static final long serialVersionUID = 1L;

    /**
     * 工号
     */
    private String workNumber;

    /**
     * 员工状态
     */
    private EmployeeStatusEnum employeeStatus;

    /**
     * 所属部门ID
     */
    private Long departmentId;

    /**
     * 所属部门名称
     */
    private String departmentName;

    /**
     * 职位ID
     */
    private Long positionId;

    /**
     * 职位名称
     */
    private String positionName;

    /**
     * 入职日期
     */
    private LocalDate entryDate;

    /**
     * 转正日期
     */
    private LocalDate regularDate;

    /**
     * 离职日期
     */
    private LocalDate resignDate;

    /**
     * 工作邮箱
     */
    private String workEmail;

    /**
     * 工作电话
     */
    private String workPhone;

    /**
     * 办公地点
     */
    private String officeLocation;

    /**
     * 直属上级ID
     */
    private Long supervisorId;

    /**
     * 直属上级姓名
     */
    private String supervisorName;

    /**
     * 工作年限
     */
    private Integer workYears;

    /**
     * 最高学历（1-高中 2-专科 3-本科 4-硕士 5-博士）
     */
    private Integer education;

    /**
     * 学历描述
     */
    private String educationDesc;

    /**
     * 毕业院校
     */
    private String graduateSchool;

    /**
     * 专业
     */
    private String major;

    /**
     * 毕业年份
     */
    private Integer graduateYear;

    /**
     * 员工类型（1-全职 2-兼职 3-实习 4-外包）
     */
    private Integer employeeType;

    /**
     * 员工类型描述
     */
    private String employeeTypeDesc;

    /**
     * 合同签订状态（0-未签订 1-已签订）
     */
    private Integer contractStatus;

    /**
     * 合同签订状态描述
     */
    private String contractStatusDesc;

    /**
     * 当前合同ID
     */
    private Long currentContractId;

    /**
     * 当前合同编号
     */
    private String currentContractNumber;
}