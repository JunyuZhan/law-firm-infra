package com.lawfirm.core.storage.task;

import com.lawfirm.core.storage.config.StorageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 过期分片文件清理定时任务
 * 定期清理临时目录中的过期分片文件，防止磁盘空间被占满
 *
 * @author Claude
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ExpiredChunkCleanTask {

    private final StorageProperties storageProperties;

    /**
     * 每天凌晨2点执行清理任务
     * 清理超过24小时未更新的分片文件
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanExpiredChunks() {
        log.info("开始清理过期分片文件任务");
        
        String chunkTempDir = storageProperties.getLocal().getTempPath() + File.separator + "chunks";
        Path chunksPath = Paths.get(chunkTempDir);
        
        // 检查目录是否存在
        if (!Files.exists(chunksPath)) {
            log.info("分片临时目录不存在，跳过清理: {}", chunkTempDir);
            return;
        }
        
        // 设置过期时间（24小时）
        long expireTimeMillis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
        AtomicInteger cleanCount = new AtomicInteger(0);
        
        try {
            File chunksDir = chunksPath.toFile();
            // 遍历所有上传ID目录
            Arrays.stream(Objects.requireNonNull(chunksDir.listFiles()))
                    .filter(File::isDirectory)
                    .forEach(uploadIdDir -> {
                        // 检查目录最后修改时间
                        if (uploadIdDir.lastModified() < expireTimeMillis) {
                            cleanUploadDir(uploadIdDir, cleanCount);
                        } else {
                            // 检查目录中的每个分片文件
                            Arrays.stream(Objects.requireNonNull(uploadIdDir.listFiles()))
                                    .filter(File::isFile)
                                    .filter(file -> file.lastModified() < expireTimeMillis)
                                    .forEach(file -> {
                                        if (file.delete()) {
                                            log.debug("删除过期分片文件: {}", file.getAbsolutePath());
                                            cleanCount.incrementAndGet();
                                        } else {
                                            log.warn("删除过期分片文件失败: {}", file.getAbsolutePath());
                                        }
                                    });
                            
                            // 如果目录为空，删除目录
                            if (Objects.requireNonNull(uploadIdDir.listFiles()).length == 0) {
                                if (uploadIdDir.delete()) {
                                    log.debug("删除空的上传ID目录: {}", uploadIdDir.getAbsolutePath());
                                }
                            }
                        }
                    });
            
            log.info("过期分片文件清理完成，共清理 {} 个文件", cleanCount.get());
        } catch (Exception e) {
            log.error("清理过期分片文件时发生错误: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 清理整个上传目录
     */
    private void cleanUploadDir(File uploadIdDir, AtomicInteger cleanCount) {
        try {
            // 获取上次修改时间
            LocalDateTime lastModified = LocalDateTime.ofInstant(
                    java.time.Instant.ofEpochMilli(uploadIdDir.lastModified()),
                    ZoneId.systemDefault());
            
            log.info("清理过期上传目录: {}, 上次修改时间: {}", uploadIdDir.getName(), lastModified);
            
            // 删除目录中的所有文件
            Arrays.stream(Objects.requireNonNull(uploadIdDir.listFiles()))
                    .forEach(file -> {
                        if (file.delete()) {
                            cleanCount.incrementAndGet();
                        } else {
                            log.warn("删除文件失败: {}", file.getAbsolutePath());
                        }
                    });
            
            // 删除目录
            if (uploadIdDir.delete()) {
                log.debug("成功删除上传目录: {}", uploadIdDir.getAbsolutePath());
            } else {
                log.warn("删除上传目录失败: {}", uploadIdDir.getAbsolutePath());
            }
        } catch (Exception e) {
            log.error("清理上传目录时发生错误: {}, 错误: {}", uploadIdDir.getAbsolutePath(), e.getMessage(), e);
        }
    }
} 