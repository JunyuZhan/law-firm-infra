package com.lawfirm.client.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.client.entity.Client;
import com.lawfirm.client.vo.request.ClientAddRequest;
import com.lawfirm.client.vo.request.ClientQueryRequest;
import com.lawfirm.client.vo.request.ClientUpdateRequest;
import com.lawfirm.client.vo.response.ClientResponse;

/**
 * 客户服务接口
 */
public interface IClientService extends IService<Client> {

    /**
     * 添加客户
     *
     * @param request 添加请求
     * @return 客户ID
     */
    Long addClient(ClientAddRequest request);

    /**
     * 更新客户
     *
     * @param request 更新请求
     */
    void updateClient(ClientUpdateRequest request);

    /**
     * 删除客户
     *
     * @param id 客户ID
     */
    void deleteClient(Long id);

    /**
     * 分页查询客户
     *
     * @param request 查询请求
     * @return 分页结果
     */
    IPage<ClientResponse> pageClients(ClientQueryRequest request);

    /**
     * 获取客户详情
     *
     * @param id 客户ID
     * @return 客户详情
     */
    ClientResponse getClientById(Long id);
} 