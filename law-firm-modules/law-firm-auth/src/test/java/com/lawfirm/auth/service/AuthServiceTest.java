package com.lawfirm.auth.service;

import com.lawfirm.auth.model.LoginUser;
import com.lawfirm.auth.repository.UserRepository;
import com.lawfirm.auth.service.impl.AuthServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.model.auth.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    private User mockUser;
    private LoginUser mockLoginUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // 准备测试数据
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
        mockUser.setPassword("encoded_password");
        mockUser.setEnabled(true);
        
        mockLoginUser = new LoginUser(mockUser, null);
        mockLoginUser.setToken("test_token");
        
        // Mock Redis操作
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void loginSuccess() {
        // 准备测试数据
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("password", "encoded_password")).thenReturn(true);
        
        // 执行测试
        LoginUser result = authService.login("testuser", "password");
        
        // 验证结果
        assertNotNull(result);
        assertEquals(mockUser.getId(), result.getUserId());
        assertEquals(mockUser.getUsername(), result.getUsername());
        assertNotNull(result.getToken());
        
        // 验证Redis存储
        verify(valueOperations).set(anyString(), any(LoginUser.class), anyLong(), any());
    }

    @Test
    void loginFailUserNotFound() {
        // 准备测试数据
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        
        // 执行测试并验证异常
        assertThrows(BusinessException.class, () -> authService.login("testuser", "password"));
    }

    @Test
    void loginFailWrongPassword() {
        // 准备测试数据
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("wrong_password", "encoded_password")).thenReturn(false);
        
        // 执行测试并验证异常
        assertThrows(BusinessException.class, () -> authService.login("testuser", "wrong_password"));
    }

    @Test
    void loginFailUserDisabled() {
        // 准备测试数据
        mockUser.setEnabled(false);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("password", "encoded_password")).thenReturn(true);
        
        // 执行测试并验证异常
        assertThrows(BusinessException.class, () -> authService.login("testuser", "password"));
    }

    @Test
    void logoutSuccess() {
        // 执行测试
        authService.logout("Bearer test_token");
        
        // 验证Redis删除
        verify(redisTemplate).delete("auth:token:test_token");
    }

    @Test
    void verifyTokenSuccess() {
        // 准备测试数据
        when(valueOperations.get("auth:token:test_token")).thenReturn(mockLoginUser);
        mockLoginUser.setExpireTime(System.currentTimeMillis() + 3600000); // 1小时后过期
        
        // 执行测试
        LoginUser result = authService.verifyToken("Bearer test_token");
        
        // 验证结果
        assertNotNull(result);
        assertEquals(mockLoginUser.getToken(), result.getToken());
    }

    @Test
    void verifyTokenFailExpired() {
        // 准备测试数据
        when(valueOperations.get("auth:token:test_token")).thenReturn(mockLoginUser);
        mockLoginUser.setExpireTime(System.currentTimeMillis() - 1000); // 已过期
        
        // 执行测试并验证异常
        assertThrows(BusinessException.class, () -> authService.verifyToken("Bearer test_token"));
        
        // 验证Redis删除
        verify(redisTemplate).delete("auth:token:test_token");
    }

    @Test
    void verifyTokenFailNotFound() {
        // 准备测试数据
        when(valueOperations.get("auth:token:test_token")).thenReturn(null);
        
        // 执行测试并验证异常
        assertThrows(BusinessException.class, () -> authService.verifyToken("Bearer test_token"));
    }
} 