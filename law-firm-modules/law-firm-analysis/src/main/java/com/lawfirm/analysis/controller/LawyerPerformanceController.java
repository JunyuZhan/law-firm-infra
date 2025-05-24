package com.lawfirm.analysis.controller;

import com.lawfirm.model.analysis.dto.LawyerPerformanceRequestDTO;
import com.lawfirm.model.analysis.vo.LawyerPerformanceVO;
import com.lawfirm.model.analysis.service.ILawyerPerformanceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.lawfirm.analysis.constant.AnalysisBusinessConstants;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyRole('ADMIN','DIRECTOR','PARTNER')")
@Tag(name = "律师绩效分析", description = "律师绩效排行与分析接口")
@RestController
@RequestMapping(AnalysisBusinessConstants.Controller.API_PREFIX + "/lawyer-performance")
public class LawyerPerformanceController {
    @Autowired
    private ILawyerPerformanceService lawyerPerformanceService;

    @Operation(summary = "律师绩效排行分析")
    @PostMapping("/rank")
    public List<LawyerPerformanceVO> analyzePerformance(@RequestBody LawyerPerformanceRequestDTO request) {
        return lawyerPerformanceService.analyzePerformance(request);
    }
} 