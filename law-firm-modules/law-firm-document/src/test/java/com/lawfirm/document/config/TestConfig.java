package com.lawfirm.document.config;

import com.lawfirm.document.service.DocumentStorageService;
import com.lawfirm.document.service.IDocumentConversionService;
import com.lawfirm.document.service.impl.DocumentConversionServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {
    
    @MockBean
    private DocumentStorageService documentStorageService;
    
    @Bean
    @Primary
    public IDocumentConversionService documentConversionService() {
        return new DocumentConversionServiceImpl();
    }
} 