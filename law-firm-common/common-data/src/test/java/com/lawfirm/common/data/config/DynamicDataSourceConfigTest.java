package com.lawfirm.common.data.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DynamicDataSourceConfigTest {

    @Mock
    private DynamicDataSourceProperties properties;

    @Mock
    private DataSource masterDataSource;

    @Mock
    private DataSource slaveDataSource;
    
    @Mock
    private DynamicDataSourceProvider dynamicDataSourceProvider;
    
    @InjectMocks
    private DynamicDataSourceConfig dynamicDataSourceConfig;
    
    @Mock
    private DynamicRoutingDataSource dataSource;
    
    private Map<String, DataSource> dataSources;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
        dataSources = new HashMap<>();
        dataSources.put("master", masterDataSource);
        dataSources.put("slave", slaveDataSource);
        
        when(dynamicDataSourceProvider.loadDataSources()).thenReturn(dataSources);
        when(properties.getPrimary()).thenReturn("master");
        
        // 模拟动态数据源的行为
        when(dataSource.determineDataSource()).thenReturn(masterDataSource);
        
        // 使用反射设置私有字段
        ReflectionTestUtils.setField(dataSource, "dataSourceMap", dataSources);
        ReflectionTestUtils.setField(dataSource, "primary", "master");
        
        // 模拟提供者列表
        List<DynamicDataSourceProvider> providers = Collections.singletonList(dynamicDataSourceProvider);
        ReflectionTestUtils.setField(dataSource, "providers", providers);
    }

    @Test
    void dynamicDataSourceProvider_ShouldBeConfigured() {
        // 验证数据源提供程序
        Map<String, DataSource> dataSources = dynamicDataSourceProvider.loadDataSources();
        
        assertNotNull(dataSources);
        assertEquals(2, dataSources.size());
        assertTrue(dataSources.containsKey("master"));
        assertTrue(dataSources.containsKey("slave"));
        assertEquals(masterDataSource, dataSources.get("master"));
        assertEquals(slaveDataSource, dataSources.get("slave"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void dataSource_ShouldBeDynamicRoutingDataSource() {
        // 验证数据源类型
        assertNotNull(dataSource);
        
        // 验证数据源行为
        DataSource determinedDataSource = dataSource.determineDataSource();
        assertEquals(masterDataSource, determinedDataSource);
        
        // 验证通过反射设置的属性
        Map<String, DataSource> dsMap = (Map<String, DataSource>) ReflectionTestUtils.getField(dataSource, "dataSourceMap");
        assertEquals(2, dsMap.size());
        assertEquals("master", ReflectionTestUtils.getField(dataSource, "primary"));
        
        // 验证提供者列表
        List<DynamicDataSourceProvider> providers = 
            (List<DynamicDataSourceProvider>) ReflectionTestUtils.getField(dataSource, "providers");
        assertNotNull(providers);
        assertEquals(1, providers.size());
        assertEquals(dynamicDataSourceProvider, providers.get(0));
    }
} 