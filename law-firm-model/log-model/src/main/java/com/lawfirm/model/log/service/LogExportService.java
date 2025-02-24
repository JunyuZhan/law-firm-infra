package com.lawfirm.model.log.service;

import com.lawfirm.model.log.dto.LogExportDTO;

/**
 * 日志导出服务接口
 * 提供日志导出相关功能
 */
public interface LogExportService {

    /**
     * 导出日志
     *
     * @param exportDTO 导出条件
     * @return 导出文件路径
     */
    String exportLogs(LogExportDTO exportDTO);

    /**
     * 异步导出日志
     *
     * @param exportDTO 导出条件
     * @return 任务ID
     */
    String asyncExportLogs(LogExportDTO exportDTO);

    /**
     * 获取导出进度
     *
     * @param taskId 任务ID
     * @return 导出进度(0-100)
     */
    Integer getExportProgress(String taskId);

    /**
     * 取消导出任务
     *
     * @param taskId 任务ID
     * @return 是否取消成功
     */
    Boolean cancelExport(String taskId);
} 