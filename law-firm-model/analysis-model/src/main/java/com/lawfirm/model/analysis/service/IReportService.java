package com.lawfirm.model.analysis.service;

import com.lawfirm.model.analysis.dto.ReportExportDTO;
import com.lawfirm.model.analysis.vo.ReportVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;

/**
 * 报表服务接口
 */
@Tag(name = "分析报表", description = "分析报表相关接口")
public interface IReportService {
    @Operation(summary = "查询报表", description = "按类型和周期查询分析报表")
    List<ReportVO> queryReports(
        @Parameter(description = "报表类型") String reportType,
        @Parameter(description = "统计周期") String period
    );

    @Operation(summary = "导出报表", description = "导出分析报表数据")
    byte[] exportReport(
        @Parameter(description = "报表导出参数") ReportExportDTO exportDTO
    );
} 