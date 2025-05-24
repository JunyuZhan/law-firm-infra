package com.lawfirm.analysis.controller;

import com.lawfirm.model.analysis.service.IAnalysisTaskService;
import com.lawfirm.model.analysis.entity.AnalysisTask;
import com.lawfirm.analysis.constant.AnalysisBusinessConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;

@Tag(name = "分析任务管理", description = "分析任务相关接口")
@RestController("analysisTaskController")
@RequestMapping(AnalysisBusinessConstants.Controller.API_TASK_PREFIX)
public class AnalysisTaskController {

    private final IAnalysisTaskService analysisTaskService;

    public AnalysisTaskController(IAnalysisTaskService analysisTaskService) {
        this.analysisTaskService = analysisTaskService;
    }

    @Operation(summary = "查询分析任务列表")
    @GetMapping("/list")
    public List<AnalysisTask> list() {
        return analysisTaskService.list(new QueryWrapper<>());
    }

    @Operation(summary = "根据ID获取分析任务")
    @GetMapping("/{id}")
    public AnalysisTask getById(@Parameter(description = "任务ID") @PathVariable Long id) {
        return analysisTaskService.getById(id);
    }

    @Operation(summary = "新增分析任务")
    @PostMapping("/create")
    public boolean create(@RequestBody AnalysisTask task) {
        return analysisTaskService.save(task);
    }

    @Operation(summary = "更新分析任务")
    @PutMapping("/update")
    public boolean update(@RequestBody AnalysisTask task) {
        return analysisTaskService.update(task);
    }

    @Operation(summary = "删除分析任务")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@Parameter(description = "任务ID") @PathVariable Long id) {
        return analysisTaskService.remove(id);
    }
} 