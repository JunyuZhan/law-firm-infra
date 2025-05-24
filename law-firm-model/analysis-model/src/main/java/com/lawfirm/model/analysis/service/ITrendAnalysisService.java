package com.lawfirm.model.analysis.service;

import com.lawfirm.model.analysis.dto.TrendAnalysisRequestDTO;
import com.lawfirm.model.analysis.vo.TrendAnalysisVO;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 趋势分析服务接口
 */
@Schema(description = "趋势分析服务接口")
public interface ITrendAnalysisService {
    /**
     * 趋势分析
     * @param request 请求参数
     * @return 分析结果列表
     */
    List<TrendAnalysisVO> analyze(TrendAnalysisRequestDTO request);
}