package com.lawfirm.staff.client.finance;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.model.request.finance.charge.ChargeCreateRequest;
import com.lawfirm.staff.model.request.finance.charge.ChargePageRequest;
import com.lawfirm.staff.model.request.finance.charge.ChargeUpdateRequest;
import com.lawfirm.staff.model.response.finance.charge.ChargeResponse;
import com.lawfirm.staff.model.response.finance.charge.ChargeStatisticsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

/**
 * 收费服务Feign客户端
 */
@FeignClient(name = "law-firm-finance", contextId = "charge", path = "/charge", fallbackFactory = ChargeClientFallbackFactory.class)
public interface ChargeClient {
    
    @GetMapping("/page")
    Result<PageResult<ChargeResponse>> page(@SpringQueryMap ChargePageRequest request);
    
    @PostMapping
    Result<Void> create(@RequestBody ChargeCreateRequest request);
    
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody ChargeUpdateRequest request);
    
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
    
    @GetMapping("/{id}")
    Result<ChargeResponse> get(@PathVariable("id") Long id);
    
    @PostMapping("/{id}/confirm")
    Result<Void> confirm(@PathVariable("id") Long id);
    
    @PostMapping("/{id}/cancel")
    Result<Void> cancel(@PathVariable("id") Long id, @RequestParam String reason);
    
    @PutMapping("/{id}/audit")
    Result<Void> audit(@PathVariable("id") Long id, @RequestParam Integer status, @RequestParam String remark);
    
    @GetMapping("/export")
    void export(@SpringQueryMap ChargePageRequest request);
    
    @GetMapping("/statistics")
    Result<ChargeStatisticsResponse> getStats(@SpringQueryMap ChargePageRequest request);
} 