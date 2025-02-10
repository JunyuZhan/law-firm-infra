package com.lawfirm.archive.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.archive.converter.ArchiveConverter;
import com.lawfirm.archive.model.dto.ArchiveCreateDTO;
import com.lawfirm.archive.model.dto.ArchiveUpdateDTO;
import com.lawfirm.archive.model.entity.Archive;
import com.lawfirm.archive.model.enums.ArchiveStatusEnum;
import com.lawfirm.archive.model.vo.ArchiveVO;
import com.lawfirm.archive.service.ArchiveService;
import com.lawfirm.common.core.model.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping
    @Operation(summary = "创建档案")
    @PreAuthorize("hasAuthority('archive:create')")
    public Result<ArchiveVO> createArchive(@Valid @RequestBody ArchiveCreateDTO createDTO) {
        Archive archive = archiveConverter.toEntity(createDTO);
        archive = archiveService.createArchive(archive);
        return Result.ok(archiveConverter.toVO(archive));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新档案")
    @PreAuthorize("hasAuthority('archive:update')")
    public Result<ArchiveVO> updateArchive(
            @Parameter(description = "档案ID") @PathVariable Long id,
            @Valid @RequestBody ArchiveUpdateDTO updateDTO) {
        Archive archive = archiveService.getArchiveById(id);
        archiveConverter.updateEntity(archive, updateDTO);
        archive = archiveService.updateArchive(archive);
        return Result.ok(archiveConverter.toVO(archive));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除档案")
    @PreAuthorize("hasAuthority('archive:delete')")
    public Result<Void> deleteArchive(@Parameter(description = "档案ID") @PathVariable Long id) {
        archiveService.deleteArchive(id);
        return Result.ok();
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取档案详情")
    @PreAuthorize("hasAuthority('archive:read')")
    public Result<ArchiveVO> getArchive(@Parameter(description = "档案ID") @PathVariable Long id) {
        Archive archive = archiveService.getArchiveById(id);
        return Result.ok(archiveConverter.toVO(archive));
    }

    @GetMapping("/page")
    @Operation(summary = "获取档案列表")
    @PreAuthorize("hasAuthority('archive:read')")
    public Result<Page<ArchiveVO>> getArchivePage(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "关键字") @RequestParam(required = false) String keyword) {
        Page<Archive> page = archiveService.getArchivePage(pageNum, pageSize, keyword);
        Page<ArchiveVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(page.getRecords().stream()
                .map(archiveConverter::toVO)
                .collect(Collectors.toList()));
        return Result.ok(voPage);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "根据状态查询档案")
    @PreAuthorize("hasAuthority('archive:read')")
    public Result<List<ArchiveVO>> getArchivesByStatus(
            @Parameter(description = "档案状态") @PathVariable ArchiveStatusEnum status) {
        List<Archive> archives = archiveService.getArchivesByStatus(status);
        List<ArchiveVO> voList = archives.stream()
                .map(archiveConverter::toVO)
                .collect(Collectors.toList());
        return Result.ok(voList);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "根据分类查询档案")
    @PreAuthorize("hasAuthority('archive:read')")
    public Result<List<ArchiveVO>> getArchivesByCategory(
            @Parameter(description = "分类ID") @PathVariable Long categoryId) {
        List<Archive> archives = archiveService.getArchivesByCategory(categoryId);
        List<ArchiveVO> voList = archives.stream()
                .map(archiveConverter::toVO)
                .collect(Collectors.toList());
        return Result.ok(voList);
    }

    @PostMapping("/{id}/borrow")
    @Operation(summary = "借出档案")
    @PreAuthorize("hasAuthority('archive:borrow')")
    public Result<Void> borrowArchive(
            @Parameter(description = "档案ID") @PathVariable Long id,
            @Parameter(description = "借阅人") @RequestParam String borrower,
            @Parameter(description = "预计归还时间") @RequestParam LocalDateTime expectedReturnTime,
            @Parameter(description = "借阅原因") @RequestParam String borrowReason) {
        archiveService.borrowArchive(id, borrower, expectedReturnTime, borrowReason);
        return Result.ok();
    }

    @PostMapping("/{id}/return")
    @Operation(summary = "归还档案")
    @PreAuthorize("hasAuthority('archive:return')")
    public Result<Void> returnArchive(@Parameter(description = "档案ID") @PathVariable Long id) {
        archiveService.returnArchive(id);
        return Result.ok();
    }

    @PostMapping("/{id}/destroy")
    @Operation(summary = "销毁档案")
    @PreAuthorize("hasAuthority('archive:destroy')")
    public Result<Void> destroyArchive(
            @Parameter(description = "档案ID") @PathVariable Long id,
            @Parameter(description = "操作人") @RequestParam String operator,
            @Parameter(description = "销毁原因") @RequestParam String reason) {
        archiveService.destroyArchive(id, operator, reason);
        return Result.ok();
    }

    @GetMapping("/count/status/{status}")
    @Operation(summary = "统计档案数量（按状态）")
    @PreAuthorize("hasAuthority('archive:read')")
    public Result<Long> countArchivesByStatus(
            @Parameter(description = "档案状态") @PathVariable ArchiveStatusEnum status) {
        return Result.ok(archiveService.countArchivesByStatus(status));
    }

    @GetMapping("/count/category/{categoryId}")
    @Operation(summary = "统计档案数量（按分类）")
    @PreAuthorize("hasAuthority('archive:read')")
    public Result<Long> countArchivesByCategory(
            @Parameter(description = "分类ID") @PathVariable Long categoryId) {
        return Result.ok(archiveService.countArchivesByCategory(categoryId));
    }
} 