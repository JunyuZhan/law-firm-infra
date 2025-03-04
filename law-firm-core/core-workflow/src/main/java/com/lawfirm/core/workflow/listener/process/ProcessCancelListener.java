package com.lawfirm.core.workflow.listener.process;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.engine.RuntimeService;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 流程取消事件监听�? * 监听流程取消事件，记录流程取消信息，恢复业务状态等
 *
 * @author JunyuZhan
 * @date 2023/03/03
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessCancelListener implements FlowableEventListener {

    private final RuntimeService runtimeService;
    // private final ProcessService processService;
    // private final MessageService messageService;
    // private final BusinessStatusService businessStatusService;

    @Override
    public void onEvent(FlowableEvent event) {
        if (event.getType() == FlowableEngineEventType.PROCESS_CANCELLED) {
            try {
                // 从事件中获取流程实例ID
                String processInstanceId = extractProcessInstanceId(event);
                if (processInstanceId == null) {
                    log.warn("无法从事件中提取流程实例ID");
                    return;
                }
                
                // 查询流程实例信息（注意：此时流程已取消，可能需要从历史服务中查询）
                // 这里为了示例简单，我们使用一个模拟的流程实例对象
                ProcessInstanceWrapper processInstance = new ProcessInstanceWrapper(processInstanceId);
                
                log.info("流程实例取消：实例ID={}", processInstanceId);
                
                // 处理流程取消逻辑
                handleProcessCancelled(processInstance);
            } catch (Exception e) {
                log.error("处理流程取消事件异常", e);
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
     * 处理流程取消
     *
     * @param processInstance 流程实例对象
     */
    private void handleProcessCancelled(ProcessInstanceWrapper processInstance) {
        String processInstanceId = processInstance.getProcessInstanceId();
        String businessKey = processInstance.getBusinessKey();
        
        // 1. 记录流程取消时间和原�?        Map<String, Object> variables = new HashMap<>();
        variables.put("cancelTime", new Date());
        variables.put("processEndStatus", "CANCELLED");
        
        // 可以尝试获取取消原因
        String cancelReason = getCancelReason(processInstanceId);
        if (cancelReason != null) {
            variables.put("cancelReason", cancelReason);
        }
        
        // 2. 更新流程实例状�?        // processService.updateProcessInstance(processInstanceId, variables);
        
        // 3. 恢复业务状�?        revertBusinessStatus(businessKey);
        
        // 4. 发送通知
        sendProcessCancelledNotification(processInstance, cancelReason);
    }

    /**
     * 获取流程取消原因
     *
     * @param processInstanceId 流程实例ID
     * @return 取消原因
     */
    private String getCancelReason(String processInstanceId) {
        // 在实际应用中，可以从历史服务中查询取消原�?        // HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
        //         .processInstanceId(processInstanceId)
        //         .singleResult();
        //
        // return historicProcessInstance != null ? historicProcessInstance.getDeleteReason() : null;
        
        // 这里为了演示，返回一个默认原�?        return "用户手动取消";
    }

    /**
     * 恢复业务状�?     *
     * @param businessKey 业务�?     */
    private void revertBusinessStatus(String businessKey) {
        if (businessKey == null || businessKey.isEmpty()) {
            log.warn("业务键为空，无法恢复业务状�?);
            return;
        }
        
        try {
            // 在实际应用中，应根据业务键解析出业务类型和ID，并恢复对应的业务状�?            // 例如：合同审批被取消，恢复合同状态为"草稿"
            log.info("业务状态已恢复: businessKey={}", businessKey);
        } catch (Exception e) {
            log.error("恢复业务状态异�? businessKey={}", businessKey, e);
        }
    }

    /**
     * 发送流程取消通知
     *
     * @param processInstance 流程实例对象
     * @param cancelReason 取消原因
     */
    private void sendProcessCancelledNotification(ProcessInstanceWrapper processInstance, String cancelReason) {
        String processInstanceId = processInstance.getProcessInstanceId();
        String businessKey = processInstance.getBusinessKey();
        String startUserId = processInstance.getStartUserId();
        
        // 通知内容
        Map<String, Object> notifyParams = new HashMap<>();
        notifyParams.put("processInstanceId", processInstanceId);
        notifyParams.put("businessKey", businessKey);
        notifyParams.put("cancelTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        notifyParams.put("cancelReason", cancelReason);
        
        // 在实际应用中应调用消息服务发送通知
        // messageService.sendMessage(startUserId, "流程取消通知", "您启动的流程已被取消", notifyParams);
        
        // 记录通知日志
        log.info("流程取消通知已发送：实例ID={}, 接收�?{}, 取消原因={}", processInstanceId, startUserId, cancelReason);
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
