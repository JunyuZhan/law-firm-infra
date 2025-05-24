package com.lawfirm.analysis.controller;

import com.lawfirm.model.analysis.dto.TrendAnalysisRequestDTO;
import com.lawfirm.model.analysis.vo.TrendAnalysisVO;
import com.lawfirm.model.analysis.service.ITrendAnalysisService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.lawfirm.analysis.constant.AnalysisBusinessConstants;
import org.springframework.security.access.prepost.PreAuthorize;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;

@PreAuthorize("hasAuthority('" + ANALYSIS_VIEW + "')")
@Tag(name = "趋势分析", description = "趋势分析接口")
@RestController
@RequestMapping(AnalysisBusinessConstants.Controller.API_PREFIX + "/trend-analysis")
public class TrendAnalysisController {
    @Autowired
    private ITrendAnalysisService trendAnalysisService;

    @Operation(summary = "趋势分析")
    @PostMapping("/analyze")
    public List<TrendAnalysisVO> analyze(@RequestBody TrendAnalysisRequestDTO request) {
        return trendAnalysisService.analyze(request);
    }
} 