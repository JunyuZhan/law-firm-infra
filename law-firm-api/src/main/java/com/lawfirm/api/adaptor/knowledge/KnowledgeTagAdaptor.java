package com.lawfirm.api.adaptor.knowledge;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.knowledge.entity.KnowledgeTag;
import com.lawfirm.model.knowledge.service.KnowledgeTagService;
import com.lawfirm.model.knowledge.service.convert.KnowledgeConvert;
import com.lawfirm.model.knowledge.vo.KnowledgeTagVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 知识标签适配器
 * <p>
 * 该适配器负责处理与知识标签相关的所有操作，包括标签的创建、更新、删除、查询等功能。
 * 作为API层与服务层之间的桥梁，转换请求参数并调用KnowledgeTagService提供的服务。
 * </p>
 */
@Slf4j
@Component("knowledgeTagAdaptor")
public class KnowledgeTagAdaptor extends BaseAdaptor {

    private final KnowledgeTagService tagService;
    private final KnowledgeConvert knowledgeConvert;

    @Autowired
    public KnowledgeTagAdaptor(@Qualifier("knowledgeTagServiceImpl") KnowledgeTagService tagService,
                               KnowledgeConvert knowledgeConvert) {
        this.tagService = tagService;
        this.knowledgeConvert = knowledgeConvert;
    }

    /**
     * 获取所有标签
     *
     * @return 标签列表
     */
    public List<KnowledgeTagVO> getAllTags() {
        log.info("获取所有标签");
        List<KnowledgeTag> tags = tagService.listAll();
        return tags.stream()
                .map(knowledgeConvert::toTagVO)
                .collect(Collectors.toList());
    }

    /**
     * 根据名称查询标签
     *
     * @param name 标签名称
     * @return 标签列表
     */
    public List<KnowledgeTagVO> getTagsByName(String name) {
        log.info("根据名称查询标签: name={}", name);
        List<KnowledgeTag> tags = tagService.listByName(name);
        return tags.stream()
                .map(knowledgeConvert::toTagVO)
                .collect(Collectors.toList());
    }

    /**
     * 根据知识ID获取标签
     *
     * @param knowledgeId 知识ID
     * @return 标签列表
     */
    public List<KnowledgeTagVO> getTagsByKnowledgeId(Long knowledgeId) {
        log.info("根据知识ID获取标签: knowledgeId={}", knowledgeId);
        List<KnowledgeTag> tags = tagService.listByKnowledgeId(knowledgeId);
        return tags.stream()
                .map(knowledgeConvert::toTagVO)
                .collect(Collectors.toList());
    }

    /**
     * 获取热门标签
     *
     * @param limit 限制条数
     * @return 标签列表
     */
    public List<KnowledgeTagVO> getHotTags(Integer limit) {
        log.info("获取热门标签: limit={}", limit);
        List<KnowledgeTag> tags = tagService.listHotTags(limit);
        return tags.stream()
                .map(knowledgeConvert::toTagVO)
                .collect(Collectors.toList());
    }

    /**
     * 创建标签
     *
     * @param tag 标签
     * @return 是否成功
     */
    public boolean createTag(KnowledgeTag tag) {
        log.info("创建标签: name={}", tag.getName());
        return tagService.save(tag);
    }

    /**
     * 更新标签
     *
     * @param tag 标签
     * @return 是否成功
     */
    public boolean updateTag(KnowledgeTag tag) {
        log.info("更新标签: id={}, name={}", tag.getId(), tag.getName());
        return tagService.updateById(tag);
    }

    /**
     * 删除标签
     *
     * @param id 标签ID
     * @return 是否成功
     */
    public boolean deleteTag(Long id) {
        log.info("删除标签: id={}", id);
        return tagService.removeById(id);
    }

    /**
     * 根据编码获取标签
     *
     * @param code 标签编码
     * @return 标签
     */
    public KnowledgeTagVO getTagByCode(String code) {
        log.info("根据编码获取标签: code={}", code);
        KnowledgeTag tag = tagService.getByCode(code);
        return knowledgeConvert.toTagVO(tag);
    }
} 