package com.lawfirm.schedule.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 日程模块消息服务
 * 处理会议提醒、日程安排等通知
 */
@Service("scheduleMessageService")
public class ScheduleMessageService {

    private static final Logger log = LoggerFactory.getLogger(ScheduleMessageService.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 发送会议提醒通知
     */
    public void sendMeetingReminder(Long meetingId, String meetingTitle, LocalDateTime meetingTime, String location, List<Long> participantIds, int minutesBefore) {
        if (participantIds == null || participantIds.isEmpty()) return;

        try {
            String title = "会议提醒：" + meetingTitle;
            String content = String.format("会议时间：%s，地点：%s，还有%d分钟开始", 
                    meetingTime.format(DATE_TIME_FORMATTER), location, minutesBefore);
            
            for (Long participantId : participantIds) {
                // 这里应该调用实际的消息发送服务
                // 目前使用日志记录作为占位符
                log.info("[日程消息] 发送会议提醒: meetingId={}, participantId={}, title={}, content={}", 
                        meetingId, participantId, title, content);
            }
            
            log.info("[日程消息] 会议提醒已发送: meetingId={}, minutesBefore={}, recipients={}", 
                    meetingId, minutesBefore, participantIds.size());

        } catch (Exception e) {
            log.error("发送会议提醒失败: meetingId={}, error={}", meetingId, e.getMessage());
        }
    }

    /**
     * 发送日程变更通知
     */
    public void sendScheduleChangeNotification(Long scheduleId, String scheduleTitle, String changeType, String changeDetails, List<Long> affectedUserIds) {
        if (affectedUserIds == null || affectedUserIds.isEmpty()) return;

        try {
            String title = "日程变更通知：" + scheduleTitle;
            String content = String.format("变更类型：%s，详情：%s", changeType, changeDetails);
            
            for (Long userId : affectedUserIds) {
                log.info("[日程消息] 发送日程变更通知: scheduleId={}, userId={}, title={}, content={}", 
                        scheduleId, userId, title, content);
            }
            
            log.info("[日程消息] 日程变更通知已发送: scheduleId={}, changeType={}, recipients={}", 
                    scheduleId, changeType, affectedUserIds.size());

        } catch (Exception e) {
            log.error("发送日程变更通知失败: scheduleId={}, error={}", scheduleId, e.getMessage());
        }
    }

    /**
     * 发送会议室预订冲突通知
     */
    public void sendRoomConflictNotification(Long roomId, String roomName, LocalDateTime conflictTime, List<Long> conflictUserIds) {
        if (conflictUserIds == null || conflictUserIds.isEmpty()) return;

        try {
            String title = "会议室预订冲突：" + roomName;
            String content = String.format("时间：%s，存在预订冲突，请重新安排", conflictTime.format(DATE_TIME_FORMATTER));
            
            for (Long userId : conflictUserIds) {
                log.info("[日程消息] 发送会议室冲突通知: roomId={}, userId={}, title={}, content={}", 
                        roomId, userId, title, content);
            }
            
            log.info("[日程消息] 会议室冲突通知已发送: roomId={}, conflictTime={}, recipients={}", 
                    roomId, conflictTime, conflictUserIds.size());

        } catch (Exception e) {
            log.error("发送会议室冲突通知失败: roomId={}, error={}", roomId, e.getMessage());
        }
    }
} 