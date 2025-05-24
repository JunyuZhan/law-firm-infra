package com.lawfirm.model.analysis.service;

import com.lawfirm.model.analysis.dto.AnalysisRequestDTO;
import com.lawfirm.model.analysis.vo.CaseAnalysisResultVO;
import java.util.List;

/**
 * 分析服务接口
 */
public interface IAnalysisService {
    List<CaseAnalysisResultVO> caseAnalysis(AnalysisRequestDTO requestDTO);
} 