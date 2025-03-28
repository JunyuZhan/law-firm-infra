package com.lawfirm.api.adaptor.knowledge;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.knowledge.dto.KnowledgeDTO;
import com.lawfirm.model.knowledge.entity.Knowledge;
import com.lawfirm.model.knowledge.service.KnowledgeService;
import com.lawfirm.model.knowledge.service.convert.KnowledgeConvert;
import com.lawfirm.model.knowledge.vo.KnowledgeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 知识管理适配器
 * <p>
 * 该适配器负责处理与知识管理相关的所有操作，包括知识文档的创建、更新、删除、查询等功能。
 * 作为API层与服务层之间的桥梁，转换请求参数并调用KnowledgeService提供的服务。
 * </p>
 */
@Slf4j
@Component("knowledgeAdaptor")
public class KnowledgeAdaptor extends BaseAdaptor {

    private final KnowledgeService knowledgeService;
    private final KnowledgeConvert knowledgeConvert;

    @Autowired
    public KnowledgeAdaptor(@Qualifier("knowledgeServiceImpl") KnowledgeService knowledgeService,
                            KnowledgeConvert knowledgeConvert) {
        this.knowledgeService = knowledgeService;
        this.knowledgeConvert = knowledgeConvert;
    }

    /**
     * 创建知识文档
     *
     * @param knowledgeDTO 知识文档DTO
     * @return 创建的知识文档VO
     */
    public KnowledgeVO createKnowledge(KnowledgeDTO knowledgeDTO) {
        log.info("创建知识文档: {}", knowledgeDTO.getTitle());
        Knowledge knowledge = knowledgeService.addKnowledge(knowledgeDTO);
        return knowledgeConvert.toVO(knowledge);
    }

    /**
     * 更新知识文档
     *
     * @param id 知识文档ID
     * @param knowledgeDTO 知识文档DTO
     * @return 更新后的知识文档VO
     */
    public KnowledgeVO updateKnowledge(Long id, KnowledgeDTO knowledgeDTO) {
        log.info("更新知识文档: id={}, title={}", id, knowledgeDTO.getTitle());
        knowledgeDTO.setId(id);
        Knowledge knowledge = knowledgeConvert.fromDTO(knowledgeDTO);
        knowledgeService.update(knowledge);
        return knowledgeConvert.toVO(knowledge);
    }

    /**
     * 获取知识文档详情
     *
     * @param id 知识文档ID
     * @return 知识文档VO
     */
    public KnowledgeVO getKnowledge(Long id) {
        log.info("获取知识文档详情: id={}", id);
        Knowledge knowledge = knowledgeService.getById(id);
        return knowledgeConvert.toVO(knowledge);
    }

    /**
     * 删除知识文档
     *
     * @param id 知识文档ID
     * @return 删除操作是否成功
     */
    public boolean deleteKnowledge(Long id) {
        log.info("删除知识文档: id={}", id);
        return knowledgeService.remove(id);
    }

    /**
     * 分页查询知识文档
     *
     * @param page 分页参数
     * @param categoryId 分类ID
     * @return 知识文档分页结果
     */
    public Page<KnowledgeVO> pageKnowledgeByCategoryId(Page<Knowledge> page, Long categoryId) {
        log.info("分页查询知识文档: categoryId={}, page={}", categoryId, page);
        List<Knowledge> knowledgeList = knowledgeService.listByCategoryId(categoryId);
        
        // 手动分页处理
        long total = knowledgeList.size();
        int fromIndex = (int)((page.getCurrent() - 1) * page.getSize());
        int toIndex = Math.min(fromIndex + (int)page.getSize(), knowledgeList.size());
        
        List<Knowledge> pageList = knowledgeList.subList(fromIndex, toIndex);
        List<KnowledgeVO> voList = pageList.stream()
                .map(knowledgeConvert::toVO)
                .collect(Collectors.toList());
        
        Page<KnowledgeVO> resultPage = new Page<>(page.getCurrent(), page.getSize(), total);
        resultPage.setRecords(voList);
        return resultPage;
    }

    /**
     * 根据标签ID查询知识文档
     *
     * @param tagId 标签ID
     * @return 知识文档VO列表
     */
    public List<KnowledgeVO> listKnowledgeByTagId(Long tagId) {
        log.info("根据标签查询知识文档: tagId={}", tagId);
        List<Knowledge> knowledgeList = knowledgeService.listByTagId(tagId);
        return knowledgeList.stream()
                .map(knowledgeConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 搜索知识文档
     *
     * @param keyword 关键词
     * @return 知识文档VO列表
     */
    public List<KnowledgeVO> searchKnowledge(String keyword) {
        log.info("搜索知识文档: keyword={}", keyword);
        List<Knowledge> knowledgeList = knowledgeService.searchByKeyword(keyword);
        return knowledgeList.stream()
                .map(knowledgeConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 获取最新知识文档
     *
     * @param limit 限制条数
     * @return 知识文档VO列表
     */
    public List<KnowledgeVO> listLatestKnowledge(Integer limit) {
        log.info("获取最新知识文档: limit={}", limit);
        List<Knowledge> knowledgeList = knowledgeService.listLatest(limit);
        return knowledgeList.stream()
                .map(knowledgeConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 获取相关知识文档
     *
     * @param knowledgeId 当前知识文档ID
     * @param categoryId 分类ID
     * @param limit 限制条数
     * @return 知识文档VO列表
     */
    public List<KnowledgeVO> listRelatedKnowledge(Long knowledgeId, Long categoryId, Integer limit) {
        log.info("获取相关知识文档: knowledgeId={}, categoryId={}, limit={}", knowledgeId, categoryId, limit);
        List<Knowledge> knowledgeList = knowledgeService.listRelated(categoryId, knowledgeId, limit);
        return knowledgeList.stream()
                .map(knowledgeConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 上传知识文档附件
     *
     * @param file 文件
     * @param knowledgeId 知识文档ID
     * @return 附件ID
     */
    public Long uploadKnowledgeAttachment(MultipartFile file, Long knowledgeId) {
        log.info("上传知识文档附件: knowledgeId={}, fileName={}", knowledgeId, file.getOriginalFilename());
        try {
            return knowledgeService.uploadAttachment(file, knowledgeId);
        } catch (Exception e) {
            log.error("上传知识文档附件失败: {}", e.getMessage(), e);
            throw new RuntimeException("上传附件失败: " + e.getMessage());
        }
    }
} 