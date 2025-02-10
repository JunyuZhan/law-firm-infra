package com.lawfirm.model.document.service;

import com.lawfirm.model.document.entity.DocumentTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 文档模板服务接口
 */
public interface DocumentTemplateService {
    
    /**
     * 创建模板
     */
    DocumentTemplate createTemplate(DocumentTemplate template, MultipartFile file);
    
    /**
     * 更新模板
     */
    DocumentTemplate updateTemplate(DocumentTemplate template);
    
    /**
     * 删除模板
     */
    void deleteTemplate(Long templateId);
    
    /**
     * 获取模板详情
     */
    DocumentTemplate getTemplate(Long templateId);
    
    /**
     * 根据编码获取模板
     */
    DocumentTemplate getTemplateByCode(String templateCode);
    
    /**
     * 分页查询模板
     */
    Page<DocumentTemplate> listTemplates(Pageable pageable);
    
    /**
     * 根据分类查询模板
     */
    List<DocumentTemplate> listByCategory(String category);
    
    /**
     * 根据部门查询模板
     */
    List<DocumentTemplate> listByDepartment(String departmentId);
    
    /**
     * 获取公共模板
     */
    List<DocumentTemplate> getPublicTemplates();
    
    /**
     * 使用模板创建文档
     */
    Long createDocumentFromTemplate(Long templateId, Map<String, Object> params);
    
    /**
     * 获取模板文件
     */
    byte[] getTemplateFile(Long templateId);
    
    /**
     * 获取模板文件URL
     */
    String getTemplateFileUrl(Long templateId);
    
    /**
     * 更新模板文件
     */
    void updateTemplateFile(Long templateId, MultipartFile file);
    
    /**
     * 获取模板占位符
     */
    Map<String, String> getTemplatePlaceholders(Long templateId);
} 