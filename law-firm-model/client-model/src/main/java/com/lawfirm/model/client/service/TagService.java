package com.lawfirm.model.client.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.client.entity.common.ClientTag;

import java.util.List;

/**
 * 客户标签服务接口
 */
public interface TagService extends IService<ClientTag> {
    
    /**
     * 创建标签
     * 
     * @param tag 标签信息
     * @return 标签ID
     */
    Long createTag(ClientTag tag);
    
    /**
     * 更新标签
     * 
     * @param tag 标签信息
     */
    void updateTag(ClientTag tag);
    
    /**
     * 删除标签
     * 
     * @param id 标签ID
     * @return 是否成功
     */
    boolean deleteTag(Long id);
    
    /**
     * 获取标签详情
     * 
     * @param id 标签ID
     * @return 标签信息
     */
    ClientTag getTag(Long id);
    
    /**
     * 获取所有标签
     * 
     * @return 标签列表
     */
    List<ClientTag> listAllTags();
    
    /**
     * 根据类型获取标签
     * 
     * @param tagType 标签类型
     * @return 标签列表
     */
    List<ClientTag> listByType(String tagType);
    
    /**
     * 获取客户的标签
     * 
     * @param clientId 客户ID
     * @return 标签列表
     */
    List<ClientTag> getClientTags(Long clientId);
    
    /**
     * 为客户添加标签
     * 
     * @param clientId 客户ID
     * @param tagId 标签ID
     */
    void addTagToClient(Long clientId, Long tagId);
    
    /**
     * 移除客户标签
     * 
     * @param clientId 客户ID
     * @param tagId 标签ID
     */
    void removeTagFromClient(Long clientId, Long tagId);
    
    /**
     * 设置客户标签
     * 
     * @param clientId 客户ID
     * @param tagIds 标签ID列表
     */
    void setClientTags(Long clientId, List<Long> tagIds);
} 