package com.lawfirm.admin.client.system;

import com.lawfirm.model.base.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "law-firm-system", path = "/system/menu")
public interface SysMenuClient {
    
    @GetMapping("/list")
    ApiResponse<?> listMenus();
    
    @PostMapping
    ApiResponse<?> createMenu(@RequestBody Object request);
    
    @PutMapping("/{id}")
    ApiResponse<?> updateMenu(@PathVariable("id") Long id, @RequestBody Object request);
    
    @DeleteMapping("/{id}")
    ApiResponse<?> deleteMenu(@PathVariable("id") Long id);
} 
import com.lawfirm.model.base.enums.BaseEnum  
