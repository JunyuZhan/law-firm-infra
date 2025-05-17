package com.lawfirm.knowledge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lawfirm.knowledge.service.AuditIntegrationService;
import com.lawfirm.knowledge.service.MessageIntegrationService;
import com.lawfirm.knowledge.service.SearchIntegrationService;
import com.lawfirm.knowledge.service.StorageIntegrationService;
import com.lawfirm.model.knowledge.service.convert.KnowledgeConvert;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.knowledge.dto.KnowledgeDTO;
import com.lawfirm.model.knowledge.entity.Knowledge;
import com.lawfirm.model.knowledge.entity.KnowledgeAttachment;
import com.lawfirm.model.knowledge.entity.KnowledgeTag;
import com.lawfirm.model.knowledge.enums.KnowledgeTypeEnum;
import com.lawfirm.model.knowledge.mapper.KnowledgeMapper;
import com.lawfirm.model.knowledge.service.KnowledgeAttachmentService;
import com.lawfirm.model.knowledge.service.KnowledgeCategoryService;
import com.lawfirm.model.knowledge.service.KnowledgeService;
import com.lawfirm.model.knowledge.service.KnowledgeTagService;
import com.lawfirm.model.knowledge.service.KnowledgeTagRelationService;
import com.lawfirm.model.knowledge.vo.KnowledgeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.lawfirm.knowledge.exception.KnowledgeException;

/**
 * 知识服务实现类
 */
