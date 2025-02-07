package com.lawfirm.admin.client;

import com.lawfirm.admin.model.ApiResult;
import com.lawfirm.admin.model.request.auth.user.UserPageRequest;
import com.lawfirm.admin.model.request.auth.role.RolePageRequest;
import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.admin.model.request.auth.user.CreateUserRequest;
import com.lawfirm.admin.model.request.auth.user.UpdateUserRequest;
import com.lawfirm.admin.model.request.auth.role.CreateRoleRequest;
import com.lawfirm.admin.model.request.auth.role.UpdateRoleRequest;
import com.lawfirm.admin.model.request.auth.menu.CreateMenuRequest;
import com.lawfirm.admin.model.request.auth.menu.UpdateMenuRequest;
import com.lawfirm.admin.model.response.auth.user.UserResponse;
import com.lawfirm.admin.model.response.auth.role.RoleResponse;
import com.lawfirm.admin.model.response.auth.menu.MenuResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 认证服务Feign客户端
 */
@FeignClient(name = "law-firm-auth", contextId = "auth", path = "/auth")
public interface AuthClient {
    
    // 用户相关接口
    @PostMapping("/user")
    Result<Void> createUser(@RequestBody CreateUserRequest request);
    
    @PutMapping("/user/{id}")
    Result<Void> updateUser(@PathVariable("id") Long id, @RequestBody UpdateUserRequest request);
    
    @DeleteMapping("/user/{id}")
    Result<Void> deleteUser(@PathVariable("id") Long id);
    
    @GetMapping("/user/{id}")
    Result<UserResponse> getUser(@PathVariable("id") Long id);
    
    @GetMapping("/user/page")
    Result<PageResult<UserResponse>> pageUsers(@SpringQueryMap UserPageRequest request);
    
    @PutMapping("/user/{id}/password/reset")
    Result<Void> resetPassword(@PathVariable("id") Long id);
    
    @PutMapping("/user/password")
    Result<Void> updatePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword);
    
    @PutMapping("/user/profile")
    Result<Void> updateProfile(@RequestBody UpdateUserRequest request);
    
    @GetMapping("/user/profile")
    Result<UserResponse> getProfile();
    
    @PutMapping("/user/{id}/roles")
    Result<Void> assignRoles(@PathVariable("id") Long id, @RequestBody List<Long> roleIds);
    
    @GetMapping("/user/check/{username}")
    Result<Boolean> checkUsernameExists(@PathVariable("username") String username);
    
    // 角色相关接口
    @PostMapping("/role")
    Result<Void> createRole(@RequestBody CreateRoleRequest request);
    
    @PutMapping("/role/{id}")
    Result<Void> updateRole(@PathVariable("id") Long id, @RequestBody UpdateRoleRequest request);
    
    @DeleteMapping("/role/{id}")
    Result<Void> deleteRole(@PathVariable("id") Long id);
    
    @GetMapping("/role/{id}")
    Result<RoleResponse> getRole(@PathVariable("id") Long id);
    
    @GetMapping("/role/page")
    Result<PageResult<RoleResponse>> pageRoles(@SpringQueryMap RolePageRequest request);
    
    @PutMapping("/role/{id}/menus")
    Result<Void> assignMenus(@PathVariable("id") Long id, @RequestBody List<Long> menuIds);
    
    // 菜单相关接口
    @PostMapping("/menu")
    Result<Void> createMenu(@RequestBody CreateMenuRequest request);
    
    @PutMapping("/menu/{id}")
    Result<Void> updateMenu(@PathVariable("id") Long id, @RequestBody UpdateMenuRequest request);
    
    @DeleteMapping("/menu/{id}")
    Result<Void> deleteMenu(@PathVariable("id") Long id);
    
    @GetMapping("/menu/{id}")
    Result<MenuResponse> getMenu(@PathVariable("id") Long id);
    
    @GetMapping("/menu/tree")
    Result<List<MenuResponse>> getMenuTree();
    
    @GetMapping("/menu/user")
    Result<List<MenuResponse>> getUserMenus();
} 