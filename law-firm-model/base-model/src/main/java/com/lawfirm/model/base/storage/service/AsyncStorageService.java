package com.lawfirm.model.base.storage.service;

import com.lawfirm.model.base.storage.model.FileTask;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 异步存储服务接口
 */
public interface AsyncStorageService {
    
    /**
     * 异步上传文件
     *
     * @param file 文件
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 任务信息
     */
    FileTask asyncUpload(MultipartFile file, String businessType, String businessId);
    
    /**
     * 异步下载文件
     *
     * @param fileId 文件ID
     * @return 任务信息
     */
    FileTask asyncDownload(String fileId);
    
    /**
     * 异步处理文件
     *
     * @param fileId 文件ID
     * @param processType 处理类型
     * @param params 处理参数
     * @return 任务信息
     */
    FileTask asyncProcess(String fileId, String processType, String params);
    
    /**
     * 获取任务信息
     *
     * @param taskId 任务ID
     * @return 任务信息
     */
    FileTask getTaskInfo(String taskId);
    
    /**
     * 获取文件的所有任务
     *
     * @param fileId 文件ID
     * @return 任务列表
     */
    List<FileTask> getFileTasks(String fileId);
    
    /**
     * 取消任务
     *
     * @param taskId 任务ID
     */
    void cancelTask(String taskId);
} 