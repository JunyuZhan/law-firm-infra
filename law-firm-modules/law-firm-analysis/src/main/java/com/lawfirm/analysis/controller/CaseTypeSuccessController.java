package com.lawfirm.analysis.controller;

import com.lawfirm.model.analysis.dto.CaseTypeSuccessRequestDTO;
import com.lawfirm.model.analysis.vo.CaseTypeSuccessVO;
import com.lawfirm.model.analysis.service.ICaseTypeSuccessService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.lawfirm.analysis.constant.AnalysisBusinessConstants;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyRole('ADMIN','DIRECTOR','PARTNER')")
@Tag(name = "案件类型与成功率分析", description = "案件类型与成功率分析接口")
@RestController
@RequestMapping(AnalysisBusinessConstants.Controller.API_PREFIX + "/case-type-success")
public class CaseTypeSuccessController {
    @Autowired
    private ICaseTypeSuccessService caseTypeSuccessService;

    @Operation(summary = "案件类型与成功率分析")
    @PostMapping("/analyze")
    public List<CaseTypeSuccessVO> analyze(@RequestBody CaseTypeSuccessRequestDTO request) {
        return caseTypeSuccessService.analyze(request);
    }
} 