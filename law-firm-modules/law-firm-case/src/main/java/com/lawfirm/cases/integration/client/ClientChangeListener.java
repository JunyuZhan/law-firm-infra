package com.lawfirm.cases.integration.client;

import com.lawfirm.cases.service.CaseService;
import com.lawfirm.model.client.event.ClientChangeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 客户变更监听器
 * 监听客户信息变更事件，同步更新相关案件信息
 */
@Slf4j
@Component
@Lazy
@RequiredArgsConstructor
public class ClientChangeListener {

    private final CaseService caseService;

    /**
     * 监听客户信息变更事件
     * 异步处理，确保不阻塞客户模块操作
     *
     * @param event 客户变更事件
     */
    @Async
    @EventListener
    public void handleClientChangeEvent(ClientChangeEvent event) {
        log.info("接收到客户变更事件：{}", event);
        
        if (event == null || event.getClientId() == null) {
            log.warn("客户变更事件数据不完整");
            return;
        }

        Long clientId = event.getClientId();
        
        try {
            switch (event.getChangeType()) {
                case UPDATE:
                    // 客户信息更新，同步更新案件中的客户相关信息
                    syncClientInfoToCase(clientId);
                    break;
                case DELETE:
                    // 客户被删除，标记相关案件为风险状态
                    markCasesWithRisk(clientId);
                    break;
                case STATUS_CHANGE:
                    // 客户状态变更，更新相关案件的客户状态
                    updateClientStatusInCases(clientId, event.getNewStatus());
                    break;
                default:
                    log.warn("未知的客户变更类型: {}", event.getChangeType());
            }
        } catch (Exception e) {
            log.error("处理客户变更事件异常，clientId={}", clientId, e);
        }
    }

    /**
     * 同步客户信息到相关案件
     *
     * @param clientId 客户ID
     */
    private void syncClientInfoToCase(Long clientId) {
        log.debug("同步客户信息到相关案件，clientId={}", clientId);
        // 调用案件服务更新相关案件的客户信息
        caseService.syncClientInfo(clientId);
    }

    /**
     * 标记客户相关案件为风险状态
     *
     * @param clientId 客户ID
     */
    private void markCasesWithRisk(Long clientId) {
        log.debug("标记客户相关案件为风险状态，clientId={}", clientId);
        // 调用案件服务标记相关案件为风险状态
        caseService.markCasesWithRisk(clientId, "客户信息已删除");
    }

    /**
     * 更新案件中的客户状态
     *
     * @param clientId 客户ID
     * @param newStatus 新状态
     */
    private void updateClientStatusInCases(Long clientId, String newStatus) {
        log.debug("更新案件中的客户状态，clientId={}, newStatus={}", clientId, newStatus);
        // 调用案件服务更新相关案件的客户状态
        caseService.updateClientStatusInCases(clientId, newStatus);
    }
} 