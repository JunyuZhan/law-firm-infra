package com.lawfirm.model.contract.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 合同审批记录视图对象
 * 用于展示合同的审批流程和审批记录
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ContractReviewVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    
    private Long contractId;       // 合同ID
    private String contractNo;     // 合同编号
    private Integer reviewNodeNum; // 审批节点序号
    private String nodeName;       // 节点名称
    private Long reviewerId;       // 审批人ID
    private String reviewerName;   // 审批人姓名
    private String department;     // 审批人部门
    private String reviewerRole;   // 审批人角色
    private Date reviewTime;       // 审批时间
    private Integer reviewResult;  // 审批结果(1:通过 2:驳回 3:退回修改)
    private String resultName;     // 审批结果名称
    private String comments;       // 审批意见
    private String attachments;    // 附件信息
    private Integer reviewStatus;  // 审批状态(0:待审批 1:已审批 2:跳过)
    private String statusName;     // 状态名称
    private Long nextReviewerId;   // 下一审批人ID
    private String nextReviewerName; // 下一审批人姓名
    private Integer timeSpent;     // 审批耗时(小时)
} 