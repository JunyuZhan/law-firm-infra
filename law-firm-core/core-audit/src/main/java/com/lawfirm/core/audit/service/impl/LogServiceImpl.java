package com.lawfirm.core.audit.service.impl;

import com.lawfirm.model.log.service.LogService;
import com.lawfirm.model.log.dto.BaseLogDTO;
import org.springframework.stereotype.Service;

/**
 * 日志服务实现，主要用于日志清理等后台任务，日志记录依然由AOP注解完成。
 */
@Service("logService")
public class LogServiceImpl implements LogService<BaseLogDTO> {
    @Override
    public Integer cleanExpiredLogs(Integer days) {
        // 实际项目可根据日志表实现清理，这里先返回0，保证项目可启动
        return 0;
    }

    @Override
    public Long recordLog(BaseLogDTO logDTO) {
        // AOP注解已处理日志记录，这里返回null
        return null;
    }

    @Override
    public String exportLogs(com.lawfirm.model.log.dto.LogExportDTO exportDTO) {
        // 暂不实现
        return null;
    }

    @Override
    public com.baomidou.mybatisplus.extension.plugins.pagination.Page<BaseLogDTO> page(com.baomidou.mybatisplus.extension.plugins.pagination.Page<BaseLogDTO> page, com.lawfirm.model.log.dto.LogQueryDTO queryDTO) {
        // 暂不实现
        return null;
    }

    @Override
    public BaseLogDTO getById(Long id) {
        // 暂不实现
        return null;
    }
} 