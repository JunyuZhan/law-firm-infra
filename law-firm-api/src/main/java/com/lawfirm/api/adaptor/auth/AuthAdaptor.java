package com.lawfirm.api.adaptor.auth;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.auth.model.dto.LoginDTO;
import com.lawfirm.auth.model.vo.LoginVO;
import com.lawfirm.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 认证适配器
 */
@Component
@RequiredArgsConstructor
public class AuthAdaptor extends BaseAdaptor {

    private final AuthService authService;

    /**
     * 用户登录
     */
    public LoginVO login(LoginDTO loginDTO) {
        return convert(authService.login(loginDTO), LoginVO.class);
    }
} 