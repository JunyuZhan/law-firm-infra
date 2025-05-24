package com.lawfirm.model.analysis.service;

import com.lawfirm.model.analysis.dto.ClientAnalysisRequestDTO;
import com.lawfirm.model.analysis.vo.ClientAnalysisVO;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 客户分析服务接口
 */
@Schema(description = "客户分析服务接口")
public interface IClientAnalysisService {
    /**
     * 客户分析
     * @param request 请求参数
     * @return 分析结果列表
     */
    List<ClientAnalysisVO> analyze(ClientAnalysisRequestDTO request);
} 