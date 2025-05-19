package com.lawfirm.api.controller;

import com.lawfirm.api.constant.ApiConstants;
import com.lawfirm.common.core.api.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 缓存演示控制器
 * 用于演示和测试缓存功能
 * 仅在开发和测试环境可用
 */
@Tag(name = "缓存演示", description = "缓存功能演示接口(仅开发环境)")
@RestController
@RequestMapping(ApiConstants.API_BASE + "/demo/cache")
@Slf4j
@Profile({"dev", "test"})
public class CacheDemoController extends BaseApiController {

    @Autowired
    private CacheManager cacheManager;

    /**
     * 获取缓存数据
     */
    @GetMapping("/{key}")
    @Operation(summary = "获取缓存", description = "根据key获取缓存数据")
    public CommonResult<Object> get(@PathVariable String key) {
        Cache cache = cacheManager.getCache("demo");
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(key);
            if (valueWrapper != null) {
                return success(valueWrapper.get(), "获取缓存成功");
            }
        }
        return error("缓存不存在");
    }

    /**
     * 设置缓存数据
     */
    @PostMapping("/{key}")
    @Operation(summary = "设置缓存", description = "设置缓存数据")
    public CommonResult<Void> set(@PathVariable String key, @RequestBody Object value) {
        Cache cache = cacheManager.getCache("demo");
        if (cache != null) {
            cache.put(key, value);
            return success("设置缓存成功");
        }
        return error("缓存操作失败");
    }

    /**
     * 删除缓存数据
     */
    @DeleteMapping("/{key}")
    @Operation(summary = "删除缓存", description = "删除指定key的缓存数据")
    public CommonResult<Void> delete(@PathVariable String key) {
        Cache cache = cacheManager.getCache("demo");
        if (cache != null) {
            cache.evict(key);
            return success("删除缓存成功");
        }
        return error("缓存操作失败");
    }

    /**
     * 清空所有缓存数据
     */
    @DeleteMapping
    @Operation(summary = "清空缓存", description = "清空所有缓存数据")
    public CommonResult<Void> clear() {
        Cache cache = cacheManager.getCache("demo");
        if (cache != null) {
            cache.clear();
            return success("清空缓存成功");
        }
        return error("缓存操作失败");
    }

    /**
     * 获取缓存统计信息
     */
    @GetMapping("/stats")
    @Operation(summary = "缓存统计", description = "获取缓存统计信息")
    public CommonResult<Map<String, Object>> stats() {
        Map<String, Object> stats = new HashMap<>();
        
        // 获取所有缓存名称
        cacheManager.getCacheNames().forEach(cacheName -> {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                // 这里只是演示，实际统计信息需要根据具体的缓存实现获取
                stats.put(cacheName, Map.of(
                    "name", cacheName,
                    "type", cache.getClass().getSimpleName()
                ));
            }
        });
        
        return success(stats);
    }

    /**
     * 模拟耗时操作（带缓存）
     */
    @GetMapping("/slow/{id}")
    @Operation(summary = "模拟耗时操作", description = "模拟一个耗时操作，演示缓存效果")
    public CommonResult<Map<String, Object>> slowOperation(@PathVariable String id) {
        log.info("执行耗时操作，ID: {}", id);
        
        // 检查缓存
        Cache cache = cacheManager.getCache("demo");
        String cacheKey = "slow:" + id;
        
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(cacheKey);
            if (valueWrapper != null) {
                log.info("从缓存获取结果，ID: {}", id);
                @SuppressWarnings("unchecked")
                Map<String, Object> cachedResult = (Map<String, Object>) valueWrapper.get();
                return success(cachedResult, "从缓存获取成功");
            }
        }
        
        // 模拟耗时操作
        try {
            log.info("模拟耗时处理，等待2秒...");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // 生成结果
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("timestamp", System.currentTimeMillis());
        result.put("data", "这是ID为" + id + "的耗时操作结果");
        
        // 存入缓存
        if (cache != null) {
            cache.put(cacheKey, result);
            log.info("结果已存入缓存，ID: {}", id);
        }
        
        return success(result, "操作成功");
    }
} 