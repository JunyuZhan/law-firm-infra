package com.lawfirm.knowledge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.knowledge.service.AuditIntegrationService;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.knowledge.entity.KnowledgeTag;
import com.lawfirm.model.knowledge.mapper.KnowledgeTagMapper;
import com.lawfirm.model.knowledge.service.KnowledgeTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.lawfirm.knowledge.exception.KnowledgeException;

import java.util.Collections;
import java.util.List;

/**
 * 知识标签服务实现类
 * 
 * 注：当前实现未直接注入core层服务。
 * 如需使用审计服务、消息服务等core能力，参考以下方式注入：
 * 
 * @Autowired
 * @Qualifier("knowledgeAuditService")
 * private AuditIntegrationService auditService;
 * 
 * @Autowired
 * @Qualifier("knowledgeMessageService")
 * private MessageIntegrationService messageService;
 */
@Slf4j
@Service("knowledgeTagServiceImpl")
public class KnowledgeTagServiceImpl extends BaseServiceImpl<KnowledgeTagMapper, KnowledgeTag> implements KnowledgeTagService {

    @Autowired
    @Qualifier("knowledgeAuditService")
    private AuditIntegrationService auditService;

    @Override
    public KnowledgeTag getByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return null;
        }
        return baseMapper.selectByCode(code);
    }

    @Override
    public List<KnowledgeTag> listByName(String name) {
        if (!StringUtils.hasText(name)) {
            return Collections.emptyList();
        }
        return baseMapper.selectByName(name);
    }

    @Override
    public List<KnowledgeTag> listAll() {
        LambdaQueryWrapper<KnowledgeTag> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByAsc(KnowledgeTag::getSort);
        return list(wrapper);
    }

    @Override
    public List<KnowledgeTag> listByKnowledgeId(Long knowledgeId) {
        if (knowledgeId == null) {
            return Collections.emptyList();
        }
        // 通过自定义SQL查询与知识文档关联的标签
        log.info("查询知识文档关联的标签: knowledgeId={}", knowledgeId);
        return Collections.emptyList(); // 暂时返回空列表，需要实现该方法
    }

    @Override
    public List<KnowledgeTag> listHotTags(Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 10;
        }
        // 查询热门标签，可以基于标签使用频率
        log.info("查询热门标签，数量: {}", limit);
        
        LambdaQueryWrapper<KnowledgeTag> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(KnowledgeTag::getUpdateTime);
        wrapper.last("LIMIT " + limit);
        return list(wrapper);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(KnowledgeTag entity) {
        boolean success = super.save(entity);
        
        if (success) {
            // 记录审计日志
            auditService.recordKnowledgeOperation(
                String.format("创建知识标签：[%s]，CODE：[%s]", entity.getName(), entity.getCode()),
                "CREATE_TAG",
                null // 标签与知识无直接关联，所以knowledgeId为null
            );
        }
        
        return success;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(KnowledgeTag entity) {
        // 获取原始标签信息
        KnowledgeTag original = getById(entity.getId());
        if (original == null) {
            log.error("更新标签失败，标签ID不存在: {}", entity.getId());
            return false;
        }
        
        boolean success = super.updateById(entity);
        
        if (success) {
            // 记录审计日志
            auditService.recordKnowledgeOperation(
                String.format("更新知识标签，从 [%s] 更新为 [%s]", original.getName(), entity.getName()),
                "UPDATE_TAG",
                null
            );
        }
        
        return success;
    }
    
    /**
     * 删除标签并记录审计日志
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteTagWithAudit(Long id) {
        // 获取标签信息
        KnowledgeTag tag = getById(id);
        if (tag == null) {
            log.error("删除标签失败，标签ID不存在: {}", id);
            return false;
        }
        
        boolean success = super.removeById(id);
        
        if (success) {
            // 记录审计日志
            auditService.recordKnowledgeOperation(
                String.format("删除知识标签：[%s]，ID：[%s]", tag.getName(), id),
                "DELETE_TAG",
                null
            );
        }
        
        return success;
    }
} 