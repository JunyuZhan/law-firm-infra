package com.lawfirm.model.client.service;

import com.lawfirm.model.client.dto.contact.ContactCreateDTO;
import com.lawfirm.model.client.dto.contact.ContactQueryDTO;
import com.lawfirm.model.client.vo.ContactVO;

import java.util.List;

/**
 * 联系人服务接口
 */
public interface ContactService {

    /**
     * 创建联系人
     *
     * @param createDTO 创建参数
     * @return 联系人ID
     */
    Long createContact(ContactCreateDTO createDTO);

    /**
     * 更新联系人
     *
     * @param updateDTO 更新参数
     */
    void updateContact(ContactCreateDTO updateDTO);

    /**
     * 删除联系人
     *
     * @param id 联系人ID
     */
    void deleteContact(Long id);

    /**
     * 获取联系人详情
     *
     * @param id 联系人ID
     * @return 联系人详情
     */
    ContactVO getContact(Long id);

    /**
     * 查询联系人列表
     *
     * @param queryDTO 查询参数
     * @return 联系人列表
     */
    List<ContactVO> listContacts(ContactQueryDTO queryDTO);

    /**
     * 设置默认联系人
     *
     * @param id 联系人ID
     * @param isDefault 是否默认
     */
    void setDefault(Long id, Integer isDefault);

    /**
     * 获取客户的默认联系人
     *
     * @param clientId 客户ID
     * @return 默认联系人
     */
    ContactVO getDefaultContact(Long clientId);

    /**
     * 获取客户的所有联系人
     *
     * @param clientId 客户ID
     * @return 联系人列表
     */
    List<ContactVO> listClientContacts(Long clientId);
} 