package com.lawfirm.admin.client.system;

import com.lawfirm.model.base.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "law-firm-system", path = "/system/role")
public interface SysRoleClient {
    
    @GetMapping("/page")
    ApiResponse<?> pageRoles(@RequestBody Object request);
    
    @PostMapping
    ApiResponse<?> createRole(@RequestBody Object request);
    
    @PutMapping("/{id}")
    ApiResponse<?> updateRole(@PathVariable("id") Long id, @RequestBody Object request);
    
    @DeleteMapping("/{id}")
    ApiResponse<?> deleteRole(@PathVariable("id") Long id);
    
    @GetMapping("/{id}")
    ApiResponse<?> getRole(@PathVariable("id") Long id);
    
    @GetMapping("/list")
    ApiResponse<?> listRoles();
    
    @PutMapping("/{id}/status")
    ApiResponse<?> updateRoleStatus(@PathVariable("id") Long id, @RequestBody Object request);
    
    @PutMapping("/{id}/menu")
    ApiResponse<?> assignMenu(@PathVariable("id") Long id, @RequestBody Object request);
} 