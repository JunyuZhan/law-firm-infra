package com.lawfirm.client.task;

import com.lawfirm.client.service.impl.FollowUpServiceImpl;
import com.lawfirm.client.service.ClientMessageService;
import com.lawfirm.client.constant.ClientModuleConstant;
import com.lawfirm.model.client.entity.follow.ClientFollowUp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户跟进提醒定时任务
 * 定期检查待处理的跟进任务，发送提醒通知
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FollowUpReminderTask {

    private final FollowUpServiceImpl followUpService;
    private final ClientMessageService clientMessageService;
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 每天早上9点检查当天需要跟进的客户，发送提醒
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void dailyReminder() {
        log.info("开始执行客户跟进日常提醒任务");
        
        try {
            // 获取今天需要跟进的记录
            LocalDateTime startTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
            LocalDateTime endTime = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
            
            List<ClientFollowUp> pendingFollowUps = followUpService.listPendingFollowUps(startTime, endTime);
            
            log.info("找到{}条待处理的跟进任务", pendingFollowUps.size());
            
            // 对每条记录发送提醒
            for (ClientFollowUp followUp : pendingFollowUps) {
                try {
                    sendReminder(followUp);
                } catch (Exception e) {
                    log.error("发送跟进提醒失败，跟进ID: {}", followUp.getId(), e);
                }
            }
        } catch (Exception e) {
            log.error("执行客户跟进提醒任务失败", e);
        }
        
        log.info("客户跟进日常提醒任务执行完成");
    }
    
    /**
     * 每小时检查即将到期（1小时内）的跟进任务，发送紧急提醒
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void hourlyUrgentReminder() {
        log.info("开始执行紧急跟进提醒任务");
        
        try {
            // 获取1小时内需要跟进的记录
            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime endTime = LocalDateTime.now().plusHours(1);
            
            List<ClientFollowUp> urgentFollowUps = followUpService.listPendingFollowUps(startTime, endTime);
            
            log.info("找到{}条紧急待处理的跟进任务", urgentFollowUps.size());
            
            // 对每条记录发送紧急提醒
            for (ClientFollowUp followUp : urgentFollowUps) {
                try {
                    sendUrgentReminder(followUp);
                } catch (Exception e) {
                    log.error("发送紧急跟进提醒失败，跟进ID: {}", followUp.getId(), e);
                }
            }
        } catch (Exception e) {
            log.error("执行紧急跟进提醒任务失败", e);
        }
        
        log.info("紧急跟进提醒任务执行完成");
    }
    
    /**
     * 每周一早上10点检查本周需要跟进的任务，发送周计划提醒
     */
    @Scheduled(cron = "0 0 10 ? * MON")
    public void weeklyPlanReminder() {
        log.info("开始执行周计划跟进提醒任务");
        
        try {
            // 获取本周需要跟进的记录
            LocalDateTime startTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
            LocalDateTime endTime = startTime.plusDays(7);
            
            List<ClientFollowUp> weeklyFollowUps = followUpService.listPendingFollowUps(startTime, endTime);
            
            log.info("找到{}条本周待处理的跟进任务", weeklyFollowUps.size());
            
            // 按负责人分组发送周计划提醒
            Map<Long, List<ClientFollowUp>> groupByHandler = weeklyFollowUps.stream()
                .filter(f -> f.getAssigneeId() != null)
                .collect(java.util.stream.Collectors.groupingBy(ClientFollowUp::getAssigneeId));
            
            for (Map.Entry<Long, List<ClientFollowUp>> entry : groupByHandler.entrySet()) {
                Long handlerId = entry.getKey();
                List<ClientFollowUp> handlerFollowUps = entry.getValue();
                
                try {
                    sendWeeklyPlanReminder(handlerId, handlerFollowUps);
                } catch (Exception e) {
                    log.error("发送周计划提醒失败，负责人ID: {}", handlerId, e);
                }
            }
        } catch (Exception e) {
            log.error("执行周计划跟进提醒任务失败", e);
        }
        
        log.info("周计划跟进提醒任务执行完成");
    }
    
    /**
     * 对已超期未处理的跟进任务进行提醒
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void overdueReminder() {
        log.info("开始执行超期跟进提醒任务");
        
        try {
            // 获取已超期未处理的跟进记录
            LocalDateTime endTime = LocalDateTime.now();
            
            List<ClientFollowUp> overdueFollowUps = followUpService.listPendingFollowUps(null, endTime);
            
            log.info("找到{}条已超期未处理的跟进任务", overdueFollowUps.size());
            
            // 对每条记录发送超期提醒
            for (ClientFollowUp followUp : overdueFollowUps) {
                try {
                    // 将Date转换为LocalDateTime进行比较
                    LocalDateTime followUpTime = followUp.getNextFollowTime().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    
                    if (followUpTime.isBefore(LocalDateTime.now())) {
                        sendOverdueReminder(followUp);
                    }
                } catch (Exception e) {
                    log.error("发送超期跟进提醒失败，跟进ID: {}", followUp.getId(), e);
                }
            }
        } catch (Exception e) {
            log.error("执行超期跟进提醒任务失败", e);
        }
        
        log.info("超期跟进提醒任务执行完成");
    }
    
    /**
     * 发送普通提醒
     * 
     * @param followUp 跟进记录
     */
    private void sendReminder(ClientFollowUp followUp) {
        LocalDateTime followUpTime = followUp.getNextFollowTime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        
        // 使用ClientMessageService发送提醒
        clientMessageService.sendFollowUpReminder(
            followUp.getId(),
            followUp.getClientId(),
            getClientName(followUp.getClientId()),
            followUp.getAssigneeId(),
            followUpTime
        );
    }
    
    /**
     * 发送紧急提醒
     * 
     * @param followUp 跟进记录
     */
    private void sendUrgentReminder(ClientFollowUp followUp) {
        LocalDateTime followUpTime = followUp.getNextFollowTime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        
        // 使用ClientMessageService发送紧急提醒
        clientMessageService.sendUrgentFollowUpReminder(
            followUp.getId(),
            followUp.getClientId(),
            getClientName(followUp.getClientId()),
            followUp.getAssigneeId(),
            followUpTime
        );
    }
    
    /**
     * 发送超期提醒
     * 
     * @param followUp 跟进记录
     */
    private void sendOverdueReminder(ClientFollowUp followUp) {
        LocalDateTime followUpTime = followUp.getNextFollowTime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        long hoursOverdue = java.time.Duration.between(followUpTime, LocalDateTime.now()).toHours();
        
        // 使用ClientMessageService发送超期提醒
        clientMessageService.sendOverdueFollowUpReminder(
            followUp.getId(),
            followUp.getClientId(),
            getClientName(followUp.getClientId()),
            followUp.getAssigneeId(),
            followUpTime,
            hoursOverdue
        );
    }
    
    /**
     * 发送周计划提醒
     * 
     * @param handlerId 负责人ID
     * @param followUps 跟进任务列表
     */
    private void sendWeeklyPlanReminder(Long handlerId, List<ClientFollowUp> followUps) {
        StringBuilder content = new StringBuilder("您本周有 ");
        content.append(followUps.size()).append(" 个客户需要跟进：\n");
        
        for (ClientFollowUp followUp : followUps) {
            LocalDateTime followUpTime = followUp.getNextFollowTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            content.append("- ").append(getClientName(followUp.getClientId()))
                   .append("（").append(followUpTime.format(DATE_TIME_FORMATTER)).append("）\n");
        }
        
        // 使用ClientMessageService发送批量通知
        clientMessageService.sendBatchClientNotification(
            java.util.Arrays.asList(handlerId),
            "本周客户跟进计划",
            content.toString(),
            null
        );
    }
    
    /**
     * 获取客户名称（简化实现，实际应该调用客户服务）
     */
    private String getClientName(Long clientId) {
        // 这里简化处理，实际应该调用客户服务获取客户名称
        return "客户" + clientId;
    }
} 
