package com.lawfirm.staff.client.clerk;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.clerk.fallback.ArchiveClientFallback;
import com.lawfirm.staff.model.request.clerk.archive.*;
import com.lawfirm.staff.model.response.clerk.archive.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 档案管理Feign客户端
 */
@FeignClient(
    name = "law-firm-archive",
    contextId = "archiveClient",
    path = "/archive",
    fallback = ArchiveClientFallback.class
)
public interface ArchiveClient {
    
    @GetMapping("/page")
    Result<PageResult<ArchiveResponse>> page(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "orderType", required = false) String orderType);
    
    @PostMapping
    Result<Void> create(@RequestBody ArchiveCreateRequest request);
    
    @PutMapping
    Result<Void> update(@RequestBody ArchiveUpdateRequest request);
    
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
    
    @GetMapping("/{id}")
    Result<ArchiveResponse> get(@PathVariable("id") Long id);
    
    @PostMapping("/upload")
    Result<String> upload(@RequestParam("file") MultipartFile file);
    
    @GetMapping("/download/{fileId}")
    void download(@PathVariable("fileId") Long fileId);
    
    @GetMapping("/categories")
    Result<List<ArchiveCategoryResponse>> getCategories();
    
    @PostMapping("/batch/delete")
    Result<Void> batchDelete(@RequestBody List<Long> ids);
    
    @GetMapping("/statistics")
    Result<ArchiveStatisticsResponse> getStatistics();
    
    @PostMapping("/borrow")
    Result<Void> borrow(@RequestBody ArchiveBorrowRequest request);
    
    @PostMapping("/return/{id}")
    Result<Void> returnArchive(@PathVariable("id") Long id);
    
    @GetMapping("/borrow/records")
    Result<PageResult<ArchiveBorrowResponse>> getBorrowRecords(@SpringQueryMap ArchiveBorrowPageRequest request);
} 