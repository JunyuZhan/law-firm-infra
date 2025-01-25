package com.lawfirm.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.client.exception.ClientException;
import com.lawfirm.client.mapper.ClientMapper;
import com.lawfirm.client.service.ClientService;
import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.model.client.entity.Client;
import com.lawfirm.model.client.enums.ClientStatusEnum;
import com.lawfirm.model.client.enums.ClientTypeEnum;
import com.lawfirm.model.client.query.ClientQuery;
import com.lawfirm.model.client.vo.ClientVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientServiceImpl extends BaseServiceImpl<ClientMapper, Client, ClientVO> implements ClientService {

    @Override
    protected ClientVO createDTO() {
        return new ClientVO();
    }

    @Override
    protected Client createEntity() {
        return new Client();
    }

    @Override
    @Transactional
    @OperationLog(description = "启用客户", operationType = "ENABLE")
    public void enableClient(Long id, String operator) {
        Client client = getById(id);
        if (client == null) {
            throw new ClientException(ResultCode.DATA_NOT_FOUND);
        }
        
        client.setStatus(ClientStatusEnum.ENABLED.getCode());
        updateById(client);
        log.info("Client {} enabled by {}", id, operator);
    }

    @Override
    @Transactional
    @OperationLog(description = "批量启用客户", operationType = "BATCH_ENABLE")
    public void enableClients(List<Long> ids, String operator) {
        List<Client> clients = listByIds(ids);
        if (clients.isEmpty()) {
            throw new ClientException(ResultCode.DATA_NOT_FOUND);
        }
        
        clients.forEach(client -> client.setStatus(ClientStatusEnum.ENABLED.getCode()));
        updateBatchById(clients);
        log.info("Clients {} enabled by {}", ids, operator);
    }

    @Override
    @Transactional
    @OperationLog(description = "禁用客户", operationType = "DISABLE")
    public void disableClient(Long id, String operator) {
        Client client = getById(id);
        if (client == null) {
            throw new ClientException(ResultCode.DATA_NOT_FOUND);
        }
        
        client.setStatus(ClientStatusEnum.DISABLED.getCode());
        updateById(client);
        log.info("Client {} disabled by {}", id, operator);
    }

    @Override
    @Transactional
    @OperationLog(description = "批量禁用客户", operationType = "BATCH_DISABLE")
    public void disableClients(List<Long> ids, String operator) {
        List<Client> clients = listByIds(ids);
        if (clients.isEmpty()) {
            throw new ClientException(ResultCode.DATA_NOT_FOUND);
        }
        
        clients.forEach(client -> client.setStatus(ClientStatusEnum.DISABLED.getCode()));
        updateBatchById(clients);
        log.info("Clients {} disabled by {}", ids, operator);
    }

    @Override
    @OperationLog(description = "按类型统计客户", operationType = "COUNT")
    public Map<String, Long> countByType() {
        List<Client> clients = list();
        return clients.stream()
                .collect(Collectors.groupingBy(
                        client -> ClientTypeEnum.valueOf(client.getClientType()).getDescription(),
                        Collectors.counting()
                ));
    }

    @Override
    @OperationLog(description = "按状态统计客户", operationType = "COUNT")
    public Map<String, Long> countByStatus() {
        List<Client> clients = list();
        return clients.stream()
                .collect(Collectors.groupingBy(
                        client -> ClientStatusEnum.valueOf(client.getStatus()).getDescription(),
                        Collectors.counting()
                ));
    }

    @Override
    @OperationLog(description = "查询个人客户", operationType = "QUERY")
    public List<ClientVO> findPersonalClients(ClientQuery query) {
        LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Client::getClientType, ClientTypeEnum.PERSONAL.getCode());
        
        if (query.getStatus() != null) {
            wrapper.eq(Client::getStatus, query.getStatus());
        }
        if (query.getClientNumber() != null) {
            wrapper.like(Client::getClientNumber, query.getClientNumber());
        }
        if (query.getClientName() != null) {
            wrapper.like(Client::getClientName, query.getClientName());
        }
        if (query.getContactPhone() != null) {
            wrapper.like(Client::getContactPhone, query.getContactPhone());
        }
        if (query.getIdNumber() != null) {
            wrapper.like(Client::getIdNumber, query.getIdNumber());
        }
        
        return list(wrapper).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @OperationLog(description = "查询企业客户", operationType = "QUERY")
    public List<ClientVO> findEnterpriseClients(ClientQuery query) {
        LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Client::getClientType, ClientTypeEnum.ENTERPRISE.getCode());
        
        if (query.getStatus() != null) {
            wrapper.eq(Client::getStatus, query.getStatus());
        }
        if (query.getClientNumber() != null) {
            wrapper.like(Client::getClientNumber, query.getClientNumber());
        }
        if (query.getClientName() != null) {
            wrapper.like(Client::getClientName, query.getClientName());
        }
        if (query.getContactPhone() != null) {
            wrapper.like(Client::getContactPhone, query.getContactPhone());
        }
        if (query.getIdNumber() != null) {
            wrapper.like(Client::getIdNumber, query.getIdNumber());
        }
        
        return list(wrapper).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @OperationLog(description = "按名称模糊查询", operationType = "QUERY")
    public List<ClientVO> findByNameLike(String name) {
        LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Client::getClientName, name);
        return list(wrapper).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @OperationLog(description = "检查客户编号是否存在", operationType = "CHECK")
    public boolean checkClientNumberExists(String clientNumber) {
        LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Client::getClientNumber, clientNumber);
        return count(wrapper) > 0;
    }

    @Override
    @OperationLog(description = "检查证件号码是否存在", operationType = "CHECK")
    public boolean checkIdNumberExists(String idNumber) {
        LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Client::getIdNumber, idNumber);
        return count(wrapper) > 0;
    }

    @Override
    @Transactional
    @OperationLog(description = "批量创建客户", operationType = "BATCH_CREATE")
    public List<ClientVO> batchCreate(List<Client> clients) {
        // 检查客户编号和证件号码是否重复
        List<String> clientNumbers = clients.stream()
                .map(Client::getClientNumber)
                .collect(Collectors.toList());
        List<String> idNumbers = clients.stream()
                .map(Client::getIdNumber)
                .collect(Collectors.toList());
                
        LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Client::getClientNumber, clientNumbers)
                .or()
                .in(Client::getIdNumber, idNumbers);
        List<Client> existingClients = list(wrapper);
        
        if (!existingClients.isEmpty()) {
            throw new ClientException("存在重复的客户编号或证件号码");
        }
        
        saveBatch(clients);
        return clients.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @OperationLog(description = "批量更新客户", operationType = "BATCH_UPDATE")
    public List<ClientVO> batchUpdate(List<Client> clients) {
        // 检查客户编号和证件号码是否重复
        Map<String, Client> clientNumberMap = new ArrayList<>(clients).stream()
                .collect(Collectors.toMap(Client::getClientNumber, client -> client));
        Map<String, Client> idNumberMap = new ArrayList<>(clients).stream()
                .collect(Collectors.toMap(Client::getIdNumber, client -> client));
                
        if (clientNumberMap.size() != clients.size() || idNumberMap.size() != clients.size()) {
            throw new ClientException("客户列表中存在重复的客户编号或证件号码");
        }
        
        updateBatchById(clients);
        return clients.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    protected void beforeCreate(Client entity) {
        // 检查客户编号是否已存在
        if (checkClientNumberExists(entity.getClientNumber())) {
            throw new ClientException("客户编号已存在");
        }
        // 检查证件号码是否已存在
        if (checkIdNumberExists(entity.getIdNumber())) {
            throw new ClientException("证件号码已存在");
        }
    }

    @Override
    protected void beforeUpdate(Client entity) {
        Client oldClient = getById(entity.getId());
        // 如果客户编号发生变化，检查新的客户编号是否已存在
        if (!oldClient.getClientNumber().equals(entity.getClientNumber()) 
                && checkClientNumberExists(entity.getClientNumber())) {
            throw new ClientException("客户编号已存在");
        }
        // 如果证件号码发生变化，检查新的证件号码是否已存在
        if (!oldClient.getIdNumber().equals(entity.getIdNumber()) 
                && checkIdNumberExists(entity.getIdNumber())) {
            throw new ClientException("证件号码已存在");
        }
    }

    @Override
    protected void afterLoad(ClientVO dto, Client entity) {
        // 设置类型描述
        dto.setClientTypeDesc(ClientTypeEnum.valueOf(entity.getClientType()).getDescription());
        // 设置状态描述
        dto.setStatusDesc(ClientStatusEnum.valueOf(entity.getStatus()).getDescription());
    }
} 