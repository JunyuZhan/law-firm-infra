package com.lawfirm.api.config;

import org.springframework.context.annotation.Configuration;
import org.mybatis.spring.annotation.MapperScan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import com.lawfirm.common.data.config.DataAutoConfiguration;

/**
 * Mapper 扫描配置
 * 在 API 层统一配置所有 Mapper 接口的扫描
 */
@Configuration
@AutoConfigureAfter(DataAutoConfiguration.class)
@MapperScan(
    basePackages = {
        "com.lawfirm.model.**.mapper",
        "com.lawfirm.modules.**.mapper"
    },
    markerInterface = BaseMapper.class,
    sqlSessionFactoryRef = "sqlSessionFactory"
)
public class MapperScanConfig {
    // 全局 Mapper 扫描配置
} 