package com.lawfirm.analysis.service;

import com.lawfirm.analysis.constant.AnalysisTypeEnum;
import com.lawfirm.analysis.model.dto.AnalysisReportRequest;
import com.lawfirm.analysis.model.dto.AnalysisReportResponse;

/**
 * 统计分析报告服务接口
 */
public interface AnalysisReportService {

    /**
     * 生成分析报告
     *
     * @param request 报告请求
     * @return 报告响应
     */
    AnalysisReportResponse generateReport(AnalysisReportRequest request);

    /**
     * 获取最新报告
     *
     * @param type 分析类型
     * @return 报告响应
     */
    AnalysisReportResponse getLatestReport(AnalysisTypeEnum type);
} 
