package com.lawfirm.staff.client.finance;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.model.request.lawyer.contract.ContractPageRequest;
import com.lawfirm.staff.model.response.lawyer.contract.ContractResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 合同服务Feign客户端
 */
@FeignClient(name = "law-firm-finance", contextId = "contractClient", path = "/finance/contract")
public interface ContractClient {
    
    @GetMapping("/page")
    Result<PageResult<ContractResponse>> page(ContractPageRequest request);
    
    @GetMapping("/{id}")
    Result<ContractResponse> get(@PathVariable Long id);
    
    @GetMapping("/download/{id}")
    void download(@PathVariable Long id);
    
    @GetMapping("/preview/{id}")
    Result<String> preview(@PathVariable Long id);
} 