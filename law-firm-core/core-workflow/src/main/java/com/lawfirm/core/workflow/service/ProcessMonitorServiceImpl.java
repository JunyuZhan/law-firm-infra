package com.lawfirm.core.workflow.service;

import com.lawfirm.model.workflow.service.ProcessMonitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 流程监控服务实现类
 *
 * @author JunyuZhan
 */
@Slf4j
@Component("coreProcessMonitorServiceImpl")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "law-firm", name = "workflow.enabled", havingValue = "true", matchIfMissing = false)
public class ProcessMonitorServiceImpl implements ProcessMonitorService {

    // 内存模拟监控数据存储
    private static final CopyOnWriteArrayList<Map<String, Object>> EXCEPTION_STORE = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<Map<String, Object>> METRIC_STORE = new CopyOnWriteArrayList<>();
    private static final AtomicLong METRIC_ID_GENERATOR = new AtomicLong(1);

    @Override
    public Map<String, Object> getProcessCompletionTimeStats(String processDefinitionKey, Date startTime, Date endTime) {
        // 返回模拟统计数据
        return Map.of("avgCompletionTime", 120, "maxCompletionTime", 300, "minCompletionTime", 60);
    }

    @Override
    public Map<String, Object> getTaskCompletionTimeStats(String processDefinitionKey, String taskDefinitionKey,
                                                         Date startTime, Date endTime) {
        return Map.of("avgTaskTime", 30, "maxTaskTime", 60, "minTaskTime", 10);
    }

    @Override
    public void createCustomProcessMetric(String metricName, Number value, Map<String, String> tags) {
        Map<String, Object> metric = new HashMap<>();
        metric.put("id", METRIC_ID_GENERATOR.getAndIncrement());
        metric.put("metricName", metricName);
        metric.put("value", value);
        metric.put("tags", tags);
        metric.put("timestamp", new Date());
        METRIC_STORE.add(metric);
    }

    @Override
    public Map<String, Object> getProcessExceptionStats(String processDefinitionKey, Date startTime, Date endTime) {
        // 返回异常数量统计
        long count = EXCEPTION_STORE.stream().filter(e -> processDefinitionKey == null || processDefinitionKey.equals(e.get("processDefinitionKey"))).count();
        return Map.of("exceptionCount", count);
    }

    @Override
    public Map<String, Object> getProcessActivityDistribution(String processDefinitionKey) {
        // 返回模拟分布
        return Map.of("startEvent", 10, "userTask", 20, "endEvent", 10);
    }

    @Override
    public Map<String, Object> getTaskCount(String processDefinitionKey, Date startTime, Date endTime) {
        // 返回模拟任务数量
        return Map.of("totalTasks", 100);
    }

    @Override
    public Map<String, Object> getProcessInstanceExecutionPath(String processInstanceId) {
        // 返回模拟执行路径
        return Map.of("path", List.of("startEvent", "userTask1", "userTask2", "endEvent"));
    }

    @Override
    public List<Map<String, Object>> getTimeoutProcessInstances(String processDefinitionKey, int timeoutMinutes,
                                                              int pageNum, int pageSize) {
        // 返回空列表
        return List.of();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordProcessException(String processInstanceId, String activityId, String exceptionMessage) {
        Map<String, Object> exception = new HashMap<>();
        exception.put("processInstanceId", processInstanceId);
        exception.put("activityId", activityId);
        exception.put("exceptionMessage", exceptionMessage);
        exception.put("timestamp", new Date());
        EXCEPTION_STORE.add(exception);
    }

    @Override
    public Map<String, Object> getUserTaskEfficiencyStats(List<String> taskDefinitionKeys, Date startTime, Date endTime) {
        // 返回模拟效率数据
        return Map.of("avgEfficiency", 0.8);
    }

    @Override
    public Map<String, Object> getProcessPerformanceMetrics(String processDefinitionKey, Date startTime, Date endTime) {
        // 返回模拟性能指标
        return Map.of("throughput", 100, "latency", 50);
    }

    @Override
    public List<Map<String, Object>> getTimeoutTasks(String processDefinitionKey, String taskDefinitionKey,
                                                    String assignee, int timeoutMinutes, int pageNum, int pageSize) {
        // 返回空列表
        return List.of();
    }

    @Override
    public Map<String, Object> getProcessInstanceCount(String processDefinitionKey, Date startTime, Date endTime) {
        // 返回模拟流程实例数量
        return Map.of("instanceCount", 10);
    }
} 
