package com.lawfirm.core.workflow.adapter.flowable;

import com.lawfirm.core.workflow.exception.ProcessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Flowable流程引擎适配器实现类
 * 
 * @author JunyuZhan
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FlowableProcessAdapterImpl implements FlowableProcessAdapter {

    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;
    private final HistoryService historyService;

    @Override
    public String startProcess(String processDefinitionKey, String businessKey, Map<String, Object> variables) {
        log.info("启动流程, 流程定义Key: {}, 业务标识: {}", processDefinitionKey, businessKey);
        
        if (variables == null) {
            variables = new HashMap<>();
        }
        
        try {
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
                    processDefinitionKey, 
                    businessKey, 
                    variables);
            
            log.info("流程启动成功, 流程实例ID: {}", processInstance.getId());
            return processInstance.getId();
        } catch (Exception e) {
            log.error("启动流程失败", e);
            throw new ProcessException("启动流程失败: " + e.getMessage());
        }
    }

    @Override
    public String startProcessById(String processDefinitionId, String businessKey, Map<String, Object> variables) {
        log.info("启动流程, 流程定义ID: {}, 业务标识: {}", processDefinitionId, businessKey);
        
        if (variables == null) {
            variables = new HashMap<>();
        }
        
        try {
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(
                    processDefinitionId, 
                    businessKey, 
                    variables);
            
            log.info("流程启动成功, 流程实例ID: {}", processInstance.getId());
            return processInstance.getId();
        } catch (Exception e) {
            log.error("启动流程失败", e);
            throw new ProcessException("启动流程失败: " + e.getMessage());
        }
    }

    @Override
    public void suspendProcess(String processInstanceId) {
        log.info("挂起流程, 流程实例ID: {}", processInstanceId);
        
        try {
            runtimeService.suspendProcessInstanceById(processInstanceId);
            log.info("流程挂起成功");
        } catch (FlowableObjectNotFoundException e) {
            log.error("流程实例不存�?, e);
            throw new ProcessException("流程实例不存�?);
        } catch (Exception e) {
            log.error("挂起流程失败", e);
            throw new ProcessException("挂起流程失败: " + e.getMessage());
        }
    }

    @Override
    public void activateProcess(String processInstanceId) {
        log.info("激活流�? 流程实例ID: {}", processInstanceId);
        
        try {
            runtimeService.activateProcessInstanceById(processInstanceId);
            log.info("流程激活成�?);
        } catch (FlowableObjectNotFoundException e) {
            log.error("流程实例不存�?, e);
            throw new ProcessException("流程实例不存�?);
        } catch (Exception e) {
            log.error("激活流程失�?, e);
            throw new ProcessException("激活流程失�? " + e.getMessage());
        }
    }

    @Override
    public void terminateProcess(String processInstanceId, String reason) {
        log.info("终止流程, 流程实例ID: {}, 原因: {}", processInstanceId, reason);
        
        String deleteReason = StringUtils.hasText(reason) ? reason : "用户手动终止";
        
        try {
            runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
            log.info("流程终止成功");
        } catch (FlowableObjectNotFoundException e) {
            log.error("流程实例不存�?, e);
            throw new ProcessException("流程实例不存�?);
        } catch (Exception e) {
            log.error("终止流程失败", e);
            throw new ProcessException("终止流程失败: " + e.getMessage());
        }
    }
    
    @Override
    public void deleteProcess(String processInstanceId, String reason) {
        log.info("删除流程, 流程实例ID: {}, 原因: {}", processInstanceId, reason);
        
        String deleteReason = StringUtils.hasText(reason) ? reason : "用户手动删除";
        
        try {
            // 删除运行中的流程实例
            try {
                runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
            } catch (FlowableObjectNotFoundException e) {
                // 流程实例可能已经结束，忽略异�?                log.info("流程实例{}不存在于运行时数据库中，可能已经结束", processInstanceId);
            }
            
            // 删除历史数据
            historyService.deleteHistoricProcessInstance(processInstanceId);
            log.info("流程删除成功");
        } catch (Exception e) {
            log.error("删除流程失败", e);
            throw new ProcessException("删除流程失败: " + e.getMessage());
        }
    }

    @Override
    public ProcessInstance getProcessInstance(String processInstanceId) {
        log.info("获取流程实例, 流程实例ID: {}", processInstanceId);
        
        try {
            return runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();
        } catch (Exception e) {
            log.error("获取流程实例失败", e);
            throw new ProcessException("获取流程实例失败: " + e.getMessage());
        }
    }

    @Override
    public Object getVariable(String processInstanceId, String variableName) {
        log.info("获取流程变量, 流程实例ID: {}, 变量�? {}", processInstanceId, variableName);
        
        try {
            return runtimeService.getVariable(processInstanceId, variableName);
        } catch (FlowableObjectNotFoundException e) {
            log.error("流程实例不存�?, e);
            throw new ProcessException("流程实例不存�?);
        } catch (Exception e) {
            log.error("获取流程变量失败", e);
            throw new ProcessException("获取流程变量失败: " + e.getMessage());
        }
    }

    @Override
    public void setVariable(String processInstanceId, String variableName, Object value) {
        log.info("设置流程变量, 流程实例ID: {}, 变量�? {}", processInstanceId, variableName);
        
        try {
            runtimeService.setVariable(processInstanceId, variableName, value);
            log.info("设置流程变量成功");
        } catch (FlowableObjectNotFoundException e) {
            log.error("流程实例不存�?, e);
            throw new ProcessException("流程实例不存�?);
        } catch (Exception e) {
            log.error("设置流程变量失败", e);
            throw new ProcessException("设置流程变量失败: " + e.getMessage());
        }
    }

    @Override
    public boolean isProcessEnded(String processInstanceId) {
        log.info("检查流程是否已结束, 流程实例ID: {}", processInstanceId);
        
        try {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();
            
            return processInstance == null;
        } catch (Exception e) {
            log.error("检查流程是否已结束失败", e);
            throw new ProcessException("检查流程是否已结束失败: " + e.getMessage());
        }
    }
} 
