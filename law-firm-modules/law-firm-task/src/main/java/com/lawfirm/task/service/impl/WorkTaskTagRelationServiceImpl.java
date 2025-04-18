package com.lawfirm.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.task.entity.WorkTaskTagRelation;
import com.lawfirm.model.task.mapper.WorkTaskTagRelationMapper;
import com.lawfirm.model.task.service.WorkTaskTagRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lawfirm.common.security.utils.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作任务标签关联服务实现类
 */
@Service("workTaskTagRelationService")
public class WorkTaskTagRelationServiceImpl extends ServiceImpl<WorkTaskTagRelationMapper, WorkTaskTagRelation> implements WorkTaskTagRelationService {

    @Override
    public List<Long> getTagIdsByTaskId(Long taskId) {
        return baseMapper.selectTagIdsByTaskId(taskId);
    }

    @Override
    public List<Long> getTaskIdsByTagId(Long tagId) {
        return baseMapper.selectTaskIdsByTagId(tagId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTaskTagRelations(Long taskId, List<Long> tagIds) {
        // 先删除原有关联
        deleteByTaskId(taskId);
        
        // 保存新关联
        if (tagIds != null && !tagIds.isEmpty()) {
            List<WorkTaskTagRelation> relations = new ArrayList<>();
            
            for (Long tagId : tagIds) {
                WorkTaskTagRelation relation = new WorkTaskTagRelation();
                relation.setTaskId(taskId);
                relation.setTagId(tagId);
                relation.setCreateBy(getCurrentUsername());
                relation.setUpdateBy(getCurrentUsername());
                relations.add(relation);
            }
            
            saveBatch(relations);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTaskId(Long taskId) {
        LambdaQueryWrapper<WorkTaskTagRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkTaskTagRelation::getTaskId, taskId);
        remove(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTagId(Long tagId) {
        LambdaQueryWrapper<WorkTaskTagRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkTaskTagRelation::getTagId, tagId);
        remove(wrapper);
    }
    
    @Override
    public Long getCurrentUserId() {
        return SecurityUtils.getUserId();
    }
    
    @Override
    public String getCurrentUsername() {
        return SecurityUtils.getUsername();
    }
    
    @Override
    public Long getCurrentTenantId() {
        // 如果SecurityUtils中没有直接获取租户ID的方法，可以通过用户对象获取
        // 这里假设租户ID需要从其他地方获取，暂时返回默认值
        // 在实际实现中，应该从当前用户的上下文中获取租户ID
        return 1L; // 默认租户ID，后续应替换为实际获取逻辑
    }
    
    @Override
    public boolean exists(QueryWrapper<WorkTaskTagRelation> queryWrapper) {
        return baseMapper.exists(queryWrapper);
    }
    
    @Override
    public WorkTaskTagRelation getById(Long id) {
        return baseMapper.selectById(id);
    }
    
    @Override
    public boolean update(WorkTaskTagRelation entity) {
        return updateById(entity);
    }
    
    @Override
    public boolean removeBatch(List<Long> ids) {
        return removeByIds(ids);
    }
    
    @Override
    public boolean remove(Long id) {
        return removeById(id);
    }
    
    @Override
    public long count(QueryWrapper<WorkTaskTagRelation> queryWrapper) {
        return baseMapper.selectCount(queryWrapper);
    }
    
    @Override
    public List<WorkTaskTagRelation> list(QueryWrapper<WorkTaskTagRelation> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }
    
    @Override
    public Page<WorkTaskTagRelation> page(Page<WorkTaskTagRelation> page, QueryWrapper<WorkTaskTagRelation> queryWrapper) {
        return baseMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public boolean save(WorkTaskTagRelation entity) {
        return super.save(entity);
    }
    
    @Override
    public boolean saveBatch(List<WorkTaskTagRelation> entityList) {
        return super.saveBatch(entityList);
    }
    
    @Override
    public boolean updateBatch(List<WorkTaskTagRelation> entityList) {
        return super.updateBatchById(entityList);
    }
} 