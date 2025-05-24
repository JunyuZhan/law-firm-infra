package com.lawfirm.model.analysis.service;

import com.lawfirm.model.analysis.dto.ContractAnalysisQueryDTO;
import com.lawfirm.model.analysis.vo.ContractAnalysisVO;
import com.lawfirm.model.analysis.vo.PageResultVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;

/**
 * 合同分析服务接口
 */
@Tag(name = "合同分析", description = "合同相关数据分析接口")
public interface IContractAnalysisService {
    @Operation(summary = "合同分析分页", description = "按条件分页统计合同分析结果")
    PageResultVO<ContractAnalysisVO> analyzeContracts(
        @Parameter(description = "合同分析查询参数") ContractAnalysisQueryDTO queryDTO
    );

    @Operation(summary = "合同TOP榜", description = "获取合同分析TOP榜")
    List<ContractAnalysisVO> topContracts(
        @Parameter(description = "合同分析查询参数") ContractAnalysisQueryDTO queryDTO
    );
} 