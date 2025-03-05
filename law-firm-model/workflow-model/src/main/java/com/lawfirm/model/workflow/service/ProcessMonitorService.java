package com.lawfirm.model.workflow.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 流程监控服务接口
 * 提供流程监控相关的所有业务功能
 *
 * @author JunyuZhan
 */
public interface ProcessMonitorService {
    /**
     * 获取流程完成时间统计
     *
     * @param processDefinitionKey 流程定义键
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    Map<String, Object> getProcessCompletionTimeStats(String processDefinitionKey, Date startTime, Date endTime);

    /**
     * 获取任务完成时间统计
     *
     * @param processDefinitionKey 流程定义键
     * @param taskDefinitionKey 任务定义键
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    Map<String, Object> getTaskCompletionTimeStats(String processDefinitionKey, String taskDefinitionKey, 
                                                  Date startTime, Date endTime);

    /**
     * 创建自定义流程指标
     *
     * @param metricName 指标名称
     * @param value 指标值
     * @param tags 标签
     */
    void createCustomProcessMetric(String metricName, Number value, Map<String, String> tags);

    /**
     * 获取流程异常统计
     *
     * @param processDefinitionKey 流程定义键
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    Map<String, Object> getProcessExceptionStats(String processDefinitionKey, Date startTime, Date endTime);

    /**
     * 获取流程活动分布
     *
     * @param processDefinitionKey 流程定义键
     * @return 分布结果
     */
    Map<String, Object> getProcessActivityDistribution(String processDefinitionKey);

    /**
     * 获取任务数量统计
     *
     * @param processDefinitionKey 流程定义键
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    Map<String, Object> getTaskCount(String processDefinitionKey, Date startTime, Date endTime);

    /**
     * 获取流程实例执行路径
     *
     * @param processInstanceId 流程实例ID
     * @return 执行路径
     */
    Map<String, Object> getProcessInstanceExecutionPath(String processInstanceId);

    /**
     * 获取超时流程实例
     *
     * @param processDefinitionKey 流程定义键
     * @param timeoutMinutes 超时分钟数
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 超时流程实例列表
     */
    List<Map<String, Object>> getTimeoutProcessInstances(String processDefinitionKey, int timeoutMinutes, 
                                                        int pageNum, int pageSize);

    /**
     * 记录流程异常
     *
     * @param processInstanceId 流程实例ID
     * @param activityId 活动ID
     * @param exceptionMessage 异常信息
     */
    void recordProcessException(String processInstanceId, String activityId, String exceptionMessage);

    /**
     * 获取用户任务效率统计
     *
     * @param taskDefinitionKeys 任务定义键列表
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    Map<String, Object> getUserTaskEfficiencyStats(List<String> taskDefinitionKeys, Date startTime, Date endTime);

    /**
     * 获取流程性能指标
     *
     * @param processDefinitionKey 流程定义键
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 性能指标
     */
    Map<String, Object> getProcessPerformanceMetrics(String processDefinitionKey, Date startTime, Date endTime);

    /**
     * 获取超时任务
     *
     * @param processDefinitionKey 流程定义键
     * @param taskDefinitionKey 任务定义键
     * @param assignee 处理人
     * @param timeoutMinutes 超时分钟数
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 超时任务列表
     */
    List<Map<String, Object>> getTimeoutTasks(String processDefinitionKey, String taskDefinitionKey, 
                                             String assignee, int timeoutMinutes, int pageNum, int pageSize);

    /**
     * 获取流程实例数量统计
     *
     * @param processDefinitionKey 流程定义键
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    Map<String, Object> getProcessInstanceCount(String processDefinitionKey, Date startTime, Date endTime);
} 
