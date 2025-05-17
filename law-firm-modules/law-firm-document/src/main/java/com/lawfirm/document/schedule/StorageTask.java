package com.lawfirm.document.schedule;

import com.lawfirm.document.manager.storage.StorageManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
     * 存储统计任务，统计本地存储目录的空间用量和文件数量（兼容无BucketService场景）
     * 每天凌晨3点执行
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void calculateStorageUsage() {
        log.info("开始统计存储使用情况（本地存储模式）");
        try {
            // 假设本地存储路径通过StorageManager或配置获取
            String localPath = System.getProperty("lawfirm.document.storage.local-path", "data/documents");
            Path storagePath = Paths.get(localPath);
            if (Files.exists(storagePath)) {
                long totalSize = Files.walk(storagePath)
                        .filter(Files::isRegularFile)
                        .mapToLong(p -> {
                            try { return Files.size(p); } catch (Exception e) { return 0L; }
                        }).sum();
                long fileCount = Files.walk(storagePath)
                        .filter(Files::isRegularFile)
                        .count();
                log.info("本地存储目录: {}，文件数: {}，总空间: {} MB", localPath, fileCount, totalSize / 1024 / 1024);
            } else {
                log.warn("本地存储目录不存在: {}", localPath);
            }
            log.info("存储使用情况统计完成");
        } catch (Exception e) {
            log.error("存储使用情况统计失败", e);
        }
    }

    /**
     * 存储统计任务（最小可用实现）
     * TODO: 后续需对接存储模块，统计文档存储空间等信息
     */
    public void statStorage() {
        log.info("存储统计功能尚未实现，需要先完成存储模块");
        // 目前仅打印日志，后续补全存储统计逻辑
    }
}
