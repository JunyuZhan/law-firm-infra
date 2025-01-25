package com.lawfirm.archive.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.archive.converter.ArchiveBorrowConverter;
import com.lawfirm.archive.model.dto.ArchiveBorrowDTO;
import com.lawfirm.archive.model.entity.ArchiveBorrow;
import com.lawfirm.archive.model.enums.BorrowStatusEnum;
import com.lawfirm.archive.model.vo.ArchiveBorrowVO;
import com.lawfirm.archive.service.ArchiveBorrowService;
import com.lawfirm.common.core.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 档案借阅控制器
 */
@Tag(name = "档案借阅管理")
@RestController
@RequestMapping("/archive/borrows")
@RequiredArgsConstructor
public class ArchiveBorrowController {

    private final ArchiveBorrowService borrowService;
    private final ArchiveBorrowConverter borrowConverter;

    @Operation(summary = "创建借阅记录")
    @PostMapping
    public ApiResult<ArchiveBorrowVO> createBorrow(@Valid @RequestBody ArchiveBorrowDTO createDTO) {
        ArchiveBorrow borrow = borrowConverter.toEntity(createDTO);
        borrow = borrowService.createBorrow(borrow);
        return ApiResult.success(borrowConverter.toVO(borrow));
    }

    @Operation(summary = "获取借阅记录详情")
    @GetMapping("/{id}")
    public ApiResult<ArchiveBorrowVO> getBorrow(@Parameter(description = "借阅记录ID") @PathVariable Long id) {
        ArchiveBorrow borrow = borrowService.getBorrowById(id);
        return ApiResult.success(borrowConverter.toVO(borrow));
    }

    @Operation(summary = "获取借阅记录列表")
    @GetMapping("/page")
    public ApiResult<Page<ArchiveBorrowVO>> getBorrowPage(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "关键字") @RequestParam(required = false) String keyword) {
        Page<ArchiveBorrow> page = borrowService.getBorrowPage(pageNum, pageSize, keyword);
        Page<ArchiveBorrowVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(page.getRecords().stream()
                .map(borrowConverter::toVO)
                .collect(Collectors.toList()));
        return ApiResult.success(voPage);
    }

    @Operation(summary = "根据档案ID获取借阅记录")
    @GetMapping("/archive/{archiveId}")
    public ApiResult<List<ArchiveBorrowVO>> getBorrowsByArchiveId(
            @Parameter(description = "档案ID") @PathVariable Long archiveId) {
        List<ArchiveBorrow> borrows = borrowService.getBorrowsByArchiveId(archiveId);
        List<ArchiveBorrowVO> voList = borrows.stream()
                .map(borrowConverter::toVO)
                .collect(Collectors.toList());
        return ApiResult.success(voList);
    }

    @Operation(summary = "根据借阅人获取借阅记录")
    @GetMapping("/borrower/{borrower}")
    public ApiResult<List<ArchiveBorrowVO>> getBorrowsByBorrower(
            @Parameter(description = "借阅人") @PathVariable String borrower) {
        List<ArchiveBorrow> borrows = borrowService.getBorrowsByBorrower(borrower);
        List<ArchiveBorrowVO> voList = borrows.stream()
                .map(borrowConverter::toVO)
                .collect(Collectors.toList());
        return ApiResult.success(voList);
    }

    @Operation(summary = "根据状态获取借阅记录")
    @GetMapping("/status/{status}")
    public ApiResult<List<ArchiveBorrowVO>> getBorrowsByStatus(
            @Parameter(description = "借阅状态") @PathVariable BorrowStatusEnum status) {
        List<ArchiveBorrow> borrows = borrowService.getBorrowsByStatus(status);
        List<ArchiveBorrowVO> voList = borrows.stream()
                .map(borrowConverter::toVO)
                .collect(Collectors.toList());
        return ApiResult.success(voList);
    }

    @Operation(summary = "审批借阅申请")
    @PostMapping("/{id}/approve")
    public ApiResult<Void> approveBorrow(
            @Parameter(description = "借阅记录ID") @PathVariable Long id,
            @Parameter(description = "审批人") @RequestParam String approver,
            @Parameter(description = "审批意见") @RequestParam String approvalOpinion,
            @Parameter(description = "是否通过") @RequestParam boolean approved) {
        borrowService.approveBorrow(id, approver, approvalOpinion, approved);
        return ApiResult.success();
    }

    @Operation(summary = "归还档案")
    @PostMapping("/{id}/return")
    public ApiResult<Void> returnArchive(
            @Parameter(description = "借阅记录ID") @PathVariable Long id,
            @Parameter(description = "实际归还时间") @RequestParam LocalDateTime actualReturnTime) {
        borrowService.returnArchive(id, actualReturnTime);
        return ApiResult.success();
    }

    @Operation(summary = "获取逾期未还列表")
    @GetMapping("/overdue")
    public ApiResult<List<ArchiveBorrowVO>> getOverdueBorrows() {
        List<ArchiveBorrow> borrows = borrowService.getOverdueBorrows();
        List<ArchiveBorrowVO> voList = borrows.stream()
                .map(borrowConverter::toVO)
                .collect(Collectors.toList());
        return ApiResult.success(voList);
    }

    @Operation(summary = "检查是否有逾期未还")
    @GetMapping("/check-overdue")
    public ApiResult<Boolean> hasOverdueBorrows(
            @Parameter(description = "借阅人") @RequestParam String borrower) {
        return ApiResult.success(borrowService.hasOverdueBorrows(borrower));
    }

    @Operation(summary = "统计借阅数量（按状态）")
    @GetMapping("/count/status/{status}")
    public ApiResult<Long> countBorrowsByStatus(
            @Parameter(description = "借阅状态") @PathVariable BorrowStatusEnum status) {
        return ApiResult.success(borrowService.countBorrowsByStatus(status));
    }

    @Operation(summary = "统计借阅数量（按借阅人）")
    @GetMapping("/count/borrower/{borrower}")
    public ApiResult<Long> countBorrowsByBorrower(
            @Parameter(description = "借阅人") @PathVariable String borrower) {
        return ApiResult.success(borrowService.countBorrowsByBorrower(borrower));
    }
} 