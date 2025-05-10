package com.lawfirm.common.web.config;

import com.lawfirm.common.web.filter.XssFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SecurityConfig.class)
class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;

    @Test
    void testXssFilter() {
        XssFilter filter = securityConfig.commonXssFilter();
        assertNotNull(filter);
    }
} 