package com.lawfirm.model.client.service;

import com.lawfirm.model.client.dto.client.ClientCreateDTO;
import com.lawfirm.model.client.dto.client.ClientQueryDTO;
import com.lawfirm.model.client.dto.client.ClientUpdateDTO;
import com.lawfirm.model.client.vo.ClientVO;
import com.lawfirm.model.client.entity.base.Client;
import com.lawfirm.model.base.service.BaseService;

import java.util.List;

/**
 * 客户服务接口
 */
public interface ClientService extends BaseService<Client> {

    /**
     * 创建客户
     *
     * @param createDTO 创建参数
     * @return 客户ID
     */
    Long createClient(ClientCreateDTO createDTO);

    /**
     * 更新客户
     *
     * @param updateDTO 更新参数
     */
    void updateClient(ClientUpdateDTO updateDTO);

    /**
     * 删除客户
     *
     * @param id 客户ID
     */
    void deleteClient(Long id);

    /**
     * 获取客户详情
     *
     * @param id 客户ID
     * @return 客户详情
     */
    ClientVO getClient(Long id);

    /**
     * 查询客户列表
     *
     * @param queryDTO 查询参数
     * @return 客户列表
     */
    List<ClientVO> listClients(ClientQueryDTO queryDTO);

    /**
     * 更新客户状态
     *
     * @param id 客户ID
     * @param status 状态
     */
    void updateStatus(Long id, Integer status);

    /**
     * 更新客户信用等级
     *
     * @param id 客户ID
     * @param creditLevel 信用等级
     */
    void updateCreditLevel(Long id, String creditLevel);
} 