package com.lawfirm.common.data.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest(classes = TestConfig.class)
@TestPropertySource(properties = {
    "spring.datasource.dynamic.enabled=true",
    "spring.datasource.dynamic.primary=master",
    "spring.datasource.dynamic.strict=true",
    "spring.datasource.dynamic.p6spy=false",
    "spring.datasource.dynamic.seata=false"
})
class DynamicDataSourceConfigTest extends BaseConfigTest {

    @MockBean(name = "dynamicDataSourceProperties")
    private DynamicDataSourceProperties properties;

    @MockBean(name = "masterDataSource")
    private DataSource masterDataSource;

    @MockBean(name = "slaveDataSource")
    private DataSource slaveDataSource;

    @Autowired
    private DynamicDataSourceProvider dynamicDataSourceProvider;

    @Autowired
    private DataSource dataSource;

    @Test
    void dynamicDataSourceProvider_ShouldBeConfigured() {
        Map<String, DataSource> dataSources = dynamicDataSourceProvider.loadDataSources();
        
        assertNotNull(dataSources);
        assertEquals(2, dataSources.size());
        assertTrue(dataSources.containsKey("master"));
        assertTrue(dataSources.containsKey("slave"));
        assertEquals(masterDataSource, dataSources.get("master"));
        assertEquals(slaveDataSource, dataSources.get("slave"));
    }

    @Test
    void dataSource_ShouldBeDynamicRoutingDataSource() {
        assertNotNull(dataSource, "DataSource should not be null");
        assertTrue(dataSource instanceof DynamicRoutingDataSource, "DataSource should be instance of DynamicRoutingDataSource");
    }
} 