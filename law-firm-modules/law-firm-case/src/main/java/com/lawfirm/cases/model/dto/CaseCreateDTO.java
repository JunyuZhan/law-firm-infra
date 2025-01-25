package com.lawfirm.cases.model.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class CaseCreateDTO extends BaseDTO {
    
    @NotBlank(message = "案件编号不能为空")
    @Size(max = 100, message = "案件编号长度不能超过100个字符")
    private String caseNumber;
    
    @NotBlank(message = "案件名称不能为空")
    @Size(max = 128, message = "案件名称长度不能超过128个字符")
    private String caseName;
    
    @Size(max = 512, message = "案件描述长度不能超过512个字符")
    private String description;
    
    @NotNull(message = "案件类型不能为空")
    private CaseTypeEnum caseType;
    
    @NotNull(message = "案件进展不能为空")
    private CaseProgressEnum progress;
    
    @NotNull(message = "办理方式不能为空")
    private CaseHandleTypeEnum handleType;
    
    private CaseDifficultyEnum difficulty;
    
    private CaseImportanceEnum importance;
    
    private CasePriorityEnum priority;
    
    @NotNull(message = "收费方式不能为空")
    private CaseFeeTypeEnum feeType;
    
    private CaseSourceEnum source;
    
    @NotBlank(message = "主办律师不能为空")
    @Size(max = 64, message = "主办律师长度不能超过64个字符")
    private String lawyer;
    
    @NotNull(message = "委托人ID不能为空")
    private Long clientId;
    
    @NotNull(message = "律师ID不能为空")
    private Long lawyerId;
    
    @NotNull(message = "律所ID不能为空")
    private Long lawFirmId;
    
    private Long branchId;
    
    private Long departmentId;
    
    @NotNull(message = "收费金额不能为空")
    private BigDecimal fee;
    
    private Boolean isMajor = false;
    
    private Boolean hasConflict = false;
    
    private String conflictReason;
    
    @Size(max = 50, message = "法院名称长度不能超过50个字符")
    private String courtName;
    
    @Size(max = 50, message = "法官姓名长度不能超过50个字符")
    private String judgeName;
    
    @Size(max = 50, message = "法院案号长度不能超过50个字符")
    private String courtCaseNumber;
    
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
} 