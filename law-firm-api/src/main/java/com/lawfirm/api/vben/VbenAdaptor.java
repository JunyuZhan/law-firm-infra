package com.lawfirm.api.vben;

import com.lawfirm.api.adaptor.auth.AuthAdaptor;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.dto.auth.LoginDTO;
import com.lawfirm.model.auth.dto.auth.TokenDTO;
import com.lawfirm.model.auth.service.AuthService;
import com.lawfirm.model.auth.service.UserService;
import com.lawfirm.model.auth.vo.LoginVO;
import com.lawfirm.model.auth.vo.UserInfoVO;
import com.lawfirm.model.auth.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Vue-Vben-Admin适配器
 * 负责将现有系统API转换为Vben期望的格式
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VbenAdaptor {

    private final AuthAdaptor authAdaptor;
    private final AuthService authService;
    private final UserService userService;
    
    /**
     * 登录
     */
    public CommonResult<LoginVO> login(LoginDTO loginDTO) {
        LoginVO loginVO = authAdaptor.login(loginDTO);
        return CommonResult.success(loginVO);
    }
    
    /**
     * 获取用户信息
     */
    public CommonResult<UserInfoVO> getUserInfo() {
        Long currentUserId = getCurrentUserId();
        if (currentUserId == null) {
            return CommonResult.unauthorized("用户未登录");
        }
        
        UserInfoVO userInfo = userService.getUserInfo(currentUserId);
        return CommonResult.success(userInfo);
    }
    
    /**
     * 获取权限代码列表
     */
    public CommonResult<List<String>> getPermissionCodes() {
        Long currentUserId = getCurrentUserId();
        if (currentUserId == null) {
            return CommonResult.unauthorized("用户未登录");
        }
        
        UserInfoVO userInfo = userService.getUserInfo(currentUserId);
        List<String> permCodes = userInfo.getPermissions();
        return CommonResult.success(permCodes);
    }
    
    /**
     * 刷新Token
     */
    public CommonResult<Map<String, String>> refreshToken(String refreshToken) {
        TokenDTO tokenDTO = authService.refreshToken(refreshToken);
        
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", tokenDTO.getAccessToken());
        tokenMap.put("refreshToken", tokenDTO.getRefreshToken());
        
        return CommonResult.success(tokenMap);
    }
    
    /**
     * 退出登录
     */
    public CommonResult<Boolean> logout() {
        // 获取当前用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        authService.logout(username);
        return CommonResult.success(true);
    }
    
    /**
     * 获取当前用户ID
     */
    private Long getCurrentUserId() {
        // 从Spring Security Context中获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User user = userService.getByUsername(username);
                if (user != null) {
                    return user.getId();
                }
            }
        }
        throw new RuntimeException("当前用户未登录或未找到");
    }
} 