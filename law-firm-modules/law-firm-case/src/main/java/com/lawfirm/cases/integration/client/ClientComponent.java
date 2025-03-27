package com.lawfirm.cases.integration.client;

import com.lawfirm.model.client.dto.ClientDTO;
import com.lawfirm.model.client.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class ClientComponent {

    private final ClientService clientService;

    @Autowired
    public ClientComponent(@Qualifier("clientServiceImpl") ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * 获取客户详情
     *
     * @param clientId 客户ID
     * @return 客户详情
     */
    public ClientDTO getClientDetail(Long clientId) {
        log.debug("获取客户详情: {}", clientId);
        return clientService.getClientDetail(clientId);
    }

    /**
     * 搜索客户
     *
     * @param name 客户名称
     * @param limit 限制数量
     * @return 客户列表
     */
    public List<ClientDTO> searchClientsByName(String name, int limit) {
        log.debug("搜索客户: name={}, limit={}", name, limit);
        return clientService.searchClientsByName(name, limit);
    }

    /**
     * 检查客户利益冲突
     *
     * @param clientId 客户ID
     * @param oppositeClientIds 对方当事人ID列表
     * @return 是否存在冲突
     */
    public boolean checkClientConflict(Long clientId, List<Long> oppositeClientIds) {
        log.debug("检查客户利益冲突: clientId={}, oppositeClientIds={}", clientId, oppositeClientIds);
        return clientService.checkClientConflict(clientId, oppositeClientIds);
    }

    /**
     * 更新客户案件统计
     *
     * @param clientId 客户ID
     * @param totalCases 总案件数
     * @param activeCases 活跃案件数
     */
    public void updateClientCaseStats(Long clientId, int totalCases, int activeCases) {
        log.debug("更新客户案件统计: clientId={}, totalCases={}, activeCases={}", 
                clientId, totalCases, activeCases);
        clientService.updateClientCaseStats(clientId, totalCases, activeCases);
    }
} 