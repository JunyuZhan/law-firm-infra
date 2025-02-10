package com.lawfirm.personnel.dto.response;

import com.lawfirm.model.personnel.enums.EmployeeTypeEnum;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmployeeResponse {
    
    private Long id;
    
    private Long lawFirmId;
    
    private String employeeCode;
    
    private String name;
    
    private EmployeeTypeEnum employeeType;
    
    private String employeeTypeName;
    
    private String idNumber;
    
    private LocalDate birthDate;
    
    private String gender;
    
    private String address;
    
    private String mobile;
    
    private String email;
    
    private Long branchId;
    
    private String branchName;
    
    private Long departmentId;
    
    private String departmentName;
    
    private Long positionId;
    
    private String positionName;
    
    private LocalDate entryDate;
    
    private LocalDate leaveDate;
    
    private String employmentStatus;
    
    private String employmentStatusName;
    
    private Boolean enabled;
    
    private String remark;
    
    private LocalDateTime createdTime;
    
    private LocalDateTime updatedTime;
    
    private String createdBy;
    
    private String updatedBy;
} 