package com.lawfirm.auth.controller;

import com.lawfirm.auth.utils.SecurityUtils;
import com.lawfirm.model.auth.dto.auth.LoginDTO;
import com.lawfirm.model.auth.dto.auth.TokenDTO;
import com.lawfirm.model.auth.dto.user.UserCreateDTO;
import com.lawfirm.model.auth.service.AuthService;
import com.lawfirm.model.auth.service.UserService;
import com.lawfirm.model.auth.vo.LoginVO;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.model.log.enums.OperateTypeEnum;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.model.message.entity.system.SystemMessage;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.workflow.service.ProcessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import com.lawfirm.model.auth.constant.AuthConstants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 * 适配Vue-Vben-Admin风格
 */
@Slf4j
@Tag(name = "认证管理", description = "认证相关接口")
@RestController("authController")
@RequestMapping(AuthConstants.Api.AUTH)
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final UserService userService;
    
    @Autowired
    @Qualifier("coreAuditServiceImpl")
    private AuditService auditService;
    
    @Autowired
    @Qualifier("searchServiceImpl")
    private SearchService searchService;
    
    @Autowired
    @Qualifier("messageSender")
    private MessageSender messageSender;
    
    @Autowired
    @Qualifier("coreProcessServiceImpl")
    private ProcessService processService;
    
    /**
     * 用户登录
     */
    @Operation(summary = "用户登录", description = "用户登录接口")
    @PostMapping("/login")
    public CommonResult<LoginVO> login(@RequestBody @Valid LoginDTO loginDTO) {
        LoginVO loginVO = authService.login(loginDTO);
        return CommonResult.success(loginVO);
    }
    
    /**
     * 用户注册
     */
    @Operation(summary = "用户注册", description = "用户注册接口")
    @PostMapping("/register")
    public CommonResult<?> register(@RequestBody UserCreateDTO createDTO) {
        Long userId = userService.createUser(createDTO);
        
        // 记录审计日志
        AuditLogDTO auditLog = new AuditLogDTO();
        auditLog.setModule("auth");
        auditLog.setDescription("用户注册: " + createDTO.getUsername());
        auditLog.setOperateType(OperateTypeEnum.CREATE);
        auditLog.setBusinessType(BusinessTypeEnum.USER);
        auditLog.setOperatorName(createDTO.getUsername());
        auditLog.setStatus(0); // 正常状态
        auditService.logAsync(auditLog);
        
        // 发送注册成功消息通知
        SystemMessage message = new SystemMessage();
        message.setTitle("注册成功通知");
        message.setContent("恭喜您成功注册成为系统用户！");
        message.setReceivers(Arrays.asList(createDTO.getUsername()));
        messageSender.send(message);
        
        // 如果需要启动注册审核流程
        if (createDTO.getRoleIds() != null && createDTO.getRoleIds().length > 0) {
            // 可以启动一个工作流处理用户角色授权审批
            Map<String, Object> variables = new HashMap<>();
            variables.put("userId", userId);
            variables.put("username", createDTO.getUsername());
            variables.put("requestRoles", Arrays.toString(createDTO.getRoleIds()));
            try {
                processService.startProcessInstance("user-role-approve", userId.toString(), variables);
            } catch (Exception e) {
                log.error("启动用户角色审批流程失败", e);
                // 流程失败不影响用户注册
            }
        }
        
        return CommonResult.success();
    }
    
    /**
     * 刷新令牌
     */
    @Operation(summary = "刷新令牌", description = "刷新访问令牌接口")
    @PostMapping("/refreshToken")
    public CommonResult<TokenDTO> refreshToken(@RequestParam @NotBlank String refreshToken) {
        TokenDTO tokenDTO = authService.refreshToken(refreshToken);
        return CommonResult.success(tokenDTO);
    }
    
    /**
     * 用户登出
     */
    @Operation(summary = "用户登出", description = "用户登出接口")
    @GetMapping("/logout")
    public CommonResult<?> logout() {
        String username = SecurityUtils.getCurrentUsername();
        authService.logout(username);
        return CommonResult.success();
    }
    
    /**
     * 获取验证码
     */
    @Operation(summary = "获取验证码", description = "获取验证码接口")
    @GetMapping("/getCaptcha")
    public CommonResult<String> getCaptcha() {
        // 这里需要生成验证码并存储到Redis
        // 简化处理，直接返回成功
        log.info("获取验证码");
        return CommonResult.success("验证码已发送");
    }
}

