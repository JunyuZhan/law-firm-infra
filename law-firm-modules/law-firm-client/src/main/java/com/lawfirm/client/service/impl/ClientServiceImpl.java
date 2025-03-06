package com.lawfirm.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.client.exception.ClientException;
import com.lawfirm.client.mapper.ClientMapper;
import com.lawfirm.client.processor.ClientEntityProcessor;
import com.lawfirm.client.service.ClientService;
import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.common.core.enums.StatusEnum;
import com.lawfirm.model.client.entity.Client;
import com.lawfirm.model.client.enums.ClientStatusEnum;
import com.lawfirm.model.client.enums.ClientTypeEnum;
import com.lawfirm.model.client.query.ClientQuery;
import com.lawfirm.model.client.vo.ClientVO;
import com.lawfirm.model.client.dto.ClientDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import com.lawfirm.common.util.BeanUtils;
import org.springframework.util.CollectionUtils;
import java.util.Collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * 客户服务实现类
 */
@Service
@Slf4j
@Validated
public class ClientServiceImpl extends BaseServiceImpl<ClientMapper, Client, ClientVO> implements ClientService {

    @Autowired
    public void setClientEntityProcessor(ClientEntityProcessor processor) {
        this.entityProcessor = processor;
    }

    @Override
    protected Client createEntity() {
        return new Client();
    }

    @Override
    protected ClientVO createVO() {
        return new ClientVO();
    }

    // DTO转换方法
    protected ClientVO dtoToVO(ClientDTO dto) {
        if (dto == null) {
            return null;
        }
        ClientVO vo = createVO();
        BeanUtils.copyProperties(dto, vo);
        return vo;
    }

    protected List<ClientVO> dtoListToVOList(List<ClientDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Collections.emptyList();
        }
        return dtoList.stream()
                .map(this::dtoToVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @OperationLog(description = "批量创建客户")
    public List<ClientVO> batchCreate(@Valid List<ClientDTO> dtos) {
        List<ClientVO> vos = dtoListToVOList(dtos);
        List<Client> entities = voListToEntityList(vos);
        saveBatch(entities);
        return entityListToVOList(entities);
    }

    @Override
    @Transactional
    @OperationLog(description = "批量更新客户")
    public List<ClientVO> batchUpdate(@Valid List<ClientDTO> dtos) {
        List<ClientVO> vos = dtoListToVOList(dtos);
        List<Client> entities = voListToEntityList(vos);
        updateBatchById(entities);
        return entityListToVOList(entities);
    }

    @Override
    public ClientVO create(@Valid ClientDTO dto) {
        ClientVO vo = dtoToVO(dto);
        Client entity = voToEntity(vo);
        save(entity);
        return entityToVO(entity);
    }

    @Override
    public ClientVO update(@Valid ClientDTO dto) {
        ClientVO vo = dtoToVO(dto);
        Client entity = voToEntity(vo);
        updateById(entity);
        return entityToVO(entity);
    }

    @Override
    public ClientVO entityToVO(Client entity) {
        if (entity == null) {
            return null;
        }
        ClientVO vo = createVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public Client voToEntity(ClientVO vo) {
        if (vo == null) {
            return null;
        }
        Client entity = createEntity();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }

    @Override
    public List<ClientVO> entityListToVOList(List<Client> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
        return entityList.stream()
                .map(this::entityToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Client> voListToEntityList(List<ClientVO> voList) {
        if (CollectionUtils.isEmpty(voList)) {
            return Collections.emptyList();
        }
        return voList.stream()
                .map(this::voToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientVO> findByQuery(ClientQuery query) {
        QueryWrapper<Client> wrapper = new QueryWrapper<>();
        if (query != null) {
            if (query.getClientType() != null) {
                wrapper.eq("client_type", query.getClientType());
            }
            if (query.getStatus() != null) {
                wrapper.eq("client_status", query.getStatus());
            }
            if (query.getClientName() != null) {
                wrapper.like("client_name", query.getClientName());
            }
        }
        return listVO(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(description = "启用客户")
    public void enableClient(@NotNull Long id, @NotBlank String operator) {
        Client client = getById(id);
        if (client != null) {
            client.setClientStatus(ClientStatusEnum.ENABLED);
            client.setUpdateBy(operator);
            updateById(client);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(description = "批量启用客户")
    public void enableClients(@NotEmpty List<Long> ids, @NotBlank String operator) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<Client> clients = listByIds(ids);
            clients.forEach(client -> {
                client.setClientStatus(ClientStatusEnum.ENABLED);
                client.setUpdateBy(operator);
            });
            updateBatchById(clients);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(description = "禁用客户")
    public void disableClient(@NotNull Long id, @NotBlank String operator) {
        Client client = getById(id);
        if (client != null) {
            client.setClientStatus(ClientStatusEnum.DISABLED);
            client.setUpdateBy(operator);
            updateById(client);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(description = "批量禁用客户")
    public void disableClients(@NotEmpty List<Long> ids, @NotBlank String operator) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<Client> clients = listByIds(ids);
            clients.forEach(client -> {
                client.setClientStatus(ClientStatusEnum.DISABLED);
                client.setUpdateBy(operator);
            });
            updateBatchById(clients);
        }
    }

    @Override
    public List<ClientVO> findPersonalClients(ClientQuery query) {
        QueryWrapper<Client> wrapper = new QueryWrapper<>();
        wrapper.eq("client_type", ClientTypeEnum.INDIVIDUAL);
        if (query.getStatus() != null) {
            wrapper.eq("client_status", query.getStatus());
        }
        if (query.getClientName() != null) {
            wrapper.like("client_name", query.getClientName());
        }
        return listVO(wrapper);
    }

    @Override
    public List<ClientVO> findEnterpriseClients(ClientQuery query) {
        QueryWrapper<Client> wrapper = new QueryWrapper<>();
        wrapper.eq("client_type", ClientTypeEnum.ENTERPRISE);
        if (query.getStatus() != null) {
            wrapper.eq("client_status", query.getStatus());
        }
        if (query.getClientName() != null) {
            wrapper.like("client_name", query.getClientName());
        }
        return listVO(wrapper);
    }

    @Override
    public List<ClientVO> findByNameLike(String name) {
        QueryWrapper<Client> wrapper = new QueryWrapper<>();
        wrapper.like("client_name", name);
        return listVO(wrapper);
    }

    @Override
    public boolean checkClientNumberExists(String clientNumber) {
        QueryWrapper<Client> wrapper = new QueryWrapper<>();
        wrapper.eq("client_number", clientNumber);
        return count(wrapper) > 0;
    }

    @Override
    public boolean checkIdNumberExists(String idNumber) {
        QueryWrapper<Client> wrapper = new QueryWrapper<>();
        wrapper.eq("id_number", idNumber);
        return count(wrapper) > 0;
    }

    @Override
    public Map<String, Long> countByType() {
        QueryWrapper<Client> wrapper = new QueryWrapper<>();
        wrapper.select("client_type", "count(*) as count")
                .groupBy("client_type");
        return list(wrapper).stream()
                .collect(Collectors.groupingBy(
                        client -> client.getClientType().name(),
                        Collectors.counting()
                ));
    }

    @Override
    public Map<String, Long> countByStatus() {
        QueryWrapper<Client> wrapper = new QueryWrapper<>();
        wrapper.select("client_status", "count(*) as count")
                .groupBy("client_status");
        return list(wrapper).stream()
                .collect(Collectors.groupingBy(
                        client -> client.getClientStatus().name(),
                        Collectors.counting()
                ));
    }
}