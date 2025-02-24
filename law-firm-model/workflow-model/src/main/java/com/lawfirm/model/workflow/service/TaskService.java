package com.lawfirm.model.workflow.service;

import com.lawfirm.model.workflow.dto.task.TaskCreateDTO;
import com.lawfirm.model.workflow.dto.task.TaskQueryDTO;
import com.lawfirm.model.workflow.vo.TaskVO;

import java.util.List;

/**
 * 任务服务接口
 */
public interface TaskService {

    /**
     * 创建任务
     *
     * @param createDTO 创建参数
     * @return 任务ID
     */
    Long createTask(TaskCreateDTO createDTO);

    /**
     * 删除任务
     *
     * @param id 任务ID
     */
    void deleteTask(Long id);

    /**
     * 获取任务详情
     *
     * @param id 任务ID
     * @return 任务详情
     */
    TaskVO getTask(Long id);

    /**
     * 查询任务列表
     *
     * @param queryDTO 查询参数
     * @return 任务列表
     */
    List<TaskVO> listTasks(TaskQueryDTO queryDTO);

    /**
     * 开始处理任务
     *
     * @param id 任务ID
     */
    void startTask(Long id);

    /**
     * 完成任务
     *
     * @param id 任务ID
     * @param result 处理结果
     * @param comment 处理意见
     */
    void completeTask(Long id, String result, String comment);

    /**
     * 取消任务
     *
     * @param id 任务ID
     */
    void cancelTask(Long id);

    /**
     * 转办任务
     *
     * @param id 任务ID
     * @param handlerId 处理人ID
     * @param handlerName 处理人名称
     */
    void transferTask(Long id, Long handlerId, String handlerName);

    /**
     * 获取流程的任务列表
     *
     * @param processId 流程ID
     * @return 任务列表
     */
    List<TaskVO> listProcessTasks(Long processId);

    /**
     * 获取我的待办任务
     *
     * @param handlerId 处理人ID
     * @return 任务列表
     */
    List<TaskVO> listMyTodoTasks(Long handlerId);

    /**
     * 获取我的已办任务
     *
     * @param handlerId 处理人ID
     * @return 任务列表
     */
    List<TaskVO> listMyDoneTasks(Long handlerId);
} 