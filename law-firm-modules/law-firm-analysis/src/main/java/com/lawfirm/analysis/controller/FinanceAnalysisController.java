package com.lawfirm.analysis.controller;

import com.lawfirm.model.analysis.dto.FinanceAnalysisRequestDTO;
import com.lawfirm.model.analysis.vo.FinanceAnalysisVO;
import com.lawfirm.model.analysis.service.IFinanceAnalysisService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.lawfirm.analysis.constant.AnalysisBusinessConstants;
import org.springframework.security.access.prepost.PreAuthorize;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;

@PreAuthorize("hasAuthority('" + ANALYSIS_VIEW + "')")
@Tag(name = "财务分析", description = "财务数据分析接口")
@RestController
@RequestMapping(AnalysisBusinessConstants.Controller.API_PREFIX + "/finance-analysis")
public class FinanceAnalysisController {
    @Autowired
    private IFinanceAnalysisService financeAnalysisService;

    @Operation(summary = "财务分析")
    @PostMapping("/analyze")
    public List<FinanceAnalysisVO> analyze(@RequestBody FinanceAnalysisRequestDTO request) {
        return financeAnalysisService.analyze(request);
    }
} 