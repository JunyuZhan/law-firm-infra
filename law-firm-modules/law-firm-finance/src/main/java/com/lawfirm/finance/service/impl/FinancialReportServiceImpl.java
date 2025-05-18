package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lawfirm.common.util.excel.ExcelWriter;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.finance.entity.FinancialReport;
import com.lawfirm.model.finance.enums.ReportTypeEnum;
import com.lawfirm.model.finance.mapper.FinancialReportMapper;
import com.lawfirm.model.finance.service.FinancialReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lawfirm.finance.exception.FinanceException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
        LambdaQueryWrapper<FinancialReport> lambdaWrapper = new LambdaQueryWrapper<>();
        lambdaWrapper.eq(FinancialReport::getReportType, reportType.getValue())
               .eq(FinancialReport::getReportPeriod, period)
               .eq(FinancialReport::getDepartmentId, departmentId);
        
        // 转换为QueryWrapper
        QueryWrapper<FinancialReport> wrapper = new QueryWrapper<>();
        wrapper.eq("report_type", reportType.getValue())
                .eq("report_period", period)
                .eq("department_id", departmentId);
                
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
        statistics.put("totalAssets", report.getTotalAssets() != null ? report.getTotalAssets() : BigDecimal.ZERO);
        statistics.put("totalLiabilities", report.getTotalLiabilities() != null ? report.getTotalLiabilities() : BigDecimal.ZERO);
        statistics.put("totalEquity", report.getTotalEquity() != null ? report.getTotalEquity() : BigDecimal.ZERO);
        statistics.put("operatingIncome", report.getOperatingIncome() != null ? report.getOperatingIncome() : BigDecimal.ZERO);
        statistics.put("operatingCost", report.getOperatingCost() != null ? report.getOperatingCost() : BigDecimal.ZERO);
        statistics.put("operatingProfit", report.getOperatingProfit() != null ? report.getOperatingProfit() : BigDecimal.ZERO);
        statistics.put("netProfit", report.getNetProfit() != null ? report.getNetProfit() : BigDecimal.ZERO);
        statistics.put("operatingCashFlow", report.getOperatingCashFlow() != null ? report.getOperatingCashFlow() : BigDecimal.ZERO);
        statistics.put("investingCashFlow", report.getInvestingCashFlow() != null ? report.getInvestingCashFlow() : BigDecimal.ZERO);
        statistics.put("financingCashFlow", report.getFinancingCashFlow() != null ? report.getFinancingCashFlow() : BigDecimal.ZERO);
        return statistics;
    }

    @Override
    public Map<String, BigDecimal> getDepartmentReportStatistics(Long departmentId, LocalDateTime startTime,
                                                                LocalDateTime endTime) {
        log.info("获取部门报表统计数据: departmentId={}, startTime={}, endTime={}",
                departmentId, startTime, endTime);
        Map<String, BigDecimal> statistics = new HashMap<>();
        LambdaQueryWrapper<FinancialReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FinancialReport::getDepartmentId, departmentId)
               .ge(startTime != null, FinancialReport::getGenerateTime, startTime)
               .le(endTime != null, FinancialReport::getGenerateTime, endTime);
        List<FinancialReport> reports = list(wrapper);
        BigDecimal totalAssets = BigDecimal.ZERO;
        BigDecimal totalLiabilities = BigDecimal.ZERO;
        BigDecimal totalEquity = BigDecimal.ZERO;
        BigDecimal operatingIncome = BigDecimal.ZERO;
        BigDecimal operatingCost = BigDecimal.ZERO;
        BigDecimal operatingProfit = BigDecimal.ZERO;
        BigDecimal netProfit = BigDecimal.ZERO;
        BigDecimal operatingCashFlow = BigDecimal.ZERO;
        BigDecimal investingCashFlow = BigDecimal.ZERO;
        BigDecimal financingCashFlow = BigDecimal.ZERO;
        for (FinancialReport report : reports) {
            totalAssets = totalAssets.add(report.getTotalAssets() != null ? report.getTotalAssets() : BigDecimal.ZERO);
            totalLiabilities = totalLiabilities.add(report.getTotalLiabilities() != null ? report.getTotalLiabilities() : BigDecimal.ZERO);
            totalEquity = totalEquity.add(report.getTotalEquity() != null ? report.getTotalEquity() : BigDecimal.ZERO);
            operatingIncome = operatingIncome.add(report.getOperatingIncome() != null ? report.getOperatingIncome() : BigDecimal.ZERO);
            operatingCost = operatingCost.add(report.getOperatingCost() != null ? report.getOperatingCost() : BigDecimal.ZERO);
            operatingProfit = operatingProfit.add(report.getOperatingProfit() != null ? report.getOperatingProfit() : BigDecimal.ZERO);
            netProfit = netProfit.add(report.getNetProfit() != null ? report.getNetProfit() : BigDecimal.ZERO);
            operatingCashFlow = operatingCashFlow.add(report.getOperatingCashFlow() != null ? report.getOperatingCashFlow() : BigDecimal.ZERO);
            investingCashFlow = investingCashFlow.add(report.getInvestingCashFlow() != null ? report.getInvestingCashFlow() : BigDecimal.ZERO);
            financingCashFlow = financingCashFlow.add(report.getFinancingCashFlow() != null ? report.getFinancingCashFlow() : BigDecimal.ZERO);
        }
        statistics.put("totalAssets", totalAssets);
        statistics.put("totalLiabilities", totalLiabilities);
        statistics.put("totalEquity", totalEquity);
        statistics.put("operatingIncome", operatingIncome);
        statistics.put("operatingCost", operatingCost);
        statistics.put("operatingProfit", operatingProfit);
        statistics.put("netProfit", netProfit);
        statistics.put("operatingCashFlow", operatingCashFlow);
        statistics.put("investingCashFlow", investingCashFlow);
        statistics.put("financingCashFlow", financingCashFlow);
        return statistics;
    }

    @Override
    public Map<String, BigDecimal> getCostCenterReportStatistics(Long costCenterId, LocalDateTime startTime,
                                                                LocalDateTime endTime) {
        log.info("获取成本中心报表统计数据: costCenterId={}, startTime={}, endTime={}",
                costCenterId, startTime, endTime);
        Map<String, BigDecimal> statistics = new HashMap<>();
        LambdaQueryWrapper<FinancialReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FinancialReport::getCostCenterId, costCenterId)
               .ge(startTime != null, FinancialReport::getGenerateTime, startTime)
               .le(endTime != null, FinancialReport::getGenerateTime, endTime);
        List<FinancialReport> reports = list(wrapper);
        BigDecimal totalAssets = BigDecimal.ZERO;
        BigDecimal totalLiabilities = BigDecimal.ZERO;
        BigDecimal totalEquity = BigDecimal.ZERO;
        BigDecimal operatingIncome = BigDecimal.ZERO;
        BigDecimal operatingCost = BigDecimal.ZERO;
        BigDecimal operatingProfit = BigDecimal.ZERO;
        BigDecimal netProfit = BigDecimal.ZERO;
        BigDecimal operatingCashFlow = BigDecimal.ZERO;
        BigDecimal investingCashFlow = BigDecimal.ZERO;
        BigDecimal financingCashFlow = BigDecimal.ZERO;
        for (FinancialReport report : reports) {
            totalAssets = totalAssets.add(report.getTotalAssets() != null ? report.getTotalAssets() : BigDecimal.ZERO);
            totalLiabilities = totalLiabilities.add(report.getTotalLiabilities() != null ? report.getTotalLiabilities() : BigDecimal.ZERO);
            totalEquity = totalEquity.add(report.getTotalEquity() != null ? report.getTotalEquity() : BigDecimal.ZERO);
            operatingIncome = operatingIncome.add(report.getOperatingIncome() != null ? report.getOperatingIncome() : BigDecimal.ZERO);
            operatingCost = operatingCost.add(report.getOperatingCost() != null ? report.getOperatingCost() : BigDecimal.ZERO);
            operatingProfit = operatingProfit.add(report.getOperatingProfit() != null ? report.getOperatingProfit() : BigDecimal.ZERO);
            netProfit = netProfit.add(report.getNetProfit() != null ? report.getNetProfit() : BigDecimal.ZERO);
            operatingCashFlow = operatingCashFlow.add(report.getOperatingCashFlow() != null ? report.getOperatingCashFlow() : BigDecimal.ZERO);
            investingCashFlow = investingCashFlow.add(report.getInvestingCashFlow() != null ? report.getInvestingCashFlow() : BigDecimal.ZERO);
            financingCashFlow = financingCashFlow.add(report.getFinancingCashFlow() != null ? report.getFinancingCashFlow() : BigDecimal.ZERO);
        }
        statistics.put("totalAssets", totalAssets);
        statistics.put("totalLiabilities", totalLiabilities);
        statistics.put("totalEquity", totalEquity);
        statistics.put("operatingIncome", operatingIncome);
        statistics.put("operatingCost", operatingCost);
        statistics.put("operatingProfit", operatingProfit);
        statistics.put("netProfit", netProfit);
        statistics.put("operatingCashFlow", operatingCashFlow);
        statistics.put("investingCashFlow", investingCashFlow);
        statistics.put("financingCashFlow", financingCashFlow);
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

        // 准备Excel数据
        List<List<String>> excelData = new ArrayList<>();
        
        // 添加表头
        List<String> header = new ArrayList<>();
        header.add("报表ID");
        header.add("报表编号");
        header.add("报表名称");
        header.add("报表类型");
        header.add("报表期间");
        header.add("币种");
        header.add("总资产");
        header.add("总负债");
        header.add("所有者权益");
        header.add("营业收入");
        header.add("营业成本");
        header.add("营业利润");
        header.add("净利润");
        header.add("经营活动现金流量");
        header.add("投资活动现金流量");
        header.add("筹资活动现金流量");
        header.add("报表状态");
        header.add("生成时间");
        header.add("部门ID");
        header.add("成本中心ID");
        header.add("报表说明");
        excelData.add(header);
        
        // 添加数据行
        List<String> row = new ArrayList<>();
        row.add(String.valueOf(report.getId()));
        row.add(report.getReportNumber());
        row.add(report.getReportName());
        row.add(getReportTypeName(report.getReportType()));
        row.add(report.getReportPeriod());
        row.add(report.getCurrency() != null ? report.getCurrency().toString() : "");
        row.add(report.getTotalAssets() != null ? report.getTotalAssets().toString() : "0");
        row.add(report.getTotalLiabilities() != null ? report.getTotalLiabilities().toString() : "0");
        row.add(report.getTotalEquity() != null ? report.getTotalEquity().toString() : "0");
        row.add(report.getOperatingIncome() != null ? report.getOperatingIncome().toString() : "0");
        row.add(report.getOperatingCost() != null ? report.getOperatingCost().toString() : "0");
        row.add(report.getOperatingProfit() != null ? report.getOperatingProfit().toString() : "0");
        row.add(report.getNetProfit() != null ? report.getNetProfit().toString() : "0");
        row.add(report.getOperatingCashFlow() != null ? report.getOperatingCashFlow().toString() : "0");
        row.add(report.getInvestingCashFlow() != null ? report.getInvestingCashFlow().toString() : "0");
        row.add(report.getFinancingCashFlow() != null ? report.getFinancingCashFlow().toString() : "0");
        row.add(getReportStatusName(report.getReportStatus()));
        row.add(report.getGenerateTime() != null ? report.getGenerateTime().format(DATE_FORMATTER) : "");
        row.add(report.getDepartmentId() != null ? String.valueOf(report.getDepartmentId()) : "");
        row.add(report.getCostCenterId() != null ? String.valueOf(report.getCostCenterId()) : "");
        row.add(report.getDescription() != null ? report.getDescription() : "");
        excelData.add(row);
        
        // 生成临时文件名
        String fileName = "financial_report_" + report.getReportNumber() + "_" + 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx";
        String filePath = System.getProperty("java.io.tmpdir") + File.separator + fileName;
        
        try {
            // 将数据写入文件
            FileOutputStream outputStream = new FileOutputStream(filePath);
            ExcelWriter.write(excelData, outputStream);
            outputStream.close();
            
            // 返回文件路径
            return filePath;
        } catch (IOException e) {
            log.error("导出财务报表失败", e);
            return null;
        }
    }
    
    /**
     * 获取报表类型名称
     */
    private String getReportTypeName(Integer reportType) {
        if (reportType == null) {
            return "";
        }
        
        switch (reportType) {
            case 1: return "资产负债表";
            case 2: return "利润表";
            case 3: return "现金流量表";
            case 4: return "其他";
            default: return "未知类型";
        }
    }
    
    /**
     * 获取报表状态名称
     */
    private String getReportStatusName(Integer reportStatus) {
        if (reportStatus == null) {
            return "";
        }
        
        switch (reportStatus) {
            case 0: return "草稿";
            case 1: return "已提交";
            case 2: return "已审核";
            default: return "未知状态";
        }
    }
}