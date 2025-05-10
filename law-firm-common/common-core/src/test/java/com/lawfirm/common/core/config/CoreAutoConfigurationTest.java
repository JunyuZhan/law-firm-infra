package com.lawfirm.common.core.config;

import com.lawfirm.common.core.config.properties.AppProperties;
import com.lawfirm.common.core.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class CoreAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withPropertyValues("law-firm.common.app.name=测试应用")
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
    
    @Test
    void shouldLoadAppProperties() {
        contextRunner
            .withPropertyValues(
                "law-firm.common.app.version=1.0.0-TEST",
                "law-firm.common.app.description=单元测试环境"
            )
            .run(context -> {
                // 验证AppProperties是否正确加载
                assertThat(context).hasSingleBean(AppProperties.class);
                AppProperties properties = context.getBean(AppProperties.class);
                assertThat(properties.getName()).isEqualTo("测试应用");
                assertThat(properties.getVersion()).isEqualTo("1.0.0-TEST");
                assertThat(properties.getDescription()).isEqualTo("单元测试环境");
            });
    }
} 