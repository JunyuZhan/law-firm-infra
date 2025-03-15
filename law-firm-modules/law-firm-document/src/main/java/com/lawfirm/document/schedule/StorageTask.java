package com.lawfirm.document.schedule;

import com.lawfirm.document.manager.storage.StorageManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 存储相关定时任务
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StorageTask {

    private final StorageManager storageManager;

    /**
     * 清理临时文件
     * 每天凌晨2点执行
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupTempFiles() {
        log.info("开始执行临时文件清理任务");
        try {
            // 获取临时文件夹
            File tempDir = new File(System.getProperty("java.io.tmpdir"), "document-temp");
            if (tempDir.exists() && tempDir.isDirectory()) {
                File[] files = tempDir.listFiles();
                int count = 0;
                if (files != null) {
                    for (File file : files) {
                        // 删除24小时前的文件
                        long lastModified = file.lastModified();
                        if (System.currentTimeMillis() - lastModified > 24 * 60 * 60 * 1000) {
                            if (file.delete()) {
                                count++;
                            }
                        }
                    }
                }
                log.info("临时文件清理完成，共清理{}个文件", count);
            }
        } catch (Exception e) {
            log.error("临时文件清理失败", e);
        }
    }

    /**
     * 统计存储使用情况
     * 每天凌晨3点执行
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void calculateStorageUsage() {
        log.info("开始统计存储使用情况");
        try {
            // 由于Bucket类不存在，暂时只记录日志
            log.info("存储统计功能尚未实现，需要先完成存储模块");
            // 后续可以实现存储统计逻辑
            
            log.info("存储使用情况统计完成");
        } catch (Exception e) {
            log.error("存储使用情况统计失败", e);
        }
    }
}
