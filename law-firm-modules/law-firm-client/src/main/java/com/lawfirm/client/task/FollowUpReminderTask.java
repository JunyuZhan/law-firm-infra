package com.lawfirm.client.task;

import com.lawfirm.client.service.impl.FollowUpServiceImpl;
import com.lawfirm.client.constant.ClientModuleConstant;
import com.lawfirm.model.client.entity.follow.ClientFollowUp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * 客户跟进提醒定时任务
 * 定期检查待处理的跟进任务，发送提醒通知
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FollowUpReminderTask {

    private final FollowUpServiceImpl followUpService;

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
            // TODO: 实现按负责人分组逻辑
            
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
        // TODO: 实现发送提醒的逻辑，可以是发送邮件、短信、系统消息等
        log.info("发送跟进提醒，跟进ID: {}，客户ID: {}，计划时间: {}", 
                followUp.getId(), followUp.getClientId(), followUp.getNextFollowTime());
    }
    
    /**
     * 发送紧急提醒
     * 
     * @param followUp 跟进记录
     */
    private void sendUrgentReminder(ClientFollowUp followUp) {
        // TODO: 实现发送紧急提醒的逻辑
        log.info("发送紧急跟进提醒，跟进ID: {}，客户ID: {}，计划时间: {}", 
                followUp.getId(), followUp.getClientId(), followUp.getNextFollowTime());
    }
    
    /**
     * 发送超期提醒
     * 
     * @param followUp 跟进记录
     */
    private void sendOverdueReminder(ClientFollowUp followUp) {
        // TODO: 实现发送超期提醒的逻辑
        // 将Date转换为LocalDateTime计算时间差
        LocalDateTime followUpTime = followUp.getNextFollowTime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        
        log.info("发送超期跟进提醒，跟进ID: {}，客户ID: {}，计划时间: {}，已超期: {} 小时", 
                followUp.getId(), followUp.getClientId(), followUp.getNextFollowTime(),
                java.time.Duration.between(followUpTime, LocalDateTime.now()).toHours());
    }
} 
