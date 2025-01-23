package com.lawfirm.core.workflow.service;

import com.lawfirm.core.workflow.model.Form;

import java.util.List;
import java.util.Map;

/**
 * 表单服务接口
 */
public interface FormService {
    
    /**
     * 创建表单
     *
     * @param form 表单
     * @return 表单
     */
    Form createForm(Form form);
    
    /**
     * 更新表单
     *
     * @param form 表单
     * @return 表单
     */
    Form updateForm(Form form);
    
    /**
     * 删除表单
     *
     * @param formId 表单ID
     */
    void deleteForm(String formId);
    
    /**
     * 获取表单
     *
     * @param formId 表单ID
     * @return 表单
     */
    Form getForm(String formId);
    
    /**
     * 获取表单(通过表单键)
     *
     * @param formKey 表单键
     * @return 表单
     */
    Form getFormByKey(String formKey);
    
    /**
     * 查询表单列表
     *
     * @param key 表单键
     * @param name 表单名称
     * @param category 表单分类
     * @param tenantId 租户ID
     * @return 表单列表
     */
    List<Form> listForms(String key, String name, String category, String tenantId);
    
    /**
     * 获取任务表单
     *
     * @param taskId 任务ID
     * @return 表单
     */
    Form getTaskForm(String taskId);
    
    /**
     * 获取启动表单
     *
     * @param processDefinitionId 流程定义ID
     * @return 表单
     */
    Form getStartForm(String processDefinitionId);
    
    /**
     * 提交任务表单
     *
     * @param taskId 任务ID
     * @param formId 表单ID
     * @param variables 表单变量
     */
    void submitTaskForm(String taskId, String formId, Map<String, Object> variables);
    
    /**
     * 提交启动表单
     *
     * @param processDefinitionId 流程定义ID
     * @param formId 表单ID
     * @param variables 表单变量
     * @return 流程实例ID
     */
    String submitStartForm(String processDefinitionId, String formId, Map<String, Object> variables);
} 