package com.lawfirm.core.workflow.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEvent;
import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 流程结束监听器
 */
@Slf4j
@Component
public class ProcessCompletionListener extends BaseProcessListener {

    private final MongoTemplate mongoTemplate;
    private final HistoryService historyService;
    
    public ProcessCompletionListener(MongoTemplate mongoTemplate, HistoryService historyService) {
        this.mongoTemplate = mongoTemplate;
        this.historyService = historyService;
    }
    
    @Override
    protected void handleEvent(FlowableEvent event) {
        if (!(event instanceof FlowableEngineEvent)) {
            return;
        }
        
        FlowableEngineEvent engineEvent = (FlowableEngineEvent) event;
        if (FlowableEngineEventType.PROCESS_COMPLETED.equals(event.getType())) {
            handleProcessCompleted(engineEvent);
        } else if (FlowableEngineEventType.PROCESS_CANCELLED.equals(event.getType())) {
            handleProcessCancelled(engineEvent);
        }
    }
    
    private void handleProcessCompleted(FlowableEngineEvent event) {
        HistoricProcessInstance historicProcess = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(event.getProcessInstanceId())
                .includeProcessVariables()
                .singleResult();
                
        if (historicProcess != null) {
            Map<String, Object> completionInfo = new HashMap<>();
            completionInfo.put("processInstanceId", event.getProcessInstanceId());
            completionInfo.put("processDefinitionId", historicProcess.getProcessDefinitionId());
            completionInfo.put("startTime", historicProcess.getStartTime());
            completionInfo.put("endTime", historicProcess.getEndTime());
            completionInfo.put("durationInMillis", historicProcess.getDurationInMillis());
            completionInfo.put("startUserId", historicProcess.getStartUserId());
            completionInfo.put("variables", historicProcess.getProcessVariables());
            completionInfo.put("status", "completed");
            completionInfo.put("completionTime", LocalDateTime.now());
            
            // 保存到MongoDB
            mongoTemplate.save(completionInfo, "process_completions");
            
            log.info("Process completed: processInstanceId={}, duration={}ms",
                    event.getProcessInstanceId(), historicProcess.getDurationInMillis());
                    
            // 发送流程完成通知
            sendCompletionNotification(completionInfo);
        }
    }
    
    private void handleProcessCancelled(FlowableEngineEvent event) {
        HistoricProcessInstance historicProcess = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(event.getProcessInstanceId())
                .includeProcessVariables()
                .singleResult();
                
        if (historicProcess != null) {
            Map<String, Object> completionInfo = new HashMap<>();
            completionInfo.put("processInstanceId", event.getProcessInstanceId());
            completionInfo.put("processDefinitionId", historicProcess.getProcessDefinitionId());
            completionInfo.put("startTime", historicProcess.getStartTime());
            completionInfo.put("endTime", historicProcess.getEndTime());
            completionInfo.put("durationInMillis", historicProcess.getDurationInMillis());
            completionInfo.put("startUserId", historicProcess.getStartUserId());
            completionInfo.put("variables", historicProcess.getProcessVariables());
            completionInfo.put("status", "cancelled");
            completionInfo.put("deleteReason", historicProcess.getDeleteReason());
            completionInfo.put("cancellationTime", LocalDateTime.now());
            
            // 保存到MongoDB
            mongoTemplate.save(completionInfo, "process_completions");
            
            log.info("Process cancelled: processInstanceId={}, reason={}",
                    event.getProcessInstanceId(), historicProcess.getDeleteReason());
                    
            // 发送流程取消通知
            sendCancellationNotification(completionInfo);
        }
    }
    
    /**
     * 发送流程完成通知
     */
    private void sendCompletionNotification(Map<String, Object> completionInfo) {
        // TODO: 实现通知逻辑,如发送邮件、消息等
        log.info("Sending completion notification for process: {}", completionInfo.get("processInstanceId"));
    }
    
    /**
     * 发送流程取消通知
     */
    private void sendCancellationNotification(Map<String, Object> completionInfo) {
        // TODO: 实现通知逻辑,如发送邮件、消息等
        log.info("Sending cancellation notification for process: {}", completionInfo.get("processInstanceId"));
    }
} 