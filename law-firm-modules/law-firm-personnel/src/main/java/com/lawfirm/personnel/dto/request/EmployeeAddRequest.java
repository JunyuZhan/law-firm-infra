package com.lawfirm.personnel.dto.request;

import com.lawfirm.model.personnel.enums.EmployeeTypeEnum;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
public class EmployeeAddRequest {
    
    @NotNull(message = "律所ID不能为空")
    private Long lawFirmId;

    @NotBlank(message = "姓名不能为空")
    @Size(max = 50, message = "姓名长度不能超过50个字符")
    private String name;
    
    @NotNull(message = "员工类型不能为空")
    private EmployeeTypeEnum employeeType;
    
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$", 
            message = "身份证号格式不正确")
    private String idNumber;
    
    private LocalDate birthDate;
    
    @Size(max = 10, message = "性别长度不能超过10个字符")
    private String gender;
    
    @Size(max = 200, message = "地址长度不能超过200个字符")
    private String address;
    
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;
    
    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    private String email;
    
    private Long branchId;
    
    private Long departmentId;
    
    private Long positionId;
    
    @NotNull(message = "入职日期不能为空")
    private LocalDate entryDate;
    
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
} 