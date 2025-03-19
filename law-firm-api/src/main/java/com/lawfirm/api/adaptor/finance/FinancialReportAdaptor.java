package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.report.FinancialReportCreateDTO;
import com.lawfirm.model.finance.dto.report.FinancialReportUpdateDTO;
import com.lawfirm.model.finance.entity.FinancialReport;
import com.lawfirm.model.finance.service.FinancialReportService;
import com.lawfirm.model.finance.vo.report.FinancialReportVO;
import com.lawfirm.model.finance.enums.ReportTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 财务报表管理适配器
 */
@Component
public class FinancialReportAdaptor extends BaseAdaptor {

    @Autowired
    private FinancialReportService financialReportService;

    /**
     * 创建财务报表
     */
    public FinancialReportVO createFinancialReport(FinancialReportCreateDTO dto) {
        FinancialReport financialReport = financialReportService.createFinancialReport(dto);
        return convert(financialReport, FinancialReportVO.class);
    }

    /**
     * 更新财务报表
     */
    public FinancialReportVO updateFinancialReport(Long id, FinancialReportUpdateDTO dto) {
        FinancialReport financialReport = financialReportService.updateFinancialReport(id, dto);
        return convert(financialReport, FinancialReportVO.class);
    }

    /**
     * 获取财务报表详情
     */
    public FinancialReportVO getFinancialReport(Long id) {
        FinancialReport financialReport = financialReportService.getFinancialReport(id);
        return convert(financialReport, FinancialReportVO.class);
    }

    /**
     * 删除财务报表
     */
    public void deleteFinancialReport(Long id) {
        financialReportService.deleteFinancialReport(id);
    }

    /**
     * 获取所有财务报表
     */
    public List<FinancialReportVO> listFinancialReports() {
        List<FinancialReport> financialReports = financialReportService.listFinancialReports();
        return financialReports.stream()
                .map(financialReport -> convert(financialReport, FinancialReportVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据报表类型查询财务报表
     */
    public List<FinancialReportVO> getFinancialReportsByType(ReportTypeEnum type) {
        List<FinancialReport> financialReports = financialReportService.getFinancialReportsByType(type);
        return financialReports.stream()
                .map(financialReport -> convert(financialReport, FinancialReportVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID查询财务报表
     */
    public List<FinancialReportVO> getFinancialReportsByDepartmentId(Long departmentId) {
        List<FinancialReport> financialReports = financialReportService.getFinancialReportsByDepartmentId(departmentId);
        return financialReports.stream()
                .map(financialReport -> convert(financialReport, FinancialReportVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据时间范围查询财务报表
     */
    public List<FinancialReportVO> getFinancialReportsByDateRange(String startDate, String endDate) {
        List<FinancialReport> financialReports = financialReportService.getFinancialReportsByDateRange(startDate, endDate);
        return financialReports.stream()
                .map(financialReport -> convert(financialReport, FinancialReportVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 生成财务报表
     */
    public FinancialReportVO generateFinancialReport(ReportTypeEnum type, String startDate, String endDate) {
        FinancialReport financialReport = financialReportService.generateFinancialReport(type, startDate, endDate);
        return convert(financialReport, FinancialReportVO.class);
    }

    /**
     * 导出财务报表
     */
    public byte[] exportFinancialReport(Long id) {
        return financialReportService.exportFinancialReport(id);
    }

    /**
     * 检查财务报表是否存在
     */
    public boolean existsFinancialReport(Long id) {
        return financialReportService.existsFinancialReport(id);
    }

    /**
     * 获取财务报表数量
     */
    public long countFinancialReports() {
        return financialReportService.countFinancialReports();
    }
} 