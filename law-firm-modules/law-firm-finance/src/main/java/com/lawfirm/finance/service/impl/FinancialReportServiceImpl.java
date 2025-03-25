package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.finance.entity.FinancialReport;
import com.lawfirm.model.finance.enums.ReportTypeEnum;
import com.lawfirm.model.finance.mapper.FinancialReportMapper;
import com.lawfirm.model.finance.service.FinancialReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 财务报表服务实现类
 */
@Slf4j
@Service("financeReportServiceImpl")
public class FinancialReportServiceImpl extends BaseServiceImpl<FinancialReportMapper, FinancialReport>
        implements FinancialReportService {

    private static final DateTimeFormatter PERIOD_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createReport(FinancialReport report) {
        log.info("创建财务报表: report={}", report);
        report.setCreateTime(LocalDateTime.now());
        report.setUpdateTime(LocalDateTime.now());
        save(report);
        return report.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateReport(FinancialReport report) {
        log.info("更新财务报表: report={}", report);
        report.setUpdateTime(LocalDateTime.now());
        return update(report);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteReport(Long reportId) {
        log.info("删除财务报表: reportId={}", reportId);
        return remove(reportId);
    }

    @Override
    public FinancialReport getReportById(Long reportId) {
        log.info("获取财务报表: reportId={}", reportId);
        return getById(reportId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long generateReport(ReportTypeEnum reportType, LocalDateTime startTime, LocalDateTime endTime,
                              Long departmentId) {
        log.info("开始生成财务报表: reportType={}, startTime={}, endTime={}, departmentId={}",
                reportType, startTime, endTime, departmentId);

        // 检查是否已存在相同期间的报表
        String period = startTime.format(PERIOD_FORMATTER);
        LambdaQueryWrapper<FinancialReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FinancialReport::getReportType, reportType.getValue())
               .eq(FinancialReport::getReportPeriod, period)
               .eq(FinancialReport::getDepartmentId, departmentId);
        if (exists(wrapper)) {
            log.warn("已存在相同期间的报表: period={}, departmentId={}", period, departmentId);
            return null;
        }

        // 创建报表
        FinancialReport report = new FinancialReport();
        report.setReportType(reportType.getValue());
        report.setReportPeriod(period);
        report.setGenerateTime(LocalDateTime.now());
        report.setDepartmentId(departmentId);
        report.setReportStatus(0); // 草稿状态
        
        // 设置报表编号：类型-年月-序号
        String reportNumber = String.format("%d-%s-%04d", 
            reportType.getValue(), 
            period,
            System.currentTimeMillis() % 10000);
        report.setReportNumber(reportNumber);
        
        // 设置报表名称：类型名称-年月
        String reportName = String.format("%s-%s", 
            reportType.getDescription(), 
            period);
        report.setReportName(reportName);
        
        // 保存报表
        save(report);
        log.info("财务报表生成成功: reportId={}", report.getId());
        return report.getId();
    }

    @Override
    public List<FinancialReport> listReports(ReportTypeEnum reportType, Long departmentId,
                                            LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询财务报表列表: reportType={}, departmentId={}, startTime={}, endTime={}",
                reportType, departmentId, startTime, endTime);
        LambdaQueryWrapper<FinancialReport> wrapper = new LambdaQueryWrapper<>();
        if (reportType != null) {
            wrapper.eq(FinancialReport::getReportType, reportType.getValue());
        }
        wrapper.eq(departmentId != null, FinancialReport::getDepartmentId, departmentId)
               .ge(startTime != null, FinancialReport::getGenerateTime, startTime)
               .le(endTime != null, FinancialReport::getGenerateTime, endTime)
               .orderByDesc(FinancialReport::getGenerateTime);
        return list(wrapper);
    }

    @Override
    public List<FinancialReport> listReportsByDepartment(Long departmentId, LocalDateTime startTime,
                                                        LocalDateTime endTime) {
        return listReports(null, departmentId, startTime, endTime);
    }

    @Override
    public List<FinancialReport> listReportsByCostCenter(Long costCenterId, LocalDateTime startTime,
                                                        LocalDateTime endTime) {
        log.info("查询成本中心报表列表: costCenterId={}, startTime={}, endTime={}",
                costCenterId, startTime, endTime);
        LambdaQueryWrapper<FinancialReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FinancialReport::getCostCenterId, costCenterId)
               .ge(startTime != null, FinancialReport::getGenerateTime, startTime)
               .le(endTime != null, FinancialReport::getGenerateTime, endTime)
               .orderByDesc(FinancialReport::getGenerateTime);
        return list(wrapper);
    }

    @Override
    public Map<String, BigDecimal> getReportStatistics(Long reportId) {
        log.info("获取报表统计数据: reportId={}", reportId);
        FinancialReport report = getById(reportId);
        if (report == null) {
            log.warn("报表不存在: reportId={}", reportId);
            return null;
        }

        Map<String, BigDecimal> statistics = new HashMap<>();
        // TODO: 根据报表类型和期间计算统计数据
        return statistics;
    }

    @Override
    public Map<String, BigDecimal> getDepartmentReportStatistics(Long departmentId, LocalDateTime startTime,
                                                                LocalDateTime endTime) {
        log.info("获取部门报表统计数据: departmentId={}, startTime={}, endTime={}",
                departmentId, startTime, endTime);

        Map<String, BigDecimal> statistics = new HashMap<>();
        // TODO: 根据部门ID和时间范围计算统计数据
        return statistics;
    }

    @Override
    public Map<String, BigDecimal> getCostCenterReportStatistics(Long costCenterId, LocalDateTime startTime,
                                                                LocalDateTime endTime) {
        log.info("获取成本中心报表统计数据: costCenterId={}, startTime={}, endTime={}",
                costCenterId, startTime, endTime);

        Map<String, BigDecimal> statistics = new HashMap<>();
        // TODO: 根据成本中心ID和时间范围计算统计数据
        return statistics;
    }

    @Override
    public String exportReport(Long reportId) {
        log.info("导出报表: reportId={}", reportId);
        FinancialReport report = getById(reportId);
        if (report == null) {
            log.warn("报表不存在: reportId={}", reportId);
            return null;
        }

        // TODO: 实现报表导出功能
        return null;
    }
}