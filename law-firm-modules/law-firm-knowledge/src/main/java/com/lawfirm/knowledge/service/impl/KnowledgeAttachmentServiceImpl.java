package com.lawfirm.knowledge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lawfirm.knowledge.service.AuditIntegrationService;
import com.lawfirm.knowledge.service.StorageIntegrationService;
import com.lawfirm.model.knowledge.mapper.KnowledgeAttachmentMapper;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.knowledge.entity.KnowledgeAttachment;
import com.lawfirm.model.knowledge.service.KnowledgeAttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.lawfirm.knowledge.exception.KnowledgeException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * 知识附件服务实现类
 * 
 * 注：当前实现未直接注入core层服务。
 * 如需使用存储服务、审计服务等core能力，参考以下方式注入：
 * 
 * @Autowired
 * @Qualifier("knowledgeStorageService")
 * private StorageIntegrationService storageService;
 * 
 * @Autowired
 * @Qualifier("knowledgeAuditService")
 * private AuditIntegrationService auditService;
 */
@Slf4j
@Service("knowledgeAttachmentServiceImpl")
public class KnowledgeAttachmentServiceImpl extends BaseServiceImpl<KnowledgeAttachmentMapper, KnowledgeAttachment> implements KnowledgeAttachmentService {

    @Autowired
    @Qualifier("knowledgeStorageService")
    private StorageIntegrationService storageService;
    
    @Autowired
    @Qualifier("knowledgeAuditService")
    private AuditIntegrationService auditService;

    @Override
    public List<KnowledgeAttachment> listByKnowledgeId(Long knowledgeId) {
        if (knowledgeId == null) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<KnowledgeAttachment> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(KnowledgeAttachment::getKnowledgeId, knowledgeId);
        wrapper.orderByAsc(KnowledgeAttachment::getSort);
        return list(wrapper);
    }

    @Override
    public List<KnowledgeAttachment> listByFileType(String fileType) {
        if (fileType == null) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<KnowledgeAttachment> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(KnowledgeAttachment::getFileType, fileType);
        wrapper.orderByDesc(KnowledgeAttachment::getCreateTime);
        return list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByKnowledgeId(Long knowledgeId) {
        if (knowledgeId == null) {
            return;
        }
        
        // 获取需要删除的附件列表
        List<KnowledgeAttachment> attachments = listByKnowledgeId(knowledgeId);
        
        // 删除数据库记录
        LambdaQueryWrapper<KnowledgeAttachment> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(KnowledgeAttachment::getKnowledgeId, knowledgeId);
        boolean success = remove(wrapper);
        
        if (success && !attachments.isEmpty()) {
            // 记录审计日志
            auditService.recordKnowledgeOperation(
                String.format("批量删除知识附件，知识ID：[%s]，附件数量：[%d]", knowledgeId, attachments.size()),
                "DELETE_ATTACHMENTS",
                knowledgeId
            );
        }
    }

    @Override
    public Integer countByKnowledgeId(Long knowledgeId) {
        if (knowledgeId == null) {
            return 0;
        }
        LambdaQueryWrapper<KnowledgeAttachment> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(KnowledgeAttachment::getKnowledgeId, knowledgeId);
        return Math.toIntExact(count(wrapper));
    }

    /**
     * 删除知识的所有附件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByKnowledgeId(Long knowledgeId) {
        deleteByKnowledgeId(knowledgeId);
    }
    
    /**
     * 上传附件
     */
    @Transactional(rollbackFor = Exception.class)
    public Long uploadAttachment(MultipartFile file, Long knowledgeId) throws IOException {
        if (file == null || file.isEmpty() || knowledgeId == null) {
            throw new KnowledgeException("文件或知识ID为空");
        }
        
        try {
            // 调用存储服务上传文件
            Long attachmentId = storageService.uploadKnowledgeAttachment(file, knowledgeId);
            
            // 记录审计日志
            if (attachmentId != null) {
                auditService.recordKnowledgeOperation(
                    String.format("上传知识附件：[%s]，知识ID：[%s]", file.getOriginalFilename(), knowledgeId),
                    "UPLOAD_ATTACHMENT",
                    knowledgeId
                );
            }
            
            return attachmentId;
        } catch (Exception e) {
            log.error("上传附件失败: {}", e.getMessage(), e);
            throw new KnowledgeException("上传附件失败", e);
        }
    }
    
    /**
     * 删除附件并记录审计日志
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAttachmentWithAudit(Long id) {
        // 获取附件信息
        KnowledgeAttachment attachment = getById(id);
        if (attachment == null) {
            return false;
        }
        
        // 删除数据库记录
        boolean success = super.removeById(id);
        
        if (success) {
            // 记录审计日志
            auditService.recordKnowledgeOperation(
                String.format("删除知识附件：[%s]，ID：[%s]，知识ID：[%s]", 
                    attachment.getFileName(), id, attachment.getKnowledgeId()),
                "DELETE_ATTACHMENT",
                attachment.getKnowledgeId()
            );
        }
        
        return success;
    }
} 