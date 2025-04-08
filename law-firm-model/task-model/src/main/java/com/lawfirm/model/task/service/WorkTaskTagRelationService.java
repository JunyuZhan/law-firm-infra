package com.lawfirm.model.task.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.task.entity.WorkTaskTagRelation;

import java.util.List;

/**
 * 任务标签关联服务接口
 */
public interface WorkTaskTagRelationService extends BaseService<WorkTaskTagRelation> {

    /**
     * 根据任务ID查询标签ID列表
     *
     * @param taskId 任务ID
     * @return 标签ID列表
     */
    List<Long> getTagIdsByTaskId(Long taskId);

    /**
     * 根据标签ID查询任务ID列表
     *
     * @param tagId 标签ID
     * @return 任务ID列表
     */
    List<Long> getTaskIdsByTagId(Long tagId);

    /**
     * 保存任务标签关联
     *
     * @param taskId 任务ID
     * @param tagIds 标签ID列表
     */
    void saveTaskTagRelations(Long taskId, List<Long> tagIds);

    /**
     * 删除任务标签关联
     *
     * @param taskId 任务ID
     */
    void deleteByTaskId(Long taskId);

    /**
     * 删除标签关联
     *
     * @param tagId 标签ID
     */
    void deleteByTagId(Long tagId);
} 