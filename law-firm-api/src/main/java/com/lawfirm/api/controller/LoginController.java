package com.lawfirm.api.controller;

import com.lawfirm.api.adaptor.auth.AuthAdaptor;
import com.lawfirm.api.common.ResponseResult;
import com.lawfirm.model.auth.dto.auth.LoginDTO;
import com.lawfirm.model.auth.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 */
@Tag(name = "认证管理")
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthAdaptor authAdaptor;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public ResponseResult<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseResult.success(authAdaptor.login(loginDTO));
    }
} 