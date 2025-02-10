package com.lawfirm.staff.client.lawyer;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.model.request.lawyer.conflict.ConflictCheckRequest;
import com.lawfirm.staff.model.request.lawyer.conflict.ConflictPageRequest;
import com.lawfirm.staff.model.response.lawyer.conflict.ConflictResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

/**
 * 冲突检查服务Feign客户端
 */
@FeignClient(name = "law-firm-conflict", contextId = "conflict", path = "/conflict", fallbackFactory = ConflictClientFallbackFactory.class)
public interface ConflictClient {
    
    @GetMapping("/page")
    Result<PageResult<ConflictResponse>> page(@SpringQueryMap ConflictPageRequest request);
    
    @PostMapping("/check")
    Result<Boolean> check(@RequestBody ConflictCheckRequest request);
    
    @GetMapping("/history")
    Result<PageResult<ConflictResponse>> getHistory(@SpringQueryMap ConflictPageRequest request);
    
    @GetMapping("/{id}")
    Result<ConflictResponse> get(@PathVariable("id") Long id);
    
    @PostMapping("/resolve")
    Result<Void> resolve(@RequestBody ConflictCheckRequest request);
} 