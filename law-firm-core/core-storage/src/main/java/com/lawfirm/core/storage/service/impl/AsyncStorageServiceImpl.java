package com.lawfirm.core.storage.service.impl;

import com.lawfirm.model.base.storage.model.FileMetadata;
import com.lawfirm.model.base.storage.model.FileTask;
import com.lawfirm.model.base.storage.service.AsyncStorageService;
import com.lawfirm.core.storage.strategy.StorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 异步存储服务实现类
 */
@Slf4j
@Service
public class AsyncStorageServiceImpl implements AsyncStorageService {

    private final StorageStrategy storageStrategy;
    private final ConcurrentHashMap<String, CompletableFuture<?>> tasks = new ConcurrentHashMap<>();

    public AsyncStorageServiceImpl(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    @Override
    public FileTask asyncUpload(MultipartFile file, String businessType, String businessId) {
        String taskId = generateTaskId();
        CompletableFuture<FileMetadata> future = CompletableFuture.supplyAsync(() -> {
            try {
                FileMetadata metadata = storageStrategy.uploadFile(file);
                metadata.setBusinessType(businessType);
                metadata.setBusinessId(businessId);
                return metadata;
            } catch (Exception e) {
                log.error("异步上传文件失败: {}", e.getMessage(), e);
                throw new RuntimeException("异步上传文件失败", e);
            }
        });
        tasks.put(taskId, future);
        
        return createFileTask(taskId, "UPLOAD", file.getOriginalFilename());
    }

    @Override
    public FileTask asyncDownload(String fileId) {
        String taskId = generateTaskId();
        CompletableFuture<byte[]> future = CompletableFuture.supplyAsync(() -> {
            try {
                return storageStrategy.downloadFile(fileId);
            } catch (Exception e) {
                log.error("异步下载文件失败: {}", e.getMessage(), e);
                throw new RuntimeException("异步下载文件失败", e);
            }
        });
        tasks.put(taskId, future);
        
        return createFileTask(taskId, "DOWNLOAD", fileId);
    }

    @Override
    public FileTask asyncProcess(String fileId, String processType, String params) {
        String taskId = generateTaskId();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                // TODO: 实现文件处理逻辑
                throw new UnsupportedOperationException("文件处理功能尚未实现");
            } catch (Exception e) {
                log.error("异步处理文件失败: {}", e.getMessage(), e);
                throw new RuntimeException("异步处理文件失败", e);
            }
        });
        tasks.put(taskId, future);
        
        return createFileTask(taskId, processType, fileId)
            .setParams(params);
    }

    @Override
    public FileTask getTaskInfo(String taskId) {
        CompletableFuture<?> future = tasks.get(taskId);
        FileTask task = createFileTask(taskId, "UNKNOWN", null);
        
        if (future != null) {
            if (future.isDone()) {
                task.setStatus(future.isCompletedExceptionally() ? "FAILED" : "SUCCESS");
                task.setProgress(100);
                if (future.isCompletedExceptionally()) {
                    try {
                        future.get(); // 获取异常信息
                    } catch (Exception e) {
                        task.setError(e.getMessage());
                    }
                }
            } else {
                task.setStatus("PROCESSING");
                task.setProgress(50); // 简化处理，实际应该根据具体进度计算
            }
        } else {
            task.setStatus("NOT_FOUND");
            task.setProgress(0);
        }
        
        return task;
    }

    @Override
    public List<FileTask> getFileTasks(String fileId) {
        List<FileTask> result = new ArrayList<>();
        tasks.forEach((taskId, future) -> {
            FileTask task = getTaskInfo(taskId);
            if (fileId.equals(task.getFileId())) {
                result.add(task);
            }
        });
        return result;
    }

    @Override
    public void cancelTask(String taskId) {
        CompletableFuture<?> future = tasks.remove(taskId);
        if (future != null && !future.isDone()) {
            future.cancel(true);
        }
    }

    private String generateTaskId() {
        return String.format("task_%d", System.currentTimeMillis());
    }
    
    private FileTask createFileTask(String taskId, String taskType, String fileId) {
        return new FileTask()
            .setId(taskId)
            .setFileId(fileId)
            .setTaskType(taskType)
            .setStatus("PENDING")
            .setProgress(0)
            .setCreateTime(LocalDateTime.now())
            .setUpdateTime(LocalDateTime.now());
    }
} 