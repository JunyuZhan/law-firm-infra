package com.lawfirm.api.adaptor.document;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.document.dto.template.TemplateCreateDTO;
import com.lawfirm.model.document.dto.template.TemplateUpdateDTO;
import com.lawfirm.model.document.enums.DocumentStatusEnum;
import com.lawfirm.model.document.enums.TemplateTypeEnum;
import com.lawfirm.model.document.vo.TemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 文档模板管理适配器空实现，当存储功能禁用时使用
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "lawfirm.storage.enabled", havingValue = "false", matchIfMissing = false)
public class NoOpDocumentTemplateAdaptor extends BaseAdaptor {

    /**
     * 创建文档模板
     */
    public TemplateVO createTemplate(TemplateCreateDTO dto) {
        log.warn("存储功能已禁用，忽略创建文档模板操作: {}", dto);
        return null;
    }

    /**
     * 更新文档模板
     */
    public TemplateVO updateTemplate(Long id, TemplateUpdateDTO dto) {
        log.warn("存储功能已禁用，忽略更新文档模板操作: {}, {}", id, dto);
        return null;
    }

    /**
     * 获取文档模板详情
     */
    public TemplateVO getTemplate(Long id) {
        log.warn("存储功能已禁用，忽略获取文档模板详情操作: {}", id);
        return null;
    }

    /**
     * 删除文档模板
     */
    public void deleteTemplate(Long id) {
        log.warn("存储功能已禁用，忽略删除文档模板操作: {}", id);
    }

    /**
     * 获取所有文档模板
     */
    public List<TemplateVO> listTemplates() {
        log.warn("存储功能已禁用，忽略获取所有文档模板操作");
        return Collections.emptyList();
    }

    /**
     * 根据模板类型查询文档模板
     */
    public List<TemplateVO> getTemplatesByType(TemplateTypeEnum type) {
        log.warn("存储功能已禁用，忽略根据类型查询文档模板操作: {}", type);
        return Collections.emptyList();
    }

    /**
     * 根据模板状态查询文档模板
     */
    public List<TemplateVO> getTemplatesByStatus(DocumentStatusEnum status) {
        log.warn("存储功能已禁用，忽略根据状态查询文档模板操作: {}", status);
        return Collections.emptyList();
    }

    /**
     * 更新文档模板状态
     */
    public TemplateVO updateTemplateStatus(Long id, DocumentStatusEnum status) {
        log.warn("存储功能已禁用，忽略更新文档模板状态操作: {}, {}", id, status);
        return null;
    }

    /**
     * 设置文档模板为默认
     */
    public TemplateVO setTemplateAsDefault(Long id) {
        log.warn("存储功能已禁用，忽略设置文档模板为默认操作: {}", id);
        return null;
    }

    /**
     * 预览文档模板
     */
    public String previewTemplate(Long id, Map<String, Object> parameters) {
        log.warn("存储功能已禁用，忽略预览文档模板操作: {}", id);
        return "模板预览功能已禁用";
    }

    /**
     * 生成文档
     */
    public Long generateDocument(Long templateId, Map<String, Object> parameters) {
        log.warn("存储功能已禁用，忽略生成文档操作: {}", templateId);
        return null;
    }

    /**
     * 检查文档模板是否存在
     */
    public boolean existsTemplate(Long id) {
        log.warn("存储功能已禁用，忽略检查文档模板是否存在操作: {}", id);
        return false;
    }

    /**
     * 获取文档模板数量
     */
    public long countTemplates() {
        log.warn("存储功能已禁用，忽略获取文档模板数量操作");
        return 0;
    }
} 