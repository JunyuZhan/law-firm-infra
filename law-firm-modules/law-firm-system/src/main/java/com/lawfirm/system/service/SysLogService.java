package com.lawfirm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.common.log.enums.BusinessType;
import com.lawfirm.model.system.entity.SysLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统操作日志服务接口
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 创建操作日志
     */
    void createLog(SysLog log);

    /**
     * 根据用户ID查询操作日志列表
     */
    List<SysLog> listByUserId(Long userId);

    /**
     * 根据模块查询操作日志列表
     */
    List<SysLog> listByModule(String module);

    /**
     * 根据时间范围查询操作日志列表
     */
    List<SysLog> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 清理指定时间之前的操作日志
     */
    @Log(module = "日志管理", businessType = BusinessType.CLEAN, description = "清理操作日志")
    void cleanBefore(LocalDateTime time);

    /**
     * 导出操作日志
     */
    @Log(module = "日志管理", businessType = BusinessType.EXPORT, description = "导出操作日志")
    void exportLog(List<Long> ids);
} 