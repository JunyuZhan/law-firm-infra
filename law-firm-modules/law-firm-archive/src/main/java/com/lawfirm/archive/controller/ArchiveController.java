package com.lawfirm.archive.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.archive.converter.ArchiveConverter;
import com.lawfirm.archive.model.dto.ArchiveCreateDTO;
import com.lawfirm.archive.model.dto.ArchiveUpdateDTO;
import com.lawfirm.archive.model.entity.Archive;
import com.lawfirm.archive.model.enums.ArchiveStatusEnum;
import com.lawfirm.archive.model.vo.ArchiveVO;
import com.lawfirm.archive.service.ArchiveService;
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
 * 档案管理控制器
 */
@Tag(name = "档案管理")
@RestController
@RequestMapping("/archives")
@RequiredArgsConstructor
public class ArchiveController {

    private final ArchiveService archiveService;
    private final ArchiveConverter archiveConverter;

    @Operation(summary = "创建档案")
    @PostMapping
    public ApiResult<ArchiveVO> createArchive(@Valid @RequestBody ArchiveCreateDTO createDTO) {
        Archive archive = archiveConverter.toEntity(createDTO);
        archive = archiveService.createArchive(archive);
        return ApiResult.success(archiveConverter.toVO(archive));
    }

    @Operation(summary = "更新档案")
    @PutMapping("/{id}")
    public ApiResult<ArchiveVO> updateArchive(
            @Parameter(description = "档案ID") @PathVariable Long id,
            @Valid @RequestBody ArchiveUpdateDTO updateDTO) {
        Archive archive = archiveService.getArchiveById(id);
        archiveConverter.updateEntity(archive, updateDTO);
        archive = archiveService.updateArchive(archive);
        return ApiResult.success(archiveConverter.toVO(archive));
    }

    @Operation(summary = "删除档案")
    @DeleteMapping("/{id}")
    public ApiResult<Void> deleteArchive(@Parameter(description = "档案ID") @PathVariable Long id) {
        archiveService.deleteArchive(id);
        return ApiResult.success();
    }

    @Operation(summary = "获取档案详情")
    @GetMapping("/{id}")
    public ApiResult<ArchiveVO> getArchive(@Parameter(description = "档案ID") @PathVariable Long id) {
        Archive archive = archiveService.getArchiveById(id);
        return ApiResult.success(archiveConverter.toVO(archive));
    }

    @Operation(summary = "获取档案列表")
    @GetMapping("/page")
    public ApiResult<Page<ArchiveVO>> getArchivePage(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "关键字") @RequestParam(required = false) String keyword) {
        Page<Archive> page = archiveService.getArchivePage(pageNum, pageSize, keyword);
        Page<ArchiveVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(page.getRecords().stream()
                .map(archiveConverter::toVO)
                .collect(Collectors.toList()));
        return ApiResult.success(voPage);
    }

    @Operation(summary = "根据状态查询档案")
    @GetMapping("/status/{status}")
    public ApiResult<List<ArchiveVO>> getArchivesByStatus(
            @Parameter(description = "档案状态") @PathVariable ArchiveStatusEnum status) {
        List<Archive> archives = archiveService.getArchivesByStatus(status);
        List<ArchiveVO> voList = archives.stream()
                .map(archiveConverter::toVO)
                .collect(Collectors.toList());
        return ApiResult.success(voList);
    }

    @Operation(summary = "根据分类查询档案")
    @GetMapping("/category/{categoryId}")
    public ApiResult<List<ArchiveVO>> getArchivesByCategory(
            @Parameter(description = "分类ID") @PathVariable Long categoryId) {
        List<Archive> archives = archiveService.getArchivesByCategory(categoryId);
        List<ArchiveVO> voList = archives.stream()
                .map(archiveConverter::toVO)
                .collect(Collectors.toList());
        return ApiResult.success(voList);
    }

    @Operation(summary = "借出档案")
    @PostMapping("/{id}/borrow")
    public ApiResult<Void> borrowArchive(
            @Parameter(description = "档案ID") @PathVariable Long id,
            @Parameter(description = "借阅人") @RequestParam String borrower,
            @Parameter(description = "预计归还时间") @RequestParam LocalDateTime expectedReturnTime,
            @Parameter(description = "借阅原因") @RequestParam String borrowReason) {
        archiveService.borrowArchive(id, borrower, expectedReturnTime, borrowReason);
        return ApiResult.success();
    }

    @Operation(summary = "归还档案")
    @PostMapping("/{id}/return")
    public ApiResult<Void> returnArchive(@Parameter(description = "档案ID") @PathVariable Long id) {
        archiveService.returnArchive(id);
        return ApiResult.success();
    }

    @Operation(summary = "销毁档案")
    @PostMapping("/{id}/destroy")
    public ApiResult<Void> destroyArchive(
            @Parameter(description = "档案ID") @PathVariable Long id,
            @Parameter(description = "操作人") @RequestParam String operator,
            @Parameter(description = "销毁原因") @RequestParam String reason) {
        archiveService.destroyArchive(id, operator, reason);
        return ApiResult.success();
    }

    @Operation(summary = "统计档案数量（按状态）")
    @GetMapping("/count/status/{status}")
    public ApiResult<Long> countArchivesByStatus(
            @Parameter(description = "档案状态") @PathVariable ArchiveStatusEnum status) {
        return ApiResult.success(archiveService.countArchivesByStatus(status));
    }

    @Operation(summary = "统计档案数量（按分类）")
    @GetMapping("/count/category/{categoryId}")
    public ApiResult<Long> countArchivesByCategory(
            @Parameter(description = "分类ID") @PathVariable Long categoryId) {
        return ApiResult.success(archiveService.countArchivesByCategory(categoryId));
    }
} 