package com.lawfirm.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.finance.entity.Fee;
import com.lawfirm.model.finance.enums.FeeStatusEnum;
import com.lawfirm.model.finance.enums.FeeTypeEnum;
import com.lawfirm.model.finance.service.FeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 费用管理控制器
 */
@Slf4j
@Tag(name = "费用管理", description = "费用管理接口")
@RestController("financeFeeController")
@RequestMapping("/finance/fee")
@RequiredArgsConstructor
public class FeeController {

    private final FeeService feeService;

    @PostMapping
    @Operation(summary = "创建费用")
    public CommonResult<Long> createFee(@Valid @RequestBody Fee fee) {
        return CommonResult.success(feeService.createFee(fee));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新费用")
    public CommonResult<Boolean> updateFee(@PathVariable("id") Long id, 
                                           @Valid @RequestBody Fee fee) {
        fee.setId(id);
        return CommonResult.success(feeService.updateFee(fee));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除费用")
    public CommonResult<Boolean> deleteFee(@PathVariable("id") Long id) {
        return CommonResult.success(feeService.deleteFee(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取费用详情")
    public CommonResult<Fee> getFee(@PathVariable("id") Long id) {
        return CommonResult.success(feeService.getFeeById(id));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "更新费用状态")
    public CommonResult<Boolean> updateFeeStatus(
            @PathVariable("id") Long id,
            @Parameter(description = "费用状态") @RequestParam FeeStatusEnum status,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        return CommonResult.success(feeService.updateFeeStatus(id, status, remark));
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "审批费用")
    public CommonResult<Boolean> approveFee(
            @PathVariable("id") Long id,
            @Parameter(description = "是否通过") @RequestParam boolean approved,
            @Parameter(description = "审批人ID") @RequestParam Long approverId,
            @Parameter(description = "审批意见") @RequestParam(required = false) String remark) {
        return CommonResult.success(feeService.approveFee(id, approved, approverId, remark));
    }

    @PutMapping("/{id}/pay")
    @Operation(summary = "支付费用")
    public CommonResult<Boolean> payFee(
            @PathVariable("id") Long id,
            @Parameter(description = "支付账户ID") @RequestParam Long accountId,
            @Parameter(description = "支付时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime paymentTime,
            @Parameter(description = "支付备注") @RequestParam(required = false) String remark) {
        return CommonResult.success(feeService.payFee(id, accountId, paymentTime, remark));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "取消费用")
    public CommonResult<Boolean> cancelFee(
            @PathVariable("id") Long id,
            @Parameter(description = "取消原因") @RequestParam String reason) {
        return CommonResult.success(feeService.cancelFee(id, reason));
    }

    @GetMapping("/list")
    @Operation(summary = "查询费用列表")
    public CommonResult<List<Fee>> listFees(
            @Parameter(description = "费用类型") @RequestParam(required = false) FeeTypeEnum feeType,
            @Parameter(description = "费用状态") @RequestParam(required = false) FeeStatusEnum status,
            @Parameter(description = "部门ID") @RequestParam(required = false) Long departmentId,
            @Parameter(description = "成本中心ID") @RequestParam(required = false) Long costCenterId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(feeService.listFees(feeType, status, departmentId, costCenterId, startTime, endTime));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询费用")
    public CommonResult<IPage<Fee>> pageFees(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") long current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") long size,
            @Parameter(description = "费用类型") @RequestParam(required = false) FeeTypeEnum feeType,
            @Parameter(description = "费用状态") @RequestParam(required = false) FeeStatusEnum status,
            @Parameter(description = "部门ID") @RequestParam(required = false) Long departmentId,
            @Parameter(description = "成本中心ID") @RequestParam(required = false) Long costCenterId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        Page<Fee> page = new Page<>(current, size);
        return CommonResult.success(feeService.pageFees(page, feeType, status, departmentId, costCenterId, startTime, endTime));
    }

    @GetMapping("/department/{departmentId}")
    @Operation(summary = "按部门查询费用")
    public CommonResult<List<Fee>> listFeesByDepartment(
            @PathVariable("departmentId") Long departmentId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(feeService.listFeesByDepartment(departmentId, startTime, endTime));
    }

    @GetMapping("/cost-center/{costCenterId}")
    @Operation(summary = "按成本中心查询费用")
    public CommonResult<List<Fee>> listFeesByCostCenter(
            @PathVariable("costCenterId") Long costCenterId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(feeService.listFeesByCostCenter(costCenterId, startTime, endTime));
    }

    @GetMapping("/case/{caseId}")
    @Operation(summary = "按案件查询费用")
    public CommonResult<List<Fee>> listFeesByCase(@PathVariable("caseId") Long caseId) {
        return CommonResult.success(feeService.listFeesByCase(caseId));
    }

    @GetMapping("/sum")
    @Operation(summary = "统计费用金额")
    public CommonResult<BigDecimal> sumFeeAmount(
            @Parameter(description = "费用类型") @RequestParam(required = false) FeeTypeEnum feeType,
            @Parameter(description = "费用状态") @RequestParam(required = false) FeeStatusEnum status,
            @Parameter(description = "部门ID") @RequestParam(required = false) Long departmentId,
            @Parameter(description = "成本中心ID") @RequestParam(required = false) Long costCenterId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(feeService.sumFeeAmount(feeType, status, departmentId, costCenterId, startTime, endTime));
    }

    @GetMapping("/sum/department/{departmentId}")
    @Operation(summary = "统计部门费用金额")
    public CommonResult<BigDecimal> sumDepartmentFeeAmount(
            @PathVariable("departmentId") Long departmentId,
            @Parameter(description = "费用类型") @RequestParam(required = false) FeeTypeEnum feeType,
            @Parameter(description = "费用状态") @RequestParam(required = false) FeeStatusEnum status,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(feeService.sumDepartmentFeeAmount(departmentId, feeType, status, startTime, endTime));
    }

    @GetMapping("/sum/cost-center/{costCenterId}")
    @Operation(summary = "统计成本中心费用金额")
    public CommonResult<BigDecimal> sumCostCenterFeeAmount(
            @PathVariable("costCenterId") Long costCenterId,
            @Parameter(description = "费用类型") @RequestParam(required = false) FeeTypeEnum feeType,
            @Parameter(description = "费用状态") @RequestParam(required = false) FeeStatusEnum status,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(feeService.sumCostCenterFeeAmount(costCenterId, feeType, status, startTime, endTime));
    }

    @GetMapping("/sum/case/{caseId}")
    @Operation(summary = "统计案件费用金额")
    public CommonResult<BigDecimal> sumCaseFeeAmount(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "费用类型") @RequestParam(required = false) FeeTypeEnum feeType,
            @Parameter(description = "费用状态") @RequestParam(required = false) FeeStatusEnum status) {
        return CommonResult.success(feeService.sumCaseFeeAmount(caseId, feeType, status));
    }

    @GetMapping("/export")
    @Operation(summary = "导出费用数据")
    public CommonResult<String> exportFees(
            @Parameter(description = "费用ID列表") @RequestParam List<Long> feeIds) {
        return CommonResult.success(feeService.exportFees(feeIds));
    }
}
