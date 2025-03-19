package com.lawfirm.api.adaptor.client;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.client.service.TagService;
import com.lawfirm.model.client.entity.common.ClientTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 客户标签适配器
 */
@Component
@RequiredArgsConstructor
public class TagAdaptor extends BaseAdaptor {

    private final TagService tagService;

    /**
     * 获取所有标签列表
     */
    public List<ClientTag> getTagList() {
        return tagService.listAllTags();
    }

    /**
     * 根据类型获取标签列表
     */
    public List<ClientTag> getTagListByType(String tagType) {
        return tagService.listByType(tagType);
    }

    /**
     * 获取标签详情
     */
    public ClientTag getTagDetail(Long id) {
        return tagService.getTag(id);
    }

    /**
     * 获取客户的标签列表
     */
    public List<ClientTag> getClientTags(Long clientId) {
        return tagService.getClientTags(clientId);
    }

    /**
     * 新增标签
     */
    public Long addTag(ClientTag tag) {
        return tagService.createTag(tag);
    }

    /**
     * 修改标签
     */
    public void updateTag(ClientTag tag) {
        tagService.updateTag(tag);
    }

    /**
     * 删除标签
     */
    public boolean deleteTag(Long id) {
        return tagService.deleteTag(id);
    }

    /**
     * 为客户添加标签
     */
    public void addTagToClient(Long clientId, Long tagId) {
        tagService.addTagToClient(clientId, tagId);
    }

    /**
     * 移除客户的标签
     */
    public void removeTagFromClient(Long clientId, Long tagId) {
        tagService.removeTagFromClient(clientId, tagId);
    }

    /**
     * 设置客户标签
     */
    public void setClientTags(Long clientId, List<Long> tagIds) {
        tagService.setClientTags(clientId, tagIds);
    }
} 