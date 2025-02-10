package com.lawfirm.document.task;

import com.lawfirm.document.service.IDocumentConversionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 文件转换任务调度器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ConversionTaskScheduler {

    private final IDocumentConversionService conversionService;

    @Value("${document.conversion.clean-days:7}")
    private Integer cleanDays;

    /**
     * 清理过期的转换任务（每天凌晨3点）
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void cleanExpiredTasks() {
        try {
            conversionService.cleanExpiredTasks(cleanDays);
        } catch (Exception e) {
            log.error("清理过期转换任务失败", e);
        }
    }
} 