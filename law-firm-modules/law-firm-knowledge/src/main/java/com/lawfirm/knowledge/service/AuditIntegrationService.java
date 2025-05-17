package com.lawfirm.knowledge.service;

import com.lawfirm.model.knowledge.dto.KnowledgeDTO;
import com.lawfirm.model.knowledge.entity.Knowledge;
import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.model.log.service.AuditQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 审计服务集成
 * 示例如何集成core-audit模块
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
            ((LogFunction) auditService).log(auditLog);
            
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
            ((LogAsyncFunction) auditService).logAsync(auditLog);
            
            log.info("知识删除审计记录成功: id={}, title={}", knowledge.getId(), knowledge.getTitle());
        } catch (Exception e) {
            log.error("知识删除审计记录失败: id={}, title={}, error={}", 
                knowledge.getId(), knowledge.getTitle(), e.getMessage(), e);
        }
    }

    /**
     * 查询知识文档操作日志
     *
     * @param knowledgeId 知识文档ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页结果
     */
    public Page<Object> queryKnowledgeAuditLogs(Long knowledgeId, Integer page, Integer size) {
        if (knowledgeId == null) {
            log.error("知识文档ID为空，无法查询审计日志");
            return new PageImpl<>(new ArrayList<>(), PageRequest.of(page - 1, size), 0);
        }

        try {
            // 构建查询条件
            Map<String, Object> queryDTO = new HashMap<>();
            queryDTO.put("module", "知识管理");
            queryDTO.put("businessId", knowledgeId.toString());
            queryDTO.put("pageNum", page);
            queryDTO.put("pageSize", size);
            
            // 查询审计日志
            Page<Object> result = ((QueryFunction) auditQueryService).queryAuditLogs(queryDTO);
            
            log.info("知识审计日志查询成功: knowledgeId={}, resultCount={}", knowledgeId, result.getContent().size());
            return result;
        } catch (Exception e) {
            log.error("知识审计日志查询失败: knowledgeId={}, error={}", knowledgeId, e.getMessage(), e);
            return new PageImpl<>(new ArrayList<>(), PageRequest.of(page - 1, size), 0);
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
            ((LogFunction) auditService).log(auditLog);
            
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
            ((LogAsyncFunction) auditService).logAsync(auditLog);
            
            log.info("分类删除审计记录成功: categoryId={}", categoryId);
        } catch (Exception e) {
            log.error("分类删除审计记录失败: categoryId={}, error={}", categoryId, e.getMessage(), e);
        }
    }

    /**
     * 审计日志记录函数接口
     */
    private interface LogFunction {
        void log(AuditLogDTO auditLog);
    }

    /**
     * 异步审计日志记录函数接口
     */
    private interface LogAsyncFunction {
        void logAsync(AuditLogDTO auditLog);
    }

    /**
     * 审计日志查询函数接口
     */
    private interface QueryFunction {
        Page<Object> queryAuditLogs(Map<String, Object> queryDTO);
    }
} 