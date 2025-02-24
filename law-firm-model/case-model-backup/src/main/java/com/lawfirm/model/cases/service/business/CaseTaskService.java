package com.lawfirm.model.cases.service.business;

import com.lawfirm.model.base.dto.PageDTO;
import com.lawfirm.model.cases.enums.task.TaskPriorityEnum;
import com.lawfirm.model.cases.enums.task.TaskStatusEnum;
import com.lawfirm.model.cases.enums.task.TaskTypeEnum;
import com.lawfirm.model.cases.vo.business.CaseTaskVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件任务服务接口
 */
public interface CaseTaskService {

    /**
     * 创建任务
     *
     * @param caseId 案件ID
     * @param title 任务标题
     * @param content 任务内容
     * @param type 任务类型
     * @param priority 优先级
     * @param assigneeId 负责人ID
     * @param deadline 截止时间
     * @return 任务ID
     */
    Long createTask(Long caseId, String title, String content, TaskTypeEnum type, 
            TaskPriorityEnum priority, Long assigneeId, LocalDateTime deadline);

    /**
     * 更新任务
     *
     * @param taskId 任务ID
     * @param title 任务标题
     * @param content 任务内容
     * @param type 任务类型
     * @param priority 优先级
     * @param assigneeId 负责人ID
     * @param deadline 截止时间
     * @return 是否成功
     */
    Boolean updateTask(Long taskId, String title, String content, TaskTypeEnum type,
            TaskPriorityEnum priority, Long assigneeId, LocalDateTime deadline);

    /**
     * 删除任务
     *
     * @param taskId 任务ID
     * @return 是否成功
     */
    Boolean deleteTask(Long taskId);

    /**
     * 更新任务状态
     *
     * @param taskId 任务ID
     * @param status 任务状态
     * @param comment 状态变更说明
     * @return 是否成功
     */
    Boolean updateTaskStatus(Long taskId, TaskStatusEnum status, String comment);

    /**
     * 分配任务
     *
     * @param taskId 任务ID
     * @param assigneeId 负责人ID
     * @return 是否成功
     */
    Boolean assignTask(Long taskId, Long assigneeId);

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
     * @param status 任务状态
     * @param type 任务类型
     * @param assigneeId 负责人ID
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageDTO<CaseTaskVO> pageTasks(Long caseId, TaskStatusEnum status, TaskTypeEnum type,
            Long assigneeId, Integer pageNum, Integer pageSize);

    /**
     * 获取待办任务
     *
     * @param assigneeId 负责人ID
     * @return 任务列表
     */
    List<CaseTaskVO> listTodoTasks(Long assigneeId);

    /**
     * 获取逾期任务
     *
     * @param assigneeId 负责人ID
     * @return 任务列表
     */
    List<CaseTaskVO> listOverdueTasks(Long assigneeId);

    /**
     * 添加任务评论
     *
     * @param taskId 任务ID
     * @param comment 评论内容
     * @return 是否成功
     */
    Boolean addTaskComment(Long taskId, String comment);

    /**
     * 更新任务进度
     *
     * @param taskId 任务ID
     * @param progress 进度（0-100）
     * @return 是否成功
     */
    Boolean updateTaskProgress(Long taskId, Integer progress);
} 