package com.lawfirm.model.contract.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 合同审核详情VO
 */
@Data
public class ContractReviewDetailVO {
    
    /**
     * 审核ID
     */
    private Long id;
    
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
     * 合同金额
     */
    private String amount;
    
    /**
     * 客户ID
     */
    private Long clientId;
    
    /**
     * 客户名称
     */
    private String clientName;
    
    /**
     * 负责律师ID
     */
    private Long lawyerId;
    
    /**
     * 负责律师名称
     */
    private String lawyerName;
    
    /**
     * 合同创建人ID
     */
    private Long contractCreateBy;
    
    /**
     * 合同创建人名称
     */
    private String contractCreateByName;
    
    /**
     * 合同创建时间
     */
    private LocalDateTime contractCreateTime;
    
    /**
     * 当前审核节点
     */
    private Integer currentNode;
    
    /**
     * 当前审核状态（0-待审核 1-已通过 2-已拒绝）
     */
    private Integer status;
    
    /**
     * 合同文件ID
     */
    private Long fileId;
    
    /**
     * 合同文件名称
     */
    private String fileName;
    
    /**
     * 合同文件URL
     */
    private String fileUrl;
    
    /**
     * 合同描述
     */
    private String description;
    
    /**
     * 审核记录列表
     */
    private List<ReviewRecord> reviewRecords;
    
    /**
     * 审核记录
     */
    @Data
    public static class ReviewRecord {
        
        /**
         * 审核ID
         */
        private Long id;
        
        /**
         * 审核节点（1-部门负责人 2-分所负责人 3-法务审核 4-财务审核）
         */
        private Integer node;
        
        /**
         * 审核节点名称
         */
        private String nodeName;
        
        /**
         * 审核人ID
         */
        private Long reviewerId;
        
        /**
         * 审核人姓名
         */
        private String reviewerName;
        
        /**
         * 审核状态（0-待审核 1-已通过 2-已拒绝）
         */
        private Integer status;
        
        /**
         * 审核意见
         */
        private String comment;
        
        /**
         * 审核时间
         */
        private LocalDateTime reviewTime;
    }
} 