package com.lawfirm.common.web.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.servlet.MultipartConfigElement;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UploadConfig.class)
class UploadConfigTest {

    @Autowired
    private UploadConfig uploadConfig;

    @Test
    void testMultipartConfigElement() {
        MultipartConfigElement config = uploadConfig.multipartConfigElement();
        
        assertNotNull(config);
        assertEquals(10 * 1024 * 1024, config.getMaxFileSize());
        assertEquals(20 * 1024 * 1024, config.getMaxRequestSize());
    }
} 