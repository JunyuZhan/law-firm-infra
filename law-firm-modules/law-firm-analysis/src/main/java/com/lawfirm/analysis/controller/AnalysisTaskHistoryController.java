package com.lawfirm.analysis.controller;

import com.lawfirm.analysis.service.impl.AnalysisTaskHistoryServiceImpl;
import com.lawfirm.model.analysis.entity.AnalysisTaskHistory;
import com.lawfirm.analysis.constant.AnalysisBusinessConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Tag(name = "分析任务历史管理", description = "分析任务历史相关接口")
@RestController("analysisTaskHistoryController")
@RequestMapping(AnalysisBusinessConstants.Controller.API_TASK_HISTORY_PREFIX)
public class AnalysisTaskHistoryController {

    private final AnalysisTaskHistoryServiceImpl analysisTaskHistoryService;

    public AnalysisTaskHistoryController(AnalysisTaskHistoryServiceImpl analysisTaskHistoryService) {
        this.analysisTaskHistoryService = analysisTaskHistoryService;
    }

    @Operation(summary = "查询分析任务历史列表")
    @GetMapping("/list")
    public List<AnalysisTaskHistory> list() {
        return analysisTaskHistoryService.list(new QueryWrapper<>());
    }

    @Operation(summary = "根据ID获取分析任务历史")
    @GetMapping("/{id}")
    public AnalysisTaskHistory getById(@Parameter(description = "历史ID") @PathVariable Long id) {
        return analysisTaskHistoryService.getById(id);
    }

    @Operation(summary = "新增分析任务历史")
    @PostMapping("/create")
    public boolean create(@RequestBody AnalysisTaskHistory history) {
        return analysisTaskHistoryService.save(history);
    }

    @Operation(summary = "删除分析任务历史")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@Parameter(description = "历史ID") @PathVariable Long id) {
        return analysisTaskHistoryService.remove(id);
    }
} 