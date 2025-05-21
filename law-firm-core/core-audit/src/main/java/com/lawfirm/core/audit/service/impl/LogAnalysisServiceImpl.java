package com.lawfirm.core.audit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lawfirm.model.log.entity.audit.AuditLog;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.model.log.enums.OperateTypeEnum;
import com.lawfirm.model.log.mapper.AuditLogMapper;
import com.lawfirm.model.log.service.LogAnalysisService;
import com.lawfirm.model.log.vo.LogStatVO;
import com.lawfirm.model.log.dto.LogQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 日志分析服务实现类
 */
@Service("logAnalysisService")
public class LogAnalysisServiceImpl implements LogAnalysisService {

    @Autowired
    private AuditLogMapper auditLogMapper;

    @Override
    public LogStatVO getLogStats(LogQueryDTO queryDTO) {
        LogStatVO statVO = new LogStatVO();
        
        // 构建查询条件
        LambdaQueryWrapper<AuditLog> queryWrapper = buildQueryWrapper(queryDTO);
        
        // 获取总日志数
        statVO.setTotalCount(auditLogMapper.selectCount(queryWrapper));
        
        // 获取今日日志数
        LambdaQueryWrapper<AuditLog> todayWrapper = Wrappers.<AuditLog>lambdaQuery()
                .ge(AuditLog::getOperationTime, LocalDateTime.now().withHour(0).withMinute(0).withSecond(0));
        statVO.setTodayCount(auditLogMapper.selectCount(todayWrapper));
        
        // 获取异常日志数
        LambdaQueryWrapper<AuditLog> errorWrapper = Wrappers.<AuditLog>lambdaQuery()
                .eq(AuditLog::getStatus, 1);
        statVO.setErrorCount(auditLogMapper.selectCount(errorWrapper));
        
        // 按各维度统计
        statVO.setOperateTypeStats(getOperateTypeStats(queryWrapper));
        statVO.setBusinessTypeStats(getBusinessTypeStats(queryWrapper));
        statVO.setModuleStats(getModuleStats(queryWrapper));
        statVO.setOperatorStats(getOperatorStats(queryWrapper));
        statVO.setIpStats(getIpStats(queryWrapper));
        
        return statVO;
    }

    @Override
    public LogStatVO getLogTrend(Integer days) {
        LogStatVO statVO = new LogStatVO();
        
        // 计算时间范围
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(days);
        
        // 构建每日统计数据列表
        List<LogStatVO.DailyStats> dailyStatsList = new ArrayList<>();
        
        // 查询每天的数据
        for (int i = 0; i < days; i++) {
            LocalDateTime dayStart = startTime.plusDays(i).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime dayEnd = dayStart.plusDays(1);
            
            // 查询条件
            LambdaQueryWrapper<AuditLog> queryWrapper = Wrappers.<AuditLog>lambdaQuery()
                    .ge(AuditLog::getOperationTime, dayStart)
                    .lt(AuditLog::getOperationTime, dayEnd);
            
            // 创建日统计
            LogStatVO.DailyStats dailyStats = new LogStatVO.DailyStats()
                    .setDate(dayStart.format(DateTimeFormatter.ISO_LOCAL_DATE));
            
            // 查询总数
            Long total = auditLogMapper.selectCount(queryWrapper);
            dailyStats.setTotal(total);
            
            // 查询成功数
            Long success = auditLogMapper.selectCount(queryWrapper.clone().eq(AuditLog::getStatus, 0));
            dailyStats.setSuccess(success);
            
            // 查询失败数
            Long fail = auditLogMapper.selectCount(queryWrapper.clone().eq(AuditLog::getStatus, 1));
            dailyStats.setFail(fail);
            
            // 计算平均响应时间（如果有相关字段）
            // 假设AuditLog中有responseTime字段，这里仅为示例
            // 实际实现需要根据实际字段调整
            // 如果没有相关字段，可以暂时设置为null或默认值
            dailyStats.setAvgTime(null);
            
            dailyStatsList.add(dailyStats);
        }
        
        statVO.setDailyStats(dailyStatsList);
        return statVO;
    }

    @Override
    public LogStatVO getErrorLogAnalysis(LogQueryDTO queryDTO) {
        LogStatVO statVO = new LogStatVO();
        
        // 构建查询条件，限定为异常日志
        LambdaQueryWrapper<AuditLog> queryWrapper = buildQueryWrapper(queryDTO)
                .eq(AuditLog::getStatus, 1);
        
        // 获取异常日志数量
        statVO.setErrorCount(auditLogMapper.selectCount(queryWrapper));
        
        // 按模块统计异常日志
        statVO.setModuleStats(getModuleStats(queryWrapper));
        
        // 按操作人统计异常日志
        statVO.setOperatorStats(getOperatorStats(queryWrapper));
        
        // 按IP地址统计异常日志
        statVO.setIpStats(getIpStats(queryWrapper));
        
        // 按业务类型统计
        statVO.setBusinessTypeStats(getBusinessTypeStats(queryWrapper));
        
        return statVO;
    }

