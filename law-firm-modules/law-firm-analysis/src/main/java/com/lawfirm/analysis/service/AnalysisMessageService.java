package com.lawfirm.analysis.service;

import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.message.entity.base.BaseMessage;
import com.lawfirm.model.message.enums.MessageTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 分析模块消息服务
 * 处理分析报告、数据异常、统计结果等通知
 */
@Service("analysisMessageService")
public class AnalysisMessageService {

    private static final Logger log = LoggerFactory.getLogger(AnalysisMessageService.class);

    @Autowired(required = false)
    @Qualifier("messageSender")
    private MessageSender messageSender;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 发送分析报告生成完成通知
     */
    public void sendReportGeneratedNotification(Long reportId, String reportTitle, String reportType, List<Long> recipientIds) {
        if (recipientIds == null || recipientIds.isEmpty()) return;

        try {
            String title = "分析报告生成完成：" + reportTitle;
            String content = String.format("报告类型：%s，报告已生成完成，请查看。", reportType);
            
            for (Long recipientId : recipientIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(title);
                    message.setContent(content);
                    message.setReceiverId(recipientId);
                    message.setBusinessId(reportId);
                    message.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(message);
                }
            }
            
            log.info("[分析消息] 分析报告生成通知已发送: reportId={}, reportType={}, recipients={}", 
                    reportId, reportType, recipientIds.size());

        } catch (Exception e) {
            log.error("发送分析报告生成通知失败: reportId={}, error={}", reportId, e.getMessage());
        }
    }

    /**
     * 发送数据异常警告通知
     */
    public void sendDataAnomalyWarning(String dataSource, String anomalyType, String description, List<Long> adminIds) {
        if (adminIds == null || adminIds.isEmpty()) return;

        try {
            String title = "数据异常警告：" + dataSource;
            String content = String.format("异常类型：%s，描述：%s，请及时处理。", anomalyType, description);
            
            for (Long adminId : adminIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(title);
                    message.setContent(content);
                    message.setReceiverId(adminId);
                    message.setType(MessageTypeEnum.SYSTEM);
                    messageSender.send(message);
                }
            }
            
            log.info("[分析消息] 数据异常警告已发送: dataSource={}, anomalyType={}, recipients={}", 
                    dataSource, anomalyType, adminIds.size());

        } catch (Exception e) {
            log.error("发送数据异常警告失败: dataSource={}, error={}", dataSource, e.getMessage());
        }
    }

    /**
     * 发送定期统计报告通知
     */
    public void sendPeriodicStatisticsNotification(String period, String statisticsType, String summary, List<Long> managerIds) {
        if (managerIds == null || managerIds.isEmpty()) return;

        try {
            String title = period + "统计报告：" + statisticsType;
            String content = String.format("统计摘要：%s", summary);
            
            for (Long managerId : managerIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(title);
                    message.setContent(content);
                    message.setReceiverId(managerId);
                    message.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(message);
                }
            }
            
            log.info("[分析消息] 定期统计报告通知已发送: period={}, statisticsType={}, recipients={}", 
                    period, statisticsType, managerIds.size());

        } catch (Exception e) {
            log.error("发送定期统计报告通知失败: period={}, error={}", period, e.getMessage());
        }
    }
} 