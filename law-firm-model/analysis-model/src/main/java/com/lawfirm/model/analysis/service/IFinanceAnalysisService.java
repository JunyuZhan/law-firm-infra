package com.lawfirm.model.analysis.service;

import com.lawfirm.model.analysis.dto.FinanceAnalysisRequestDTO;
import com.lawfirm.model.analysis.vo.FinanceAnalysisVO;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 财务分析服务接口
 */
@Schema(description = "财务分析服务接口")
public interface IFinanceAnalysisService {
    /**
     * 财务分析
     * @param request 请求参数
     * @return 分析结果列表
     */
    List<FinanceAnalysisVO> analyze(FinanceAnalysisRequestDTO request);
} 