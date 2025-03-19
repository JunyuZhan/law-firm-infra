package com.lawfirm.api.adaptor.document;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.document.dto.tag.TagCreateDTO;
import com.lawfirm.model.document.dto.tag.TagUpdateDTO;
import com.lawfirm.model.document.entity.base.DocumentTag;
import com.lawfirm.model.document.service.DocumentTagService;
import com.lawfirm.model.document.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档标签管理适配器
 */
@Component
public class DocumentTagAdaptor extends BaseAdaptor {

    @Autowired
    private DocumentTagService documentTagService;

    /**
     * 创建文档标签
     */
    public TagVO createTag(TagCreateDTO dto) {
        DocumentTag tag = documentTagService.createTag(dto);
        return convert(tag, TagVO.class);
    }

    /**
     * 更新文档标签
     */
    public TagVO updateTag(Long id, TagUpdateDTO dto) {
        DocumentTag tag = documentTagService.updateTag(id, dto);
        return convert(tag, TagVO.class);
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
        DocumentTag tag = documentTagService.getTag(id);
        return convert(tag, TagVO.class);
    }

    /**
     * 获取所有文档标签
     */
    public List<TagVO> listTags() {
        List<DocumentTag> tags = documentTagService.listTags();
        return tags.stream()
                .map(tag -> convert(tag, TagVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据文档ID查询标签
     */
    public List<TagVO> getTagsByDocumentId(Long documentId) {
        List<DocumentTag> tags = documentTagService.getTagsByDocumentId(documentId);
        return tags.stream()
                .map(tag -> convert(tag, TagVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 为文档添加标签
     */
    public void addTagToDocument(Long documentId, Long tagId) {
        documentTagService.addTagToDocument(documentId, tagId);
    }

    /**
     * 从文档移除标签
     */
    public void removeTagFromDocument(Long documentId, Long tagId) {
        documentTagService.removeTagFromDocument(documentId, tagId);
    }

    /**
     * 检查标签是否存在
     */
    public boolean existsTag(Long id) {
        return documentTagService.existsTag(id);
    }

    /**
     * 获取标签数量
     */
    public long countTags() {
        return documentTagService.countTags();
    }
} 