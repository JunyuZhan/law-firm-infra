package com.lawfirm.core.workflow.listener;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEvent;
import org.springframework.stereotype.Component;

/**
 * 流程指标监听器
 */
@Slf4j
@Component
public class ProcessMetricsListener extends BaseProcessListener {

    private final MeterRegistry meterRegistry;

    public ProcessMetricsListener(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    protected void handleEvent(FlowableEvent event) {
        if (!(event instanceof FlowableEngineEvent)) {
            return;
        }

        FlowableEngineEvent engineEvent = (FlowableEngineEvent) event;
        if (FlowableEngineEventType.PROCESS_STARTED.equals(event.getType())) {
            handleProcessStarted(engineEvent);
        } else if (FlowableEngineEventType.PROCESS_COMPLETED.equals(event.getType())) {
            handleProcessCompleted(engineEvent);
        }
    }

    private void handleProcessStarted(FlowableEngineEvent event) {
        // 记录流程启动计数
        meterRegistry.counter("workflow.process.started", 
                "processDefinitionId", event.getProcessDefinitionId(),
                "processInstanceId", event.getProcessInstanceId())
                .increment();

        log.debug("Process started metrics recorded: processInstanceId={}, processDefinitionId={}",
                event.getProcessInstanceId(), event.getProcessDefinitionId());
    }

    private void handleProcessCompleted(FlowableEngineEvent event) {
        // 记录流程完成计数
        meterRegistry.counter("workflow.process.completed",
                "processDefinitionId", event.getProcessDefinitionId(),
                "processInstanceId", event.getProcessInstanceId())
                .increment();

        log.debug("Process completed metrics recorded: processInstanceId={}, processDefinitionId={}",
                event.getProcessInstanceId(), event.getProcessDefinitionId());
    }
} 