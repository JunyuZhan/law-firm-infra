package com.lawfirm.model.cases.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.model.cases.enums.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class CaseQueryDTO extends BaseDTO {

    private String caseNumber;
    
    private String caseName;
    
    private CaseTypeEnum caseType;
    
    private List<CaseStatusEnum> caseStatus;
    
    private List<CaseProgressEnum> caseProgress;
    
    private CaseHandleTypeEnum caseHandleType;
    
    private CaseDifficultyEnum caseDifficulty;
    
    private CaseImportanceEnum caseImportance;
    
    private CasePriorityEnum casePriority;
    
    private String lawyer;
    
    private Long clientId;
    
    private Long lawFirmId;
    
    private Long departmentId;
    
    private LocalDateTime filingTimeStart;
    
    private LocalDateTime filingTimeEnd;
    
    private LocalDateTime closingTimeStart;
    
    private LocalDateTime closingTimeEnd;
    
    private String courtName;
    
    private String judgeName;
    
    private String courtCaseNumber;
    
    private Boolean isMajor;
    
    private Boolean hasConflict;
    
    private Boolean needApproval;
    
    private Boolean approved;
    
    private String approver;
    
    private LocalDateTime createTimeStart;
    
    private LocalDateTime createTimeEnd;
    
    private String createBy;
    
    private String keyword;
    
    private List<String> tags;
    
    private Boolean includeDeleted = false;

    @Override
    public CaseQueryDTO setId(Long id) {
        super.setId(id);
        return this;
    }
    
    @Override
    public CaseQueryDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }
    
    @Override
    public CaseQueryDTO setCreateBy(String createBy) {
        super.setCreateBy(createBy);
        return this;
    }
    
    @Override
    public CaseQueryDTO setUpdateBy(String updateBy) {
        super.setUpdateBy(updateBy);
        return this;
    }
} 