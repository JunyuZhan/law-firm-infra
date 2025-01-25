package com.lawfirm.finance.service;

import com.lawfirm.finance.vo.FinancialReportVO;

import java.time.LocalDateTime;

/**
 * 财务报表服务接口
 */
public interface FinancialReportService {
    
    /**
     * 生成财务报表
     *
     * @param lawFirmId  律所ID
     * @param startTime  统计开始时间
     * @param endTime    统计结束时间
     * @return 财务报表
     */
    FinancialReportVO generateReport(Long lawFirmId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 生成月度报表
     *
     * @param lawFirmId  律所ID
     * @param year       年份
     * @param month      月份
     * @return 财务报表
     */
    FinancialReportVO generateMonthlyReport(Long lawFirmId, int year, int month);
    
    /**
     * 生成年度报表
     *
     * @param lawFirmId  律所ID
     * @param year       年份
     * @return 财务报表
     */
    FinancialReportVO generateAnnualReport(Long lawFirmId, int year);
    
    /**
     * 生成季度报表
     *
     * @param lawFirmId  律所ID
     * @param year       年份
     * @param quarter    季度(1-4)
     * @return 财务报表
     */
    FinancialReportVO generateQuarterlyReport(Long lawFirmId, int year, int quarter);
} 