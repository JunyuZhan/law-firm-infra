package com.lawfirm.system.service;

import java.time.LocalDateTime;
import java.util.List;

import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.common.log.service.OperationLogService;
import com.lawfirm.common.log.domain.OperationLogDO;

/**
 * 系统日志服务接口
 */
public interface SysLogService extends OperationLogService {
    
    /**
     * 创建日志
     */
    @OperationLog(description = "创建日志", operationType = "CREATE")
    void createLog(OperationLogDO log);
    
    /**
     * 更新日志
     */
    @OperationLog(description = "更新日志", operationType = "UPDATE")
    void updateLog(OperationLogDO log);
    
    /**
     * 删除日志
     */
    @OperationLog(description = "删除日志", operationType = "DELETE")
    void delete(Long id);
    
    /**
     * 清空日志
     */
    @OperationLog(description = "清空日志", operationType = "DELETE")
    void clean();
    
    /**
     * 导出日志
     */
    @OperationLog(description = "导出日志", operationType = "EXPORT")
    void export();

    /**
     * 根据用户ID查询操作日志
     */
    List<OperationLogDO> listByUserId(Long userId);

    /**
     * 根据模块查询操作日志
     */
    List<OperationLogDO> listByModule(String module);

    /**
     * 根据时间范围查询操作日志
     */
    List<OperationLogDO> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 清理指定时间之前的日志
     */
    @OperationLog(description = "清理日志", operationType = "DELETE")
    void cleanLogs(LocalDateTime before);

    /**
     * 导出操作日志
     */
    @OperationLog(description = "导出日志", operationType = "EXPORT")
    void exportLogs(LocalDateTime startTime, LocalDateTime endTime);
} 