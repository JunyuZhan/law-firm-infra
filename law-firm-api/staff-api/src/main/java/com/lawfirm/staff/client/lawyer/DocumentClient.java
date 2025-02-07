package com.lawfirm.staff.client;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.model.request.lawyer.CreateDocumentRequest;
import com.lawfirm.staff.model.request.lawyer.DocumentPageRequest;
import com.lawfirm.staff.model.request.lawyer.UpdateDocumentRequest;
import com.lawfirm.staff.model.response.lawyer.DocumentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文档服务Feign客户端
 */
@FeignClient(name = "law-firm-document", path = "/document")
public interface DocumentClient {
    
    @GetMapping("/page")
    Result<PageResult<DocumentResponse>> page(@SpringQueryMap DocumentPageRequest request);
    
    @PostMapping
    Result<Void> add(@RequestBody CreateDocumentRequest request);
    
    @PutMapping
    Result<Void> update(@RequestBody UpdateDocumentRequest request);
    
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
    
    @GetMapping("/{id}")
    Result<DocumentResponse> get(@PathVariable("id") Long id);
    
    @GetMapping("/my")
    Result<PageResult<DocumentResponse>> getMyDocuments(@SpringQueryMap DocumentPageRequest request);
    
    @GetMapping("/department")
    Result<PageResult<DocumentResponse>> getDepartmentDocuments(@SpringQueryMap DocumentPageRequest request);
    
    @PostMapping("/upload")
    Result<String> upload(@RequestPart("file") MultipartFile file);
    
    @GetMapping("/download/{id}")
    void download(@PathVariable("id") Long id);
    
    @PostMapping("/preview/{id}")
    Result<String> preview(@PathVariable("id") Long id);
    
    @PostMapping("/share")
    Result<Void> share(@RequestBody CreateDocumentRequest request);
} 