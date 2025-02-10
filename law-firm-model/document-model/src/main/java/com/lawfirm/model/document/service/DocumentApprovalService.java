package com.lawfirm.model.document.service;

import com.lawfirm.model.document.entity.DocumentApproval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 文档审批服务接口
 */
public interface DocumentApprovalService {
    
    /**
     * 发起审批
     */
    DocumentApproval startApproval(DocumentApproval approval);
    
    /**
     * 审批通过
     */
    DocumentApproval approve(Long approvalId, String opinion);
    
    /**
     * 审批拒绝
     */
    DocumentApproval reject(Long approvalId, String opinion);
    
    /**
     * 撤销审批
     */
    void cancelApproval(Long approvalId);
    
    /**
     * 获取审批详情
     */
    DocumentApproval getApproval(Long approvalId);
    
    /**
     * 获取文档的所有审批记录
     */
    List<DocumentApproval> getDocumentApprovals(Long documentId);
    
    /**
     * 获取待审批列表
     */
    List<DocumentApproval> getPendingApprovals(String approver);
    
    /**
     * 分页查询审批记录
     */
    Page<DocumentApproval> listApprovals(Long documentId, Pageable pageable);
    
    /**
     * 催办审批
     */
    void urgeApproval(Long approvalId);
    
    /**
     * 转交审批
     */
    DocumentApproval transferApproval(Long approvalId, String newApprover);
    
    /**
     * 获取审批流程
     */
    List<DocumentApproval> getApprovalFlow(Long documentId);
    
    /**
     * 检查是否可以审批
     */
    boolean canApprove(Long approvalId, String approver);
    
    /**
     * 获取下一个审批节点
     */
    DocumentApproval getNextApprovalNode(Long documentId);
} 