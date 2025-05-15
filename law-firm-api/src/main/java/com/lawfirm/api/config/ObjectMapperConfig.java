package com.lawfirm.api.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ObjectMapper配置类
 * 解决系统中存在多个ObjectMapper Bean导致的冲突
 */
@Configuration
public class ObjectMapperConfig {

    /**
     * 将Web模块的ObjectMapper设为Primary
     * 这样在有多个ObjectMapper Bean的情况下，优先使用这个
     */
    @Bean
    @Primary
    public ObjectMapper primaryObjectMapper(@Qualifier("commonWebObjectMapper") ObjectMapper webMapper) {
        return webMapper;
    }
}