package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.exception.BusinessException;
import com.lawfirm.model.system.entity.SysLog;
import com.lawfirm.system.mapper.SysLogMapper;
import com.lawfirm.system.service.SysLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统操作日志服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    private final SysLogMapper logMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createLog(SysLog log) {
        // 保存日志
        save(log);
    }

    @Override
    public List<SysLog> listByUserId(Long userId) {
        return logMapper.selectByUserId(userId);
    }

    @Override
    public List<SysLog> listByModule(String module) {
        return logMapper.selectByModule(module);
    }

    @Override
    public List<SysLog> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return logMapper.selectByTimeRange(startTime, endTime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cleanBefore(LocalDateTime time) {
        // 校验时间是否为空
        if (time == null) {
            throw new BusinessException("清理时间不能为空");
        }
        
        // 清理指定时间之前的日志
        logMapper.cleanBefore(time);
    }

    @Override
    public void exportLogs(LocalDateTime startTime, LocalDateTime endTime) {
        // TODO 导出日志功能待实现
    }
} 