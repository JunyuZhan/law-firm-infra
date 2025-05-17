package com.lawfirm.model.cases.entity.business;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("case_approval")
public class CaseApproval {
    @TableId
    private Long id;
    private Long caseId;
    private String approvalType;
    private String approvalTitle;
    private Integer approvalStatus;
    private Long initiatorId;
    private String initiatorName;
    private Long currentApproverId;
    private String currentApproverName;
    private LocalDateTime startTime;
    private LocalDateTime deadline;
    private LocalDateTime completionTime;
    private String remarks;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 