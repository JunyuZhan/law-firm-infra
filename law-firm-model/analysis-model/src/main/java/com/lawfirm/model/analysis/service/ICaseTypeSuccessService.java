package com.lawfirm.model.analysis.service;

import com.lawfirm.model.analysis.dto.CaseTypeSuccessRequestDTO;
import com.lawfirm.model.analysis.vo.CaseTypeSuccessVO;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "案件类型与成功率分析服务接口")
public interface ICaseTypeSuccessService {
    /**
     * 案件类型与成功率分析
     * @param request 请求参数
     * @return 分析结果列表
     */
    List<CaseTypeSuccessVO> analyze(CaseTypeSuccessRequestDTO request);
} 