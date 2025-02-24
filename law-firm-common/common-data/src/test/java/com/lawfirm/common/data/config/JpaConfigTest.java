package com.lawfirm.common.data.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfig.class)
@TestPropertySource(properties = {
    "jpa.entity.packages=com.lawfirm.**.entity",
    "jpa.repository.packages=com.lawfirm.**.repository"
})
@ActiveProfiles("test")
class JpaConfigTest {

    @Autowired
    private AuditorAware<String> auditorAware;

    @Test
    void contextLoads() {
        // 测试Spring上下文是否正确加载
    }

    @Test
    void auditorAware_ShouldBeConfigured() {
        assertNotNull(auditorAware, "Auditor aware should be configured");
    }

    @Test
    void auditorAware_ShouldReturnSystem_WhenNoAuthentication() {
        SecurityContextHolder.clearContext();
        assertEquals("system", auditorAware.getCurrentAuditor().get());
    }

    @Test
    void auditorAware_ShouldReturnUsername_WhenAuthenticated() {
        // 设置安全上下文
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("testUser");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // 验证审计者
        Optional<String> auditor = auditorAware.getCurrentAuditor();
        assertEquals("testUser", auditor.get());

        // 清理上下文
        SecurityContextHolder.clearContext();
    }
} 