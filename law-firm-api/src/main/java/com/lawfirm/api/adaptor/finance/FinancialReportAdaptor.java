package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.entity.FinancialReport;
import com.lawfirm.model.finance.service.FinancialReportService;
import com.lawfirm.model.finance.vo.report.FinancialReportVO;
import com.lawfirm.model.finance.enums.ReportTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 财务报表管理适配器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FinancialReportAdaptor extends BaseAdaptor {

    private final FinancialReportService financialReportService;

    /**
     * 创建财务报表
     */
    public Long createFinancialReport(FinancialReport report) {
        log.info("创建财务报表: {}", report);
        return financialReportService.createReport(report);
    }

    /**
     * 更新财务报表
     */
    public boolean updateFinancialReport(FinancialReport report) {
        log.info("更新财务报表: {}", report);
        return financialReportService.updateReport(report);
    }

    /**
     * 删除财务报表
     */
    public boolean deleteFinancialReport(Long reportId) {
        log.info("删除财务报表: {}", reportId);
        return financialReportService.deleteReport(reportId);
    }

    /**
     * 获取财务报表详情
     */
    public FinancialReportVO getFinancialReport(Long reportId) {
        log.info("获取财务报表详情: {}", reportId);
        FinancialReport report = financialReportService.getReportById(reportId);
        return convert(report, FinancialReportVO.class);
    }

    /**
     * 生成财务报表
     */
    public Long generateFinancialReport(ReportTypeEnum reportType, LocalDateTime startTime, LocalDateTime endTime, Long departmentId) {
        log.info("生成财务报表: reportType={}, startTime={}, endTime={}, departmentId={}", reportType, startTime, endTime, departmentId);
        return financialReportService.generateReport(reportType, startTime, endTime, departmentId);
    }

    /**
     * 查询财务报表列表
     */
    public List<FinancialReportVO> listFinancialReports(ReportTypeEnum reportType, Long departmentId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询财务报表列表: reportType={}, departmentId={}, startTime={}, endTime={}", reportType, departmentId, startTime, endTime);
        List<FinancialReport> reports = financialReportService.listReports(reportType, departmentId, startTime, endTime);
        return reports.stream()
                .map(report -> convert(report, FinancialReportVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 按部门查询报表
     */
    public List<FinancialReportVO> listReportsByDepartment(Long departmentId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("按部门查询报表: departmentId={}, startTime={}, endTime={}", departmentId, startTime, endTime);
        List<FinancialReport> reports = financialReportService.listReportsByDepartment(departmentId, startTime, endTime);
        return reports.stream()
                .map(report -> convert(report, FinancialReportVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 按成本中心查询报表
     */
    public List<FinancialReportVO> listReportsByCostCenter(Long costCenterId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("按成本中心查询报表: costCenterId={}, startTime={}, endTime={}", costCenterId, startTime, endTime);
        List<FinancialReport> reports = financialReportService.listReportsByCostCenter(costCenterId, startTime, endTime);
        return reports.stream()
                .map(report -> convert(report, FinancialReportVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 获取报表统计数据
     */
    public Map<String, BigDecimal> getReportStatistics(Long reportId) {
        log.info("获取报表统计数据: {}", reportId);
        return financialReportService.getReportStatistics(reportId);
    }

    /**
     * 获取部门报表统计数据
     */
    public Map<String, BigDecimal> getDepartmentReportStatistics(Long departmentId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("获取部门报表统计数据: departmentId={}, startTime={}, endTime={}", departmentId, startTime, endTime);
        return financialReportService.getDepartmentReportStatistics(departmentId, startTime, endTime);
    }

    /**
     * 获取成本中心报表统计数据
     */
    public Map<String, BigDecimal> getCostCenterReportStatistics(Long costCenterId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("获取成本中心报表统计数据: costCenterId={}, startTime={}, endTime={}", costCenterId, startTime, endTime);
        return financialReportService.getCostCenterReportStatistics(costCenterId, startTime, endTime);
    }

    /**
     * 导出财务报表
     */
    public String exportFinancialReport(Long reportId) {
        log.info("导出财务报表: {}", reportId);
        return financialReportService.exportReport(reportId);
    }
} 