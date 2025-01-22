package com.lawfirm.common.cache.controller;

import com.lawfirm.common.cache.monitor.CacheMonitor;
import com.lawfirm.common.core.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 缓存监控控制器
 */
@RestController
@RequestMapping("/monitor/cache")
@RequiredArgsConstructor
public class CacheMonitorController {

    private final CacheMonitor cacheMonitor;

    /**
     * 获取Redis信息
     */
    @GetMapping("/info")
    public R<?> info() {
        return R.ok(cacheMonitor.getRedisInfo());
    }

    /**
     * 获取Redis统计信息
     */
    @GetMapping("/stats")
    public R<?> stats() {
        return R.ok(cacheMonitor.getRedisStats());
    }
} 