package com.lawfirm.model.log.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.log.entity.base.BaseLog;
import com.lawfirm.model.log.dto.LogExportDTO;

/**
 * 日志服务接口
 * 继承BaseService获取基础的CRUD功能
 *
 * @author weidi
 */
public interface LogService<T extends BaseLog> extends BaseService<T> {

    /**
     * 记录日志
     * 此方法会自动处理日志的创建时间、更新时间等基础字段
     *
     * @param log 日志实体
     * @return 日志ID
     */
    Long recordLog(T log);

    /**
     * 清理过期日志
     * 根据日志的创建时间清理指定天数之前的日志
     *
     * @param days 保留天数
     * @return 清理数量
     */
    Integer cleanExpiredLogs(Integer days);

    /**
     * 导出日志
     * 根据导出DTO中的条件导出日志数据
     *
     * @param exportDTO 导出条件
     * @return 导出文件路径
     */
    String exportLogs(LogExportDTO exportDTO);
} 