package com.lawfirm.knowledge.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.knowledge.dto.KnowledgeDTO;
import com.lawfirm.model.knowledge.entity.Knowledge;
import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.dto.AuditLogQuery;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.model.log.service.AuditQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 审计服务集成
 * 集成core-audit模块
 */
@Slf4j
@Service("knowledgeAuditService")
public class AuditIntegrationService {

    @Autowired
    @Qualifier("coreAuditServiceImpl")
    private AuditService auditService;

    @Autowired
    @Qualifier("auditQueryServiceImpl")
    private AuditQueryService auditQueryService;

    /**
     * 记录知识创建审计
     *
     * @param knowledge 知识文档
     */
    public void recordKnowledgeCreation(Knowledge knowledge) {
        if (knowledge == null || knowledge.getId() == null) {
            log.error("知识文档或ID为空，无法记录审计");
            return;
        }

        try {
            // 构建审计日志
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule("知识管理");
            auditLog.setDescription("创建知识");
            auditLog.setBeforeData(null);
            auditLog.setAfterData(String.format("创建知识文档：[%s]，分类：[%s]", 
                knowledge.getTitle(), knowledge.getCategoryId()));
            
            // 记录审计日志
            auditService.log(auditLog);
            
            log.info("知识创建审计记录成功: id={}, title={}", knowledge.getId(), knowledge.getTitle());
        } catch (Exception e) {
            log.error("知识创建审计记录失败: id={}, title={}, error={}", 
                knowledge.getId(), knowledge.getTitle(), e.getMessage(), e);
        }
    }

    /**
     * 记录知识删除审计
     *
     * @param knowledge 知识文档
     */
    public void recordKnowledgeDeletion(Knowledge knowledge) {
        if (knowledge == null || knowledge.getId() == null) {
            log.error("知识文档或ID为空，无法记录审计");
            return;
        }

        try {
            // 构建审计日志
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule("知识管理");
            auditLog.setDescription("删除知识");
            auditLog.setBeforeData(String.format("知识文档：[%s]，ID：[%d]", 
                knowledge.getTitle(), knowledge.getId()));
            auditLog.setAfterData(null);
            
            // 异步记录审计日志
            auditService.logAsync(auditLog);
            
            log.info("知识删除审计记录成功: id={}, title={}", knowledge.getId(), knowledge.getTitle());
        } catch (Exception e) {
            log.error("知识删除审计记录失败: id={}, title={}, error={}", 
                knowledge.getId(), knowledge.getTitle(), e.getMessage(), e);
        }
    }
    
    /**
     * 记录知识相关操作
     *
     * @param message 详细消息描述
     * @param operationType 操作类型
     * @param knowledgeId 知识ID
     */
    public void recordKnowledgeOperation(String message, String operationType, Long knowledgeId) {
        if (knowledgeId == null) {
            log.error("知识ID为空，无法记录审计");
            return;
        }

        try {
            // 构建审计日志
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule("知识管理");
            auditLog.setDescription(getOperationDescription(operationType));
            
            // 根据操作类型设置前后数据
            if (operationType.startsWith("DELETE")) {
                auditLog.setBeforeData(message);
                auditLog.setAfterData(null);
            } else if (operationType.startsWith("UPLOAD") || operationType.startsWith("CREATE")) {
                auditLog.setBeforeData(null);
                auditLog.setAfterData(message);
            } else {
                auditLog.setBeforeData("原始数据");
                auditLog.setAfterData(message);
            }
            
            // 使用请求参数保存业务ID
            auditLog.setRequestParams("{\"knowledgeId\":" + knowledgeId + ",\"operationType\":\"" + operationType + "\"}");
            
            // 记录审计日志
            auditService.log(auditLog);
            
            log.info("知识操作审计记录成功: knowledgeId={}, operationType={}", knowledgeId, operationType);
        } catch (Exception e) {
            log.error("知识操作审计记录失败: knowledgeId={}, operationType={}, error={}", 
                knowledgeId, operationType, e.getMessage(), e);
        }
    }
    
