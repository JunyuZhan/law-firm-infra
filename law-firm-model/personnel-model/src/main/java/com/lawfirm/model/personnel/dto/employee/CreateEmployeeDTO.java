package com.lawfirm.model.personnel.dto.employee;

import com.lawfirm.model.personnel.dto.person.CreatePersonDTO;
import com.lawfirm.model.personnel.enums.EmployeeStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 创建员工的数据传输对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateEmployeeDTO extends CreatePersonDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 工号
     */
    @NotBlank(message = "工号不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "工号只能包含字母和数字")
    private String workNumber;

    /**
     * 员工状态
     */
    @NotNull(message = "员工状态不能为空")
    private EmployeeStatusEnum employeeStatus;

    /**
     * 所属部门ID
     */
    @NotNull(message = "所属部门不能为空")
    private Long departmentId;

    /**
     * 职位ID
     */
    @NotNull(message = "职位不能为空")
    private Long positionId;

    /**
     * 入职日期
     */
    @NotNull(message = "入职日期不能为空")
    private LocalDate entryDate;

    /**
     * 工作邮箱
     */
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "工作邮箱格式不正确")
    private String workEmail;

    /**
     * 工作电话
     */
    @Pattern(regexp = "^\\d{3,4}-\\d{7,8}$", message = "工作电话格式不正确")
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
     * 员工类型（1-全职 2-兼职 3-实习 4-外包）
     */
    @NotNull(message = "员工类型不能为空")
    private Integer employeeType;
} 