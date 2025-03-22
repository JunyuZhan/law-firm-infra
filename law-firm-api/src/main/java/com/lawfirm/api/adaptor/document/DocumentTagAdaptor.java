package com.lawfirm.api.adaptor.document;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.document.dto.tag.TagCreateDTO;
import com.lawfirm.model.document.dto.tag.TagQueryDTO;
import com.lawfirm.model.document.dto.tag.TagUpdateDTO;
import com.lawfirm.model.document.entity.base.DocumentTag;
import com.lawfirm.model.document.service.DocumentTagService;
import com.lawfirm.model.document.vo.TagVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档标签管理适配器
 */
@Slf4j
@Component
public class DocumentTagAdaptor extends BaseAdaptor {

    @Autowired
    private DocumentTagService documentTagService;

    /**
     * 创建文档标签
     */
    public TagVO createTag(TagCreateDTO dto) {
        Long tagId = documentTagService.createTag(dto);
        return documentTagService.getTag(tagId);
    }

    /**
     * 更新文档标签
     */
    public TagVO updateTag(Long id, TagUpdateDTO dto) {
        dto.setId(id);
        documentTagService.updateTag(dto);
        return documentTagService.getTag(id);
    }

    /**
     * 删除文档标签
     */
    public void deleteTag(Long id) {
        documentTagService.deleteTag(id);
    }

    /**
     * 获取文档标签详情
     */
    public TagVO getTag(Long id) {
        return documentTagService.getTag(id);
    }

    /**
     * 获取所有文档标签
     */
    public List<TagVO> listTags() {
        TagQueryDTO queryDTO = new TagQueryDTO();
        return documentTagService.listTags(queryDTO);
    }

    /**
     * 分页查询标签
     */
    public Page<TagVO> pageTags(Integer page, Integer size, String name) {
        TagQueryDTO queryDTO = new TagQueryDTO();
        queryDTO.setPageNum(page);
        queryDTO.setPageSize(size);
        queryDTO.setTagName(name);
        return documentTagService.pageTags(queryDTO);
    }

    /**
     * 根据文档ID查询标签
     * 
     * 注意：这是一个客户端适配方法，通过查询条件过滤实现
     */
    public List<TagVO> getTagsByDocumentId(Long documentId) {
        if (documentId == null) {
            return Collections.emptyList();
        }
        
        TagQueryDTO queryDTO = new TagQueryDTO();
        queryDTO.setKeyword(documentId.toString());
        return documentTagService.listTags(queryDTO);
    }

    /**
     * 为文档添加标签
     * 
     * 注意：这是一个客户端适配方法，实际实现需要根据具体的业务逻辑开发
     */
    public void addTagToDocument(Long documentId, Long tagId) {
        log.warn("方法 addTagToDocument 暂未实现，请在DocumentTagService中增加此方法");
        // 需要在DocumentTagService中增加此方法
        // documentTagService.addTagToDocument(documentId, tagId);
    }

    /**
     * 从文档移除标签
     * 
     * 注意：这是一个客户端适配方法，实际实现需要根据具体的业务逻辑开发
     */
    public void removeTagFromDocument(Long documentId, Long tagId) {
        log.warn("方法 removeTagFromDocument 暂未实现，请在DocumentTagService中增加此方法");
        // 需要在DocumentTagService中增加此方法
        // documentTagService.removeTagFromDocument(documentId, tagId);
    }

    /**
     * 检查标签是否存在
     */
    public boolean existsTag(Long id) {
        return documentTagService.getTag(id) != null;
    }

    /**
     * 获取标签数量
     */
    public long countTags() {
        TagQueryDTO queryDTO = new TagQueryDTO();
        return documentTagService.listTags(queryDTO).size();
    }
} 