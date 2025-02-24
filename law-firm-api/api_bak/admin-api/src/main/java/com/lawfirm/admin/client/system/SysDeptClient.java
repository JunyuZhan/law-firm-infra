package com.lawfirm.admin.client.system;

import com.lawfirm.model.base.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "law-firm-system", path = "/system/dept")
public interface SysDeptClient {
    
    @GetMapping("/list")
    ApiResponse<?> listDepts();
    
    @PostMapping
    ApiResponse<?> createDept(@RequestBody Object request);
    
    @PutMapping("/{id}")
    ApiResponse<?> updateDept(@PathVariable("id") Long id, @RequestBody Object request);
    
    @DeleteMapping("/{id}")
    ApiResponse<?> deleteDept(@PathVariable("id") Long id);
    
    @GetMapping("/{id}")
    ApiResponse<?> getDept(@PathVariable("id") Long id);
    
    @PutMapping("/{id}/status")
    ApiResponse<?> updateDeptStatus(@PathVariable("id") Long id, @RequestBody Object request);
} 
import com.lawfirm.model.base.enums.BaseEnum  
