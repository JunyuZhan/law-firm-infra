package com.lawfirm.common.core.config;

import com.lawfirm.common.core.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class CoreAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(CoreAutoConfiguration.class));

    @Test
    void shouldLoadDefaultConfiguration() {
        contextRunner.run(context -> {
            // 验证自动配置类被加载
            assertThat(context).hasSingleBean(CoreAutoConfiguration.class);
            
            // 验证异常处理器被注册
            assertThat(context).hasSingleBean(GlobalExceptionHandler.class);
        });
    }

    @Test
    void shouldScanBasePackages() {
        contextRunner.run(context -> {
            // 验证组件扫描是否生效
            assertThat(context).hasSingleBean(GlobalExceptionHandler.class);
            // 可以添加更多组件的验证
        });
    }
} 