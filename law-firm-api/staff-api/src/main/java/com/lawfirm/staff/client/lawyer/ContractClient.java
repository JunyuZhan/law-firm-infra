package com.lawfirm.staff.client.lawyer;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.model.request.lawyer.contract.ContractCreateRequest;
import com.lawfirm.staff.model.request.lawyer.contract.ContractPageRequest;
import com.lawfirm.staff.model.request.lawyer.contract.ContractUpdateRequest;
import com.lawfirm.staff.model.response.lawyer.contract.ContractResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

/**
 * 合同服务Feign客户端
 */
@FeignClient(name = "law-firm-contract", contextId = "contract", path = "/contract", fallbackFactory = ContractClientFallbackFactory.class)
public interface ContractClient {
    
    @GetMapping("/page")
    Result<PageResult<ContractResponse>> page(@SpringQueryMap ContractPageRequest request);
    
    @PostMapping
    Result<Void> create(@RequestBody ContractCreateRequest request);
    
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody ContractUpdateRequest request);
    
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
    
    @GetMapping("/{id}")
    Result<ContractResponse> get(@PathVariable("id") Long id);
    
    @PostMapping("/{id}/submit")
    Result<Void> submit(@PathVariable("id") Long id);
    
    @PostMapping("/{id}/approve")
    Result<Void> approve(@PathVariable("id") Long id);
    
    @PostMapping("/{id}/reject")
    Result<Void> reject(@PathVariable("id") Long id, @RequestParam String reason);
    
    @PostMapping("/{id}/terminate")
    Result<Void> terminate(@PathVariable("id") Long id, @RequestParam String reason);
} 