package com.lawfirm.core.storage.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.model.storage.dto.file.FileQueryDTO;
import com.lawfirm.model.storage.dto.file.FileUploadDTO;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.vo.FileVO;
import com.lawfirm.model.storage.vo.PageVO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final StorageProperties storageProperties;

    /**
     * 上传文件 - MultipartFile方式
     * 
     * 实际项目中添加权限控制和防重复提交:
     * @PreAuthorize("hasAuthority('storage:file:upload')")
     * @RepeatSubmit(interval = 5000)
     */
    @PostMapping("/upload")
    public Object uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "bucketId", required = false) Long bucketId,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tags", required = false) String tags) {
        try {
            // 检查上传的文件
            if (file.isEmpty()) {
                return createErrorResult(HttpStatus.BAD_REQUEST.value(), "上传文件不能为空");
            }
            
            // 使用默认存储桶（如果未指定）
            if (bucketId == null) {
                bucketId = storageProperties.getDefaultBucketId();
            }
            
            // 上传文件
            FileVO fileVO = fileService.upload(file, bucketId, description, tags);
            
            // 返回成功结果
            return createSuccessResult(fileVO);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return createErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "文件上传失败: " + e.getMessage());
        }
    }
    
    /**
     * 上传文件 - Base64方式
     * 
     * 实际项目中添加权限控制和防重复提交:
     * @PreAuthorize("hasAuthority('storage:file:upload')")
     * @RepeatSubmit(interval = 5000)
     */
    @PostMapping("/upload/base64")
    public Object uploadBase64File(@Valid @RequestBody FileUploadDTO uploadDTO) {
        try {
            // 检查上传信息
            if (!StringUtils.hasText(uploadDTO.getFileName()) || !StringUtils.hasText(uploadDTO.getContent())) {
                return createErrorResult(HttpStatus.BAD_REQUEST.value(), "文件名和内容不能为空");
            }
            
            // 使用默认存储桶（如果未指定）
            if (uploadDTO.getBucketId() == null) {
                uploadDTO.setBucketId(storageProperties.getDefaultBucketId());
            }
            
            // 上传文件
            FileVO fileVO = fileService.upload(uploadDTO);
            
            // 返回成功结果
            return createSuccessResult(fileVO);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return createErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "文件上传失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取文件信息
     * 
     * 实际项目中添加权限控制:
     * @PreAuthorize("hasAuthority('storage:file:info')")
     */
    @GetMapping("/{fileId}")
    public Object getFileInfo(@PathVariable Long fileId) {
        try {
            FileVO fileVO = fileService.getInfo(fileId);
            return createSuccessResult(fileVO);
        } catch (Exception e) {
            log.error("获取文件信息失败", e);
            return createErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取文件信息失败: " + e.getMessage());
        }
    }
    
    /**
     * 下载文件
     * 
     * 实际项目中添加权限控制和限流:
     * @PreAuthorize("hasAuthority('storage:file:download')")
     * @RateLimiter(limit = 30, timeout = 60)
     */
    @GetMapping("/{fileId}/download")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        try {
            // 获取文件信息
            FileVO fileVO = fileService.getInfo(fileId);
            if (fileVO == null) {
                return ResponseEntity.notFound().build();
            }
            
            // 获取文件内容
            byte[] fileData = fileService.download(fileId);
            if (fileData == null) {
                return ResponseEntity.notFound().build();
            }
            
            // 构建响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileVO.getFileName());
            headers.setContentLength(fileData.length);
            
            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("文件下载失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * 获取文件访问URL
     * 
     * 实际项目中添加权限控制:
     * @PreAuthorize("hasAuthority('storage:file:access')")
     */
    @GetMapping("/{fileId}/url")
    public Object getFileUrl(
            @PathVariable Long fileId,
            @RequestParam(value = "expire", required = false, defaultValue = "3600") Integer expireSeconds) {
        try {
            String url = fileService.getAccessUrl(fileId, expireSeconds);
            if (url == null) {
                return createErrorResult(HttpStatus.NOT_FOUND.value(), "文件不存在或无法生成访问链接");
            }
            
            return createSuccessResult(url);
        } catch (Exception e) {
            log.error("获取文件访问URL失败", e);
            return createErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取文件访问URL失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除文件
     * 
     * 实际项目中添加权限控制:
     * @PreAuthorize("hasAuthority('storage:file:delete')")
     */
    @DeleteMapping("/{fileId}")
    public Object deleteFile(@PathVariable Long fileId) {
        try {
            boolean success = fileService.delete(fileId);
            if (success) {
                return createSuccessResult(true);
            } else {
                return createErrorResult(HttpStatus.NOT_FOUND.value(), "文件不存在或删除失败");
            }
        } catch (Exception e) {
            log.error("文件删除失败", e);
            return createErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "文件删除失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询文件列表
     * 
     * 实际项目中添加权限控制:
     * @PreAuthorize("hasAuthority('storage:file:list')")
     */
    @PostMapping("/list")
    public Object listFiles(@RequestBody FileQueryDTO queryDTO) {
        try {
            PageVO<FileVO> files = fileService.query(queryDTO);
            return createSuccessResult(files);
        } catch (Exception e) {
            log.error("查询文件列表失败", e);
            return createErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "查询文件列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建成功结果
     * 在实际项目中应使用common-core中的CommonResult
     */
    private Object createSuccessResult(Object data) {
        // 实际项目中：return CommonResult.success(data);
        return new Object() {
            public int getCode() { return 200; }
            public String getMessage() { return "success"; }
            public Object getData() { return data; }
        };
    }
    
    /**
     * 创建错误结果
     * 在实际项目中应使用common-core中的CommonResult
     */
    private Object createErrorResult(int code, String message) {
        // 实际项目中：return CommonResult.failed(code, message);
        return new Object() {
            public int getCode() { return code; }
            public String getMessage() { return message; }
            public Object getData() { return null; }
        };
    }
} 