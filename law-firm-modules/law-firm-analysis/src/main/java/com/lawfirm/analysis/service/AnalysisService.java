package com.lawfirm.analysis.service;

import com.lawfirm.analysis.model.dto.AnalysisRequest;
import com.lawfirm.analysis.model.dto.AnalysisResponse;
import com.lawfirm.common.core.model.page.PageResult;

/**
 * 统计分析服务接口
 */
public interface AnalysisService {

    /**
     * 创建分析任务
     *
     * @param request 分析请求
     * @return 分析响应
     */
    AnalysisResponse createAnalysis(AnalysisRequest request);

    /**
     * 获取分析记录
     *
     * @param id 记录ID
     * @return 分析响应
     */
    AnalysisResponse getAnalysis(Long id);

    /**
     * 分页查询分析记录
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResult<AnalysisResponse> pageAnalysis(Integer pageNum, Integer pageSize);
} 