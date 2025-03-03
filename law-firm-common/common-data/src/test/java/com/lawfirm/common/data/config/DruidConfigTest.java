package com.lawfirm.common.data.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import jakarta.servlet.Filter;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.druid.initial-size=5",
    "spring.datasource.druid.min-idle=10",
    "spring.datasource.druid.max-active=20",
    "spring.datasource.druid.max-wait=60000",
    "spring.datasource.druid.stat-view-servlet.enabled=true",
    "spring.datasource.druid.stat-view-servlet.url-pattern=/druid/*"
})
class DruidConfigTest {

    @TestConfiguration
    static class DruidTestConfig {
        @Bean
        public DruidStatProperties druidStatProperties() {
            DruidStatProperties properties = new DruidStatProperties();
            DruidStatProperties.StatViewServlet statViewServlet = new DruidStatProperties.StatViewServlet();
            statViewServlet.setUrlPattern("/druid/*");
            properties.setStatViewServlet(statViewServlet);
            return properties;
        }
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    private FilterRegistrationBean<Filter> filterRegistrationBean;

    @Test
    void masterDataSource_ShouldBeConfigured() {
        assertNotNull(dataSource);
        assertTrue(dataSource instanceof DruidDataSource);
        
        DruidDataSource druidDataSource = (DruidDataSource) dataSource;
        assertEquals(5, druidDataSource.getInitialSize());
        assertEquals(10, druidDataSource.getMinIdle());
        assertEquals(20, druidDataSource.getMaxActive());
        assertEquals(60000, druidDataSource.getMaxWait());
    }

    @Test
    void removeDruidFilter_ShouldBeConfigured() {
        assertNotNull(filterRegistrationBean);
        assertEquals("/druid/js/common.js", filterRegistrationBean.getUrlPatterns().iterator().next());
    }
} 