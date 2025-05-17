package com.lawfirm.knowledge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lawfirm.model.knowledge.mapper.KnowledgeAttachmentMapper;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.knowledge.entity.KnowledgeAttachment;
import com.lawfirm.model.knowledge.service.KnowledgeAttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lawfirm.knowledge.exception.KnowledgeException;

import java.util.Collections;
import java.util.List;

/**
 * 知识附件服务实现类
 */
@Slf4j
@Service("knowledgeAttachmentServiceImpl")
public class KnowledgeAttachmentServiceImpl extends BaseServiceImpl<KnowledgeAttachmentMapper, KnowledgeAttachment> implements KnowledgeAttachmentService {

    @Override
    public List<KnowledgeAttachment> listByKnowledgeId(Long knowledgeId) {
        if (knowledgeId == null) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<KnowledgeAttachment> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(KnowledgeAttachment::getKnowledgeId, knowledgeId);
        wrapper.orderByAsc(KnowledgeAttachment::getSort);
        return list(wrapper);
    }

    @Override
    public List<KnowledgeAttachment> listByFileType(String fileType) {
        if (fileType == null) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<KnowledgeAttachment> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(KnowledgeAttachment::getFileType, fileType);
        wrapper.orderByDesc(KnowledgeAttachment::getCreateTime);
        return list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByKnowledgeId(Long knowledgeId) {
        if (knowledgeId == null) {
            return;
        }
        LambdaQueryWrapper<KnowledgeAttachment> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(KnowledgeAttachment::getKnowledgeId, knowledgeId);
        remove(wrapper);
    }

    @Override
    public Integer countByKnowledgeId(Long knowledgeId) {
        if (knowledgeId == null) {
            return 0;
        }
        LambdaQueryWrapper<KnowledgeAttachment> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(KnowledgeAttachment::getKnowledgeId, knowledgeId);
        return Math.toIntExact(count(wrapper));
    }

    /**
     * 删除知识的所有附件（兼容方法）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByKnowledgeId(Long knowledgeId) {
        deleteByKnowledgeId(knowledgeId);
    }
} 