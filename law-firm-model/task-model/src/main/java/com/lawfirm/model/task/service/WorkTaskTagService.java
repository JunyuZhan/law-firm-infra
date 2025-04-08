package com.lawfirm.model.task.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.task.dto.WorkTaskTagDTO;
import com.lawfirm.model.task.entity.WorkTaskTag;
import com.lawfirm.model.task.query.WorkTaskTagQuery;

import java.util.List;

/**
 * 工作任务标签服务接口
 */
public interface WorkTaskTagService extends BaseService<WorkTaskTag> {
    
    /**
     * 创建工作任务标签
     *
     * @param tagDTO 工作任务标签DTO
     * @return 创建的工作任务标签ID
     */
    Long createTag(WorkTaskTagDTO tagDTO);
    
    /**
     * 更新工作任务标签
     *
     * @param tagDTO 工作任务标签DTO
     */
    void updateTag(WorkTaskTagDTO tagDTO);
    
    /**
     * 删除工作任务标签
     *
     * @param tagId 工作任务标签ID
     */
    void deleteTag(Long tagId);
    
    /**
     * 获取工作任务标签详情
     *
     * @param tagId 工作任务标签ID
     * @return 工作任务标签DTO
     */
    WorkTaskTagDTO getTagDetail(Long tagId);
    
    /**
     * 查询工作任务标签列表
     *
     * @param query 查询参数
     * @return 工作任务标签列表
     */
    List<WorkTaskTagDTO> queryTagList(WorkTaskTagQuery query);
    
    /**
     * 查询所有任务标签
     *
     * @return 所有可用的任务标签列表
     */
    List<WorkTaskTagDTO> listAllTags();
    
    /**
     * 为工作任务添加标签
     *
     * @param taskId 工作任务ID
     * @param tagId 标签ID
     */
    void addTagToTask(Long taskId, Long tagId);
    
    /**
     * 从工作任务移除标签
     *
     * @param taskId 工作任务ID
     * @param tagId 标签ID
     */
    void removeTagFromTask(Long taskId, Long tagId);
    
    /**
     * 获取工作任务的标签列表
     *
     * @param taskId 工作任务ID
     * @return 标签列表
     */
    List<WorkTaskTagDTO> getTaskTags(Long taskId);
    
    /**
     * 获取任务关联的标签列表
     *
     * @param taskId 任务ID
     * @return 标签列表
     */
    List<WorkTaskTagDTO> getTagsByTaskId(Long taskId);
    
    /**
     * 获取热门标签列表
     *
     * @param limit 限制数量
     * @return 热门标签列表
     */
    List<WorkTaskTagDTO> getPopularTags(Integer limit);
} 