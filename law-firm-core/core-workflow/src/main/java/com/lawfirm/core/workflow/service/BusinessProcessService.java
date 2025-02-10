package com.lawfirm.core.workflow.service;

import com.lawfirm.core.workflow.model.BusinessProcess;

import java.util.List;
import java.util.Map;

/**
 * 业务流程关联服务
 */
public interface BusinessProcessService {
    
    /**
     * 创建业务流程关联
     *
     * @param businessProcess 业务流程关联
     * @return 业务流程关联
     */
    BusinessProcess createBusinessProcess(BusinessProcess businessProcess);
    
    /**
     * 更新业务流程关联
     *
     * @param businessProcess 业务流程关联
     * @return 业务流程关联
     */
    BusinessProcess updateBusinessProcess(BusinessProcess businessProcess);
    
    /**
     * 删除业务流程关联
     *
     * @param businessType 业务类型
     * @param businessId 业务ID
     */
    void deleteBusinessProcess(String businessType, String businessId);
    
    /**
     * 获取业务流程关联
     *
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 业务流程关联
     */
    BusinessProcess getBusinessProcess(String businessType, String businessId);
    
    /**
     * 获取流程实例关联的业务
     *
     * @param processInstanceId 流程实例ID
     * @return 业务流程关联
     */
    BusinessProcess getByProcessInstanceId(String processInstanceId);
    
    /**
     * 查询业务流程关联列表
     *
     * @param businessType 业务类型
     * @param processStatus 流程状态
     * @param startUserId 发起人ID
     * @return 业务流程关联列表
     */
    List<BusinessProcess> listBusinessProcesses(String businessType, String processStatus, String startUserId);
    
    /**
     * 更新流程状态
     *
     * @param processInstanceId 流程实例ID
     * @param processStatus 流程状态
     */
    void updateProcessStatus(String processInstanceId, String processStatus);
    
    /**
     * 更新当前任务信息
     *
     * @param processInstanceId 流程实例ID
     * @param taskId 任务ID
     * @param taskName 任务名称
     * @param assignee 处理人
     */
    void updateCurrentTask(String processInstanceId, String taskId, String taskName, String assignee);
    
    /**
     * 保存业务表单数据
     *
     * @param processInstanceId 流程实例ID
     * @param formData 表单数据
     */
    void saveFormData(String processInstanceId, Map<String, Object> formData);
    
    /**
     * 保存流程变量
     *
     * @param processInstanceId 流程实例ID
     * @param variables 流程变量
     */
    void saveProcessVariables(String processInstanceId, Map<String, Object> variables);
} 