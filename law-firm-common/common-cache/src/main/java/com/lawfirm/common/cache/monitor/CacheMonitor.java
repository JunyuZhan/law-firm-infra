package com.lawfirm.common.cache.monitor;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Redis缓存监控
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CacheMonitor {

    private final RedissonClient redissonClient;

    /**
     * 获取Redis信息
     */
    public RedisInfo getRedisInfo() {
        RedisInfo redisInfo = new RedisInfo();
        try {
            RedissonConnectionFactory connectionFactory = new RedissonConnectionFactory(redissonClient);
            RedisConnection connection = connectionFactory.getConnection();
            Properties info = connection.info();
            
            redisInfo.setVersion(info.getProperty("redis_version"));
            redisInfo.setUsedMemory(info.getProperty("used_memory_human"));
            redisInfo.setUsedMemoryPeak(info.getProperty("used_memory_peak_human"));
            redisInfo.setUsedMemoryRss(info.getProperty("used_memory_rss_human"));
            redisInfo.setConnectedClients(info.getProperty("connected_clients"));
            redisInfo.setTotalConnections(info.getProperty("total_connections_received"));
            redisInfo.setTotalCommands(info.getProperty("total_commands_processed"));
            redisInfo.setKeyspaceHits(info.getProperty("keyspace_hits"));
            redisInfo.setKeyspaceMisses(info.getProperty("keyspace_misses"));
            redisInfo.setExpiredKeys(info.getProperty("expired_keys"));
            redisInfo.setEvictedKeys(info.getProperty("evicted_keys"));
            
            connection.close();
            connectionFactory.destroy();
            return redisInfo;
        } catch (Exception e) {
            log.error("获取Redis信息失败", e);
            return null;
        }
    }

    @Data
    public static class RedisInfo {
        private String version;
        private String usedMemory;
        private String usedMemoryPeak;
        private String usedMemoryRss;
        private String connectedClients;
        private String totalConnections;
        private String totalCommands;
        private String keyspaceHits;
        private String keyspaceMisses;
        private String expiredKeys;
        private String evictedKeys;
    }
} 