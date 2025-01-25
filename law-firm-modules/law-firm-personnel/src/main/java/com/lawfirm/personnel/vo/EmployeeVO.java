package com.lawfirm.personnel.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 员工信息VO
 */
@Data
public class EmployeeVO {
    
    private Long id;
    private String employeeNo;
    private String name;
    private Integer gender;
    private LocalDateTime birthday;
    private String idCard;
    private String mobile;
    private String email;
    
    // 组织信息
    private Long branchId;
    private String branchName;
    private Long departmentId;
    private String departmentName;
    private Long positionId;
    private String positionName;
    
    // 入职信息
    private LocalDateTime entryDate;
    private LocalDateTime regularDate;
    private LocalDateTime leaveDate;
    private Integer status;
    
    // 紧急联系人
    private String emergencyContact;
    private String emergencyPhone;
    
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 