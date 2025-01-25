package com.lawfirm.cases.model.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.enums.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class CaseUpdateDTO extends BaseDTO {
    
    @Size(max = 128, message = "案件名称长度不能超过128个字符")
    private String caseName;
    
    @Size(max = 512, message = "案件描述长度不能超过512个字符")
    private String description;
    
    private CaseTypeEnum caseType;
    
    private CaseProgressEnum progress;
    
    private CaseHandleTypeEnum handleType;
    
    private CaseDifficultyEnum difficulty;
    
    private CaseImportanceEnum importance;
    
    private CasePriorityEnum priority;
    
    private CaseFeeTypeEnum feeType;
    
    private CaseSourceEnum source;
    
    @Size(max = 64, message = "主办律师长度不能超过64个字符")
    private String lawyer;
    
    private Long clientId;
    
    private Long lawFirmId;
    
    private Long branchId;
    
    private Long departmentId;
    
    private BigDecimal fee;
    
    @Size(max = 50, message = "法院名称长度不能超过50个字符")
    private String courtName;
    
    @Size(max = 50, message = "法官姓名长度不能超过50个字符")
    private String judgeName;
    
    @Size(max = 50, message = "法院案号长度不能超过50个字符")
    private String courtCaseNumber;
    
    private Boolean isMajor;
    
    private Boolean hasConflict;
} 