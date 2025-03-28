package com.lawfirm.api.controller;

import com.lawfirm.api.adaptor.auth.AuthAdaptor;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.dto.auth.LoginDTO;
import com.lawfirm.model.auth.dto.auth.TokenDTO;
import com.lawfirm.model.auth.service.AuthService;
import com.lawfirm.model.auth.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 * 适配Vue-Vben-Admin
 */
@Tag(name = "认证管理")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final AuthAdaptor authAdaptor;
    private final AuthService authService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public CommonResult<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        return CommonResult.success(authAdaptor.login(loginDTO));
    }
    
    /**
     * 刷新Token
     */
    @Operation(summary = "刷新Token")
    @PostMapping("/refreshToken")
    public CommonResult<Map<String, String>> refreshToken(@RequestBody Map<String, String> params) {
        String refreshToken = params.get("refreshToken");
        TokenDTO tokenDTO = authService.refreshToken(refreshToken);
        
        Map<String, String> result = new HashMap<>();
        result.put("token", tokenDTO.getAccessToken());
        result.put("refreshToken", tokenDTO.getRefreshToken());
        
        return CommonResult.success(result);
    }
} 