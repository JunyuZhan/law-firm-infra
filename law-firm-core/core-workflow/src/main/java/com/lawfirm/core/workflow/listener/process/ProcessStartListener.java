package com.lawfirm.core.workflow.listener.process;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.engine.delegate.event.FlowableProcessStartedEvent;
import org.flowable.engine.delegate.event.impl.FlowableProcessEventImpl;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 流程启动事件监听�? * 监听流程启动事件，记录流程启动信息，设置流程变量�? *
 * @author JunyuZhan
 * @date 2023/03/03
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessStartListener implements FlowableEventListener {

    // private final ProcessService processService;
    // private final MessageService messageService;

    @Override
    public void onEvent(FlowableEvent event) {
        if (event.getType() == FlowableEngineEventType.PROCESS_STARTED) {
            FlowableProcessStartedEvent processStartedEvent = (FlowableProcessStartedEvent) event;
            Object entity = processStartedEvent.getEntity();
            if (entity instanceof ProcessInstance) {
                ProcessInstance processInstance = (ProcessInstance) entity;
                
                String processInstanceId = processInstance.getProcessInstanceId();
                String processDefinitionId = processInstance.getProcessDefinitionId();
                String businessKey = processInstance.getBusinessKey();
                String startUserId = processInstance.getStartUserId();
                
                log.info("流程实例启动：实例ID={}, 流程定义ID={}, 业务�?{}, 启动用户={}",
                        processInstanceId, processDefinitionId, businessKey, startUserId);
                
                try {
                    // 处理流程启动逻辑
                    handleProcessStarted(processInstance);
                } catch (Exception e) {
                    log.error("处理流程启动事件异常", e);
                }
            } else {
                log.warn("流程启动事件实体类型不是ProcessInstance: {}", entity != null ? entity.getClass().getName() : "null");
            }
        }
    }

    /**
     * 处理流程启动
     *
     * @param processInstance 流程实例对象
     */
    private void handleProcessStarted(ProcessInstance processInstance) {
        // 1. 设置流程变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("startTime", new Date());
        variables.put("processStartStatus", "SUCCESS");
        
        // 2. 更新流程实例，在实际应用中可能会更新数据库中的流程记�?        // processService.updateProcessInstance(processInstance.getProcessInstanceId(), variables);
        
        // 3. 发送通知
        sendProcessStartNotification(processInstance);
    }

    /**
     * 发送流程启动通知
     *
     * @param processInstance 流程实例对象
     */
    private void sendProcessStartNotification(ProcessInstance processInstance) {
        // 实际应用中应根据流程类型和业务需求发送不同的通知
        String processInstanceId = processInstance.getProcessInstanceId();
        String processDefinitionId = processInstance.getProcessDefinitionId();
        String businessKey = processInstance.getBusinessKey();
        String startUserId = processInstance.getStartUserId();
        
        // 通知内容
        Map<String, Object> notifyParams = new HashMap<>();
        notifyParams.put("processInstanceId", processInstanceId);
        notifyParams.put("processDefinitionId", processDefinitionId);
        notifyParams.put("businessKey", businessKey);
        notifyParams.put("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        
        // 在实际应用中应调用消息服务发送通知
        // messageService.sendMessage(startUserId, "流程启动通知", "您的流程已成功启�?, notifyParams);
        
        // 记录通知日志
        log.info("流程启动通知已发送：实例ID={}, 接收�?{}", processInstanceId, startUserId);
    }

    @Override
    public boolean isFailOnException() {
        // 异常不中断流程执�?        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        // 在事务提交后触发
        return true;
    }

    @Override
    public String getOnTransaction() {
        // 事务提交后触�?        return "COMMITTED";
    }
} 
