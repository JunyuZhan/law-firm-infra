package com.lawfirm.task.schedule;

import com.lawfirm.task.service.impl.WorkTaskServiceImpl;
import com.lawfirm.model.task.entity.WorkTask;
import com.lawfirm.model.task.enums.WorkTaskStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component("workTaskReminderScheduler")
public class TaskReminderScheduler {

    @Autowired
    private WorkTaskServiceImpl workTaskService;

    // 每10分钟扫描一次
    @Scheduled(cron = "0 */10 * * * ?")
    public void remindDueTasks() {
        LocalDateTime now = LocalDateTime.now();
        // 查询所有未完成且设置了截止时间的任务
        List<WorkTask> tasks = workTaskService.lambdaQuery()
            .in(WorkTask::getStatus, WorkTaskStatusEnum.TO_DO.getCode(), WorkTaskStatusEnum.IN_PROGRESS.getCode())
            .isNotNull(WorkTask::getEndTime)
            .list();

        for (WorkTask task : tasks) {
            if (task.getEndTime() == null) continue;
            // 提前1小时提醒
            if (task.getEndTime().isAfter(now) && task.getEndTime().isBefore(now.plusHours(1))) {
                workTaskService.sendTaskDueReminder(task.getId(), task.getTitle(), task.getAssigneeId(), task.getEndTime());
            }
            // 已超时
            if (task.getEndTime().isBefore(now)) {
                workTaskService.sendTaskOverdueNotification(task.getId(), task.getTitle(), task.getAssigneeId(), task.getEndTime());
            }
        }
    }
} 