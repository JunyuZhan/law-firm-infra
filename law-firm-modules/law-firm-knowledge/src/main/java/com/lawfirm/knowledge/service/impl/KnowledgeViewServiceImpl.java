package com.lawfirm.knowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.knowledge.entity.KnowledgeView;
import com.lawfirm.knowledge.mapper.KnowledgeViewMapper;
import com.lawfirm.knowledge.service.KnowledgeViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 知识库查看记录服务实现类
 */
@Slf4j
@Service("knowledgeViewServiceImpl")
@RequiredArgsConstructor
public class KnowledgeViewServiceImpl extends ServiceImpl<KnowledgeViewMapper, KnowledgeView> implements KnowledgeViewService {

    private final KnowledgeViewMapper viewMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createView(Long knowledgeId, Long userId, String ip) {
        KnowledgeView view = new KnowledgeView();
        view.setKnowledgeId(knowledgeId);
        view.setUserId(userId);
        view.setIp(ip);
        view.setViewTime(LocalDateTime.now());
        view.setCreateTime(LocalDateTime.now());
        view.setUpdateTime(LocalDateTime.now());
        save(view);
        return view.getId();
    }

    @Override
    public List<KnowledgeView> listByUser(Long userId) {
        return viewMapper.selectByUserId(userId);
    }

    @Override
    public List<KnowledgeView> listByKnowledge(Long knowledgeId) {
        return viewMapper.selectByKnowledgeId(knowledgeId);
    }

    @Override
    public Integer countViews(Long knowledgeId, LocalDateTime startTime, LocalDateTime endTime) {
        return viewMapper.countViews(knowledgeId, startTime, endTime);
    }

    @Override
    public Integer countUserViews(Long knowledgeId, Long userId) {
        return viewMapper.countUserViews(knowledgeId, userId);
    }
} 