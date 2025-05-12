package com.lawfirm.task.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.task.dto.WorkTaskAttachmentDTO;
import com.lawfirm.model.task.service.WorkTaskAttachmentService;
import com.lawfirm.task.constant.TaskBusinessConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 工作任务附件控制器
 */
@Slf4j
@Tag(name = "工作任务附件管理", description = "工作任务附件管理接口")
@RestController("workTaskAttachmentController")
@RequestMapping(TaskBusinessConstants.Controller.API_ATTACHMENT_PREFIX)
@RequiredArgsConstructor
public class WorkTaskAttachmentController {

    private final WorkTaskAttachmentService workTaskAttachmentService;
    
    /**
     * 上传任务附件
     */
    @Operation(summary = "上传任务附件", description = "为工作任务上传附件文件")
    @PostMapping("/upload")
    public CommonResult<WorkTaskAttachmentDTO> uploadAttachment(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "附件文件") 
            @RequestParam("file") MultipartFile file) {
        log.info("上传任务附件: taskId={}, fileName={}", taskId, file.getOriginalFilename());
        WorkTaskAttachmentDTO attachment = workTaskAttachmentService.uploadAttachment(taskId, file);
        return CommonResult.success(attachment);
    }
    
    /**
     * 删除任务附件
     */
    @Operation(summary = "删除任务附件", description = "删除指定的任务附件")
    @DeleteMapping("/{attachmentId}")
    public CommonResult<Void> deleteAttachment(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "附件ID") 
            @PathVariable Long attachmentId) {
        log.info("删除任务附件: taskId={}, attachmentId={}", taskId, attachmentId);
        workTaskAttachmentService.deleteAttachment(attachmentId);
        return CommonResult.success();
    }
    
    /**
     * 获取任务附件详情
     */
    @Operation(summary = "获取任务附件详情", description = "获取任务附件的详细信息")
    @GetMapping("/{attachmentId}")
    public CommonResult<WorkTaskAttachmentDTO> getAttachmentDetail(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "附件ID") 
            @PathVariable Long attachmentId) {
        log.info("获取任务附件详情: taskId={}, attachmentId={}", taskId, attachmentId);
        return CommonResult.success(workTaskAttachmentService.getAttachmentDetail(attachmentId));
    }
    
    /**
     * 获取任务附件列表
     */
    @Operation(summary = "获取任务附件列表", description = "获取指定任务的所有附件")
    @GetMapping("/list")
    public CommonResult<List<WorkTaskAttachmentDTO>> getTaskAttachments(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId) {
        log.info("获取任务附件列表: taskId={}", taskId);
        return CommonResult.success(workTaskAttachmentService.getAttachmentsByTaskId(taskId));
    }
    
    /**
     * 下载任务附件
     */
    @Operation(summary = "下载任务附件", description = "下载指定的任务附件")
    @GetMapping("/{attachmentId}/download")
    public ResponseEntity<byte[]> downloadAttachment(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "附件ID") 
            @PathVariable Long attachmentId) {
        log.info("下载任务附件: taskId={}, attachmentId={}", taskId, attachmentId);
        WorkTaskAttachmentDTO attachment = workTaskAttachmentService.getAttachmentDetail(attachmentId);
        byte[] fileData = workTaskAttachmentService.downloadAttachment(attachmentId);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(fileData.length)
                .body(fileData);
    }
    
    /**
     * 预览任务附件
     */
    @Operation(summary = "预览任务附件", description = "预览指定的任务附件")
    @GetMapping("/{attachmentId}/preview")
    public ResponseEntity<byte[]> previewAttachment(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "附件ID") 
            @PathVariable Long attachmentId) {
        log.info("预览任务附件: taskId={}, attachmentId={}", taskId, attachmentId);
        WorkTaskAttachmentDTO attachment = workTaskAttachmentService.getAttachmentDetail(attachmentId);
        byte[] fileData = workTaskAttachmentService.previewAttachment(attachmentId);
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .body(fileData);
    }
    
    /**
     * 获取任务附件预览链接
     */
    @Operation(summary = "获取任务附件预览链接", description = "获取指定任务附件的预览链接")
    @GetMapping("/{attachmentId}/preview-url")
    public CommonResult<String> getAttachmentPreviewUrl(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "附件ID") 
            @PathVariable Long attachmentId) {
        log.info("获取任务附件预览链接: taskId={}, attachmentId={}", taskId, attachmentId);
        return CommonResult.success(workTaskAttachmentService.getAttachmentPreviewUrl(attachmentId));
    }
    
    /**
     * 获取任务附件下载链接
     */
    @Operation(summary = "获取任务附件下载链接", description = "获取指定任务附件的下载链接")
    @GetMapping("/{attachmentId}/download-url")
    public CommonResult<String> getAttachmentDownloadUrl(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "附件ID") 
            @PathVariable Long attachmentId) {
        log.info("获取任务附件下载链接: taskId={}, attachmentId={}", taskId, attachmentId);
        return CommonResult.success(workTaskAttachmentService.getAttachmentDownloadUrl(attachmentId));
    }
} 