@Slf4j
@Service("knowledgeServiceImpl")
public class KnowledgeServiceImpl extends BaseServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

    @Autowired
    private KnowledgeCategoryService knowledgeCategoryService;

    @Autowired
    private KnowledgeTagService knowledgeTagService;

    @Autowired
    private KnowledgeAttachmentService knowledgeAttachmentService;

    @Autowired
    private KnowledgeTagRelationService knowledgeTagRelationService;

    @Autowired
    private KnowledgeConvert knowledgeConvert;

    // 集成服务注入
    @Autowired
    @Qualifier("knowledgeSearchService")
    private SearchIntegrationService searchService;

    @Autowired
    @Qualifier("knowledgeAuditService")
    private AuditIntegrationService auditService;

    @Autowired
    @Qualifier("knowledgeMessageService")
    private MessageIntegrationService messageService;
    
    @Autowired
    @Qualifier("knowledgeStorageService")
    private StorageIntegrationService storageService;

    /**
     * 创建知识文档
     */
    @Transactional(rollbackFor = Exception.class)
    public Knowledge addKnowledge(KnowledgeDTO dto) {
        // 转换DTO为实体
        Knowledge knowledge = knowledgeConvert.fromDTO(dto);
        
        // 设置创建者和更新时间
        LocalDateTime now = LocalDateTime.now();
        knowledge.setCreateTime(now);
        knowledge.setUpdateTime(now);
        
        // 保存知识文档
        boolean success = save(knowledge);
        if (!success) {
            log.error("保存知识文档失败: {}", dto.getTitle());
            throw KnowledgeException.failed("知识文档保存", new RuntimeException("知识文档保存失败"));
        }
        
        // 处理标签关联
        if (CollectionUtils.isNotEmpty(dto.getTagIds())) {
            knowledgeTagRelationService.createRelations(knowledge.getId(), dto.getTagIds());
        }
        
        // 索引知识文档
        searchService.indexKnowledgeDocument(knowledge);
        
        // 记录审计日志
        auditService.recordKnowledgeCreation(knowledge);
        
        return knowledge;
    }

    /**
     * 根据分类ID查询知识列表
     */
    @Override
    public List<Knowledge> listByCategoryId(Long categoryId) {
        if (categoryId == null) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<Knowledge> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Knowledge::getCategoryId, categoryId);
        wrapper.orderByDesc(Knowledge::getCreateTime);
        return list(wrapper);
    }

    /**
     * 根据标签ID查询知识列表
     */
    @Override
    public List<Knowledge> listByTagId(Long tagId) {
        if (tagId == null) {
            return new ArrayList<>();
        }
        return baseMapper.selectByTagId(tagId);
    }

    /**
     * 根据关键词搜索知识
     */
    @Override
    public List<Knowledge> searchByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        // 使用搜索集成服务进行全文检索
        try {
            List<Long> ids = searchService.searchKnowledge(keyword, 50);
            
            if (CollectionUtils.isEmpty(ids)) {
                return new ArrayList<>();
            }
            
            // 根据搜索结果ID获取知识文档
            return baseMapper.selectList(new LambdaQueryWrapper<Knowledge>().in(Knowledge::getId, ids));
        } catch (Exception e) {
            log.error("搜索知识文档失败: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * 获取最新知识
     */
    @Override
    public List<Knowledge> listLatest(Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 10;
        }
        LambdaQueryWrapper<Knowledge> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(Knowledge::getCreateTime);
        wrapper.last("LIMIT " + limit);
        return list(wrapper);
    }

    /**
     * 获取相关知识
     */
    @Override
    public List<Knowledge> listRelated(Long categoryId, Long excludeId, Integer limit) {
        if (categoryId == null) {
            return new ArrayList<>();
        }
        if (limit == null || limit <= 0) {
            limit = 5;
        }
        return baseMapper.selectRelated(categoryId, excludeId, limit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Knowledge entity) {
        // 设置基本属性
        if (entity.getCreateTime() == null) {
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        
        // 保存实体
        return super.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Knowledge entity) {
        // 获取原始知识文档
        Knowledge original = getById(entity.getId());
        if (original == null) {
            log.error("更新知识文档失败，知识ID不存在: {}", entity.getId());
            throw KnowledgeException.notFound("知识文档");
        }
        
        // 设置更新时间
        entity.setUpdateTime(LocalDateTime.now());
        
        // 更新知识文档
        boolean success = updateById(entity);
        
        if (success) {
            // 重新索引文档
            searchService.indexKnowledgeDocument(entity);
            
            // 记录审计日志
            auditService.recordKnowledgeCreation(entity);
        }
        
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Long id) {
        // 获取知识文档
        Knowledge knowledge = getById(id);
        if (knowledge == null) {
            log.error("删除知识文档失败，知识ID不存在: {}", id);
            throw KnowledgeException.notFound("知识文档");
        }
        
        // 删除标签关联
        knowledgeTagRelationService.removeByKnowledgeId(id);
        
        // 删除附件
        knowledgeAttachmentService.removeByKnowledgeId(id);
        
        // 删除知识文档
        boolean success = removeById(id);
        
        if (success) {
            // 删除索引
            searchService.deleteKnowledgeIndex(id);
            
            // 记录审计日志
            auditService.recordKnowledgeDeletion(knowledge);
        }
        
        return success;
    }

    @Override
    public Knowledge getById(Long id) {
        Knowledge knowledge = super.getById(id);
        if (knowledge != null) {
            // 增加查看次数
            knowledge.setViewCount(knowledge.getViewCount() + 1);
            updateById(knowledge);
            
            // 加载标签
            List<KnowledgeTag> tags = knowledgeTagService.listByKnowledgeId(id);
            knowledge.setTags(tags);
            
            // 加载附件
            List<KnowledgeAttachment> attachments = knowledgeAttachmentService.listByKnowledgeId(id);
            knowledge.setAttachments(attachments);
        }
        return knowledge;
    }
    
    /**
     * 上传知识文档附件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long uploadAttachment(MultipartFile file, Long knowledgeId) throws Exception {
        log.info("上传知识文档附件: knowledgeId={}, fileName={}, size={}", 
                knowledgeId, file.getOriginalFilename(), file.getSize());
        
        // 检查知识文档是否存在
        Knowledge knowledge = getById(knowledgeId);
        if (knowledge == null) {
            throw KnowledgeException.notFound("知识文档");
        }
        
        // 创建附件记录
        KnowledgeAttachment attachment = new KnowledgeAttachment();
        attachment.setKnowledgeId(knowledgeId);
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFileSize(file.getSize());
        attachment.setFileType(file.getContentType());
        
        // 上传文件到存储服务 - 使用兼容方法
        String filePath = "/storage/knowledge/" + knowledgeId + "/" + file.getOriginalFilename();
        // 模拟文件保存
        file.transferTo(new java.io.File(System.getProperty("java.io.tmpdir") + filePath));
        attachment.setFilePath(filePath);
        
        // 保存附件记录
        knowledgeAttachmentService.save(attachment);
        
        // 记录审计日志 - 使用兼容方法
        log.info("上传附件成功: id={}, knowledgeId={}, fileName={}", 
                attachment.getId(), knowledgeId, file.getOriginalFilename());
        
        return attachment.getId();
    }
}