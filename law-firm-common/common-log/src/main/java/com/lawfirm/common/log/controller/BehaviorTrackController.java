package com.lawfirm.common.log.controller;

import com.lawfirm.common.core.result.R;
import com.lawfirm.common.log.domain.TrackData;
import com.lawfirm.common.log.service.BehaviorTrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public R<Void> trackBehavior(@RequestBody TrackData trackData) {
        behaviorTrackService.trackBehavior(trackData);
        return R.ok();
    }

    /**
     * 获取行为记录
     */
    @GetMapping("/{id}")
    public R<TrackData> getBehavior(@PathVariable Long id) {
        return R.ok(behaviorTrackService.getBehaviorById(id));
    }

    /**
     * 分析用户行为
     */
    @PostMapping("/analyze/{userId}")
    public R<Void> analyzeBehavior(@PathVariable Long userId) {
        behaviorTrackService.analyzeBehavior(userId);
        return R.ok();
    }

    /**
     * 清理过期数据
     */
    @DeleteMapping("/clean")
    public R<Void> cleanExpiredData() {
        behaviorTrackService.cleanExpiredData();
        return R.ok();
    }
} 