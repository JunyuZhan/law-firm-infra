package com.lawfirm.analysis.controller;

import com.lawfirm.model.analysis.dto.ClientAnalysisRequestDTO;
import com.lawfirm.model.analysis.vo.ClientAnalysisVO;
import com.lawfirm.model.analysis.service.IClientAnalysisService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.lawfirm.analysis.constant.AnalysisBusinessConstants;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyRole('ADMIN','DIRECTOR','PARTNER')")
@Tag(name = "客户分析", description = "客户数据分析接口")
@RestController
@RequestMapping(AnalysisBusinessConstants.Controller.API_PREFIX + "/client-analysis")
public class ClientAnalysisController {
    @Autowired
    private IClientAnalysisService clientAnalysisService;

    @Operation(summary = "客户分析")
    @PostMapping("/analyze")
    public List<ClientAnalysisVO> analyze(@RequestBody ClientAnalysisRequestDTO request) {
        return clientAnalysisService.analyze(request);
    }
} 