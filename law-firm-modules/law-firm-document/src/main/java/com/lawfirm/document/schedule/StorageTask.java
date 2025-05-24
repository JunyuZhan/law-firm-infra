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
import java.util.Map;
import java.util.HashMap;

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
     */
    @Scheduled(cron = "0 0 4 * * ?") // 每天凌晨4点执行
    public void statStorage() {
        log.info("开始统计存储空间和文件数");
        try {
            // 获取存储类型
            String storageType = storageManager.getStorageType();
            log.info("当前存储类型: {}", storageType);
            
            // 统计本地存储
            long totalSize = storageManager.getTotalStorageSize();
            long fileCount = storageManager.getTotalFileCount();
            
            // 如果是基于对象存储的实现，获取更详细的统计信息
            try {
                // 通过StorageManager获取BucketService
                java.lang.reflect.Field bucketServiceField = StorageManager.class.getDeclaredField("bucketService");
                bucketServiceField.setAccessible(true);
                Object bucketService = bucketServiceField.get(storageManager);
                
                if (bucketService != null) {
                    log.info("发现BucketService，收集存储桶统计信息");
                    
                    // 获取所有存储桶方法
                    java.lang.reflect.Method findAllMethod = bucketService.getClass().getMethod("findAll");
                    java.lang.reflect.Method getUsageMethod = bucketService.getClass().getMethod("getUsage", Long.class);
                    
                    // 获取所有存储桶
                    List<?> buckets = (List<?>) findAllMethod.invoke(bucketService);
                    
                    // 按存储类型分组统计
                    Map<String, StorageStats> statsByType = new HashMap<>();
                    
                    for (Object bucket : buckets) {
                        // 获取存储桶ID和类型
                        Long bucketId = (Long) bucket.getClass().getMethod("getId").invoke(bucket);
                        Object storageTypeEnum = bucket.getClass().getMethod("getStorageType").invoke(bucket);
                        String bucketName = (String) bucket.getClass().getMethod("getBucketName").invoke(bucket);
                        String bucketType = storageTypeEnum.toString();
                        
                        // 获取存储桶使用情况
                        Object bucketInfo = getUsageMethod.invoke(bucketService, bucketId);
                        
                        // 从BucketVO中提取统计信息
                        Long usedSize = (Long) bucketInfo.getClass().getMethod("getUsedSize").invoke(bucketInfo);
                        Long bucketFileCount = (Long) bucketInfo.getClass().getMethod("getFileCount").invoke(bucketInfo);
                        
                        // 更新统计
                        StorageStats stats = statsByType.getOrDefault(bucketType, new StorageStats());
                        stats.addSize(usedSize != null ? usedSize : 0L);
                        stats.addCount(bucketFileCount != null ? bucketFileCount : 0L);
                        stats.addBucket();
                        
                        statsByType.put(bucketType, stats);
                        
                        log.info("存储桶 [{}] 统计：文件数：{}，总空间：{} MB", 
                                bucketName, 
                                bucketFileCount, 
                                usedSize != null ? usedSize / 1024 / 1024 : 0);
                    }
                    
                    // 输出各类型存储的统计
                    for (Map.Entry<String, StorageStats> entry : statsByType.entrySet()) {
                        StorageStats stats = entry.getValue();
                        log.info("存储类型 [{}] 统计：存储桶数：{}，文件数：{}，总空间：{} MB", 
                                entry.getKey(), 
                                stats.getBucketCount(),
                                stats.getFileCount(), 
                                stats.getTotalSize() / 1024 / 1024);
                    }
                }
            } catch (Exception e) {
                log.warn("获取存储桶统计信息失败，仅使用本地统计: {}", e.getMessage());
            }
            
            // 输出总计
            log.info("存储统计总计：文件数：{}，总空间：{} MB", fileCount, totalSize / 1024 / 1024);
        } catch (Exception e) {
            log.error("存储统计失败", e);
        }
    }

    /**
     * 存储统计辅助类
     */
    private static class StorageStats {
        private long totalSize = 0;
        private long fileCount = 0;
        private int bucketCount = 0;
        
        public void addSize(long size) {
            this.totalSize += size;
        }
        
        public void addCount(long count) {
            this.fileCount += count;
        }
        
        public void addBucket() {
            this.bucketCount++;
        }
        
        public long getTotalSize() {
            return totalSize;
        }
        
        public long getFileCount() {
            return fileCount;
        }
        
        public int getBucketCount() {
            return bucketCount;
        }
    }
}
