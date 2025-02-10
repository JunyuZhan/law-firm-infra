package com.lawfirm.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.finance.dto.request.FeeRecordAddRequest;
import com.lawfirm.finance.dto.request.FeeRecordUpdateRequest;
import com.lawfirm.finance.dto.request.FeeRecordQueryRequest;
import com.lawfirm.finance.dto.response.FeeRecordResponse;
import com.lawfirm.finance.service.IFeeRecordService;
import com.lawfirm.finance.entity.FeeRecord;
import com.lawfirm.finance.converter.FeeRecordConverter;
import com.lawfirm.common.core.base.BaseResponse;
import com.lawfirm.finance.constant.FinanceConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "收费记录管理", description = "提供收费记录的增删改查等功能")
@RestController
@RequestMapping("/api/v1/fee-records")
@RequiredArgsConstructor
public class FeeRecordController {

    private final IFeeRecordService feeRecordService;
    private final FeeRecordConverter feeRecordConverter;

    @Operation(summary = "创建收费记录",
            description = "创建一条新的收费记录，包括案件收费、咨询收费等")
    @ApiResponse(responseCode = "200", description = "创建成功")
    @PostMapping
    public BaseResponse<Long> addFeeRecord(
            @Parameter(description = "收费记录信息", required = true)
            @Validated @RequestBody FeeRecordAddRequest request) {
        FeeRecord entity = feeRecordConverter.toEntity(request);
        return BaseResponse.success(feeRecordService.createFeeRecord(entity));
    }

    @Operation(summary = "更新收费记录",
            description = "根据ID更新收费记录的信息")
    @ApiResponse(responseCode = "200", description = "更新成功")
    @PutMapping("/{id}")
    public BaseResponse<Void> updateFeeRecord(
            @Parameter(description = "收费记录ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "更新的收费记录信息", required = true)
            @Validated @RequestBody FeeRecordUpdateRequest request) {
        request.setId(id);
        FeeRecord entity = feeRecordConverter.toEntity(request);
        feeRecordService.updateFeeRecord(entity);
        return BaseResponse.success();
    }

    @Operation(summary = "删除收费记录",
            description = "根据ID删除收费记录")
    @ApiResponse(responseCode = "200", description = "删除成功")
    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteFeeRecord(
            @Parameter(description = "收费记录ID", required = true)
            @PathVariable Long id) {
        feeRecordService.deleteFeeRecord(id);
        return BaseResponse.success();
    }

    @Operation(summary = "获取收费记录详情",
            description = "根据ID获取收费记录的详细信息")
    @ApiResponse(responseCode = "200", description = "获取成功")
    @GetMapping("/{id}")
    public BaseResponse<FeeRecordResponse> getFeeRecord(
            @Parameter(description = "收费记录ID", required = true)
            @PathVariable Long id) {
        FeeRecord entity = feeRecordService.getFeeRecordById(id);
        return BaseResponse.success(feeRecordConverter.toResponse(entity));
    }

    @Operation(summary = "分页查询收费记录", description = "分页查询所有收费记录")
    @GetMapping
    public BaseResponse<IPage<FeeRecordResponse>> getFeeRecords(
            @Parameter(description = "查询条件") FeeRecordQueryRequest request) {
        IPage<FeeRecord> page = feeRecordService.pageFeeRecords(
            request.getPageNum(), 
            request.getPageSize(),
            request.getFeeType(),
            request.getFeeStatus(),
            request.getClientId(),
            request.getLawFirmId(),
            request.getStartTime(),
            request.getEndTime()
        );
        
        return BaseResponse.success(page.convert(feeRecordConverter::toResponse));
    }

    @Operation(summary = "获取客户未支付金额",
            description = "获取指定客户的所有未支付费用总额")
    @ApiResponse(responseCode = "200", description = "获取成功")
    @GetMapping("/unpaid-amount")
    public BaseResponse<BigDecimal> getUnpaidAmount(
            @Parameter(description = "客户ID", required = true)
            @RequestParam Long clientId) {
        return BaseResponse.success(feeRecordService.getUnpaidAmount(clientId));
    }

    @Operation(summary = "支付费用",
            description = "为指定的收费记录支付费用")
    @ApiResponse(responseCode = "200", description = "支付成功")
    @PostMapping("/{id}/pay")
    public BaseResponse<Void> payFee(
            @Parameter(description = "收费记录ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "支付金额", required = true)
            @RequestParam BigDecimal amount) {
        feeRecordService.payFee(id, amount);
        return BaseResponse.success();
    }

    @Operation(summary = "查询案件收费记录", description = "根据案件ID查询相关的收费记录")
    @GetMapping("/case/{caseId}")
    public BaseResponse<List<FeeRecordResponse>> getFeeRecordsByCaseId(
            @Parameter(description = "案件ID", required = true) @PathVariable Long caseId) {
        List<FeeRecord> records = feeRecordService.getFeeRecordsByCaseId(caseId);
        return BaseResponse.success(feeRecordConverter.toResponseList(records));
    }

    @Operation(summary = "查询客户收费记录", description = "根据客户ID查询相关的收费记录")
    @GetMapping("/client/{clientId}")
    public BaseResponse<List<FeeRecordResponse>> getFeeRecordsByClientId(
            @Parameter(description = "客户ID", required = true) @PathVariable Long clientId) {
        List<FeeRecord> records = feeRecordService.getFeeRecordsByClientId(clientId);
        return BaseResponse.success(feeRecordConverter.toResponseList(records));
    }

    @Operation(summary = "统计总收费金额", description = "统计律所的总收费金额")
    @GetMapping("/statistics/total-amount")
    public BaseResponse<BigDecimal> getTotalAmount(
            @Parameter(description = "律所ID", required = true) @RequestParam Long lawFirmId) {
        return BaseResponse.success(feeRecordService.calculateTotalAmount(lawFirmId));
    }

    @Operation(summary = "统计已收费金额", description = "统计律所的已收费金额")
    @GetMapping("/statistics/total-paid-amount")
    public BaseResponse<BigDecimal> getTotalPaidAmount(
            @Parameter(description = "律所ID", required = true) @RequestParam Long lawFirmId) {
        return BaseResponse.success(feeRecordService.calculateTotalPaidAmount(lawFirmId));
    }
} 