    @Override
    public LogStatVO getUserOperationAnalysis(LogQueryDTO queryDTO) {
        LogStatVO statVO = new LogStatVO();
        
        // 构建查询条件
        LambdaQueryWrapper<AuditLog> queryWrapper = buildQueryWrapper(queryDTO);
        
        // 按用户统计操作数量
        statVO.setOperatorStats(getOperatorStats(queryWrapper));
        
        // 按操作类型统计
        statVO.setOperateTypeStats(getOperateTypeStats(queryWrapper));
        
        // 按业务类型统计
        statVO.setBusinessTypeStats(getBusinessTypeStats(queryWrapper));
        
        // 按模块统计
        statVO.setModuleStats(getModuleStats(queryWrapper));
        
        return statVO;
    }
    
    /**
     * 根据查询DTO构建查询条件
     */
    private LambdaQueryWrapper<AuditLog> buildQueryWrapper(LogQueryDTO queryDTO) {
        LambdaQueryWrapper<AuditLog> queryWrapper = Wrappers.lambdaQuery();
        
        // 添加查询条件
        if (queryDTO != null) {
            if (queryDTO.getOperatorId() != null) {
                queryWrapper.eq(AuditLog::getOperatorId, queryDTO.getOperatorId());
            }
            if (StringUtils.isNotBlank(queryDTO.getOperatorName())) {
                queryWrapper.like(AuditLog::getOperatorName, queryDTO.getOperatorName());
            }
            if (queryDTO.getOperateType() != null) {
                queryWrapper.eq(AuditLog::getOperateType, queryDTO.getOperateType());
            }
            if (queryDTO.getBusinessType() != null) {
                queryWrapper.eq(AuditLog::getBusinessType, queryDTO.getBusinessType());
            }
            if (StringUtils.isNotBlank(queryDTO.getModule())) {
                queryWrapper.eq(AuditLog::getModule, queryDTO.getModule());
            }
            if (queryDTO.getStatus() != null) {
                queryWrapper.eq(AuditLog::getStatus, queryDTO.getStatus());
            }
            if (StringUtils.isNotBlank(queryDTO.getIpAddress())) {
                queryWrapper.eq(AuditLog::getOperatorIp, queryDTO.getIpAddress());
            }
            // 根据时间范围查询
            if (queryDTO.getStartTime() != null) {
                queryWrapper.ge(AuditLog::getOperationTime, queryDTO.getStartTime());
            }
            if (queryDTO.getEndTime() != null) {
                queryWrapper.le(AuditLog::getOperationTime, queryDTO.getEndTime());
            }
        }
        
        return queryWrapper;
    }

    /**
     * 按操作类型统计
     */
    private Map<String, Long> getOperateTypeStats(LambdaQueryWrapper<AuditLog> baseWrapper) {
        Map<String, Long> result = new HashMap<>();
        
        // 获取所有操作类型枚举值
        for (OperateTypeEnum type : OperateTypeEnum.values()) {
            LambdaQueryWrapper<AuditLog> wrapper = baseWrapper.clone()
                    .eq(AuditLog::getOperateType, type);
            Long count = auditLogMapper.selectCount(wrapper);
            result.put(type.name(), count);
        }
        
        return result;
    }
    
    /**
     * 按业务类型统计
     */
    private Map<String, Long> getBusinessTypeStats(LambdaQueryWrapper<AuditLog> baseWrapper) {
        Map<String, Long> result = new HashMap<>();
        
        // 获取所有业务类型枚举值
        for (BusinessTypeEnum type : BusinessTypeEnum.values()) {
            LambdaQueryWrapper<AuditLog> wrapper = baseWrapper.clone()
                    .eq(AuditLog::getBusinessType, type);
            Long count = auditLogMapper.selectCount(wrapper);
            result.put(type.name(), count);
        }
        
        return result;
    }
    
    /**
     * 按模块统计
     */
    private Map<String, Long> getModuleStats(LambdaQueryWrapper<AuditLog> baseWrapper) {
        // 查询所有模块及其计数
        List<AuditLog> logs = auditLogMapper.selectList(baseWrapper);
        
        // 使用Java Stream API分组统计
        Map<String, Long> result = logs.stream()
                .filter(log -> log.getModule() != null)
                .collect(Collectors.groupingBy(
                        AuditLog::getModule,
                        Collectors.counting()
                ));
        
        return result;
    }
    
    /**
     * 按操作人统计
     */
    private Map<String, Long> getOperatorStats(LambdaQueryWrapper<AuditLog> baseWrapper) {
        // 查询所有日志
        List<AuditLog> logs = auditLogMapper.selectList(baseWrapper);
        
        // 使用Java Stream API分组统计
        Map<String, Long> result = logs.stream()
                .filter(log -> log.getOperatorName() != null)
                .collect(Collectors.groupingBy(
                        AuditLog::getOperatorName,
                        Collectors.counting()
                ));
        
        return result;
    }
    
    /**
     * 按IP地址统计
     */
    private Map<String, Long> getIpStats(LambdaQueryWrapper<AuditLog> baseWrapper) {
        // 查询所有日志
        List<AuditLog> logs = auditLogMapper.selectList(baseWrapper);
        
        // 使用Java Stream API分组统计
        Map<String, Long> result = logs.stream()
                .filter(log -> log.getOperatorIp() != null)
                .collect(Collectors.groupingBy(
                        AuditLog::getOperatorIp,
                        Collectors.counting()
                ));
        
        return result;
    }
} 