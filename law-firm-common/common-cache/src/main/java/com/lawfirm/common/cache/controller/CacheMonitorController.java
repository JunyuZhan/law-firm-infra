package com.lawfirm.common.cache.controller;

import com.lawfirm.common.cache.monitor.CacheMonitor;
import com.lawfirm.common.cache.service.CacheMonitorService;
import com.lawfirm.common.cache.model.CacheStats;
import com.lawfirm.common.core.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 缓存监控控制器
 */
@RestController
@RequestMapping("/cache/monitor")
@RequiredArgsConstructor
public class CacheMonitorController {

    private final CacheMonitor cacheMonitor;
    private final CacheMonitorService cacheMonitorService;

    /**
     * 获取Redis信息
     */
    @GetMapping("/info")
    public Result<?> getRedisInfo() {
        return Result.ok(cacheMonitor.getRedisInfo());
    }

    @GetMapping("/stats")
    public Result<CacheStats> getStats() {
        return Result.ok(cacheMonitorService.getStats());
    }

    @PostMapping("/clear")
    public Result<Void> clearCache() {
        cacheMonitorService.clearCache();
        return Result.ok();
    }
} 