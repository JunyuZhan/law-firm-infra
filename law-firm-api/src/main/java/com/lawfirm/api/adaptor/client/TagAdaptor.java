package com.lawfirm.api.adaptor.client;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.client.service.TagService;
import com.lawfirm.model.client.entity.common.ClientTag;
import com.lawfirm.model.client.vo.tag.ClientTagVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<ClientTagVO> getTagList() {
        List<ClientTag> tags = tagService.listAllTags();
        return tags.stream()
                .map(tag -> convert(tag, ClientTagVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据类型获取标签列表
     */
    public List<ClientTagVO> getTagListByType(String tagType) {
        List<ClientTag> tags = tagService.listByType(tagType);
        return tags.stream()
                .map(tag -> convert(tag, ClientTagVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 获取标签详情
     */
    public ClientTagVO getTagDetail(Long id) {
        ClientTag tag = tagService.getTag(id);
        return convert(tag, ClientTagVO.class);
    }

    /**
     * 获取客户的标签列表
     */
    public List<ClientTagVO> getClientTags(Long clientId) {
        List<ClientTag> tags = tagService.getClientTags(clientId);
        return tags.stream()
                .map(tag -> convert(tag, ClientTagVO.class))
                .collect(Collectors.toList());
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