    /**
     * 获取操作描述
     */
    private String getOperationDescription(String operationType) {
        switch (operationType) {
            case "UPLOAD_ATTACHMENT":
                return "上传附件";
            case "DELETE_ATTACHMENT":
                return "删除附件";
            case "DELETE_ATTACHMENTS":
                return "批量删除附件";
            case "CREATE_TAG":
                return "创建标签";
            case "DELETE_TAG":
                return "删除标签";
            case "CREATE_TAG_RELATION":
                return "创建标签关联";
            case "DELETE_TAG_RELATION":
                return "删除标签关联";
            default:
                return "知识操作";
        }
    }

    /**
     * 查询知识文档操作日志
     *
     * @param knowledgeId 知识文档ID
     * @param page 页码
     * @param size 每页大小
     * @return 日志列表
     */
    public List<AuditLogDTO> queryKnowledgeAuditLogs(Long knowledgeId, Integer page, Integer size) {
        if (knowledgeId == null) {
            log.error("知识文档ID为空，无法查询审计日志");
            return Collections.emptyList();
        }

        try {
            // 构建查询条件
            AuditLogQuery query = new AuditLogQuery();
            query.setModule("知识管理");
            // 设置业务ID需要根据实际审计日志存储方式调整
            query.setPageNum(page);
            query.setPageSize(size);
            
            // 查询审计日志
            Page<AuditLogDTO> result = auditQueryService.queryAuditLogs(query);
            
            List<AuditLogDTO> logs = result != null ? result.getRecords() : Collections.emptyList();
            log.info("知识审计日志查询成功: knowledgeId={}, resultCount={}", knowledgeId, logs.size());
            return logs;
        } catch (Exception e) {
            log.error("知识审计日志查询失败: knowledgeId={}, error={}", knowledgeId, e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    /**
     * 记录分类创建审计
     *
     * @param message 审计消息
     * @param categoryId 分类ID
     */
    public void recordCategoryCreation(String message, Long categoryId) {
        if (categoryId == null) {
            log.error("分类ID为空，无法记录审计");
            return;
        }

        try {
            // 构建审计日志
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule("知识管理");
            auditLog.setDescription("创建分类");
            auditLog.setBeforeData(null);
            auditLog.setAfterData(message);
            // 使用请求参数保存业务ID
            auditLog.setRequestParams("{\"categoryId\":" + categoryId + "}");
            auditLog.setId(categoryId); // 使用ID字段保存业务ID
            
            // 记录审计日志
            auditService.log(auditLog);
            
            log.info("分类创建审计记录成功: categoryId={}, message={}", categoryId, message);
        } catch (Exception e) {
            log.error("分类创建审计记录失败: categoryId={}, error={}", categoryId, e.getMessage(), e);
        }
    }
    
    /**
     * 记录分类删除审计
     *
     * @param message 审计消息
     * @param categoryId 分类ID
     */
    public void recordCategoryDeletion(String message, Long categoryId) {
        if (categoryId == null) {
            log.error("分类ID为空，无法记录审计");
            return;
        }

        try {
            // 构建审计日志
            AuditLogDTO auditLog = new AuditLogDTO();
            auditLog.setModule("知识管理");
            auditLog.setDescription("删除分类");
            auditLog.setBeforeData(message);
            auditLog.setAfterData(null);
            // 使用请求参数保存业务ID
            auditLog.setRequestParams("{\"categoryId\":" + categoryId + "}");
            auditLog.setId(categoryId); // 使用ID字段保存业务ID
            
            // 异步记录审计日志
            auditService.logAsync(auditLog);
            
            log.info("分类删除审计记录成功: categoryId={}", categoryId);
        } catch (Exception e) {
            log.error("分类删除审计记录失败: categoryId={}, error={}", categoryId, e.getMessage(), e);
        }
    }
} 