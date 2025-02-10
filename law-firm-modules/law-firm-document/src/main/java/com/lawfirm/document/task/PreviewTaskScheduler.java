package com.lawfirm.document.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 预览任务调度器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PreviewTaskScheduler {

    private final PreviewGenerateTask previewGenerateTask;

    @Value("${document.preview.max-retries:3}")
    private Integer maxRetries;

    @Value("${document.preview.clean-days:7}")
    private Integer cleanDays;

    /**
     * 处理待生成的预览（每分钟）
     */
    @Scheduled(fixedRate = 60000)
    public void processGeneratingPreviews() {
        try {
            previewGenerateTask.processGeneratingPreviews();
        } catch (Exception e) {
            log.error("处理待生成预览失败", e);
        }
    }

    /**
     * 重试失败的预览（每5分钟）
     */
    @Scheduled(fixedRate = 300000)
    public void retryFailedPreviews() {
        try {
            previewGenerateTask.retryFailedPreviews(maxRetries);
        } catch (Exception e) {
            log.error("重试失败预览失败", e);
        }
    }

    /**
     * 清理过期预览（每天凌晨2点）
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanExpiredPreviews() {
        try {
            previewGenerateTask.cleanExpiredPreviews(cleanDays);
        } catch (Exception e) {
            log.error("清理过期预览失败", e);
        }
    }
} 