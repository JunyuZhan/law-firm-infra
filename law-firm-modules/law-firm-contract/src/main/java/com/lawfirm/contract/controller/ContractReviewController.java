package com.lawfirm.contract.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.contract.service.ContractReviewService;
import com.lawfirm.model.contract.dto.ContractReviewDTO;
import com.lawfirm.model.contract.dto.ContractReviewQueryDTO;
import com.lawfirm.model.contract.vo.ContractReviewDetailVO;
import com.lawfirm.model.contract.vo.ContractReviewVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 合同审批控制器
 */
@Slf4j
@RestController
@RequestMapping("/contract-reviews")
@RequiredArgsConstructor
@Tag(name = "合同审批接口", description = "提供合同审批相关功能，包括提交审批、审批操作、查询审批历史等，支持完整的合同审批流程管理")
public class ContractReviewController {

    private final ContractReviewService contractReviewService;

    /**
     * 提交审批
     */
    @PostMapping
    @Operation(
        summary = "提交审批",
        description = "提交合同审批申请，支持指定审批人、审批类型、审批说明等信息"
    )
    public CommonResult<Long> submitReview(
            @Parameter(description = "审批申请参数，包括合同ID、审批人、审批说明等") @RequestBody @Validated ContractReviewDTO reviewDTO) {
        log.info("提交合同审批, 合同ID: {}, 审批人: {}", reviewDTO.getContractId(), reviewDTO.getReviewerId());
        Long reviewId = contractReviewService.submitReview(reviewDTO);
        return CommonResult.success(reviewId, "提交审批成功");
    }

    /**
     * 审批通过
     */
    @PostMapping("/{id}/approve")
    @Operation(
        summary = "审批通过",
        description = "通过合同审批，可以添加审批意见。审批通过后，合同状态将更新为已通过"
    )
    public CommonResult<Boolean> approveReview(
            @Parameter(description = "审批记录ID") @PathVariable("id") Long id,
            @Parameter(description = "审批意见，可选") @RequestParam(required = false) String comment) {
        log.info("审批通过, 审批ID: {}", id);
        boolean result = contractReviewService.approveReview(id, comment);
        return CommonResult.success(result, "审批通过" + (result ? "成功" : "失败"));
    }

    /**
     * 审批拒绝
     */
    @PostMapping("/{id}/reject")
    @Operation(
        summary = "审批拒绝",
        description = "拒绝合同审批，必须添加拒绝原因。审批拒绝后，合同状态将更新为已拒绝"
    )
    public CommonResult<Boolean> rejectReview(
            @Parameter(description = "审批记录ID") @PathVariable("id") Long id,
            @Parameter(description = "拒绝原因，必填") @RequestParam String comment) {
        log.info("审批拒绝, 审批ID: {}", id);
        boolean result = contractReviewService.rejectReview(id, comment);
        return CommonResult.success(result, "审批拒绝" + (result ? "成功" : "失败"));
    }

    /**
     * 获取审批详情
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "获取审批详情",
        description = "根据ID获取审批记录的详细信息，包括审批状态、审批意见、审批时间等"
    )
    public CommonResult<ContractReviewDetailVO> getReview(
            @Parameter(description = "审批记录ID") @PathVariable("id") Long id) {
        log.info("获取审批详情, 审批ID: {}", id);
        ContractReviewDetailVO detailVO = contractReviewService.getReviewDetail(id);
        return CommonResult.success(detailVO);
    }

    /**
     * 查询合同的审批历史
     */
    @GetMapping("/contract/{contractId}")
    @Operation(
        summary = "查询合同的审批历史",
        description = "根据合同ID查询该合同的所有审批记录，包括每次审批的状态、意见、时间等"
    )
    public CommonResult<List<ContractReviewVO>> listContractReviews(
            @Parameter(description = "合同ID") @PathVariable("contractId") Long contractId) {
        log.info("查询合同的审批历史, 合同ID: {}", contractId);
        List<ContractReviewVO> reviews = contractReviewService.listContractReviews(contractId);
        return CommonResult.success(reviews);
    }

