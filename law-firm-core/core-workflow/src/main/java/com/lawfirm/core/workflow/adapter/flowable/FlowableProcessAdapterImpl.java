package com.lawfirm.core.workflow.adapter.flowable;

import com.lawfirm.core.workflow.exception.ProcessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.lawfirm.core.workflow.vo.ProcessDefinitionVO;
import com.lawfirm.core.workflow.adapter.converter.ProcessConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Flowable流程引擎适配器实现类
 *
 * @author JunyuZhan
 */
@Slf4j
@Component("coreFlowableProcessAdapterImpl")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "law-firm", name = "workflow.enabled", havingValue = "true", matchIfMissing = false)
public class FlowableProcessAdapterImpl implements FlowableProcessAdapter {

    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;
    private final HistoryService historyService;
    private final ProcessConverter processConverter;

    @Override
    public ProcessDefinitionVO getProcessDefinition(String processDefinitionId) {
        log.info("获取流程定义: processDefinitionId={}", processDefinitionId);
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        return processConverter.toProcessDefinitionVO(processDefinition);
    }

    @Override
    public List<ProcessDefinitionVO> getProcessDefinitionList(String processDefinitionKey) {
        log.info("获取流程定义列表: processDefinitionKey={}", processDefinitionKey);
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .orderByProcessDefinitionVersion().desc()
                .list();
        return processDefinitions.stream()
                .map(processConverter::toProcessDefinitionVO)
                .collect(Collectors.toList());
    }

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
    public void suspendProcess(String processInstanceId) {
        log.info("挂起流程, 流程实例ID: {}", processInstanceId);
        
        try {
            runtimeService.suspendProcessInstanceById(processInstanceId);
            log.info("流程挂起成功");
        } catch (FlowableObjectNotFoundException e) {
            log.error("流程实例不存在", e);
            throw new ProcessException("流程实例不存在");
        } catch (Exception e) {
            log.error("挂起流程失败", e);
            throw new ProcessException("挂起流程失败: " + e.getMessage());
        }
    }

    @Override
    public void activateProcess(String processInstanceId) {
        log.info("激活流程, 流程实例ID: {}", processInstanceId);
        
        try {
            runtimeService.activateProcessInstanceById(processInstanceId);
            log.info("流程激活成功");
        } catch (FlowableObjectNotFoundException e) {
            log.error("流程实例不存在", e);
            throw new ProcessException("流程实例不存在");
        } catch (Exception e) {
            log.error("激活流程失败", e);
            throw new ProcessException("激活流程失败: " + e.getMessage());
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
            log.error("流程实例不存在", e);
            throw new ProcessException("流程实例不存在");
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
            try {
                runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
            } catch (FlowableObjectNotFoundException e) {
                log.info("流程实例{}不存在于运行时数据库中，可能已经结束", processInstanceId);
            }
            
            historyService.deleteHistoricProcessInstance(processInstanceId);
            log.info("流程删除成功");
        } catch (Exception e) {
            log.error("删除流程失败", e);
            throw new ProcessException("删除流程失败: " + e.getMessage());
        }
    }

    @Override
    public Object getVariable(String processInstanceId, String variableName) {
        log.info("获取流程变量, 流程实例ID: {}, 变量名: {}", processInstanceId, variableName);
        
        try {
            return runtimeService.getVariable(processInstanceId, variableName);
        } catch (FlowableObjectNotFoundException e) {
            log.error("流程实例不存在", e);
            throw new ProcessException("流程实例不存在");
        } catch (Exception e) {
            log.error("获取流程变量失败", e);
            throw new ProcessException("获取流程变量失败: " + e.getMessage());
        }
    }

    @Override
    public void setVariable(String processInstanceId, String variableName, Object value) {
        log.info("设置流程变量, 流程实例ID: {}, 变量名: {}", processInstanceId, variableName);
        
        try {
            runtimeService.setVariable(processInstanceId, variableName, value);
            log.info("设置流程变量成功");
        } catch (FlowableObjectNotFoundException e) {
            log.error("流程实例不存在", e);
            throw new ProcessException("流程实例不存在");
        } catch (Exception e) {
            log.error("设置流程变量失败", e);
            throw new ProcessException("设置流程变量失败: " + e.getMessage());
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
