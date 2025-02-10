package com.lawfirm.document.service.impl;

import com.lawfirm.model.document.repository.DocumentTemplateRepository;
import com.lawfirm.model.document.entity.DocumentTemplate;
import com.lawfirm.model.document.service.DocumentTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文档模板服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentTemplateServiceImpl implements DocumentTemplateService {

    private final DocumentTemplateRepository documentTemplateRepository;

    @Override
    @Transactional
    public DocumentTemplate createTemplate(DocumentTemplate template) {
        // 设置初始状态
        template.setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setIsDeleted(false);
        
        return documentTemplateRepository.save(template);
    }

    @Override
    @Transactional
    public DocumentTemplate updateTemplate(DocumentTemplate template) {
        DocumentTemplate existingTemplate = getTemplate(template.getId());
        if (existingTemplate == null) {
            throw new IllegalArgumentException("模板不存在");
        }

        // 更新基本信息
        template.setUpdateTime(LocalDateTime.now());
        return documentTemplateRepository.save(template);
    }

    @Override
    @Transactional
    public void deleteTemplate(Long id) {
        DocumentTemplate template = getTemplate(id);
        if (template == null) {
            return;
        }

        // 逻辑删除
        template.setIsDeleted(true)
                .setDeleteTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        documentTemplateRepository.save(template);
    }

    @Override
    public DocumentTemplate getTemplate(Long id) {
        return documentTemplateRepository.findById(id)
                .filter(t -> !Boolean.TRUE.equals(t.getIsDeleted()))
                .orElse(null);
    }

    @Override
    public List<DocumentTemplate> listTemplates(Long categoryId) {
        return documentTemplateRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<DocumentTemplate> listByType(String templateType) {
        return documentTemplateRepository.findByTemplateType(templateType);
    }

    @Override
    @Transactional
    public void moveToCategory(Long templateId, Long categoryId) {
        DocumentTemplate template = getTemplate(templateId);
        if (template == null) {
            throw new IllegalArgumentException("模板不存在");
        }

        template.setCategoryId(categoryId)
                .setUpdateTime(LocalDateTime.now());
        documentTemplateRepository.save(template);
    }

    @Override
    @Transactional
    public DocumentTemplate copyTemplate(Long sourceId, String newName) {
        DocumentTemplate source = getTemplate(sourceId);
        if (source == null) {
            throw new IllegalArgumentException("源模板不存在");
        }

        // 创建新模板
        DocumentTemplate newTemplate = new DocumentTemplate();
        newTemplate.setTemplateName(newName)
                .setTemplateType(source.getTemplateType())
                .setCategoryId(source.getCategoryId())
                .setContent(source.getContent())
                .setDescription(source.getDescription())
                .setTags(source.getTags())
                .setStatus(source.getStatus());

        return createTemplate(newTemplate);
    }

    @Override
    public boolean hasPermission(Long templateId, Long userId, String permission) {
        // TODO: 调用权限服务检查权限
        return true;
    }

    @Override
    public long countByCategory(Long categoryId) {
        return documentTemplateRepository.countByCategoryId(categoryId);
    }
} 