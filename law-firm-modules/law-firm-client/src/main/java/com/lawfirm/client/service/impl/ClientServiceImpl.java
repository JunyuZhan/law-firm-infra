package com.lawfirm.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.client.util.ClientConverter;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.client.dto.ClientDTO;
import com.lawfirm.model.client.dto.client.ClientCreateDTO;
import com.lawfirm.model.client.dto.client.ClientQueryDTO;
import com.lawfirm.model.client.dto.client.ClientUpdateDTO;
import com.lawfirm.model.client.entity.base.Client;
import com.lawfirm.model.client.mapper.ClientMapper;
import com.lawfirm.model.client.service.ClientService;
import com.lawfirm.model.client.vo.ClientVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户服务实现类
 */
@Slf4j
@Service("clientServiceImpl")
@RequiredArgsConstructor
public class ClientServiceImpl extends BaseServiceImpl<ClientMapper, Client> implements ClientService {
    
    private final ClientMapper clientMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createClient(ClientCreateDTO dto) {
        // 数据转换与验证
        Client client = ClientConverter.toEntity(dto);
        
        // 使用继承的save方法保存数据
        save(client);
        
        // 处理关联数据
        handleRelatedData(client.getId(), dto);
        
        return client.getId();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateClient(ClientUpdateDTO dto) {
        // 获取客户
        Client client = getById(dto.getId());
        if (client == null) {
            throw new IllegalArgumentException("客户不存在");
        }
        
        // 更新客户信息
        ClientConverter.updateEntity(client, dto);
        
        // 保存更新
        updateById(client);
        
        // 处理关联数据
        // TODO: 处理关联数据更新
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteClient(Long id) {
        // 检查客户是否存在
        Client client = getById(id);
        if (client == null) {
            return;
        }
        
        // 删除客户
        removeById(id);
        
        // TODO: 删除关联数据（联系人、地址、标签等）
    }
    
    @Override
    public ClientVO getClient(Long id) {
        // 获取客户
        Client client = getById(id);
        if (client == null) {
            return null;
        }
        
        // 转换为VO
        return ClientConverter.toVO(client);
    }
    
    @Override
    public List<ClientVO> listClients(ClientQueryDTO queryDTO) {
        // 构建查询条件
        LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
        
        // 根据查询参数添加条件
        if (queryDTO.getClientType() != null) {
            wrapper.eq(Client::getClientType, queryDTO.getClientType());
        }
        if (queryDTO.getStatus() != null) {
            wrapper.eq(Client::getStatus, queryDTO.getStatus());
        }
        if (queryDTO.getKeyword() != null && !queryDTO.getKeyword().isEmpty()) {
            wrapper.and(w -> w.like(Client::getClientName, queryDTO.getKeyword())
                    .or()
                    .like(Client::getClientNo, queryDTO.getKeyword()));
        }
        
        // 添加排序
        wrapper.orderByDesc(Client::getUpdateTime);
        
        // 分页参数
        int pageNum = queryDTO.getPageNum() != null ? queryDTO.getPageNum() : 1;
        int pageSize = queryDTO.getPageSize() != null ? queryDTO.getPageSize() : 10;
        
        // 获取客户列表
        List<Client> clients = list(wrapper);
        
        // 转换为VO列表
        return clients.stream()
                .map(ClientConverter::toVO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, Integer status) {
        // 检查客户是否存在
        Client client = getById(id);
        if (client == null) {
            throw new IllegalArgumentException("客户不存在");
        }
        
        // 更新状态
        client.setStatus(status);
        updateById(client);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCreditLevel(Long id, String creditLevel) {
        // 检查客户是否存在
        Client client = getById(id);
        if (client == null) {
            throw new IllegalArgumentException("客户不存在");
        }
        
        // 更新信用等级
        client.setCreditLevel(creditLevel);
        updateById(client);
    }
    
    /**
     * 处理关联数据
     * @param clientId 客户ID
     * @param dto 创建DTO
     */
    private void handleRelatedData(Long clientId, ClientCreateDTO dto) {
        // TODO: 实现关联数据处理
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateClientCaseStats(Long clientId, int totalCases, int activeCases) {
        // 检查客户是否存在
        Client client = getById(clientId);
        if (client == null) {
            throw new IllegalArgumentException("客户不存在");
        }
        
        // 更新案件统计信息
        client.setCaseCount(totalCases);
        client.setActiveCaseCount(activeCases);
        
        // 保存更新
        updateById(client);
    }

    @Override
    public ClientDTO getClientDetail(Long clientId) {
        // 获取客户基本信息
        Client client = getById(clientId);
        if (client == null) {
            return null;
        }
        
        // TODO: 获取客户的详细信息，包括联系人、地址、案件等
        
        // 转换为DTO
        return ClientConverter.toDTO(client);
    }

    @Override
    public List<ClientDTO> searchClientsByName(String name, int limit) {
        // 构建查询条件
        LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Client::getClientName, name)
              .orderByDesc(Client::getUpdateTime)
              .last("LIMIT " + limit);
        
        // 查询客户列表
        List<Client> clients = list(wrapper);
        
        // 转换为DTO列表
        return clients.stream()
                .map(ClientConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkClientConflict(Long clientId, List<Long> oppositeClientIds) {
        // 获取当前客户
        Client client = getById(clientId);
        if (client == null) {
            throw new IllegalArgumentException("客户不存在");
        }
        
        // TODO: 实现利益冲突检查逻辑
        // 1. 检查是否存在关联案件
        // 2. 检查是否存在利益冲突标记
        // 3. 检查其他冲突条件
        
        return false; // 暂时返回false，需要根据实际业务逻辑实现
    }
}
