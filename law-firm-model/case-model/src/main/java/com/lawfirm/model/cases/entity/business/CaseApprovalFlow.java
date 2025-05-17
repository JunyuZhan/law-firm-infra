package com.lawfirm.model.cases.entity.business;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("case_approval_flow")
public class CaseApprovalFlow {
    @TableId
    private Long id;
    private Long approvalId;
    private Long approverId;
    private String approverName;
    private Integer approvalStatus;
    private String approvalOpinion;
    private LocalDateTime approvalTime;
    private Integer nodeOrder;
    private String remarks;
    private LocalDateTime createTime;
} 