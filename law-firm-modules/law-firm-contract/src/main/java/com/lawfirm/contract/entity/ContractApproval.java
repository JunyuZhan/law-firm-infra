package com.lawfirm.contract.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 合同审批实体类
 */
@Data
@TableName("contract_approval")
public class ContractApproval {
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 合同ID
     */
    private Long contractId;
    
    /**
     * 审批人ID
     */
    private Long approverId;
    
    /**
     * 审批人姓名
     */
    private String approverName;
    
    /**
     * 审批节点
     */
    private Integer node;
    
    /**
     * 审批状态
     */
    private Integer status;
    
    /**
     * 审批意见
     */
    private String comment;
    
    /**
     * 审批时间
     */
    private LocalDateTime approvalTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 创建人
     */
    private String createBy;
    
    /**
     * 更新人
     */
    private String updateBy;
    
    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;
} 