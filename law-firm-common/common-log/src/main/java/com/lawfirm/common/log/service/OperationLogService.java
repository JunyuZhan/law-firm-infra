package com.lawfirm.common.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.common.log.domain.OperationLogDO;

/**
 * 操作日志服务接口
 */
public interface OperationLogService extends IService<OperationLogDO> {

    /**
     * 保存操作日志
     */
    void saveOperationLog(OperationLogDO operationLog);

    /**
     * 查询操作日志详细信息
     */
    OperationLogDO getOperationLogById(Long id);

    /**
     * 删除操作日志
     */
    void deleteOperationLogById(Long id);

    /**
     * 批量删除操作日志
     */
    void deleteOperationLogByIds(Long[] ids);

    /**
     * 清空操作日志
     */
    void cleanOperationLog();
} 