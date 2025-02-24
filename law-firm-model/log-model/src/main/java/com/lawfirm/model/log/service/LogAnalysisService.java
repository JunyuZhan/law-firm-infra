package com.lawfirm.model.log.service;

import com.lawfirm.model.log.vo.LogStatVO;
import com.lawfirm.model.log.dto.LogQueryDTO;

/**
 * 日志分析服务接口
 * 提供日志统计分析相关功能
 */
public interface LogAnalysisService {

    /**
     * 获取日志统计信息
     *
     * @param queryDTO 查询条件
     * @return 日志统计信息
     */
    LogStatVO getLogStats(LogQueryDTO queryDTO);

    /**
     * 获取操作日志趋势
     *
     * @param days 统计天数
     * @return 日志统计信息
     */
    LogStatVO getLogTrend(Integer days);

    /**
     * 获取异常日志分析
     *
     * @param queryDTO 查询条件
     * @return 日志统计信息
     */
    LogStatVO getErrorLogAnalysis(LogQueryDTO queryDTO);

    /**
     * 获取用户操作分析
     *
     * @param queryDTO 查询条件
     * @return 日志统计信息
     */
    LogStatVO getUserOperationAnalysis(LogQueryDTO queryDTO);
} 