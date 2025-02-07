package com.lawfirm.system.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.common.log.domain.OperationLogDO;
import com.lawfirm.common.log.service.impl.OperationLogServiceImpl;
import com.lawfirm.system.mapper.SysLogMapper;
import com.lawfirm.system.service.SysLogService;

import lombok.RequiredArgsConstructor;

/**
 * 系统日志服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl extends OperationLogServiceImpl implements SysLogService {

    private final SysLogMapper logMapper;

    @Override
    public void createLog(OperationLogDO log) {
        save(log);
    }

    @Override
    public void updateLog(OperationLogDO log) {
        updateById(log);
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }

    @Override
    public List<OperationLogDO> listByUserId(Long userId) {
        return list(new LambdaQueryWrapper<OperationLogDO>()
                .eq(OperationLogDO::getOperatorId, userId));
    }

    @Override
    public List<OperationLogDO> listByModule(String module) {
        return list(new LambdaQueryWrapper<OperationLogDO>()
                .eq(OperationLogDO::getModule, module));
    }

    @Override
    public List<OperationLogDO> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return list(new LambdaQueryWrapper<OperationLogDO>()
                .between(OperationLogDO::getOperationTime, startTime, endTime));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cleanLogs(LocalDateTime before) {
        remove(new LambdaQueryWrapper<OperationLogDO>()
                .lt(OperationLogDO::getOperationTime, before));
    }

    @Override
    public void exportLogs(LocalDateTime startTime, LocalDateTime endTime) {
        // TODO 导出日志功能待实现
    }

    @Override
    public void clean() {
        // 默认清理30天前的日志
        cleanLogs(LocalDateTime.now().minusDays(30));
    }

    @Override
    public void export() {
        // 默认导出最近7天的日志
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(7);
        exportLogs(startTime, endTime);
    }
}