    /**
     * 分页查询待审批列表
     */
    @GetMapping("/pending/page")
    @Operation(
        summary = "分页查询待审批列表",
        description = "分页查询当前用户待处理的审批列表，支持按合同名称、提交时间等条件筛选"
    )
    @Parameters({
            @Parameter(name = "current", description = "当前页码，从1开始", required = true),
            @Parameter(name = "size", description = "每页记录数", required = true),
            @Parameter(name = "reviewerId", description = "审批人ID，用于筛选指定审批人的待办项")
    })
    public CommonResult<IPage<ContractReviewVO>> pagePendingReviews(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam Long reviewerId) {
        log.info("分页查询待审批列表, 审批人: {}, current={}, size={}", reviewerId, current, size);
        Page<ContractReviewVO> page = new Page<>(current, size);
        ContractReviewQueryDTO queryDTO = new ContractReviewQueryDTO();
        queryDTO.setReviewerId(reviewerId);
        queryDTO.setStatus(0); // 待审批状态
        IPage<ContractReviewVO> pageResult = contractReviewService.pageReviews(page, queryDTO);
        return CommonResult.success(pageResult);
    }

    /**
     * 分页查询已审批列表
     */
    @GetMapping("/completed/page")
    @Operation(
        summary = "分页查询已审批列表",
        description = "分页查询当前用户已处理的审批列表，包括已通过和已拒绝的记录"
    )
    @Parameters({
            @Parameter(name = "current", description = "当前页码，从1开始", required = true),
            @Parameter(name = "size", description = "每页记录数", required = true),
            @Parameter(name = "reviewerId", description = "审批人ID，用于筛选指定审批人的已办项")
    })
    public CommonResult<IPage<ContractReviewVO>> pageCompletedReviews(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam Long reviewerId) {
        log.info("分页查询已审批列表, 审批人: {}, current={}, size={}", reviewerId, current, size);
        Page<ContractReviewVO> page = new Page<>(current, size);
        ContractReviewQueryDTO queryDTO = new ContractReviewQueryDTO();
        queryDTO.setReviewerId(reviewerId);
        // 已审批状态 (1:通过, 2:拒绝)
        queryDTO.setStatusList(List.of(1, 2));
        IPage<ContractReviewVO> pageResult = contractReviewService.pageReviews(page, queryDTO);
        return CommonResult.success(pageResult);
    }
    
    /**
     * 撤销审批
     */
    @PostMapping("/{id}/revoke")
    @Operation(
        summary = "撤销审批",
        description = "撤销已提交的审批申请，需要提供撤销原因。仅能撤销未审批的申请"
    )
    public CommonResult<Boolean> revokeReview(
            @Parameter(description = "审批记录ID") @PathVariable("id") Long id,
            @Parameter(description = "撤销原因，必填") @RequestParam String reason) {
        log.info("撤销审批, 审批ID: {}", id);
        boolean result = contractReviewService.revokeReview(id, reason);
        return CommonResult.success(result, "撤销审批" + (result ? "成功" : "失败"));
    }
    
    /**
     * 催办审批
     */
    @PostMapping("/{id}/urge")
    @Operation(
        summary = "催办审批",
        description = "对未处理的审批进行催办，系统将通知审批人尽快处理"
    )
    public CommonResult<Boolean> urgeReview(
            @Parameter(description = "审批记录ID") @PathVariable("id") Long id) {
        log.info("催办审批, 审批ID: {}", id);
        boolean result = contractReviewService.urgeReview(id);
        return CommonResult.success(result, "催办审批" + (result ? "成功" : "失败"));
    }
    
    /**
     * 转交审批
     */
    @PostMapping("/{id}/transfer")
    @Operation(
        summary = "转交审批",
        description = "将审批任务转交给其他审批人处理，需要提供转交原因"
    )
    public CommonResult<Boolean> transferReview(
            @Parameter(description = "审批记录ID") @PathVariable("id") Long id,
            @Parameter(description = "新审批人ID") @RequestParam Long newReviewerId,
            @Parameter(description = "转交原因，必填") @RequestParam String reason) {
        log.info("转交审批, 审批ID: {}, 新审批人: {}", id, newReviewerId);
        boolean result = contractReviewService.transferReview(id, newReviewerId, reason);
        return CommonResult.success(result, "转交审批" + (result ? "成功" : "失败"));
    }
} 