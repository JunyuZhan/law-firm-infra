package com.lawfirm.model.finance.service;

import com.lawfirm.model.finance.entity.FinancialReport;
import com.lawfirm.model.finance.enums.ReportTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 财务报表服务接口
 */
public interface FinancialReportService {
    
    /**
     * 创建财务报表
     *
     * @param report 报表信息
     * @return 报表ID
     */
    Long createReport(FinancialReport report);
    
    /**
     * 更新财务报表
     *
     * @param report 报表信息
     * @return 是否更新成功
     */
    boolean updateReport(FinancialReport report);
    
    /**
     * 删除财务报表
     *
     * @param reportId 报表ID
     * @return 是否删除成功
     */
    boolean deleteReport(Long reportId);
    
    /**
     * 获取财务报表详情
     *
     * @param reportId 报表ID
     * @return 报表信息
     */
    FinancialReport getReportById(Long reportId);
    
    /**
     * 生成财务报表
     *
     * @param reportType 报表类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param departmentId 部门ID，可为null
     * @return 报表ID
     */
    Long generateReport(ReportTypeEnum reportType, LocalDateTime startTime, LocalDateTime endTime,
                        Long departmentId);
    
    /**
     * 查询财务报表列表
     *
     * @param reportType 报表类型，可为null
     * @param departmentId 部门ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 报表列表
     */
    List<FinancialReport> listReports(ReportTypeEnum reportType, Long departmentId,
                                     LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按部门查询报表
     *
     * @param departmentId 部门ID
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 报表列表
     */
    List<FinancialReport> listReportsByDepartment(Long departmentId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按成本中心查询报表
     *
     * @param costCenterId 成本中心ID
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 报表列表
     */
    List<FinancialReport> listReportsByCostCenter(Long costCenterId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取报表统计数据
     *
     * @param reportId 报表ID
     * @return 统计数据
     */
    Map<String, BigDecimal> getReportStatistics(Long reportId);
    
    /**
     * 获取部门报表统计数据
     *
     * @param departmentId 部门ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计数据
     */
    Map<String, BigDecimal> getDepartmentReportStatistics(Long departmentId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取成本中心报表统计数据
     *
     * @param costCenterId 成本中心ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计数据
     */
    Map<String, BigDecimal> getCostCenterReportStatistics(Long costCenterId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 导出财务报表
     *
     * @param reportId 报表ID
     * @return 导出文件路径
     */
    String exportReport(Long reportId);
} 