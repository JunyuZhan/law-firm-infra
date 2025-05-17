package com.lawfirm.document.schedule;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.document.service.DocumentService;
import com.lawfirm.model.log.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 清理相关定时任务
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CleanupTask {

    private final DocumentService documentService;
    private final LogService<?> logService;
    @Value("${law-firm.log.retention-days:90}")
    private int logRetentionDays;

    /**
     * 清理过期文档
     * 每天凌晨5点执行
     */
    @Scheduled(cron = "0 0 5 * * ?")
    public void cleanupExpiredDocuments() {
        log.info("开始清理过期文档");
        try {
            LocalDateTime expirationTime = LocalDateTime.now().minusDays(90); // 90天前的文档
            
            // 查询过期文档
            LambdaQueryWrapper<BaseDocument> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.le(BaseDocument::getCreateTime, expirationTime);
            List<BaseDocument> expiredDocs = documentService.list(queryWrapper);
            
            if (!expiredDocs.isEmpty()) {
                for (BaseDocument doc : expiredDocs) {
                    try {
                        documentService.deleteDocument(doc.getId());
                        log.info("已删除过期文档: {}", doc.getFileName());
                    } catch (Exception e) {
                        log.error("删除过期文档失败: {}", doc.getFileName(), e);
                    }
                }
            }
            
            log.info("过期文档清理完成，共清理{}个文档", expiredDocs.size());
        } catch (Exception e) {
            log.error("过期文档清理失败", e);
        }
    }

    /**
     * 操作日志清理任务，调用日志服务清理过期操作日志
     * 每月1日凌晨6点执行
     */
    @Scheduled(cron = "0 0 6 1 * ?")
    public void cleanOperationLogs() {
        log.info("开始清理过期操作日志，保留天数:{}", logRetentionDays);
        try {
            int count = logService.cleanExpiredLogs(logRetentionDays);
            log.info("操作日志清理完成，共清理{}条日志", count);
        } catch (Exception e) {
            log.error("操作日志清理失败", e);
        }
    }
}
