package com.lawfirm.model.personnel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.organization.enums.CenterTypeEnum;
import com.lawfirm.model.personnel.constant.PersonnelConstants;
import com.lawfirm.model.personnel.enums.EmployeeStatusEnum;
import com.lawfirm.model.personnel.enums.EmployeeTypeEnum;
import com.lawfirm.model.personnel.enums.LawyerLevelEnum;
import com.lawfirm.model.personnel.enums.StaffFunctionEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工信息实体
 * <p>
 * 该实体采用扁平化设计，合并了之前的Lawyer和Staff实体的属性。
 * 通过employeeType字段区分不同类型的员工，各类型员工特有属性仅在对应类型下有效。
 * </p>
 * 
 * <p>
 * 设计说明：
 * <ul>
 *   <li>基础属性：所有员工通用的基本属性，如工号、部门、职位等</li>
 *   <li>律师特有属性：仅当employeeType=LAWYER时有效，如执业证号、职级等</li>
 *   <li>行政人员特有属性：仅当employeeType=STAFF时有效，如职能类型、工作职责等</li>
 * </ul>
 * </p>
 * 
 * <p>
 * 使用示例：
 * <pre>
 * // 创建律师
 * Employee lawyer = new Employee();
 * lawyer.setName("张三");
 * lawyer.setEmployeeType(EmployeeTypeEnum.LAWYER);
 * lawyer.setLicenseNumber("L20230001"); // 律师特有属性
 * 
 * // 创建行政人员
 * Employee staff = new Employee();
 * staff.setName("李四");
 * staff.setEmployeeType(EmployeeTypeEnum.STAFF);
 * staff.setFunctionType(StaffFunctionEnum.ADMIN); // 行政人员特有属性
 * </pre>
 * </p>
 * 
 * <p>
 * 与auth-model的关联：
 * <ul>
 *   <li>通过userId字段关联到auth-model中的User实体</li>
 *   <li>auth-model中的User.employeeId引用本实体的id</li>
 * </ul>
 * </p>
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
     * 用于建立与auth-model中User实体的关联
     */
    @TableField(PersonnelConstants.Field.Employee.USER_ID)
    private Long userId;

    /**
     * 员工状态
     */
    @TableField("employee_status")
    private EmployeeStatusEnum employeeStatus;
    
    /**
     * 员工类型
     * 用于区分不同类型的员工，决定哪些特有属性有效
     */
    @TableField("employee_type")
    private EmployeeTypeEnum employeeType;

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
     * 合同状态（0-未签 1-已签 2-过期 3-终止）
     */
    @TableField(PersonnelConstants.Field.Employee.CONTRACT_STATUS)
    private Integer contractStatus;

    /**
     * 当前合同ID
     */
    @TableField(PersonnelConstants.Field.Employee.CURRENT_CONTRACT_ID)
    private Long currentContractId;
    
    // =============== 律师特有属性 ===============
    
    /**
     * 律师执业证号（仅当employeeType=LAWYER时有效）
     */
    @TableField("license_number")
    private String licenseNumber;

    /**
     * 执业证书发证日期（仅当employeeType=LAWYER时有效）
     */
    @TableField("license_issue_date")
    private LocalDate licenseIssueDate;

    /**
     * 执业证书失效日期（仅当employeeType=LAWYER时有效）
     */
    @TableField("license_expire_date")
    private LocalDate licenseExpireDate;

    /**
     * 执业年限（仅当employeeType=LAWYER时有效）
     */
    @TableField("practice_years")
    private Integer practiceYears;

    /**
     * 律师职级（仅当employeeType=LAWYER时有效）
     */
    @TableField("lawyer_level")
    private LawyerLevelEnum lawyerLevel;

    /**
     * 专业领域（仅当employeeType=LAWYER时有效）
     */
    @TableField("practice_areas")
    private transient List<String> practiceAreas;

    /**
     * 擅长业务（仅当employeeType=LAWYER时有效）
     */
    @TableField("expertise")
    private String expertise;

    /**
     * 个人简介（仅当employeeType=LAWYER时有效）
     */
    @TableField("profile")
    private String profile;

    /**
     * 主要业绩（仅当employeeType=LAWYER时有效）
     */
    @TableField("achievements")
    private String achievements;

    /**
     * 团队ID（仅当employeeType=LAWYER时有效）
     */
    @TableField("team_id")
    private Long teamId;

    /**
     * 导师ID（针对实习律师，仅当employeeType=LAWYER时有效）
     */
    @TableField("mentor_id")
    private Long mentorId;

    /**
     * 是否合伙人（仅当employeeType=LAWYER时有效）
     */
    @TableField("is_partner")
    private Boolean partner;

    /**
     * 合伙人加入日期（仅当employeeType=LAWYER时有效）
     */
    @TableField("partner_date")
    private LocalDate partnerDate;

    /**
     * 股权比例（仅当employeeType=LAWYER时有效）
     */
    @TableField("equity_ratio")
    private Double equityRatio;
    
    // =============== 行政人员特有属性 ===============
    
    /**
     * 职能类型（仅当employeeType=STAFF时有效）
     */
    @TableField("function_type")
    private StaffFunctionEnum functionType;

    /**
     * 职能描述（仅当employeeType=STAFF时有效）
     */
    @TableField("function_desc")
    private String functionDesc;

    /**
     * 工作职责（仅当employeeType=STAFF时有效）
     */
    @TableField("job_duties")
    private String jobDuties;

    /**
     * 服务范围（仅当employeeType=STAFF时有效）
     */
    @TableField("service_scope")
    private String serviceScope;

    /**
     * 技能证书（仅当employeeType=STAFF时有效）
     */
    @TableField("skill_certificates")
    private String skillCertificates;

    /**
     * 所属中心（仅当employeeType=STAFF时有效）
     */
    @TableField("center_type")
    private CenterTypeEnum centerType;

    /**
     * 是否兼任其他职能（仅当employeeType=STAFF时有效）
     */
    @TableField("has_other_functions")
    private Boolean hasOtherFunctions;

    /**
     * 兼任职能描述（仅当employeeType=STAFF时有效）
     */
    @TableField("other_functions")
    private String otherFunctions;

    /**
     * 离职原因
     */
    @TableField("resign_reason")
    private String resignReason;

    // =============== 辅助方法 ===============
    
    /**
     * 获取用户名
     * 用于与认证系统集成
     * 
     * @return 用户名（默认为工号）
     */
    public String getUsername() {
        // 默认返回工号作为用户名
        return this.workNumber;
    }

    /**
     * 判断员工是否启用
     * 用于与认证系统集成
     * 
     * @return 是否启用
     */
    public boolean getEnabled() {
        return this.employeeStatus != null && 
               (this.employeeStatus.getCode() == PersonnelConstants.Status.ON_JOB || 
                this.employeeStatus.getCode() == PersonnelConstants.Status.PROBATION);
    }
    
    /**
     * 判断是否是律师
     * 
     * @return 是否是律师
     */
    public boolean isLawyer() {
        return this.employeeType == EmployeeTypeEnum.LAWYER;
    }
    
    /**
     * 判断是否是行政人员
     * 
     * @return 是否是行政人员
     */
    public boolean isStaff() {
        return this.employeeType == EmployeeTypeEnum.STAFF;
    }
    
    /**
     * 获取是否合伙人
     * 
     * @return 是否合伙人
     */
    public Boolean getIsPartner() {
        return this.partner;
    }
    
    /**
     * 设置是否合伙人
     * 
     * @param isPartner 是否合伙人
     */
    public void setIsPartner(Boolean isPartner) {
        this.partner = isPartner;
    }
    
    /**
     * 获取职能类型
     * 
     * @return 职能类型
     */
    public StaffFunctionEnum getStaffFunction() {
        return this.functionType;
    }
    
    /**
     * 设置职能类型
     * 
     * @param staffFunction 职能类型
     */
    public void setStaffFunction(StaffFunctionEnum staffFunction) {
        this.functionType = staffFunction;
    }
} 