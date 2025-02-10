package com.lawfirm.common.log.service;

import com.lawfirm.common.log.domain.BehaviorTrack;
import com.lawfirm.common.log.domain.TrackData;

import java.util.List;

/**
 * 行为跟踪服务接口
 */
public interface BehaviorTrackService {

    /**
     * 记录用户行为
     *
     * @param trackData 行为数据
     */
    void trackBehavior(TrackData trackData);

    /**
     * 根据ID获取行为记录
     *
     * @param id 记录ID
     * @return 行为数据
     */
    TrackData getBehaviorById(Long id);

    /**
     * 分析用户行为
     *
     * @param userId 用户ID
     */
    void analyzeBehavior(Long userId);

    /**
     * 清理过期数据
     */
    void cleanExpiredData();

    /**
     * 获取所有行为记录
     */
    List<BehaviorTrack> list();
} 