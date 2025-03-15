package com.lawfirm.document.schedule;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
     * 清理操作日志
     * 每月1号凌晨6点执行
     */
    @Scheduled(cron = "0 0 6 1 * ?")
    public void cleanupOperationLogs() {
        log.info("开始清理操作日志");
        try {
            LocalDateTime retentionTime = LocalDateTime.now().minusMonths(6); // 保留6个月的日志
            
            // 由于OperationLog不存在，暂时只记录日志
            log.info("操作日志清理功能尚未实现，需要先完成日志模块");
            // 后续可以实现日志清理逻辑
            
            log.info("操作日志清理完成");
        } catch (Exception e) {
            log.error("操作日志清理失败", e);
        }
    }
}
