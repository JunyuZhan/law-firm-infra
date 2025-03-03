package com.lawfirm.model.cases.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.business.CaseTaskDTO;
import com.lawfirm.model.cases.vo.business.CaseTaskVO;

import java.util.List;

/**
 * 案件任务服务接口
 */
public interface CaseTaskService {

    /**
     * 创建任务
     *
     * @param taskDTO 任务信息
     * @return 任务ID
     */
    Long createTask(CaseTaskDTO taskDTO);

    /**
     * 批量创建任务
     *
     * @param taskDTOs 任务信息列表
     * @return 是否成功
     */
    boolean batchCreateTasks(List<CaseTaskDTO> taskDTOs);

    /**
     * 更新任务
     *
     * @param taskDTO 任务信息
     * @return 是否成功
     */
    boolean updateTask(CaseTaskDTO taskDTO);

    /**
     * 删除任务
     *
     * @param taskId 任务ID
     * @return 是否成功
     */
    boolean deleteTask(Long taskId);

    /**
     * 批量删除任务
     *
     * @param taskIds 任务ID列表
     * @return 是否成功
     */
    boolean batchDeleteTasks(List<Long> taskIds);

    /**
     * 获取任务详情
     *
     * @param taskId 任务ID
     * @return 任务详情
     */
    CaseTaskVO getTaskDetail(Long taskId);

    /**
     * 获取案件的所有任务
     *
     * @param caseId 案件ID
     * @return 任务列表
     */
    List<CaseTaskVO> listCaseTasks(Long caseId);

    /**
     * 分页查询任务
     *
     * @param caseId 案件ID
     * @param taskType 任务类型
     * @param taskStatus 任务状态
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    IPage<CaseTaskVO> pageTasks(Long caseId, Integer taskType, Integer taskStatus, Integer pageNum, Integer pageSize);

    /**
     * 开始任务
     *
     * @param taskId 任务ID
     * @return 是否成功
     */
    boolean startTask(Long taskId);

    /**
     * 暂停任务
     *
     * @param taskId 任务ID
     * @param reason 暂停原因
     * @return 是否成功
     */
    boolean pauseTask(Long taskId, String reason);

    /**
     * 恢复任务
     *
     * @param taskId 任务ID
     * @return 是否成功
     */
    boolean resumeTask(Long taskId);

    /**
     * 完成任务
     *
     * @param taskId 任务ID
     * @param completionNote 完成说明
     * @return 是否成功
     */
    boolean completeTask(Long taskId, String completionNote);

    /**
     * 取消任务
     *
     * @param taskId 任务ID
     * @param reason 取消原因
     * @return 是否成功
     */
    boolean cancelTask(Long taskId, String reason);

    /**
     * 分配任务
     *
     * @param taskId 任务ID
     * @param assigneeId 受理人ID
     * @return 是否成功
     */
    boolean assignTask(Long taskId, Long assigneeId);

    /**
     * 更新任务进度
     *
     * @param taskId 任务ID
     * @param progress 进度（0-100）
     * @param progressNote 进度说明
     * @return 是否成功
     */
    boolean updateTaskProgress(Long taskId, Integer progress, String progressNote);

    /**
     * 添加任务评论
     *
     * @param taskId 任务ID
     * @param comment 评论内容
     * @return 是否成功
     */
    boolean addTaskComment(Long taskId, String comment);

    /**
     * 设置任务优先级
     *
     * @param taskId 任务ID
     * @param priority 优先级
     * @return 是否成功
     */
    boolean setTaskPriority(Long taskId, Integer priority);

    /**
     * 检查任务是否存在
     *
     * @param taskId 任务ID
     * @return 是否存在
     */
    boolean checkTaskExists(Long taskId);

    /**
     * 统计案件任务数量
     *
     * @param caseId 案件ID
     * @param taskType 任务类型
     * @param taskStatus 任务状态
     * @return 数量
     */
    int countTasks(Long caseId, Integer taskType, Integer taskStatus);
} 