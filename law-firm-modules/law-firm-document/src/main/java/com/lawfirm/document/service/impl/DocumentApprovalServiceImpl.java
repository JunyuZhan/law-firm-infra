package com.lawfirm.modules.document.service.impl;

import com.lawfirm.modules.document.entity.Document;
import com.lawfirm.modules.document.entity.DocumentApproval;
import com.lawfirm.modules.document.enums.DocumentApprovalStatusEnum;
import com.lawfirm.model.document.repository.DocumentApprovalRepository;
import com.lawfirm.model.document.repository.DocumentRepository;
import com.lawfirm.modules.document.service.DocumentApprovalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档审批服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentApprovalServiceImpl implements DocumentApprovalService {

    private final DocumentApprovalRepository documentApprovalRepository;
    private final DocumentRepository documentRepository;

    @Override
    @Transactional
    public DocumentApproval startApproval(DocumentApproval approval) {
        // 检查文档是否存在
        Document document = documentRepository.findById(approval.getDocumentId())
                .orElseThrow(() -> new IllegalArgumentException("文档不存在"));

        // 检查是否已有进行中的审批
        if (hasOngoingApproval(approval.getDocumentId())) {
            throw new IllegalStateException("文档已有进行中的审批流程");
        }

        // 设置初始状态
        approval.setStatus(DocumentApprovalStatusEnum.PENDING)
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setIsDeleted(false);

        // 更新文档状态
        document.setApprovalStatus(DocumentApprovalStatusEnum.PENDING)
                .setUpdateTime(LocalDateTime.now());
        documentRepository.save(document);

        return documentApprovalRepository.save(approval);
    }

    @Override
    @Transactional
    public DocumentApproval approve(Long approvalId, String opinion) {
        DocumentApproval approval = getApproval(approvalId);
        if (approval == null) {
            throw new IllegalArgumentException("审批记录不存在");
        }

        // 检查审批状态
        if (!DocumentApprovalStatusEnum.PENDING.equals(approval.getStatus())) {
            throw new IllegalStateException("审批已完成");
        }

        // 更新审批状态
        approval.setStatus(DocumentApprovalStatusEnum.APPROVED)
                .setOpinion(opinion)
                .setApproveTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        
        // 更新文档状态
        Document document = documentRepository.findById(approval.getDocumentId())
                .orElseThrow(() -> new IllegalStateException("文档不存在"));
        document.setApprovalStatus(DocumentApprovalStatusEnum.APPROVED)
                .setUpdateTime(LocalDateTime.now());
        documentRepository.save(document);

        return documentApprovalRepository.save(approval);
    }

    @Override
    @Transactional
    public DocumentApproval reject(Long approvalId, String opinion) {
        DocumentApproval approval = getApproval(approvalId);
        if (approval == null) {
            throw new IllegalArgumentException("审批记录不存在");
        }

        // 检查审批状态
        if (!DocumentApprovalStatusEnum.PENDING.equals(approval.getStatus())) {
            throw new IllegalStateException("审批已完成");
        }

        // 更新审批状态
        approval.setStatus(DocumentApprovalStatusEnum.REJECTED)
                .setOpinion(opinion)
                .setApproveTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        
        // 更新文档状态
        Document document = documentRepository.findById(approval.getDocumentId())
                .orElseThrow(() -> new IllegalStateException("文档不存在"));
        document.setApprovalStatus(DocumentApprovalStatusEnum.REJECTED)
                .setUpdateTime(LocalDateTime.now());
        documentRepository.save(document);

        return documentApprovalRepository.save(approval);
    }

    @Override
    @Transactional
    public void cancelApproval(Long approvalId) {
        DocumentApproval approval = getApproval(approvalId);
        if (approval == null) {
            return;
        }

        // 检查审批状态
        if (!DocumentApprovalStatusEnum.PENDING.equals(approval.getStatus())) {
            throw new IllegalStateException("只能取消待审批的记录");
        }

        // 更新审批状态
        approval.setStatus(DocumentApprovalStatusEnum.CANCELLED)
                .setUpdateTime(LocalDateTime.now());
        documentApprovalRepository.save(approval);

        // 更新文档状态
        Document document = documentRepository.findById(approval.getDocumentId())
                .orElseThrow(() -> new IllegalStateException("文档不存在"));
        document.setApprovalStatus(DocumentApprovalStatusEnum.CANCELLED)
                .setUpdateTime(LocalDateTime.now());
        documentRepository.save(document);
    }

    @Override
    public DocumentApproval getApproval(Long approvalId) {
        return documentApprovalRepository.findById(approvalId)
                .filter(a -> !Boolean.TRUE.equals(a.getIsDeleted()))
                .orElse(null);
    }

    @Override
    public List<DocumentApproval> getDocumentApprovals(Long documentId) {
        return documentApprovalRepository.findByDocumentIdOrderByCreateTimeDesc(documentId).stream()
                .filter(a -> !Boolean.TRUE.equals(a.getIsDeleted()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentApproval> getPendingApprovals(String approver) {
        return documentApprovalRepository.findByApproverAndStatusOrderByCreateTimeAsc(
                approver, DocumentApprovalStatusEnum.PENDING).stream()
                .filter(a -> !Boolean.TRUE.equals(a.getIsDeleted()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<DocumentApproval> listApprovals(Long documentId, Pageable pageable) {
        return documentApprovalRepository.findByDocumentIdOrderByCreateTimeDesc(documentId, pageable);
    }

    @Override
    @Transactional
    public void urgeApproval(Long approvalId) {
        DocumentApproval approval = getApproval(approvalId);
        if (approval == null) {
            throw new IllegalArgumentException("审批记录不存在");
        }

        // 检查审批状态
        if (!DocumentApprovalStatusEnum.PENDING.equals(approval.getStatus())) {
            throw new IllegalStateException("只能催办待审批的记录");
        }

        // 更新催办次数和时间
        approval.setUrgeCount(approval.getUrgeCount() + 1)
                .setLastUrgeTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        documentApprovalRepository.save(approval);

        // TODO: 发送催办通知
    }

    @Override
    @Transactional
    public DocumentApproval transferApproval(Long approvalId, String newApprover) {
        DocumentApproval approval = getApproval(approvalId);
        if (approval == null) {
            throw new IllegalArgumentException("审批记录不存在");
        }

        // 检查审批状态
        if (!DocumentApprovalStatusEnum.PENDING.equals(approval.getStatus())) {
            throw new IllegalStateException("只能转交待审批的记录");
        }

        // 更新审批人
        approval.setApprover(newApprover)
                .setTransferTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        return documentApprovalRepository.save(approval);
    }

    @Override
    public List<DocumentApproval> getApprovalFlow(Long documentId) {
        return documentApprovalRepository.findByDocumentIdOrderByCreateTimeAsc(documentId).stream()
                .filter(a -> !Boolean.TRUE.equals(a.getIsDeleted()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean canApprove(Long approvalId, String approver) {
        DocumentApproval approval = getApproval(approvalId);
        return approval != null 
                && DocumentApprovalStatusEnum.PENDING.equals(approval.getStatus())
                && approver.equals(approval.getApprover());
    }

    @Override
    public DocumentApproval getNextApprovalNode(Long documentId) {
        return documentApprovalRepository.findFirstByDocumentIdAndStatusOrderByCreateTimeAsc(
                documentId, DocumentApprovalStatusEnum.PENDING);
    }

    private boolean hasOngoingApproval(Long documentId) {
        return documentApprovalRepository.existsByDocumentIdAndStatus(
                documentId, DocumentApprovalStatusEnum.PENDING);
    }
} 