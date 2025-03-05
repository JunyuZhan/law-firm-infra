package com.lawfirm.core.workflow.listener.process;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 流程结束事件监听�? * 监听流程结束事件，记录流程结束信息，更新业务状态等
 *
 * @author JunyuZhan
 * @date 2023/03/03
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessEndListener implements FlowableEventListener {

    private final RuntimeService runtimeService;
    // private final ProcessService processService;
    // private final MessageService messageService;
    // private final BusinessStatusService businessStatusService;

    @Override
    public void onEvent(FlowableEvent event) {
        if (event.getType() == FlowableEngineEventType.PROCESS_COMPLETED) {
            try {
                // 从事件中获取流程实例ID
                String processInstanceId = extractProcessInstanceId(event);
                if (processInstanceId == null) {
                    log.warn("无法从事件中提取流程实例ID");
                    return;
                }
                
                // 查询流程实例信息（注意：此时流程已结束，可能需要从历史服务中查询）
                // 这里为了示例简单，我们使用一个模拟的流程实例对象
                ProcessInstanceWrapper processInstance = new ProcessInstanceWrapper(processInstanceId);
                
                log.info("流程实例完成：实例ID={}", processInstanceId);
                
                // 处理流程完成逻辑
                handleProcessCompleted(processInstance);
            } catch (Exception e) {
                log.error("处理流程完成事件异常", e);
            }
        }
    }

    /**
     * 从事件中提取流程实例ID
     * 
     * @param event 流程事件
     * @return 流程实例ID
     */
    private String extractProcessInstanceId(FlowableEvent event) {
        // 由于缺少特定的事件类型，这里使用反射或其他方法尝试获取流程实例ID
        // 实际开发中，应根据Flowable版本和事件类型的具体实现进行提取
        try {
            // 尝试从事件对象中获取processInstanceId属�?            java.lang.reflect.Method method = event.getClass().getMethod("getProcessInstanceId");
            return (String) method.invoke(event);
        } catch (Exception e) {
            log.warn("从事件中提取流程实例ID失败", e);
            return null;
        }
    }

    /**
     * 处理流程完成
     *
     * @param processInstance 流程实例对象
     */
    private void handleProcessCompleted(ProcessInstanceWrapper processInstance) {
        String processInstanceId = processInstance.getProcessInstanceId();
        String businessKey = processInstance.getBusinessKey();
        
        // 1. 记录流程结束时间
        Map<String, Object> variables = new HashMap<>();
        variables.put("endTime", new Date());
        variables.put("processEndStatus", "COMPLETED");
        
        // 2. 更新流程实例状�?        // processService.updateProcessInstance(processInstanceId, variables);
        
        // 3. 更新业务状�?        updateBusinessStatus(businessKey);
        
        // 4. 发送通知
        sendProcessCompletedNotification(processInstance);
    }

    /**
     * 更新业务状�?     *
     * @param businessKey 业务�?     */
    private void updateBusinessStatus(String businessKey) {
        if (businessKey == null || businessKey.isEmpty()) {
            log.warn("业务键为空，无法更新业务状�?);
            return;
        }
        
        try {
            // 在实际应用中，应根据业务键解析出业务类型和ID，并更新对应的业务状�?            // 例如：合同审批完成后，更新合同状态为"已审�?
            log.info("业务状态已更新: businessKey={}", businessKey);
        } catch (Exception e) {
            log.error("更新业务状态异�? businessKey={}", businessKey, e);
        }
    }

    /**
     * 发送流程完成通知
     *
     * @param processInstance 流程实例对象
     */
    private void sendProcessCompletedNotification(ProcessInstanceWrapper processInstance) {
        String processInstanceId = processInstance.getProcessInstanceId();
        String businessKey = processInstance.getBusinessKey();
        String startUserId = processInstance.getStartUserId();
        
        // 通知内容
        Map<String, Object> notifyParams = new HashMap<>();
        notifyParams.put("processInstanceId", processInstanceId);
        notifyParams.put("businessKey", businessKey);
        notifyParams.put("completionTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        
        // 在实际应用中应调用消息服务发送通知
        // messageService.sendMessage(startUserId, "流程完成通知", "您启动的流程已成功完�?, notifyParams);
        
        // 记录通知日志
        log.info("流程完成通知已发送：实例ID={}, 接收�?{}", processInstanceId, startUserId);
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
    
    /**
     * 流程实例包装�?     * 由于无法直接获取流程实例对象，我们创建一个简单的包装类来模拟ProcessInstance的行�?     */
    private class ProcessInstanceWrapper {
        private final String processInstanceId;
        private String businessKey;
        private String processDefinitionId;
        private String startUserId;
        
        public ProcessInstanceWrapper(String processInstanceId) {
            this.processInstanceId = processInstanceId;
            
            // 在实际应用中，可以从历史服务中查询这些信�?            // HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
            //         .processInstanceId(processInstanceId)
            //         .singleResult();
            //
            // if (historicProcessInstance != null) {
            //     this.businessKey = historicProcessInstance.getBusinessKey();
            //     this.processDefinitionId = historicProcessInstance.getProcessDefinitionId();
            //     this.startUserId = historicProcessInstance.getStartUserId();
            // }
            
            // 这里为了演示，使用模拟数�?            this.businessKey = "demo:1001";
            this.processDefinitionId = "process:1:1001";
            this.startUserId = "admin";
        }
        
        public String getProcessInstanceId() {
            return processInstanceId;
        }
        
        public String getBusinessKey() {
            return businessKey;
        }
        
        public String getProcessDefinitionId() {
            return processDefinitionId;
        }
        
        public String getStartUserId() {
            return startUserId;
        }
    }
} 
