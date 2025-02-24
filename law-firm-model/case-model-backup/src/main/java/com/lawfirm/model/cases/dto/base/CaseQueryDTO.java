package com.lawfirm.model.cases.dto.base;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.enums.base.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件查询DTO
 */
@Data
@Schema(description = "案件查询DTO")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseQueryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "案件编号")
    private String caseNumber;
    
    @Schema(description = "案件名称")
    private String caseName;
    
    @Schema(description = "案件类型")
    private CaseTypeEnum caseType;
    
    @Schema(description = "案件状态列表")
    private List<CaseStatusEnum> caseStatus;
    
    @Schema(description = "案件进展列表")
    private List<CaseProgressEnum> caseProgress;
    
    @Schema(description = "办理方式")
    private CaseHandleTypeEnum caseHandleType;
    
    @Schema(description = "难度等级")
    private CaseDifficultyEnum caseDifficulty;
    
    @Schema(description = "重要程度")
    private CaseImportanceEnum caseImportance;
    
    @Schema(description = "优先级")
    private CasePriorityEnum casePriority;
    
    @Schema(description = "主办律师")
    private String lawyer;
    
    @Schema(description = "委托人ID")
    private Long clientId;
    
    @Schema(description = "律所ID")
    private Long lawFirmId;
    
    @Schema(description = "部门ID")
    private Long departmentId;
    
    @Schema(description = "立案开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime filingTimeStart;
    
    @Schema(description = "立案结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime filingTimeEnd;
    
    @Schema(description = "结案开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closingTimeStart;
    
    @Schema(description = "结案结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closingTimeEnd;
    
    @Schema(description = "法院名称")
    private String courtName;
    
    @Schema(description = "法官姓名")
    private String judgeName;
    
    @Schema(description = "法院案号")
    private String courtCaseNumber;
    
    @Schema(description = "是否重大案件")
    private Boolean isMajor;
    
    @Schema(description = "是否有利益冲突")
    private Boolean hasConflict;
    
    @Schema(description = "是否需要审批")
    private Boolean needApproval;
    
    @Schema(description = "是否已审批")
    private Boolean approved;
    
    @Schema(description = "审批人")
    private String approver;
    
    @Schema(description = "创建开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeStart;
    
    @Schema(description = "创建结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeEnd;
    
    @Schema(description = "创建人")
    private String createBy;
    
    @Schema(description = "关键词")
    private String keyword;
    
    @Schema(description = "标签列表")
    private List<String> tags;
    
    @Schema(description = "是否包含已删除")
    private Boolean includeDeleted = false;
} 