package com.lawfirm.api.adaptor.client;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.client.service.ClientService;
import com.lawfirm.model.client.dto.client.ClientCreateDTO;
import com.lawfirm.model.client.dto.client.ClientQueryDTO;
import com.lawfirm.model.client.dto.client.ClientUpdateDTO;
import com.lawfirm.model.client.vo.ClientVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 客户适配器
 */
@Component
@RequiredArgsConstructor
public class ClientAdaptor extends BaseAdaptor {

    private final ClientService clientService;

    /**
     * 分页查询客户列表
     */
    public List<ClientVO> getClientList(ClientQueryDTO queryDTO) {
        return clientService.listClients(queryDTO);
    }

    /**
     * 获取客户详情
     */
    public ClientVO getClientDetail(Long id) {
        return clientService.getClient(id);
    }

    /**
     * 新增客户
     */
    public Long addClient(ClientCreateDTO createDTO) {
        return clientService.createClient(createDTO);
    }

    /**
     * 修改客户
     */
    public void updateClient(ClientUpdateDTO updateDTO) {
        clientService.updateClient(updateDTO);
    }

    /**
     * 删除客户
     */
    public void deleteClient(Long id) {
        clientService.deleteClient(id);
    }

    /**
     * 更新客户状态
     */
    public void updateStatus(Long id, Integer status) {
        clientService.updateStatus(id, status);
    }

    /**
     * 更新客户信用等级
     */
    public void updateCreditLevel(Long id, String creditLevel) {
        clientService.updateCreditLevel(id, creditLevel);
    }
} 