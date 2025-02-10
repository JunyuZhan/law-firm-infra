package com.lawfirm.staff.client.clerk;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.clerk.fallback.TaskClientFallbackFactory;
import com.lawfirm.staff.model.request.clerk.task.TaskCreateRequest;
import com.lawfirm.staff.model.request.clerk.task.TaskPageRequest;
import com.lawfirm.staff.model.request.clerk.task.TaskUpdateRequest;
import com.lawfirm.staff.model.response.clerk.task.TaskResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

/**
 * 任务服务Feign客户端
 */
@FeignClient(name = "law-firm-task", contextId = "task", path = "/task", fallbackFactory = TaskClientFallbackFactory.class)
public interface TaskClient {
    
    @GetMapping("/page")
    Result<PageResult<TaskResponse>> page(@SpringQueryMap TaskPageRequest request);
    
    @PostMapping
    Result<Void> create(@RequestBody TaskCreateRequest request);
    
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody TaskUpdateRequest request);
    
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
    
    @GetMapping("/{id}")
    Result<TaskResponse> get(@PathVariable("id") Long id);
    
    @GetMapping("/my")
    Result<PageResult<TaskResponse>> getMyTasks(@SpringQueryMap TaskPageRequest request);
    
    @GetMapping("/department")
    Result<PageResult<TaskResponse>> getDepartmentTasks(@SpringQueryMap TaskPageRequest request);
    
    @PutMapping("/{id}/status")
    Result<Void> updateStatus(@PathVariable("id") Long id, @RequestParam Integer status);
} 