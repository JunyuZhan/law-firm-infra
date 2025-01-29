package com.lawfirm.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.client.exception.ClientException;
import com.lawfirm.client.mapper.ClientMapper;
import com.lawfirm.client.processor.ClientEntityProcessor;
import com.lawfirm.client.service.ClientService;
import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.model.base.enums.StatusEnum;
import com.lawfirm.model.client.entity.Client;
import com.lawfirm.model.client.enums.ClientStatusEnum;
import com.lawfirm.model.client.enums.ClientTypeEnum;
import com.lawfirm.model.client.query.ClientQuery;
import com.lawfirm.model.client.vo.ClientVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Validated
public class ClientServiceImpl extends BaseServiceImpl<ClientMapper, Client, ClientVO> implements ClientService {

    @Autowired
    public void setClientEntityProcessor(ClientEntityProcessor processor) {
        this.entityProcessor = processor;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(description = "启用客户")
    public void enableClient(Long id, String operator) {
        Client client = getById(id);
        if (client == null) {
            throw new ClientException("客户不存在");
        }
        client.setStatus(StatusEnum.ENABLED);
        client.setClientStatus(ClientStatusEnum.ACTIVE);
        updateById(client);
        log.info("客户{}已被{}启用", id, operator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(description = "批量启用客户")
    public void enableClients(List<Long> ids, String operator) {
        List<Client> clients = listByIds(ids);
        clients.forEach(client -> {
            client.setStatus(StatusEnum.ENABLED);
            client.setClientStatus(ClientStatusEnum.ACTIVE);
        });
        updateBatchById(clients);
        log.info("客户{}已被{}批量启用", ids, operator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(description = "禁用客户")
    public void disableClient(Long id, String operator) {
        Client client = getById(id);
        if (client == null) {
            throw new ClientException("客户不存在");
        }
        client.setStatus(StatusEnum.DISABLED);
        client.setClientStatus(ClientStatusEnum.INACTIVE);
        updateById(client);
        log.info("客户{}已被{}禁用", id, operator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(description = "批量禁用客户")
    public void disableClients(List<Long> ids, String operator) {
        List<Client> clients = listByIds(ids);
        clients.forEach(client -> {
            client.setStatus(StatusEnum.DISABLED);
            client.setClientStatus(ClientStatusEnum.INACTIVE);
        });
        updateBatchById(clients);
        log.info("客户{}已被{}批量禁用", ids, operator);
    }

    @Override
    @OperationLog(description = "查询个人客户", operationType = "QUERY")
    public List<ClientVO> findPersonalClients(ClientQuery query) {
        QueryWrapper<Client> wrapper = new QueryWrapper<>();
        wrapper.eq("client_type", ClientTypeEnum.INDIVIDUAL);
        if (query != null) {
            if (query.getStatus() != null) {
                wrapper.eq("status", query.getStatus());
            }
            if (query.getClientNumber() != null) {
                wrapper.like("client_number", query.getClientNumber());
            }
            if (query.getClientName() != null) {
                wrapper.like("client_name", query.getClientName());
            }
            if (query.getContactPhone() != null) {
                wrapper.like("contact_phone", query.getContactPhone());
            }
            if (query.getIdNumber() != null) {
                wrapper.like("id_number", query.getIdNumber());
            }
        }
        List<Client> clients = baseMapper.selectList(wrapper);
        return super.toDTOList(clients);
    }

    @Override
    @OperationLog(description = "查询企业客户", operationType = "QUERY")
    public List<ClientVO> findEnterpriseClients(ClientQuery query) {
        QueryWrapper<Client> wrapper = new QueryWrapper<>();
        wrapper.eq("client_type", ClientTypeEnum.ENTERPRISE);
        if (query != null) {
            if (query.getStatus() != null) {
                wrapper.eq("status", query.getStatus());
            }
            if (query.getClientNumber() != null) {
                wrapper.like("client_number", query.getClientNumber());
            }
            if (query.getClientName() != null) {
                wrapper.like("client_name", query.getClientName());
            }
            if (query.getContactPhone() != null) {
                wrapper.like("contact_phone", query.getContactPhone());
            }
            if (query.getIdNumber() != null) {
                wrapper.like("id_number", query.getIdNumber());
            }
        }
        List<Client> clients = baseMapper.selectList(wrapper);
        return super.toDTOList(clients);
    }

    @Override
    @OperationLog(description = "按名称模糊查询", operationType = "QUERY")
    public List<ClientVO> findByNameLike(String name) {
        QueryWrapper<Client> wrapper = new QueryWrapper<>();
        wrapper.like("client_name", name);
        List<Client> clients = baseMapper.selectList(wrapper);
        return super.toDTOList(clients);
    }

    @Override
    @OperationLog(description = "检查客户编号是否存在", operationType = "CHECK")
    public boolean checkClientNumberExists(String clientNumber) {
        QueryWrapper<Client> wrapper = new QueryWrapper<>();
        wrapper.eq("client_number", clientNumber);
        return count(wrapper) > 0;
    }

    @Override
    @OperationLog(description = "检查证件号码是否存在", operationType = "CHECK")
    public boolean checkIdNumberExists(String idNumber) {
        QueryWrapper<Client> wrapper = new QueryWrapper<>();
        wrapper.eq("id_number", idNumber);
        return count(wrapper) > 0;
    }

    @Override
    @Transactional
    @OperationLog(description = "批量创建客户", operationType = "BATCH_CREATE")
    public List<ClientVO> batchCreate(List<Client> clients) {
        saveBatch(clients);
        return super.toDTOList(clients);
    }

    @Override
    @Transactional
    @OperationLog(description = "批量更新客户", operationType = "BATCH_UPDATE")
    public List<ClientVO> batchUpdate(List<Client> clients) {
        updateBatchById(clients);
        return super.toDTOList(clients);
    }

    @Override
    protected ClientVO createDTO() {
        return new ClientVO();
    }

    @Override
    protected Client createEntity() {
        return new Client();
    }

    @Override
    public ClientVO create(ClientVO dto) {
        return super.create(dto);
    }

    @Override
    public ClientVO update(ClientVO dto) {
        return super.update(dto);
    }

    @Override
    @Transactional
    @OperationLog(description = "按类型统计客户", operationType = "COUNT")
    public Map<String, Long> countByType() {
        QueryWrapper<Client> wrapper = new QueryWrapper<>();
        wrapper.select("client_type", "count(*) as count")
                .groupBy("client_type");
        List<Map<String, Object>> maps = baseMapper.selectMaps(wrapper);
        return maps.stream()
                .collect(Collectors.toMap(
                        map -> ClientTypeEnum.valueOf((String) map.get("client_type")).name(),
                        map -> (Long) map.get("count")
                ));
    }

    @Override
    @Transactional
    @OperationLog(description = "按状态统计客户", operationType = "COUNT")
    public Map<String, Long> countByStatus() {
        QueryWrapper<Client> wrapper = new QueryWrapper<>();
        wrapper.select("client_status", "count(*) as count")
                .groupBy("client_status");
        List<Map<String, Object>> maps = baseMapper.selectMaps(wrapper);
        return maps.stream()
                .collect(Collectors.toMap(
                        map -> ClientStatusEnum.valueOf((String) map.get("client_status")).name(),
                        map -> (Long) map.get("count")
                ));
    }
} 