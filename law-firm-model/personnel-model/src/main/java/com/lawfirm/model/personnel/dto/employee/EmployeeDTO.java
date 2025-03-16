package com.lawfirm.model.personnel.dto.employee;

import com.lawfirm.model.personnel.dto.person.PersonDTO;
import com.lawfirm.model.personnel.enums.EmployeeStatusEnum;
import com.lawfirm.model.personnel.enums.EmployeeTypeEnum;
import com.lawfirm.model.personnel.enums.LawyerLevelEnum;
import com.lawfirm.model.personnel.enums.StaffFunctionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工数据传输对象
 * 包含所有类型员工的属性
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class EmployeeDTO extends PersonDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 工号
     */
    private String workNumber;

    /**
     * 关联的用户ID（auth模块）
     */
    private Long userId;

    /**
     * 员工状态
     */
    private EmployeeStatusEnum employeeStatus;
    
    /**
     * 员工类型
     */
    private EmployeeTypeEnum employeeType;

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
     * 合同签订状态（0-未签订 1-已签订）
     */
    private Integer contractStatus;

    /**
     * 当前合同ID
     */
    private Long currentContractId;

    /**
     * 当前合同编号
     */
    private String currentContractNumber;

    // =============== 律师特有属性 ===============
    
    /**
     * 律师执业证号（仅当employeeType=LAWYER时有效）
     */
    private String licenseNumber;

    /**
     * 执业证书发证日期（仅当employeeType=LAWYER时有效）
     */
    private LocalDate licenseIssueDate;

    /**
     * 执业证书失效日期（仅当employeeType=LAWYER时有效）
     */
    private LocalDate licenseExpireDate;

    /**
     * 执业年限（仅当employeeType=LAWYER时有效）
     */
    private Integer practiceYears;

    /**
     * 律师职级（仅当employeeType=LAWYER时有效）
     */
    private LawyerLevelEnum lawyerLevel;

    /**
     * 专业领域（仅当employeeType=LAWYER时有效）
     */
    private transient List<String> practiceAreas;

    /**
     * 擅长业务（仅当employeeType=LAWYER时有效）
     */
    private String expertise;

    /**
     * 个人简介（仅当employeeType=LAWYER时有效）
     */
    private String profile;

    /**
     * 主要业绩（仅当employeeType=LAWYER时有效）
     */
    private String achievements;

    /**
     * 团队ID（仅当employeeType=LAWYER时有效）
     */
    private Long teamId;

    /**
     * 导师ID（针对实习律师，仅当employeeType=LAWYER时有效）
     */
    private Long mentorId;

    /**
     * 是否合伙人（仅当employeeType=LAWYER时有效）
     */
    private Boolean partner;

    /**
     * 合伙人加入日期（仅当employeeType=LAWYER时有效）
     */
    private LocalDate partnerDate;

    /**
     * 股权比例（仅当employeeType=LAWYER时有效）
     */
    private Double equityRatio;

    /**
     * 执业状态（0-正常执业 1-暂停执业 2-注销执业）（仅当employeeType=LAWYER时有效）
     */
    private Integer practiceStatus;
    
    // =============== 行政人员特有属性 ===============
    
    /**
     * 职能类型（仅当employeeType=STAFF时有效）
     */
    private StaffFunctionEnum functionType;

    /**
     * 职能描述（仅当employeeType=STAFF时有效）
     */
    private String functionDesc;

    /**
     * 工作职责（仅当employeeType=STAFF时有效）
     */
    private String jobDuties;

    /**
     * 服务范围（仅当employeeType=STAFF时有效）
     */
    private String serviceScope;

    /**
     * 技能证书（仅当employeeType=STAFF时有效）
     */
    private String skillCertificates;

    /**
     * 所属中心类型（仅当employeeType=STAFF时有效）
     */
    private Integer centerType;

    /**
     * 是否兼任其他职能（仅当employeeType=STAFF时有效）
     */
    private Boolean hasOtherFunctions;

    /**
     * 兼任职能描述（仅当employeeType=STAFF时有效）
     */
    private String otherFunctions;
} 