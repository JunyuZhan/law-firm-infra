package com.lawfirm.model.analysis.service;

import com.lawfirm.model.analysis.dto.LawyerPerformanceRequestDTO;
import com.lawfirm.model.analysis.vo.LawyerPerformanceVO;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "律师绩效分析服务接口")
public interface ILawyerPerformanceService {
    /**
     * 律师绩效排行分析
     * @param request 请求参数
     * @return 绩效结果列表
     */
    List<LawyerPerformanceVO> analyzePerformance(LawyerPerformanceRequestDTO request);
} 