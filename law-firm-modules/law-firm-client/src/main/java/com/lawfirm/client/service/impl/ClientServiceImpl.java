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
import com.lawfirm.client.exception.ClientException;
import com.lawfirm.model.client.dto.contact.ContactCreateDTO;
import com.lawfirm.model.client.service.ContactService;
import com.lawfirm.model.client.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.workflow.service.ProcessService;

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
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private TagService tagService;
    
    /**
     * 注入core层审计服务，便于后续记录客户操作日志
     */
    @Autowired(required = false)
    @Qualifier("clientAuditService")
    private AuditService auditService;

    /**
     * 注入core层消息发送服务，便于后续客户通知等
     */
    @Autowired(required = false)
    @Qualifier("clientMessageSender")
    private MessageSender messageSender;

    /**
     * 注入core层文件存储服务，便于后续客户附件上传等
     */
    @Autowired(required = false)
    @Qualifier("clientFileService")
    private FileService fileService;

    /**
     * 注入core层存储桶服务
     */
    @Autowired(required = false)
    @Qualifier("clientBucketService")
    private BucketService bucketService;

    /**
     * 注入core层搜索服务
     */
    @Autowired(required = false)
    @Qualifier("clientSearchService")
    private SearchService searchService;

    /**
     * 注入core层流程服务
     */
    @Autowired(required = false)
    @Qualifier("clientProcessService")
    private ProcessService processService;
    
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
            throw ClientException.notFound(dto.getId());
        }
        
        // 更新客户信息
        ClientConverter.updateEntity(client, dto);
        
        // 保存更新
        updateById(client);
        
        // 处理关联数据更新
        // 可扩展：如dto包含联系人/标签等信息，可调用contactService、tagService更新
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
        
        // 删除关联数据（联系人、标签等）
        contactService.listClientContacts(id).forEach(c -> contactService.deleteContact(c.getId()));
        // tagService可根据实际需求实现客户标签的删除
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
            throw ClientException.notFound(id);
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
            throw ClientException.notFound(id);
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
        // 示例：如dto包含联系人信息，可批量创建联系人
        // List<ContactCreateDTO> contacts = dto.getContacts();
        // if (contacts != null) {
        //     contacts.forEach(contact -> {
        //         contact.setClientId(clientId);
        //         contactService.createContact(contact);
        //     });
        // }
        // 标签等其他关联数据同理
        // 当前留作扩展点，便于后续业务完善
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateClientCaseStats(Long clientId, int totalCases, int activeCases) {
        // 检查客户是否存在
        Client client = getById(clientId);
        if (client == null) {
            throw ClientException.notFound(clientId);
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
        // 只返回基本信息
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
        // 检查客户是否存在
        Client client = getById(clientId);
        if (client == null) {
            throw ClientException.notFound(clientId);
        }
        
        // 返回默认值
        return false;
    }
}
