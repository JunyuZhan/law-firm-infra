package com.lawfirm.api.adaptor.cases;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.cases.dto.business.CaseTaskDTO;
import com.lawfirm.model.cases.service.business.CaseTaskService;
import com.lawfirm.model.cases.vo.business.CaseTaskVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 案件任务适配器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskAdaptor extends BaseAdaptor {

    private final CaseTaskService taskService;

    /**
     * 创建任务
     */
    public Long createTask(CaseTaskDTO taskDTO) {
        log.info("创建任务: {}", taskDTO);
        return taskService.createTask(taskDTO);
    }

    /**
     * 批量创建任务
     */
    public boolean batchCreateTasks(List<CaseTaskDTO> taskDTOs) {
        log.info("批量创建任务: {}", taskDTOs);
        return taskService.batchCreateTasks(taskDTOs);
    }

    /**
     * 更新任务
     */
    public boolean updateTask(CaseTaskDTO taskDTO) {
        log.info("更新任务: {}", taskDTO);
        return taskService.updateTask(taskDTO);
    }

    /**
     * 删除任务
     */
    public boolean deleteTask(Long taskId) {
        log.info("删除任务: {}", taskId);
        return taskService.deleteTask(taskId);
    }

    /**
     * 批量删除任务
     */
    public boolean batchDeleteTasks(List<Long> taskIds) {
        log.info("批量删除任务: {}", taskIds);
        return taskService.batchDeleteTasks(taskIds);
    }

    /**
     * 获取任务详情
     */
    public CaseTaskVO getTaskDetail(Long taskId) {
        log.info("获取任务详情: {}", taskId);
        return taskService.getTaskDetail(taskId);
    }

    /**
     * 获取案件的所有任务
     */
    public List<CaseTaskVO> listCaseTasks(Long caseId) {
        log.info("获取案件的所有任务: caseId={}", caseId);
        return taskService.listCaseTasks(caseId);
    }

    /**
     * 分页查询任务
     */
    public IPage<CaseTaskVO> pageTasks(Long caseId, Integer taskType, Integer taskStatus, Integer pageNum, Integer pageSize) {
        log.info("分页查询任务: caseId={}, taskType={}, taskStatus={}, pageNum={}, pageSize={}", 
                caseId, taskType, taskStatus, pageNum, pageSize);
        return taskService.pageTasks(caseId, taskType, taskStatus, pageNum, pageSize);
    }

    /**
     * 开始任务
     */
    public boolean startTask(Long taskId) {
        log.info("开始任务: {}", taskId);
        return taskService.startTask(taskId);
    }

    /**
     * 暂停任务
     */
    public boolean pauseTask(Long taskId, String reason) {
        log.info("暂停任务: taskId={}, reason={}", taskId, reason);
        return taskService.pauseTask(taskId, reason);
    }

    /**
     * 恢复任务
     */
    public boolean resumeTask(Long taskId) {
        log.info("恢复任务: {}", taskId);
        return taskService.resumeTask(taskId);
    }

    /**
     * 完成任务
     */
    public boolean completeTask(Long taskId, String completionNote) {
        log.info("完成任务: taskId={}, completionNote={}", taskId, completionNote);
        return taskService.completeTask(taskId, completionNote);
    }

    /**
     * 取消任务
     */
    public boolean cancelTask(Long taskId, String reason) {
        log.info("取消任务: taskId={}, reason={}", taskId, reason);
        return taskService.cancelTask(taskId, reason);
    }

    /**
     * 分配任务
     */
    public boolean assignTask(Long taskId, Long assigneeId) {
        log.info("分配任务: taskId={}, assigneeId={}", taskId, assigneeId);
        return taskService.assignTask(taskId, assigneeId);
    }

    /**
     * 更新任务进度
     */
    public boolean updateTaskProgress(Long taskId, Integer progress, String progressNote) {
        log.info("更新任务进度: taskId={}, progress={}, progressNote={}", taskId, progress, progressNote);
        return taskService.updateTaskProgress(taskId, progress, progressNote);
    }

    /**
     * 添加任务评论
     */
    public boolean addTaskComment(Long taskId, String comment) {
        log.info("添加任务评论: taskId={}, comment={}", taskId, comment);
        return taskService.addTaskComment(taskId, comment);
    }

    /**
     * 设置任务优先级
     */
    public boolean setTaskPriority(Long taskId, Integer priority) {
        log.info("设置任务优先级: taskId={}, priority={}", taskId, priority);
        return taskService.setTaskPriority(taskId, priority);
    }

    /**
     * 检查任务是否存在
     */
    public boolean checkTaskExists(Long taskId) {
        log.info("检查任务是否存在: {}", taskId);
        return taskService.checkTaskExists(taskId);
    }

    /**
     * 统计案件任务数量
     */
    public int countTasks(Long caseId, Integer taskType, Integer taskStatus) {
        log.info("统计案件任务数量: caseId={}, taskType={}, taskStatus={}", caseId, taskType, taskStatus);
        return taskService.countTasks(caseId, taskType, taskStatus);
    }
} 