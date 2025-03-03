package com.lawfirm.core.workflow.adapter.flowable;

import com.lawfirm.core.workflow.exception.TaskException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskBuilder;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Flowable任务引擎适配器实现类
 * 
 * @author claude
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FlowableTaskAdapterImpl implements FlowableTaskAdapter {

    private final TaskService taskService;
    private final HistoryService historyService;

    @Override
    public Task createTask(String taskName, String assignee, String processInstanceId, Map<String, Object> variables) {
        log.info("创建任务, 任务名称: {}, 处理人: {}, 流程实例ID: {}", taskName, assignee, processInstanceId);
        
        try {
            // 创建独立任务
            Task task = taskService.newTask();
            task.setName(taskName);
            
            if (StringUtils.hasText(assignee)) {
                task.setAssignee(assignee);
            }
            
            // 保存任务
            taskService.saveTask(task);
            
            // 设置变量
            if (variables != null && !variables.isEmpty()) {
                // 如果有流程实例ID，添加到变量中
                if (StringUtils.hasText(processInstanceId)) {
                    variables.put("processInstanceId", processInstanceId);
                }
                taskService.setVariables(task.getId(), variables);
            } else if (StringUtils.hasText(processInstanceId)) {
                // 只有流程实例ID时，创建变量Map
                Map<String, Object> vars = new HashMap<>();
                vars.put("processInstanceId", processInstanceId);
                taskService.setVariables(task.getId(), vars);
            }
            
            log.info("创建任务成功, 任务ID: {}", task.getId());
            return task;
        } catch (Exception e) {
            log.error("创建任务失败", e);
            throw new TaskException("创建任务失败: " + e.getMessage());
        }
    }

    @Override
    public Task getTask(String taskId) {
        log.info("获取任务, 任务ID: {}", taskId);
        
        try {
            Task task = taskService.createTaskQuery()
                    .taskId(taskId)
                    .singleResult();
            
            if (task == null) {
                log.warn("任务不存在, 任务ID: {}", taskId);
            }
            
            return task;
        } catch (Exception e) {
            log.error("获取任务失败", e);
            throw new TaskException("获取任务失败: " + e.getMessage());
        }
    }
    
    @Override
    public HistoricTaskInstance getHistoricTask(String taskId) {
        log.info("获取历史任务, 任务ID: {}", taskId);
        
        try {
            HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery()
                    .taskId(taskId)
                    .singleResult();
            
            if (task == null) {
                log.warn("历史任务不存在, 任务ID: {}", taskId);
            }
            
            return task;
        } catch (Exception e) {
            log.error("获取历史任务失败", e);
            throw new TaskException("获取历史任务失败: " + e.getMessage());
        }
    }

    @Override
    public void completeTask(String taskId, Map<String, Object> variables) {
        log.info("完成任务, 任务ID: {}", taskId);
        
        try {
            if (variables == null) {
                variables = new HashMap<>();
            }
            
            taskService.complete(taskId, variables);
            log.info("完成任务成功");
        } catch (FlowableObjectNotFoundException e) {
            log.error("任务不存在", e);
            throw new TaskException("任务不存在");
        } catch (Exception e) {
            log.error("完成任务失败", e);
            throw new TaskException("完成任务失败: " + e.getMessage());
        }
    }

    @Override
    public void deleteTask(String taskId, String reason) {
        log.info("删除任务, 任务ID: {}, 原因: {}", taskId, reason);
        
        try {
            taskService.deleteTask(taskId, reason);
            log.info("删除任务成功");
        } catch (FlowableObjectNotFoundException e) {
            log.error("任务不存在", e);
            throw new TaskException("任务不存在");
        } catch (Exception e) {
            log.error("删除任务失败", e);
            throw new TaskException("删除任务失败: " + e.getMessage());
        }
    }

    @Override
    public List<Task> listProcessTasks(String processInstanceId) {
        log.info("查询流程任务, 流程实例ID: {}", processInstanceId);
        
        try {
            return taskService.createTaskQuery()
                    .processInstanceId(processInstanceId)
                    .orderByTaskCreateTime()
                    .desc()
                    .list();
        } catch (Exception e) {
            log.error("查询流程任务失败", e);
            throw new TaskException("查询流程任务失败: " + e.getMessage());
        }
    }

    @Override
    public List<Task> listUserTodoTasks(String assignee) {
        log.info("查询用户待办任务, 处理人: {}", assignee);
        
        try {
            return taskService.createTaskQuery()
                    .taskAssignee(assignee)
                    .orderByTaskCreateTime()
                    .desc()
                    .list();
        } catch (Exception e) {
            log.error("查询用户待办任务失败", e);
            throw new TaskException("查询用户待办任务失败: " + e.getMessage());
        }
    }

    @Override
    public List<HistoricTaskInstance> listUserDoneTasks(String assignee) {
        log.info("查询用户已办任务, 处理人: {}", assignee);
        
        try {
            return historyService.createHistoricTaskInstanceQuery()
                    .taskAssignee(assignee)
                    .finished()
                    .orderByHistoricTaskInstanceEndTime()
                    .desc()
                    .list();
        } catch (Exception e) {
            log.error("查询用户已办任务失败", e);
            throw new TaskException("查询用户已办任务失败: " + e.getMessage());
        }
    }

    @Override
    public List<Task> listGroupTasks(String candidateGroup) {
        log.info("查询组任务, 候选组: {}", candidateGroup);
        
        try {
            return taskService.createTaskQuery()
                    .taskCandidateGroup(candidateGroup)
                    .orderByTaskCreateTime()
                    .desc()
                    .list();
        } catch (Exception e) {
            log.error("查询组任务失败", e);
            throw new TaskException("查询组任务失败: " + e.getMessage());
        }
    }

    @Override
    public void transferTask(String taskId, String assignee) {
        log.info("转办任务, 任务ID: {}, 新处理人: {}", taskId, assignee);
        
        try {
            taskService.setAssignee(taskId, assignee);
            log.info("转办任务成功");
        } catch (FlowableObjectNotFoundException e) {
            log.error("任务不存在", e);
            throw new TaskException("任务不存在");
        } catch (Exception e) {
            log.error("转办任务失败", e);
            throw new TaskException("转办任务失败: " + e.getMessage());
        }
    }

    @Override
    public void delegateTask(String taskId, String assignee) {
        log.info("委派任务, 任务ID: {}, 被委派人: {}", taskId, assignee);
        
        try {
            taskService.delegateTask(taskId, assignee);
            log.info("委派任务成功");
        } catch (FlowableObjectNotFoundException e) {
            log.error("任务不存在", e);
            throw new TaskException("任务不存在");
        } catch (Exception e) {
            log.error("委派任务失败", e);
            throw new TaskException("委派任务失败: " + e.getMessage());
        }
    }

    @Override
    public void setDueDate(String taskId, Date dueDate) {
        log.info("设置过期时间, 任务ID: {}, 过期时间: {}", taskId, dueDate);
        
        try {
            taskService.setDueDate(taskId, dueDate);
            log.info("设置过期时间成功");
        } catch (FlowableObjectNotFoundException e) {
            log.error("任务不存在", e);
            throw new TaskException("任务不存在");
        } catch (Exception e) {
            log.error("设置过期时间失败", e);
            throw new TaskException("设置过期时间失败: " + e.getMessage());
        }
    }

    @Override
    public void addCandidateUser(String taskId, String candidateUser) {
        log.info("添加候选人, 任务ID: {}, 候选人: {}", taskId, candidateUser);
        
        try {
            taskService.addCandidateUser(taskId, candidateUser);
            log.info("添加候选人成功");
        } catch (FlowableObjectNotFoundException e) {
            log.error("任务不存在", e);
            throw new TaskException("任务不存在");
        } catch (Exception e) {
            log.error("添加候选人失败", e);
            throw new TaskException("添加候选人失败: " + e.getMessage());
        }
    }

    @Override
    public void addCandidateGroup(String taskId, String candidateGroup) {
        log.info("添加候选组, 任务ID: {}, 候选组: {}", taskId, candidateGroup);
        
        try {
            taskService.addCandidateGroup(taskId, candidateGroup);
            log.info("添加候选组成功");
        } catch (FlowableObjectNotFoundException e) {
            log.error("任务不存在", e);
            throw new TaskException("任务不存在");
        } catch (Exception e) {
            log.error("添加候选组失败", e);
            throw new TaskException("添加候选组失败: " + e.getMessage());
        }
    }
    
    @Override
    public void setPriority(String taskId, int priority) {
        log.info("设置任务优先级, 任务ID: {}, 优先级: {}", taskId, priority);
        
        try {
            taskService.setPriority(taskId, priority);
            log.info("设置任务优先级成功");
        } catch (FlowableObjectNotFoundException e) {
            log.error("任务不存在", e);
            throw new TaskException("任务不存在");
        } catch (Exception e) {
            log.error("设置任务优先级失败", e);
            throw new TaskException("设置任务优先级失败: " + e.getMessage());
        }
    }
    
    @Override
    public Object getTaskVariable(String taskId, String variableName) {
        log.info("获取任务变量, 任务ID: {}, 变量名: {}", taskId, variableName);
        
        try {
            return taskService.getVariable(taskId, variableName);
        } catch (FlowableObjectNotFoundException e) {
            log.error("任务不存在", e);
            throw new TaskException("任务不存在");
        } catch (Exception e) {
            log.error("获取任务变量失败", e);
            throw new TaskException("获取任务变量失败: " + e.getMessage());
        }
    }
    
    @Override
    public void setTaskVariable(String taskId, String variableName, Object value) {
        log.info("设置任务变量, 任务ID: {}, 变量名: {}", taskId, variableName);
        
        try {
            taskService.setVariable(taskId, variableName, value);
            log.info("设置任务变量成功");
        } catch (FlowableObjectNotFoundException e) {
            log.error("任务不存在", e);
            throw new TaskException("任务不存在");
        } catch (Exception e) {
            log.error("设置任务变量失败", e);
            throw new TaskException("设置任务变量失败: " + e.getMessage());
        }
    }
} 