package com.lawfirm.model.log.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.log.dto.BaseLogDTO;
import com.lawfirm.model.log.dto.LogExportDTO;
import com.lawfirm.model.log.dto.LogQueryDTO;

/**
 * 日志服务接口
 *
 * @author JunyuZhan
 */
public interface LogService<T extends BaseLogDTO> {

    /**
     * 记录日志
     * 此方法会自动处理日志的创建时间、更新时间等基础字段
     *
     * @param logDTO 日志DTO
     * @return 日志ID
     */
    Long recordLog(T logDTO);

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

    /**
     * 分页查询日志
     *
     * @param page 分页参数
     * @param queryDTO 查询条件
     * @return 日志分页数据
     */
    Page<T> page(Page<T> page, LogQueryDTO queryDTO);

    /**
     * 根据ID获取日志
     *
     * @param id 日志ID
     * @return 日志详情
     */
    T getById(Long id);
} 
