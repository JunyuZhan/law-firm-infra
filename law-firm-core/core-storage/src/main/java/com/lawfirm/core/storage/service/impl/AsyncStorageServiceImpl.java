package com.lawfirm.core.storage.service.impl;

import com.lawfirm.model.base.storage.model.FileTask;
import com.lawfirm.model.base.storage.service.AsyncStorageService;
import com.lawfirm.model.base.storage.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 异步存储服务实现类
 */
@Slf4j
@Service
public class AsyncStorageServiceImpl implements AsyncStorageService {

    private final StorageService storageService;
    private final RabbitTemplate rabbitTemplate;
    private final ConcurrentHashMap<String, FileTask> taskMap = new ConcurrentHashMap<>();

    public AsyncStorageServiceImpl(StorageService storageService, RabbitTemplate rabbitTemplate) {
        this.storageService = storageService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public FileTask asyncUpload(MultipartFile file, String businessType, String businessId) {
        FileTask task = createTask("UPLOAD");
        task.setParams(String.format("{\"businessType\":\"%s\",\"businessId\":\"%s\"}", businessType, businessId));
        
        // 发送异步任务
        rabbitTemplate.convertAndSend("storage", "upload", task);
        
        return task;
    }

    @Override
    public FileTask asyncDownload(String fileId) {
        FileTask task = createTask("DOWNLOAD");
        task.setFileId(fileId);
        
        // 发送异步任务
        rabbitTemplate.convertAndSend("storage", "download", task);
        
        return task;
    }

    @Override
    public FileTask asyncProcess(String fileId, String processType, String params) {
        FileTask task = createTask("PROCESS");
        task.setFileId(fileId);
        task.setParams(params);
        
        // 发送异步任务
        rabbitTemplate.convertAndSend("storage", processType.toLowerCase(), task);
        
        return task;
    }

    @Override
    public FileTask getTaskInfo(String taskId) {
        return taskMap.get(taskId);
    }

    @Override
    public List<FileTask> getFileTasks(String fileId) {
        // TODO: 从数据库查询文件相关的任务
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void cancelTask(String taskId) {
        FileTask task = taskMap.get(taskId);
        if (task != null && "PENDING".equals(task.getStatus())) {
            task.setStatus("CANCELLED");
            // TODO: 取消RabbitMQ中的任务
        }
    }

    private FileTask createTask(String taskType) {
        FileTask task = new FileTask();
        task.setId(UUID.randomUUID().toString().replace("-", ""));
        task.setTaskType(taskType);
        task.setStatus("PENDING");
        task.setProgress(0);
        taskMap.put(task.getId(), task);
        return task;
    }
} 