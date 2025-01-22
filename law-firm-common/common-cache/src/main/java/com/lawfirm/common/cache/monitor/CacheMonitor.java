package com.lawfirm.common.cache.monitor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 缓存监控
 */
@Slf4j
@Component
public class CacheMonitor {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 获取Redis信息
     */
    @Scheduled(fixedRate = 60000)
    public void monitor() {
        try {
            RedisInfo info = getRedisInfo();
            log.info("Redis监控信息: {}", info);
        } catch (Exception e) {
            log.error("获取Redis监控信息失败", e);
        }
    }

    /**
     * 获取Redis信息
     */
    public RedisInfo getRedisInfo() {
        Properties info = redisTemplate.getConnectionFactory().getConnection().info();
        RedisInfo redisInfo = new RedisInfo();

        redisInfo.setVersion(info.getProperty("redis_version"));
        redisInfo.setUsedMemory(info.getProperty("used_memory"));
        redisInfo.setUsedMemoryPeak(info.getProperty("used_memory_peak"));
        redisInfo.setUsedMemoryRss(info.getProperty("used_memory_rss"));
        redisInfo.setConnectedClients(info.getProperty("connected_clients"));
        redisInfo.setTotalConnections(info.getProperty("total_connections_received"));
        redisInfo.setTotalCommands(info.getProperty("total_commands_processed"));
        redisInfo.setKeyspaceHits(info.getProperty("keyspace_hits"));
        redisInfo.setKeyspaceMisses(info.getProperty("keyspace_misses"));
        redisInfo.setExpiredKeys(info.getProperty("expired_keys"));
        redisInfo.setEvictedKeys(info.getProperty("evicted_keys"));

        return redisInfo;
    }

    /**
     * 获取Redis统计信息
     */
    public List<String> getRedisStats() {
        List<String> stats = new ArrayList<>();
        Properties info = redisTemplate.getConnectionFactory().getConnection().info();

        // 计算命中率
        long hits = Long.parseLong(info.getProperty("keyspace_hits"));
        long misses = Long.parseLong(info.getProperty("keyspace_misses"));
        double hitRate = hits + misses == 0 ? 0 : (double) hits / (hits + misses) * 100;
        stats.add(String.format("缓存命中率: %.2f%%", hitRate));

        // 内存使用率
        long maxMemory = Runtime.getRuntime().maxMemory();
        long usedMemory = Long.parseLong(info.getProperty("used_memory"));
        double memoryRate = (double) usedMemory / maxMemory * 100;
        stats.add(String.format("内存使用率: %.2f%%", memoryRate));

        // 连接数
        stats.add("当前连接数: " + info.getProperty("connected_clients"));
        stats.add("历史连接数: " + info.getProperty("total_connections_received"));

        return stats;
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