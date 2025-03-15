package com.lawfirm.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.client.constant.ClientConstant;
import com.lawfirm.model.client.dto.contact.ContactCreateDTO;
import com.lawfirm.model.client.dto.contact.ContactQueryDTO;
import com.lawfirm.model.client.entity.common.ClientContact;
import com.lawfirm.model.client.mapper.ContactMapper;
import com.lawfirm.model.client.service.ContactService;
import com.lawfirm.model.client.vo.ContactVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 联系人服务实现类
 */
@Service
@RequiredArgsConstructor
public class ContactServiceImpl extends ServiceImpl<ContactMapper, ClientContact> implements ContactService {
    
    private final ContactMapper contactMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createContact(ContactCreateDTO dto) {
        // 创建联系人实体
        ClientContact contact = new ClientContact();
        contact.setClientId(dto.getClientId());
        contact.setContactName(dto.getContactName());
        contact.setMobile(dto.getMobile());
        contact.setEmail(dto.getEmail());
        contact.setPosition(dto.getPosition());
        contact.setDepartment(dto.getDepartment());
        contact.setRemark(dto.getRemark());
        
        // 设置默认联系人
        if (dto.getIsDefault() != null && dto.getIsDefault() == ClientConstant.Contact.DEFAULT) {
            // 如果设置为默认联系人，先将该客户的所有联系人设为非默认
            resetDefaultContact(dto.getClientId());
            contact.setIsDefault(ClientConstant.Contact.DEFAULT);
        } else {
            contact.setIsDefault(ClientConstant.Contact.NOT_DEFAULT);
        }
        
        // 保存联系人
        save(contact);
        
        return contact.getId();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateContact(ContactCreateDTO dto) {
        // 获取联系人
        ClientContact contact = getById(dto.getId());
        if (contact == null) {
            throw new IllegalArgumentException("联系人不存在");
        }
        
        // 更新联系人信息
        contact.setContactName(dto.getContactName());
        contact.setMobile(dto.getMobile());
        contact.setEmail(dto.getEmail());
        contact.setPosition(dto.getPosition());
        contact.setDepartment(dto.getDepartment());
        contact.setRemark(dto.getRemark());
        
        // 处理默认联系人设置
        if (dto.getIsDefault() != null && dto.getIsDefault() == ClientConstant.Contact.DEFAULT) {
            // 如果设置为默认联系人，先将该客户的所有联系人设为非默认
            resetDefaultContact(contact.getClientId());
            contact.setIsDefault(ClientConstant.Contact.DEFAULT);
        }
        
        // 更新联系人
        updateById(contact);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteContact(Long id) {
        removeById(id);
    }
    
    @Override
    public ContactVO getContact(Long id) {
        ClientContact contact = getById(id);
        if (contact == null) {
            return null;
        }
        return convertToVO(contact);
    }
    
    @Override
    public List<ContactVO> listContacts(ContactQueryDTO queryDTO) {
        // 构建查询条件
        LambdaQueryWrapper<ClientContact> wrapper = new LambdaQueryWrapper<>();
        if (queryDTO.getClientId() != null) {
            wrapper.eq(ClientContact::getClientId, queryDTO.getClientId());
        }
        if (queryDTO.getContactType() != null) {
            wrapper.eq(ClientContact::getContactType, queryDTO.getContactType());
        }
        if (queryDTO.getKeyword() != null && !queryDTO.getKeyword().isEmpty()) {
            wrapper.like(ClientContact::getContactName, queryDTO.getKeyword())
                   .or()
                   .like(ClientContact::getMobile, queryDTO.getKeyword())
                   .or()
                   .like(ClientContact::getEmail, queryDTO.getKeyword());
        }
        
        // 获取联系人列表并转换为VO
        List<ClientContact> contacts = list(wrapper);
        return contacts.stream().map(this::convertToVO).collect(Collectors.toList());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDefault(Long id, Integer isDefault) {
        ClientContact contact = getById(id);
        if (contact == null) {
            throw new IllegalArgumentException("联系人不存在");
        }
        
        if (isDefault == ClientConstant.Contact.DEFAULT) {
            // 设置为默认联系人
            resetDefaultContact(contact.getClientId());
            contact.setIsDefault(ClientConstant.Contact.DEFAULT);
        } else {
            // 设置为非默认联系人
            contact.setIsDefault(ClientConstant.Contact.NOT_DEFAULT);
        }
        
        updateById(contact);
    }
    
    @Override
    public ContactVO getDefaultContact(Long clientId) {
        // 查询默认联系人
        LambdaQueryWrapper<ClientContact> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClientContact::getClientId, clientId)
               .eq(ClientContact::getIsDefault, ClientConstant.Contact.DEFAULT);
        
        ClientContact contact = getOne(wrapper);
        return contact != null ? convertToVO(contact) : null;
    }
    
    @Override
    public List<ContactVO> listClientContacts(Long clientId) {
        // 查询客户的所有联系人
        LambdaQueryWrapper<ClientContact> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClientContact::getClientId, clientId)
               .orderByDesc(ClientContact::getIsDefault)
               .orderByDesc(ClientContact::getUpdateTime);
        
        List<ClientContact> contacts = list(wrapper);
        return contacts.stream().map(this::convertToVO).collect(Collectors.toList());
    }
    
    /**
     * 将联系人实体转换为VO
     */
    private ContactVO convertToVO(ClientContact contact) {
        ContactVO vo = new ContactVO();
        vo.setId(contact.getId());
        vo.setClientId(contact.getClientId());
        vo.setContactName(contact.getContactName());
        vo.setContactType(contact.getContactType());
        vo.setDepartment(contact.getDepartment());
        vo.setPosition(contact.getPosition());
        vo.setMobile(contact.getMobile());
        vo.setTelephone(contact.getTelephone());
        vo.setEmail(contact.getEmail());
        vo.setImportance(contact.getImportance());
        vo.setIsDefault(contact.getIsDefault());
        vo.setStatus(contact.getStatus());
        vo.setCreateTime(contact.getCreateTime());
        vo.setUpdateTime(contact.getUpdateTime());
        return vo;
    }
    
    /**
     * 重置客户的默认联系人（将所有联系人设置为非默认）
     */
    private void resetDefaultContact(Long clientId) {
        // 构建更新条件
        LambdaQueryWrapper<ClientContact> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClientContact::getClientId, clientId)
               .eq(ClientContact::getIsDefault, ClientConstant.Contact.DEFAULT);
        
        // 查询默认联系人
        List<ClientContact> defaultContacts = list(wrapper);
        
        // 将默认联系人设置为非默认
        for (ClientContact contact : defaultContacts) {
            contact.setIsDefault(ClientConstant.Contact.NOT_DEFAULT);
            updateById(contact);
        }
    }
}
