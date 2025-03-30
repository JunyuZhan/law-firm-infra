package com.lawfirm.api.controller;

import com.lawfirm.api.VbenResult;
import com.lawfirm.model.system.vo.MenuVO;
import com.lawfirm.model.auth.dto.auth.LoginDTO;
import com.lawfirm.model.auth.vo.LoginVO;
import com.lawfirm.model.auth.vo.UserInfoVO;
import com.lawfirm.model.auth.service.AuthService;
import com.lawfirm.model.auth.service.UserService;
import com.lawfirm.model.auth.dto.auth.TokenDTO;
import com.lawfirm.auth.utils.SecurityUtils;
import com.lawfirm.model.system.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Vue-Vben-Admin API控制器
 * 直接提供符合Vben期望格式的接口
 */
@Slf4j
@Tag(name = "Vue-Vben-Admin API")
@RestController("vbenApiController")
@RequestMapping("")
@RequiredArgsConstructor
public class VbenApiController {

    private final MenuService menuService;
    private final AuthService authService;
    private final UserService userService;

    /**
     * 登录接口
     */
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public VbenResult<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = authService.login(loginDTO);
        return VbenResult.success(loginVO);
    }

    /**
     * 获取用户信息
     */
    @Operation(summary = "获取用户信息")
    @GetMapping("/getUserInfo")
    public VbenResult<UserInfoVO> getUserInfo() {
        Long userId = SecurityUtils.getCurrentUserId();
        UserInfoVO userInfoVO = userService.getUserInfo(userId);
        return VbenResult.success(userInfoVO);
    }

    /**
     * 获取用户菜单
     */
    @Operation(summary = "获取用户菜单")
    @GetMapping("/getMenuList")
    public VbenResult<List<MenuVO>> getMenuList() {
        List<MenuVO> menus = menuService.getUserMenus();
        return VbenResult.success(menus);
    }

    /**
     * 获取权限代码
     */
    @Operation(summary = "获取权限代码")
    @GetMapping("/getPermCode")
    public VbenResult<List<String>> getPermCode() {
        Long userId = SecurityUtils.getCurrentUserId();
        UserInfoVO userInfo = userService.getUserInfo(userId);
        return VbenResult.success(userInfo.getPermissions());
    }

    /**
     * 刷新Token
     */
    @Operation(summary = "刷新Token")
    @PostMapping("/refreshToken")
    public VbenResult<Map<String, String>> refreshToken(@RequestBody Map<String, String> params) {
        String refreshToken = params.get("refreshToken");
        TokenDTO tokenDTO = authService.refreshToken(refreshToken);
        
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", tokenDTO.getAccessToken());
        tokenMap.put("refreshToken", tokenDTO.getRefreshToken());
        
        return VbenResult.success(tokenMap);
    }
    
    /**
     * 退出登录
     */
    @Operation(summary = "退出登录")
    @GetMapping("/logout")
    public VbenResult<Boolean> logout() {
        String username = SecurityUtils.getCurrentUsername();
        authService.logout(username);
        return VbenResult.success(true);
    }
} 