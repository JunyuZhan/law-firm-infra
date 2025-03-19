package com.lawfirm.api.adaptor.document;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.document.vo.TemplateVO;
import com.lawfirm.model.document.dto.template.TemplateCreateDTO;
import com.lawfirm.model.document.dto.template.TemplateUpdateDTO;
import com.lawfirm.model.document.entity.template.TemplateDocument;
import com.lawfirm.model.document.service.TemplateService;
import com.lawfirm.model.document.enums.TemplateTypeEnum;
import com.lawfirm.model.document.enums.DocumentStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 文档模板管理适配器
 */
@Component
public class DocumentTemplateAdaptor extends BaseAdaptor {

    @Autowired
    private TemplateService templateService;

    /**
     * 创建文档模板
     */
    public TemplateVO createTemplate(TemplateCreateDTO dto) {
        Long id = templateService.createTemplate(dto);
        return templateService.getTemplateById(id);
    }

    /**
     * 更新文档模板
     */
    public TemplateVO updateTemplate(Long id, TemplateUpdateDTO dto) {
        templateService.updateTemplate(id, dto);
        return templateService.getTemplateById(id);
    }

    /**
     * 获取文档模板详情
     */
    public TemplateVO getTemplate(Long id) {
        return templateService.getTemplateById(id);
    }

    /**
     * 删除文档模板
     */
    public void deleteTemplate(Long id) {
        templateService.deleteTemplate(id);
    }

    /**
     * 获取所有文档模板
     */
    public List<TemplateVO> listTemplates() {
        return templateService.listAllTemplates();
    }

    /**
     * 根据模板类型查询文档模板
     */
    public List<TemplateVO> getTemplatesByType(TemplateTypeEnum type) {
        return templateService.listTemplatesByType(type.getCode());
    }

    /**
     * 根据模板状态查询文档模板
     */
    public List<TemplateVO> getTemplatesByStatus(DocumentStatusEnum status) {
        return templateService.listTemplatesByType(status.getCode());
    }

    /**
     * 更新文档模板状态
     */
    public TemplateVO updateTemplateStatus(Long id, DocumentStatusEnum status) {
        templateService.updateStatus(id, Integer.parseInt(status.getCode()));
        return templateService.getTemplateById(id);
    }

    /**
     * 设置文档模板为默认
     */
    public TemplateVO setTemplateAsDefault(Long id) {
        templateService.setDefault(id, true);
        return templateService.getTemplateById(id);
    }

    /**
     * 预览文档模板
     */
    public String previewTemplate(Long id, Map<String, Object> parameters) {
        return templateService.previewTemplate(id, parameters);
    }

    /**
     * 生成文档
     */
    public Long generateDocument(Long templateId, Map<String, Object> parameters) {
        return templateService.generateDocument(templateId, parameters);
    }

    /**
     * 检查文档模板是否存在
     */
    public boolean existsTemplate(Long id) {
        try {
            return templateService.getTemplateById(id) != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取文档模板数量
     */
    public long countTemplates() {
        return templateService.listAllTemplates().size();
    }
} 