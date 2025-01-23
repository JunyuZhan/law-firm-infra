package com.lawfirm.core.workflow.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.engine.delegate.event.FlowableActivityEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 流程活动监听器
 */
@Slf4j
@Component
public class ProcessActivityListener extends BaseProcessListener {

    private final MongoTemplate mongoTemplate;
    
    public ProcessActivityListener(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
    @Override
    protected void handleEvent(FlowableEvent event) {
        if (!(event instanceof FlowableActivityEvent)) {
            return;
        }
        
        FlowableActivityEvent activityEvent = (FlowableActivityEvent) event;
        if (FlowableEngineEventType.ACTIVITY_STARTED.equals(event.getType())) {
            handleActivityStarted(activityEvent);
        } else if (FlowableEngineEventType.ACTIVITY_COMPLETED.equals(event.getType())) {
            handleActivityCompleted(activityEvent);
        }
    }
    
    private void handleActivityStarted(FlowableActivityEvent event) {
        Map<String, Object> activityInfo = new HashMap<>();
        activityInfo.put("processInstanceId", event.getProcessInstanceId());
        activityInfo.put("processDefinitionId", event.getProcessDefinitionId());
        activityInfo.put("activityId", event.getActivityId());
        activityInfo.put("activityName", event.getActivityName());
        activityInfo.put("activityType", event.getActivityType());
        activityInfo.put("startTime", LocalDateTime.now());
        
        // 保存到MongoDB
        mongoTemplate.save(activityInfo, "process_activities");
        
        log.info("Activity started: processInstanceId={}, activityId={}, activityName={}",
                event.getProcessInstanceId(), event.getActivityId(), event.getActivityName());
    }
    
    private void handleActivityCompleted(FlowableActivityEvent event) {
        // 查找活动开始记录
        Map<String, Object> activityInfo = mongoTemplate.findById(
                event.getProcessInstanceId() + "_" + event.getActivityId(),
                Map.class,
                "process_activities");
                
        if (activityInfo != null) {
            // 更新完成时间和持续时间
            LocalDateTime startTime = (LocalDateTime) activityInfo.get("startTime");
            LocalDateTime endTime = LocalDateTime.now();
            activityInfo.put("endTime", endTime);
            activityInfo.put("duration", java.time.Duration.between(startTime, endTime).toMillis());
            
            // 保存到MongoDB
            mongoTemplate.save(activityInfo, "process_activities");
            
            log.info("Activity completed: processInstanceId={}, activityId={}, activityName={}, duration={}ms",
                    event.getProcessInstanceId(), event.getActivityId(), event.getActivityName(),
                    activityInfo.get("duration"));
        }
    }
} 