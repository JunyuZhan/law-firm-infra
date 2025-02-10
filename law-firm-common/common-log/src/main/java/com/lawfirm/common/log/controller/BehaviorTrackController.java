package com.lawfirm.common.log.controller;

import com.lawfirm.common.core.result.Result;
import com.lawfirm.common.log.domain.BehaviorTrack;
import com.lawfirm.common.log.domain.TrackData;
import com.lawfirm.common.log.service.BehaviorTrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 行为跟踪控制器
 */
@RestController
@RequestMapping("/behavior")
@RequiredArgsConstructor
public class BehaviorTrackController {

    private final BehaviorTrackService behaviorTrackService;

    /**
     * 记录行为
     */
    @PostMapping("/track")
    public Result<Void> trackBehavior(@RequestBody TrackData trackData) {
        behaviorTrackService.trackBehavior(trackData);
        return Result.ok();
    }

    /**
     * 获取行为记录
     */
    @GetMapping("/{id}")
    public Result<TrackData> getBehavior(@PathVariable Long id) {
        return Result.ok(behaviorTrackService.getBehaviorById(id));
    }

    /**
     * 分析用户行为
     */
    @PostMapping("/analyze/{userId}")
    public Result<Void> analyzeBehavior(@PathVariable Long userId) {
        behaviorTrackService.analyzeBehavior(userId);
        return Result.ok();
    }

    /**
     * 清理过期数据
     */
    @DeleteMapping("/clean")
    public Result<Void> cleanExpiredData() {
        behaviorTrackService.cleanExpiredData();
        return Result.ok();
    }

    /**
     * 获取行为记录列表
     */
    @GetMapping
    public Result<List<BehaviorTrack>> list() {
        return Result.ok(behaviorTrackService.list());
    }
} 