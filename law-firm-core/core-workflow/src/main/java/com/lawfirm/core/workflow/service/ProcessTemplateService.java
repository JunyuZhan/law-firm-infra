package com.lawfirm.core.workflow.service;

import com.lawfirm.core.workflow.enums.ProcessTemplateEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 流程模板管理服务
 */
public interface ProcessTemplateService {
    
    /**
     * 部署流程模板
     *
     * @param template 流程模板
     * @param file 流程定义文件
     * @return 部署ID
     */
    String deployTemplate(ProcessTemplateEnum template, MultipartFile file);
    
    /**
     * 更新流程模板
     *
     * @param template 流程模板
     * @param file 流程定义文件
     * @return 部署ID
     */
    String updateTemplate(ProcessTemplateEnum template, MultipartFile file);
    
    /**
     * 删除流程模板
     *
     * @param template 流程模板
     * @param cascade 是否级联删除
     */
    void deleteTemplate(ProcessTemplateEnum template, boolean cascade);
    
    /**
     * 获取流程模板定义
     *
     * @param template 流程模板
     * @return 流程定义ID
     */
    String getTemplateDefinitionId(ProcessTemplateEnum template);
    
    /**
     * 获取流程模板XML
     *
     * @param template 流程模板
     * @return 流程定义XML输入流
     */
    InputStream getTemplateXml(ProcessTemplateEnum template);
    
    /**
     * 获取流程模板图片
     *
     * @param template 流程模板
     * @return 流程图输入流
     */
    InputStream getTemplateDiagram(ProcessTemplateEnum template);
    
    /**
     * 查询流程模板列表
     *
     * @param category 流程分类
     * @return 流程模板列表
     */
    List<ProcessTemplateEnum> listTemplates(String category);
    
    /**
     * 获取流程模板表单定义
     *
     * @param template 流程模板
     * @return 表单定义
     */
    Map<String, Object> getTemplateForm(ProcessTemplateEnum template);
    
    /**
     * 获取流程模板节点定义
     *
     * @param template 流程模板
     * @return 节点定义
     */
    Map<String, Object> getTemplateNodes(ProcessTemplateEnum template);
    
    /**
     * 验证流程模板定义
     *
     * @param file 流程定义文件
     * @return 是否有效
     */
    boolean validateTemplate(MultipartFile file);
} 