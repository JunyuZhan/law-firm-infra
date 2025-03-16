package com.lawfirm.model.contract.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lawfirm.model.base.query.BaseQuery;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 合同审核查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContractReviewQueryDTO extends BaseQuery {
    
    /**
     * 合同ID
     */
    private Long contractId;
    
    /**
     * 合同编号
     */
    private String contractNo;
    
    /**
     * 合同名称
     */
    private String contractName;
    
    /**
     * 审核状态（0-待审核 1-已通过 2-已拒绝）
     */
    private Integer status;
    
    /**
     * 状态列表，用于多状态查询
     */
    private List<Integer> statusList;
    
    /**
     * 审核人ID
     */
    private Long reviewerId;
    
    /**
     * 审核时间范围-开始
     */
    private LocalDateTime reviewStartTime;
    
    /**
     * 审核时间范围-结束
     */
    private LocalDateTime reviewEndTime;
    
    /**
     * 客户ID
     */
    private Long clientId;
    
    /**
     * 负责律师ID
     */
    private Long lawyerId;
} 