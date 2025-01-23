package com.lawfirm.core.workflow.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.variable.api.event.FlowableVariableEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 流程变量监听器
 */
@Slf4j
@Component
public class ProcessVariableListener extends BaseProcessListener {

    private final MongoTemplate mongoTemplate;
    
    public ProcessVariableListener(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
    @Override
    protected void handleEvent(FlowableEvent event) {
        if (!(event instanceof FlowableVariableEvent)) {
            return;
        }
        
        FlowableVariableEvent variableEvent = (FlowableVariableEvent) event;
        if (FlowableEngineEventType.VARIABLE_CREATED.equals(event.getType())) {
            handleVariableCreated(variableEvent);
        } else if (FlowableEngineEventType.VARIABLE_UPDATED.equals(event.getType())) {
            handleVariableUpdated(variableEvent);
        } else if (FlowableEngineEventType.VARIABLE_DELETED.equals(event.getType())) {
            handleVariableDeleted(variableEvent);
        }
    }
    
    private void handleVariableCreated(FlowableVariableEvent event) {
        Map<String, Object> variableInfo = new HashMap<>();
        variableInfo.put("processInstanceId", event.getProcessInstanceId());
        variableInfo.put("processDefinitionId", event.getProcessDefinitionId());
        variableInfo.put("variableName", event.getVariableName());
        variableInfo.put("variableValue", event.getVariableValue());
        variableInfo.put("variableType", event.getVariableType().getTypeName());
        variableInfo.put("createTime", LocalDateTime.now());
        variableInfo.put("eventType", "created");
        
        // 保存到MongoDB
        mongoTemplate.save(variableInfo, "process_variables");
        
        log.info("Process variable created: processInstanceId={}, name={}, value={}",
                event.getProcessInstanceId(), event.getVariableName(), event.getVariableValue());
    }
    
    private void handleVariableUpdated(FlowableVariableEvent event) {
        Map<String, Object> variableInfo = new HashMap<>();
        variableInfo.put("processInstanceId", event.getProcessInstanceId());
        variableInfo.put("processDefinitionId", event.getProcessDefinitionId());
        variableInfo.put("variableName", event.getVariableName());
        variableInfo.put("variableValue", event.getVariableValue());
        variableInfo.put("variableType", event.getVariableType().getTypeName());
        variableInfo.put("updateTime", LocalDateTime.now());
        variableInfo.put("eventType", "updated");
        
        // 保存到MongoDB
        mongoTemplate.save(variableInfo, "process_variables");
        
        log.info("Process variable updated: processInstanceId={}, name={}, value={}",
                event.getProcessInstanceId(), event.getVariableName(), event.getVariableValue());
    }
    
    private void handleVariableDeleted(FlowableVariableEvent event) {
        Map<String, Object> variableInfo = new HashMap<>();
        variableInfo.put("processInstanceId", event.getProcessInstanceId());
        variableInfo.put("processDefinitionId", event.getProcessDefinitionId());
        variableInfo.put("variableName", event.getVariableName());
        variableInfo.put("deleteTime", LocalDateTime.now());
        variableInfo.put("eventType", "deleted");
        
        // 保存到MongoDB
        mongoTemplate.save(variableInfo, "process_variables");
        
        log.info("Process variable deleted: processInstanceId={}, name={}",
                event.getProcessInstanceId(), event.getVariableName());
    }
} 