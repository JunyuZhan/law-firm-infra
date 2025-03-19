package com.lawfirm.api.adaptor.document;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.document.dto.template.TemplateCreateDTO;
import com.lawfirm.model.document.dto.template.TemplateUpdateDTO;
import com.lawfirm.model.document.entity.template.TemplateDocument;
import com.lawfirm.model.document.service.TemplateService;
import com.lawfirm.model.document.vo.template.TemplateVO;
import com.lawfirm.model.document.enums.TemplateTypeEnum;
import com.lawfirm.model.document.enums.TemplateStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

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
        TemplateDocument template = templateService.createTemplate(dto);
        return convert(template, TemplateVO.class);
    }

    /**
     * 更新文档模板
     */
    public TemplateVO updateTemplate(Long id, TemplateUpdateDTO dto) {
        TemplateDocument template = templateService.updateTemplate(id, dto);
        return convert(template, TemplateVO.class);
    }

    /**
     * 获取文档模板详情
     */
    public TemplateVO getTemplate(Long id) {
        TemplateDocument template = templateService.getTemplateById(id);
        return convert(template, TemplateVO.class);
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
        List<TemplateDocument> templates = templateService.listAllTemplates();
        return templates.stream()
                .map(template -> convert(template, TemplateVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据模板类型查询文档模板
     */
    public List<TemplateVO> getTemplatesByType(TemplateTypeEnum type) {
        List<TemplateDocument> templates = templateService.listTemplatesByType(type.getCode());
        return templates.stream()
                .map(template -> convert(template, TemplateVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据模板状态查询文档模板
     */
    public List<TemplateVO> getTemplatesByStatus(TemplateStatusEnum status) {
        List<TemplateDocument> templates = templateService.listTemplatesByStatus(status.getCode());
        return templates.stream()
                .map(template -> convert(template, TemplateVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID查询文档模板
     */
    public List<TemplateVO> getTemplatesByDepartmentId(Long departmentId) {
        List<TemplateDocument> templates = templateService.listTemplatesByDepartmentId(departmentId);
        return templates.stream()
                .map(template -> convert(template, TemplateVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 上传文档模板
     */
    public TemplateVO uploadTemplate(MultipartFile file, TemplateCreateDTO dto) {
        TemplateDocument template = templateService.uploadTemplate(file, dto);
        return convert(template, TemplateVO.class);
    }

    /**
     * 下载文档模板
     */
    public byte[] downloadTemplate(Long id) {
        return templateService.downloadTemplate(id);
    }

    /**
     * 预览文档模板
     */
    public String previewTemplate(Long id) {
        return templateService.previewTemplate(id);
    }

    /**
     * 更新文档模板状态
     */
    public TemplateVO updateTemplateStatus(Long id, TemplateStatusEnum status) {
        TemplateDocument template = templateService.updateStatus(id, status.getCode());
        return convert(template, TemplateVO.class);
    }

    /**
     * 设置文档模板为默认
     */
    public TemplateVO setTemplateAsDefault(Long id) {
        TemplateDocument template = templateService.setDefault(id, true);
        return convert(template, TemplateVO.class);
    }

    /**
     * 复制文档模板
     */
    public TemplateVO copyTemplate(Long id) {
        TemplateDocument template = templateService.copyTemplate(id);
        return convert(template, TemplateVO.class);
    }

    /**
     * 检查文档模板是否存在
     */
    public boolean existsTemplate(Long id) {
        return templateService.existsTemplate(id);
    }

    /**
     * 获取文档模板数量
     */
    public long countTemplates() {
        return templateService.countTemplates();
    }
} 