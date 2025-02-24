package com.lawfirm.admin.controller.system;

import com.lawfirm.admin.client.system.SysMenuClient;
import com.lawfirm.model.base.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/system/menu")
public class SysMenuController {
    
    @Autowired
    private SysMenuClient sysMenuClient;  // 使用Feign Client
    
    @GetMapping("/list")
    public ApiResponse<?> list() {
        return sysMenuClient.listMenus();
    }
    
    @PostMapping
    public ApiResponse<?> create(@RequestBody Object request) {
        return sysMenuClient.createMenu(request);
    }
    
    @PutMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Long id, @RequestBody Object request) {
        return sysMenuClient.updateMenu(id, request);
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {
        return sysMenuClient.deleteMenu(id);
    }
} 
import com.lawfirm.model.base.enums.BaseEnum  
