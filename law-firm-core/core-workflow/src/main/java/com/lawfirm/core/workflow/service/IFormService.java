package com.lawfirm.core.workflow.service;

import com.lawfirm.core.workflow.model.Form;

import java.util.List;
import java.util.Map;

/**
 * 表单服务接口
 */
public interface IFormService {
    
    /**
     * 创建表单
     */
    Form createForm(Form form);
    
    /**
     * 更新表单
     */
    Form updateForm(Form form);
    
    /**
     * 删除表单
     */
    void deleteForm(String formId);
    
    /**
     * 获取表单
     */
    Form getForm(String formId);
    
    /**
     * 根据Key获取表单
     */
    Form getFormByKey(String formKey);
    
    /**
     * 查询表单列表
     */
    List<Form> listForms(String key, String name, String category, String tenantId);
    
    /**
     * 获取任务表单
     */
    Form getTaskForm(String taskId);
    
    /**
     * 获取启动表单
     */
    Form getStartForm(String processDefinitionId);
    
    /**
     * 提交任务表单
     */
    void submitTaskForm(String taskId, String formId, Map<String, Object> variables);
    
    /**
     * 提交启动表单
     */
    String submitStartForm(String processDefinitionId, String formId, Map<String, Object> variables);
} 