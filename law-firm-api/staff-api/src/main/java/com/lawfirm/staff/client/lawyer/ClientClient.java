package com.lawfirm.staff.client.lawyer;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.model.request.lawyer.client.ClientCreateRequest;
import com.lawfirm.staff.model.request.lawyer.client.ClientPageRequest;
import com.lawfirm.staff.model.request.lawyer.client.ClientUpdateRequest;
import com.lawfirm.staff.model.response.lawyer.client.ClientResponse;
import com.lawfirm.model.client.vo.ClientVO;
import com.lawfirm.model.base.response.ApiResponse;
import com.lawfirm.model.base.query.PageResult;
import com.lawfirm.staff.model.response.lawyer.client.ClientStatsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 律师-客户服务Feign客户端
 */
@FeignClient(name = "law-firm-core", contextId = "lawyerClientClient", path = "/lawyer/clients")
public interface ClientClient {
    
    @GetMapping("/page")
    ApiResponse<PageResult<ClientResponse>> page(ClientPageRequest request);
    
    @PostMapping
    Result<ClientResponse> create(@RequestBody ClientCreateRequest request);
    
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody ClientUpdateRequest request);
    
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
    
    @GetMapping("/{id}")
    ApiResponse<ClientResponse> getById(@PathVariable Long id);

    @GetMapping("/list")
    Result<PageResult<ClientResponse>> list(@RequestParam(value = "keyword", required = false) String keyword);

    @GetMapping("/my")
    Result<List<ClientVO>> getMyClients();

    @GetMapping("/department")
    Result<List<ClientVO>> getDepartmentClients();

    @PostMapping("/conflict-check")
    Result<Boolean> conflictCheck(@RequestBody ClientCreateRequest request);

    @GetMapping("/export")
    void export(@SpringQueryMap ClientPageRequest request);

    @GetMapping("/stats")
    ApiResponse<ClientStatsResponse> getStats();
} 