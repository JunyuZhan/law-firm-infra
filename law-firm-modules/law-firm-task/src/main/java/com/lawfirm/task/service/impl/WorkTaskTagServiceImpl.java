package com.lawfirm.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.task.dto.WorkTaskTagDTO;
import com.lawfirm.model.task.entity.WorkTaskTag;
import com.lawfirm.model.task.entity.WorkTaskTagRelation;
import com.lawfirm.model.task.mapper.WorkTaskTagMapper;
import com.lawfirm.model.task.query.WorkTaskTagQuery;
import com.lawfirm.model.task.service.WorkTaskTagRelationService;
import com.lawfirm.model.task.service.WorkTaskTagService;
import com.lawfirm.task.converter.WorkTaskTagConverter;
import com.lawfirm.task.exception.TaskException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 工作任务标签服务实现类
 */
@Service("workTaskTagService")
public class WorkTaskTagServiceImpl extends ServiceImpl<WorkTaskTagMapper, WorkTaskTag> implements WorkTaskTagService {

    @Autowired
    private WorkTaskTagRelationService workTaskTagRelationService;
    
    @Autowired
    private WorkTaskTagConverter workTaskTagConverter;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTag(WorkTaskTagDTO tagDTO) {
        // 校验参数
        validateTagParams(tagDTO);
        
        // 检查标签名称是否重复
        checkTagNameDuplicate(tagDTO.getName(), null);
        
        // 转换为实体
        WorkTaskTag tag = workTaskTagConverter.dtoToEntity(tagDTO);
        
        // 设置创建人
        tag.setCreateBy(getCurrentUsername());
        tag.setUpdateBy(getCurrentUsername());
        
        // 保存标签
        save(tag);
        
        return tag.getId();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTag(WorkTaskTagDTO tagDTO) {
        // 校验参数
        if (tagDTO.getId() == null) {
            throw new TaskException("标签ID不能为空");
        }
        validateTagParams(tagDTO);
        
        // 检查标签是否存在
        WorkTaskTag existingTag = getById(tagDTO.getId());
        if (existingTag == null) {
            throw new TaskException("标签不存在");
        }
        
        // 检查标签名称是否重复
        checkTagNameDuplicate(tagDTO.getName(), tagDTO.getId());
        
        // 转换为实体
        WorkTaskTag tag = workTaskTagConverter.dtoToEntity(tagDTO);
        tag.setUpdateBy(getCurrentUsername());
        
        // 更新标签
        updateById(tag);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTag(Long tagId) {
        // 检查标签是否存在
        WorkTaskTag tag = getById(tagId);
        if (tag == null) {
            throw new TaskException("标签不存在");
        }
        
        // 删除标签与任务的关联
        workTaskTagRelationService.deleteByTagId(tagId);
        
        // 删除标签
        removeById(tagId);
    }
    
    @Override
    public WorkTaskTagDTO getTagDetail(Long tagId) {
        // 获取标签
        WorkTaskTag tag = getById(tagId);
        if (tag == null) {
            throw new TaskException("标签不存在");
        }
        
        // 转换为DTO
        return workTaskTagConverter.entityToDto(tag);
    }
    
    @Override
    public List<WorkTaskTagDTO> queryTagList(WorkTaskTagQuery query) {
        // 构建查询条件
        LambdaQueryWrapper<WorkTaskTag> wrapper = new LambdaQueryWrapper<>();
        
        // 添加查询条件
        if (StringUtils.hasText(query.getName())) {
            wrapper.like(WorkTaskTag::getName, query.getName());
        }
        
        if (StringUtils.hasText(query.getColor())) {
            wrapper.eq(WorkTaskTag::getColor, query.getColor());
        }
        
        if (query.getCreatorId() != null) {
            wrapper.eq(WorkTaskTag::getCreateBy, query.getCreatorId().toString());
        }
        
        // 排序
        if (Boolean.TRUE.equals(query.getOrderByUsage())) {
            wrapper.orderByDesc(WorkTaskTag::getUsageCount);
        } else {
            wrapper.orderByDesc(WorkTaskTag::getUpdateTime);
        }
        
        // 查询标签
        List<WorkTaskTag> tags = list(wrapper);
        
        // 转换为DTO
        return tags.stream()
                .map(workTaskTagConverter::entityToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<WorkTaskTagDTO> listAllTags() {
        // 构建查询条件
        LambdaQueryWrapper<WorkTaskTag> wrapper = new LambdaQueryWrapper<>();
        
        // 按更新时间降序排序
        wrapper.orderByDesc(WorkTaskTag::getUpdateTime);
        
        // 查询标签
        List<WorkTaskTag> tags = list(wrapper);
        
        // 转换为DTO
        return tags.stream()
                .map(workTaskTagConverter::entityToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTagToTask(Long taskId, Long tagId) {
        // 检查标签是否存在
        WorkTaskTag tag = getById(tagId);
        if (tag == null) {
            throw new TaskException("标签不存在");
        }
        
        // 添加关联
        WorkTaskTagRelation relation = new WorkTaskTagRelation();
        relation.setTaskId(taskId);
        relation.setTagId(tagId);
        relation.setCreateBy(getCurrentUsername());
        relation.setUpdateBy(getCurrentUsername());
        
        workTaskTagRelationService.save(relation);
        
        // 更新标签使用次数
        tag.setUsageCount(tag.getUsageCount() + 1);
        updateById(tag);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeTagFromTask(Long taskId, Long tagId) {
        // 检查标签是否存在
        WorkTaskTag tag = getById(tagId);
        if (tag == null) {
            throw new TaskException("标签不存在");
        }
        
        // 删除关联
        LambdaQueryWrapper<WorkTaskTagRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkTaskTagRelation::getTaskId, taskId)
                .eq(WorkTaskTagRelation::getTagId, tagId);
        
        workTaskTagRelationService.remove(wrapper);
        
        // 更新标签使用次数
        if (tag.getUsageCount() > 0) {
            tag.setUsageCount(tag.getUsageCount() - 1);
            updateById(tag);
        }
    }
    
    @Override
    public List<WorkTaskTagDTO> getTaskTags(Long taskId) {
        // 获取任务的标签ID列表
        List<Long> tagIds = workTaskTagRelationService.getTagIdsByTaskId(taskId);
        
        if (tagIds.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 获取标签列表
        List<WorkTaskTag> tags = listByIds(tagIds);
        
        // 转换为DTO
        return tags.stream()
                .map(workTaskTagConverter::entityToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<WorkTaskTagDTO> getTagsByTaskId(Long taskId) {
        // 这个方法和getTaskTags功能相同，复用现有实现
        return getTaskTags(taskId);
    }
    
    @Override
    public List<WorkTaskTagDTO> getPopularTags(Integer limit) {
        // 查询使用次数最多的标签
        LambdaQueryWrapper<WorkTaskTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(WorkTaskTag::getUsageCount);
        wrapper.last("LIMIT " + limit);
        
        List<WorkTaskTag> tags = list(wrapper);
        
        // 转换为DTO
        return tags.stream()
                .map(workTaskTagConverter::entityToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * 校验标签参数
     */
    private void validateTagParams(WorkTaskTagDTO tagDTO) {
        if (!StringUtils.hasText(tagDTO.getName())) {
            throw new TaskException("标签名称不能为空");
        }
        
        if (tagDTO.getName().length() > 20) {
            throw new TaskException("标签名称不能超过20个字符");
        }
        
        if (tagDTO.getDescription() != null && tagDTO.getDescription().length() > 200) {
            throw new TaskException("标签描述不能超过200个字符");
        }
    }
    
    /**
     * 检查标签名称是否重复
     */
    private void checkTagNameDuplicate(String name, Long excludeId) {
        LambdaQueryWrapper<WorkTaskTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkTaskTag::getName, name);
        
        if (excludeId != null) {
            wrapper.ne(WorkTaskTag::getId, excludeId);
        }
        
        if (exists(wrapper)) {
            throw new TaskException("标签名称已存在");
        }
    }
    
    @Override
    public Long getCurrentUserId() {
        return 1L; // TODO: 从安全上下文获取当前用户ID
    }
    
    @Override
    public String getCurrentUsername() {
        return "admin"; // TODO: 从安全上下文获取当前用户名
    }
    
    @Override
    public Long getCurrentTenantId() {
        return 1L; // TODO: 从安全上下文获取当前租户ID
    }
    
    @Override
    public boolean exists(QueryWrapper<WorkTaskTag> queryWrapper) {
        return baseMapper.exists(queryWrapper);
    }
    
    @Override
    public WorkTaskTag getById(Long id) {
        return baseMapper.selectById(id);
    }
    
    @Override
    public boolean update(WorkTaskTag entity) {
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
    public long count(QueryWrapper<WorkTaskTag> queryWrapper) {
        return baseMapper.selectCount(queryWrapper);
    }
    
    @Override
    public List<WorkTaskTag> list(QueryWrapper<WorkTaskTag> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }
    
    @Override
    public Page<WorkTaskTag> page(Page<WorkTaskTag> page, QueryWrapper<WorkTaskTag> queryWrapper) {
        return baseMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public boolean save(WorkTaskTag entity) {
        return super.save(entity);
    }
    
    @Override
    public boolean saveBatch(List<WorkTaskTag> entityList) {
        return super.saveBatch(entityList);
    }
    
    @Override
    public boolean updateBatch(List<WorkTaskTag> entityList) {
        return super.updateBatchById(entityList);
    }
} 