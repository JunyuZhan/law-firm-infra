package com.lawfirm.common.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.log.domain.BehaviorTrack;
import com.lawfirm.common.log.domain.TrackData;
import com.lawfirm.common.log.mapper.BehaviorTrackMapper;
import com.lawfirm.common.log.service.BehaviorTrackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 行为跟踪服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BehaviorTrackServiceImpl extends ServiceImpl<BehaviorTrackMapper, BehaviorTrack> implements BehaviorTrackService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void trackBehavior(TrackData trackData) {
        BehaviorTrack behaviorTrack = convertToTrack(trackData);
        save(behaviorTrack);
        log.info("记录用户行为数据: {}", trackData);
    }

    @Override
    public TrackData getBehaviorById(Long id) {
        BehaviorTrack track = getById(id);
        return convertToTrackData(track);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void analyzeBehavior(Long userId) {
        // TODO: 实现用户行为分析逻辑
        log.info("分析用户[{}]的行为数据", userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cleanExpiredData() {
        // 默认清理30天前的数据
        LocalDateTime threshold = LocalDateTime.now().minus(30, ChronoUnit.DAYS);
        // TODO: 实现清理过期数据的逻辑
        log.info("清理{}之前的行为数据", threshold);
    }

    @Override
    public List<BehaviorTrack> list() {
        return super.list();
    }

    private BehaviorTrack convertToTrack(TrackData trackData) {
        BehaviorTrack track = new BehaviorTrack();
        track.setUserId(trackData.getUserId());
        track.setUsername(trackData.getUsername());
        track.setOperationType(trackData.getEventType());
        track.setDescription(trackData.getEventDetails());
        track.setOperationTime(trackData.getOperationTime());
        track.setIpAddress(trackData.getIp());
        track.setRequestUrl(trackData.getRequestUrl());
        track.setRequestMethod(trackData.getRequestMethod());
        track.setRequestParams(trackData.getRequestParams());
        track.setExecutionTime(trackData.getDuration());
        return track;
    }

    private TrackData convertToTrackData(BehaviorTrack track) {
        TrackData trackData = new TrackData();
        trackData.setUserId(track.getUserId());
        trackData.setUsername(track.getUsername());
        trackData.setEventType(track.getOperationType());
        trackData.setEventDetails(track.getDescription());
        trackData.setOperationTime(track.getOperationTime());
        trackData.setIp(track.getIpAddress());
        trackData.setRequestUrl(track.getRequestUrl());
        trackData.setRequestMethod(track.getRequestMethod());
        trackData.setRequestParams(track.getRequestParams());
        trackData.setDuration(track.getExecutionTime());
        return trackData;
    }
} 