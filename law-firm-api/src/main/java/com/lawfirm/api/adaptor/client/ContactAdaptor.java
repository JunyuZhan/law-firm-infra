package com.lawfirm.api.adaptor.client;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.client.service.ContactService;
import com.lawfirm.model.client.dto.contact.ContactCreateDTO;
import com.lawfirm.model.client.dto.contact.ContactQueryDTO;
import com.lawfirm.model.client.vo.ContactVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 客户联系人适配器
 */
@Component
@RequiredArgsConstructor
public class ContactAdaptor extends BaseAdaptor {

    private final ContactService contactService;

    /**
     * 获取客户的联系人列表
     */
    public List<ContactVO> getContactList(Long clientId) {
        return contactService.listClientContacts(clientId);
    }

    /**
     * 分页查询联系人
     */
    public List<ContactVO> getContactPage(ContactQueryDTO queryDTO) {
        return contactService.listContacts(queryDTO);
    }

    /**
     * 获取联系人详情
     */
    public ContactVO getContactDetail(Long id) {
        return contactService.getContact(id);
    }

    /**
     * 获取客户的默认联系人
     */
    public ContactVO getDefaultContact(Long clientId) {
        return contactService.getDefaultContact(clientId);
    }

    /**
     * 新增联系人
     */
    public Long addContact(ContactCreateDTO createDTO) {
        return contactService.createContact(createDTO);
    }

    /**
     * 修改联系人
     */
    public void updateContact(ContactCreateDTO updateDTO) {
        contactService.updateContact(updateDTO);
    }

    /**
     * 删除联系人
     */
    public void deleteContact(Long id) {
        contactService.deleteContact(id);
    }

    /**
     * 设置默认联系人
     */
    public void setDefaultContact(Long id) {
        contactService.setDefault(id, 1); // 1表示设为默认
    }

    /**
     * 批量删除联系人
     */
    public void batchDeleteContacts(Long[] ids) {
        for (Long id : ids) {
            contactService.deleteContact(id);
        }
    }
} 