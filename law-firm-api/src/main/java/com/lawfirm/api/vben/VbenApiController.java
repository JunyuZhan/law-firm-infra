package com.lawfirm.api.vben;

import com.lawfirm.api.adaptor.system.MenuAdaptor;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.system.vo.MenuVO;
import com.lawfirm.model.auth.dto.auth.LoginDTO;
import com.lawfirm.model.auth.vo.LoginVO;
import com.lawfirm.model.auth.vo.UserInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 适配Vue-Vben-Admin的API控制器
 * 提供符合Vben期望格式的接口
 */
@Slf4j
@Tag(name = "Vue-Vben-Admin API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VbenApiController {

    private final MenuAdaptor menuAdaptor;
    private final VbenAdaptor vbenAdaptor;

    /**
     * 登录接口
     */
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public VbenResult<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        CommonResult<LoginVO> result = vbenAdaptor.login(loginDTO);
        return VbenResult.from(result);
    }

    /**
     * 获取用户信息
     */
    @Operation(summary = "获取用户信息")
    @GetMapping("/getUserInfo")
    public VbenResult<UserInfoVO> getUserInfo() {
        CommonResult<UserInfoVO> result = vbenAdaptor.getUserInfo();
        return VbenResult.from(result);
    }

    /**
     * 获取用户菜单
     */
    @Operation(summary = "获取用户菜单")
    @GetMapping("/getMenuList")
    public VbenResult<List<MenuVO>> getMenuList() {
        CommonResult<List<MenuVO>> result = CommonResult.success(menuAdaptor.getUserMenus());
        return VbenResult.from(result);
    }

    /**
     * 获取权限代码
     */
    @Operation(summary = "获取权限代码")
    @GetMapping("/getPermCode")
    public VbenResult<List<String>> getPermCode() {
        CommonResult<List<String>> result = vbenAdaptor.getPermissionCodes();
        return VbenResult.from(result);
    }

    /**
     * 刷新Token
     */
    @Operation(summary = "刷新Token")
    @PostMapping("/refreshToken")
    public VbenResult<Map<String, String>> refreshToken(@RequestBody Map<String, String> params) {
        String refreshToken = params.get("refreshToken");
        CommonResult<Map<String, String>> result = vbenAdaptor.refreshToken(refreshToken);
        return VbenResult.from(result);
    }
    
    /**
     * 退出登录
     */
    @Operation(summary = "退出登录")
    @GetMapping("/logout")
    public VbenResult<Boolean> logout() {
        CommonResult<Boolean> result = vbenAdaptor.logout();
        return VbenResult.from(result);
    }
} 