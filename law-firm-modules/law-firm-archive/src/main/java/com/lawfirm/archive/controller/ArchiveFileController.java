package com.lawfirm.archive.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.api.constant.ApiConstants;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.archive.constant.ArchiveApiConstant;
import com.lawfirm.model.archive.dto.ArchiveFileCreateDTO;
import com.lawfirm.model.archive.dto.ArchiveFileUpdateDTO;
import com.lawfirm.model.archive.entity.ArchiveFile;
import com.lawfirm.model.archive.enums.ArchiveStatusEnum;
import com.lawfirm.model.archive.service.ArchiveService;
import com.lawfirm.model.archive.vo.ArchiveFileVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.lawfirm.archive.constant.ArchiveBusinessConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 档案文件控制器
 */
@RestController
@RequestMapping(ArchiveBusinessConstants.Controller.API_FILE_PREFIX)
@RequiredArgsConstructor
@Tag(name = "档案文件管理", description = "档案文件管理相关接口")
@Slf4j
public class ArchiveFileController {

    private final ArchiveService archiveService;

    /**
     * 批量上传档案文件
     */
    @PostMapping("/upload/batch")
    @Operation(summary = "批量上传档案文件", description = "批量上传档案文件")
    public CommonResult<List<String>> batchUploadArchiveFiles(@RequestBody @Validated List<ArchiveFileCreateDTO> fileCreateDTOList) {
        log.info("批量上传档案文件，数量：{}", fileCreateDTOList.size());
        
        List<ArchiveFile> archiveFiles = new ArrayList<>();
        for (ArchiveFileCreateDTO createDTO : fileCreateDTOList) {
            ArchiveFile archiveFile = new ArchiveFile();
            BeanUtils.copyProperties(createDTO, archiveFile);
            archiveFile.setArchiveStatus(ArchiveStatusEnum.ACTIVE.getValue());
            archiveFiles.add(archiveFile);
        }
        
        List<String> fileIds = archiveService.batchUploadArchiveFiles(archiveFiles);
        return CommonResult.success(fileIds);
    }

    /**
     * 获取案件相关的档案文件
     */
    @GetMapping("/case/{caseId}")
    @Operation(summary = "获取案件相关的档案文件", description = "根据案件ID获取相关的档案文件")
    public CommonResult<List<ArchiveFile>> getArchiveFilesByCaseId(@PathVariable String caseId) {
        log.info("获取案件相关的档案文件，caseId：{}", caseId);
        List<ArchiveFile> archiveFiles = archiveService.getArchiveFilesByCaseId(caseId);
        return CommonResult.success(archiveFiles);
    }

    /**
     * 更新档案文件
     */
    @PutMapping(ApiConstants.Operation.UPDATE)
    @Operation(summary = "更新档案文件", description = "更新档案文件信息")
    public CommonResult<Boolean> updateArchiveFile(@RequestBody @Validated ArchiveFileUpdateDTO updateDTO) {
        log.info("更新档案文件，fileId：{}", updateDTO.getId());
        
        ArchiveFile archiveFile = new ArchiveFile();
        BeanUtils.copyProperties(updateDTO, archiveFile);
        
        boolean result = archiveService.update(archiveFile);
        return CommonResult.success(result);
    }

    /**
     * 删除档案文件
     */
    @DeleteMapping("/{fileId}")
    @Operation(summary = "删除档案文件", description = "删除档案文件")
    public CommonResult<Boolean> deleteArchiveFile(@PathVariable String fileId) {
        log.info("删除档案文件，fileId：{}", fileId);
        boolean result = archiveService.remove(Long.valueOf(fileId));
        return CommonResult.success(result);
    }

    /**
     * 更新档案文件状态
     */
    @PutMapping("/{fileId}/status")
    @Operation(summary = "更新档案文件状态", description = "更新档案文件状态")
    public CommonResult<Boolean> updateArchiveFileStatus(
            @PathVariable String fileId,
            @RequestParam ArchiveStatusEnum status) {
        log.info("更新档案文件状态，fileId：{}，status：{}", fileId, status);
        boolean result = archiveService.updateArchiveStatus(fileId, status);
        return CommonResult.success(result);
    }

    /**
     * 批量更新档案文件
     */
    @PutMapping(ApiConstants.Operation.BATCH + ApiConstants.Operation.UPDATE)
    @Operation(summary = "批量更新档案文件", description = "批量更新档案文件信息")
    public CommonResult<Boolean> batchUpdateArchiveFiles(@RequestBody List<ArchiveFileUpdateDTO> updateDTOList) {
        log.info("批量更新档案文件，数量：{}", updateDTOList.size());
        
        List<ArchiveFile> archiveFiles = new ArrayList<>();
        for (ArchiveFileUpdateDTO updateDTO : updateDTOList) {
            ArchiveFile archiveFile = new ArchiveFile();
            BeanUtils.copyProperties(updateDTO, archiveFile);
            archiveFiles.add(archiveFile);
        }
        
        boolean result = archiveService.updateBatch(archiveFiles);
        return CommonResult.success(result);
    }

    /**
     * 批量删除档案文件
     */
    @DeleteMapping(ApiConstants.Operation.BATCH)
    @Operation(summary = "批量删除档案文件", description = "批量删除档案文件")
    public CommonResult<Boolean> batchDeleteArchiveFiles(@RequestBody List<String> fileIds) {
        log.info("批量删除档案文件，数量：{}", fileIds.size());
        
        List<Long> ids = new ArrayList<>();
        for (String fileId : fileIds) {
            ids.add(Long.valueOf(fileId));
        }
        
        boolean result = archiveService.removeBatch(ids);
        return CommonResult.success(result);
    }

    /**
     * 借阅档案文件
     */
    @PostMapping(ArchiveApiConstant.ARCHIVE_BORROW)
    @Operation(summary = "借阅档案文件", description = "借阅档案文件")
    public CommonResult<Boolean> borrowArchiveFile(
            @Parameter(description = "文件ID") @RequestParam String fileId,
            @Parameter(description = "借阅人ID") @RequestParam String borrowerId,
            @Parameter(description = "预计归还时间") @RequestParam(required = false) String expectedReturnTime) {
        
        log.info("借阅档案文件，fileId：{}，borrowerId：{}", fileId, borrowerId);
        boolean result = archiveService.borrowArchiveFile(fileId, borrowerId, expectedReturnTime);
        return CommonResult.success(result);
    }

    /**
     * 归还档案文件
     */
    @PostMapping("/return")
    @Operation(summary = "归还档案文件", description = "归还档案文件")
    public CommonResult<Boolean> returnArchiveFile(
            @Parameter(description = "文件ID") @RequestParam String fileId) {
        
        log.info("归还档案文件，fileId：{}", fileId);
        boolean result = archiveService.returnArchiveFile(fileId);
        return CommonResult.success(result);
    }
} 