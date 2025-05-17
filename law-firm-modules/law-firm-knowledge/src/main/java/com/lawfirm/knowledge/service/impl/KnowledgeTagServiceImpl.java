package com.lawfirm.knowledge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.knowledge.entity.KnowledgeTag;
import com.lawfirm.model.knowledge.mapper.KnowledgeTagMapper;
import com.lawfirm.model.knowledge.service.KnowledgeTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.lawfirm.knowledge.exception.KnowledgeException;

import java.util.Collections;
import java.util.List;

/**
 * 知识标签服务实现类
 */
@Slf4j
@Service("knowledgeTagServiceImpl")
public class KnowledgeTagServiceImpl extends BaseServiceImpl<KnowledgeTagMapper, KnowledgeTag> implements KnowledgeTagService {

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
} 