package com.lawfirm.common.cache.config;

import com.lawfirm.common.cache.utils.CacheUtil;
import org.mockito.Mockito;
import org.redisson.api.RedissonClient;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;

@SpringBootConfiguration
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class,
    RedisRepositoriesAutoConfiguration.class
})
@Import({CacheUtil.class})
public class TestConfig {
    
    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> mockTemplate = Mockito.mock(RedisTemplate.class);
        
        // Mock operations
        ValueOperations<String, Object> valueOps = Mockito.mock(ValueOperations.class);
        HashOperations<String, Object, Object> hashOps = Mockito.mock(HashOperations.class);
        ListOperations<String, Object> listOps = Mockito.mock(ListOperations.class);
        SetOperations<String, Object> setOps = Mockito.mock(SetOperations.class);
        
        // Setup template operations
        Mockito.when(mockTemplate.opsForValue()).thenReturn(valueOps);
        Mockito.when(mockTemplate.opsForHash()).thenReturn(hashOps);
        Mockito.when(mockTemplate.opsForList()).thenReturn(listOps);
        Mockito.when(mockTemplate.opsForSet()).thenReturn(setOps);
        
        return mockTemplate;
    }
    
    @Bean
    @Primary
    public StringRedisTemplate stringRedisTemplate() {
        return Mockito.mock(StringRedisTemplate.class);
    }

    @Bean
    @Primary
    public RedissonClient redissonClient() {
        return Mockito.mock(RedissonClient.class);
    }
} 