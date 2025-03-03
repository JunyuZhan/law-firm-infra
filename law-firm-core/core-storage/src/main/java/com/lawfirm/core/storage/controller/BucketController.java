package com.lawfirm.core.storage.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawfirm.model.storage.dto.bucket.BucketCreateDTO;
import com.lawfirm.model.storage.dto.bucket.BucketUpdateDTO;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.storage.vo.BucketVO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 存储桶控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/buckets")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService bucketService;
    
    /**
     * 创建存储桶
     * 
     * 实际项目中添加权限控制和防重复提交:
     * @PreAuthorize("hasAuthority('storage:bucket:create')")
     * @RepeatSubmit(interval = 5000)
     */
    @PostMapping
    public Object createBucket(@Valid @RequestBody BucketCreateDTO createDTO) {
        try {
            BucketVO bucketVO = bucketService.create(createDTO);
            return createSuccessResult(bucketVO);
        } catch (Exception e) {
            log.error("创建存储桶失败", e);
            return createErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "创建存储桶失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取存储桶信息
     * 
     * 实际项目中添加权限控制:
     * @PreAuthorize("hasAuthority('storage:bucket:info')")
     */
    @GetMapping("/{bucketId}")
    public Object getBucketInfo(@PathVariable Long bucketId) {
        try {
            BucketVO bucketVO = bucketService.getInfo(bucketId);
            return createSuccessResult(bucketVO);
        } catch (Exception e) {
            log.error("获取存储桶信息失败", e);
            return createErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取存储桶信息失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新存储桶信息
     * 
     * 实际项目中添加权限控制:
     * @PreAuthorize("hasAuthority('storage:bucket:update')")
     */
    @PutMapping("/{bucketId}")
    public Object updateBucket(@PathVariable Long bucketId, @Valid @RequestBody BucketUpdateDTO updateDTO) {
        try {
            // 确保ID一致
            updateDTO.setBucketId(bucketId);
            
            BucketVO bucketVO = bucketService.update(updateDTO);
            return createSuccessResult(bucketVO);
        } catch (Exception e) {
            log.error("更新存储桶失败", e);
            return createErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "更新存储桶失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除存储桶
     * 
     * 实际项目中添加权限控制:
     * @PreAuthorize("hasAuthority('storage:bucket:delete')")
     */
    @DeleteMapping("/{bucketId}")
    public Object deleteBucket(@PathVariable Long bucketId) {
        try {
            boolean success = bucketService.delete(bucketId);
            if (success) {
                return createSuccessResult(true);
            } else {
                return createErrorResult(HttpStatus.NOT_FOUND.value(), "存储桶不存在或删除失败");
            }
        } catch (Exception e) {
            log.error("删除存储桶失败", e);
            return createErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "删除存储桶失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取所有存储桶
     * 
     * 实际项目中添加权限控制:
     * @PreAuthorize("hasAuthority('storage:bucket:list')")
     */
    @GetMapping
    public Object listAllBuckets() {
        try {
            List<BucketVO> buckets = bucketService.listAll();
            return createSuccessResult(buckets);
        } catch (Exception e) {
            log.error("获取存储桶列表失败", e);
            return createErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取存储桶列表失败: " + e.getMessage());
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