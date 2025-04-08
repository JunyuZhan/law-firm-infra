package com.lawfirm.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.finance.constant.FinanceConstants;
import com.lawfirm.model.finance.entity.CostCenter;
import com.lawfirm.model.finance.service.CostCenterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 成本中心管理控制器
 */
@Slf4j
@RestController("costCenterController")
@RequestMapping(FinanceConstants.API_COST_CENTER_PREFIX)
@RequiredArgsConstructor
@Tag(name = "成本中心管理", description = "成本中心管理接口")
public class CostCenterController {

    private final CostCenterService costCenterService;

    @PostMapping
    @Operation(summary = "创建成本中心")
    public CommonResult<Long> createCostCenter(@Valid @RequestBody CostCenter costCenter) {
        return CommonResult.success(costCenterService.createCostCenter(costCenter));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新成本中心")
    public CommonResult<Boolean> updateCostCenter(@PathVariable("id") Long id, 
                                                  @Valid @RequestBody CostCenter costCenter) {
        costCenter.setId(id);
        return CommonResult.success(costCenterService.updateCostCenter(costCenter));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除成本中心")
    public CommonResult<Boolean> deleteCostCenter(@PathVariable("id") Long id) {
        return CommonResult.success(costCenterService.deleteCostCenter(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取成本中心详情")
    public CommonResult<CostCenter> getCostCenter(@PathVariable("id") Long id) {
        return CommonResult.success(costCenterService.getCostCenterById(id));
    }

    @GetMapping("/list")
    @Operation(summary = "查询成本中心列表")
    public CommonResult<List<CostCenter>> listCostCenters(
            @Parameter(description = "部门ID") @RequestParam(required = false) Long departmentId) {
        return CommonResult.success(costCenterService.listCostCenters(departmentId));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询成本中心")
    public CommonResult<IPage<CostCenter>> pageCostCenters(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") long current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") long size,
            @Parameter(description = "部门ID") @RequestParam(required = false) Long departmentId) {
        Page<CostCenter> page = new Page<>(current, size);
        return CommonResult.success(costCenterService.pageCostCenters(page, departmentId));
    }

    @GetMapping("/department/{departmentId}")
    @Operation(summary = "按部门查询成本中心")
    public CommonResult<List<CostCenter>> listCostCentersByDepartment(
            @PathVariable("departmentId") Long departmentId) {
        return CommonResult.success(costCenterService.listCostCentersByDepartment(departmentId));
    }

    @PostMapping("/export")
    @Operation(summary = "导出成本中心数据")
    public CommonResult<String> exportCostCenters(
            @Parameter(description = "成本中心ID列表") @RequestBody List<Long> costCenterIds) {
        return CommonResult.success(costCenterService.exportCostCenters(costCenterIds));
    }
} 
