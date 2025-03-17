package com.lawfirm.cases.integration.client;

import com.lawfirm.model.client.dto.ClientDTO;
import com.lawfirm.model.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 客户组件
 * 负责与客户模块的集成
 */
@Slf4j
@Component
@Lazy
@RequiredArgsConstructor
public class ClientComponent {

    private final ClientService clientService;

    /**
     * 获取客户详细信息
     *
     * @param clientId 客户ID
     * @return 客户详情
     */
    public Optional<ClientDTO> getClientDetail(Long clientId) {
        if (clientId == null) {
            return Optional.empty();
        }

        try {
            return Optional.ofNullable(clientService.getClientDetail(clientId));
        } catch (Exception e) {
            log.error("获取客户详情异常，clientId={}", clientId, e);
            return Optional.empty();
        }
    }

    /**
     * 根据名称模糊搜索客户列表
     *
     * @param keyword 关键词
     * @param limit 限制数量
     * @return 客户列表
     */
    public List<ClientDTO> searchClientsByName(String keyword, int limit) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList();
        }

        try {
            return clientService.searchClientsByName(keyword, limit);
        } catch (Exception e) {
            log.error("搜索客户异常，keyword={}", keyword, e);
            return Collections.emptyList();
        }
    }

    /**
     * 检查客户是否存在冲突
     *
     * @param clientId 客户ID
     * @param opposingClientIds 对方当事人客户ID列表
     * @return 是否存在冲突
     */
    public boolean checkClientConflict(Long clientId, List<Long> opposingClientIds) {
        if (clientId == null || opposingClientIds == null || opposingClientIds.isEmpty()) {
            return false;
        }

        try {
            return clientService.checkClientConflict(clientId, opposingClientIds);
        } catch (Exception e) {
            log.error("检查客户冲突异常，clientId={}, opposingClientIds={}", clientId, opposingClientIds, e);
            // 发生异常时返回true表示可能存在冲突，需要人工审核
            return true;
        }
    }

    /**
     * 更新客户案件统计信息
     *
     * @param clientId 客户ID
     * @param caseCount 案件总数
     * @param activeCaseCount 活跃案件数
     */
    public void updateClientCaseStats(Long clientId, int caseCount, int activeCaseCount) {
        if (clientId == null) {
            return;
        }

        try {
            clientService.updateClientCaseStats(clientId, caseCount, activeCaseCount);
        } catch (Exception e) {
            log.error("更新客户案件统计信息异常，clientId={}", clientId, e);
        }
    }
} 