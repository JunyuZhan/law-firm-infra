package com.lawfirm.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.schedule.entity.MeetingRoom;
import com.lawfirm.model.schedule.mapper.MeetingRoomMapper;
import com.lawfirm.model.schedule.service.MeetingRoomService;
import com.lawfirm.model.schedule.vo.MeetingRoomVO;
import com.lawfirm.schedule.converter.MeetingRoomConvert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会议室服务实现类
 */
@Service("meetingRoomService")
@RequiredArgsConstructor
@Slf4j
public class MeetingRoomServiceImpl extends ServiceImpl<MeetingRoomMapper, MeetingRoom> implements MeetingRoomService {

    private final MeetingRoomConvert roomConvert;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createMeetingRoom(MeetingRoom meetingRoom) {
        log.info("创建会议室，名称：{}", meetingRoom.getName());
        save(meetingRoom);
        return meetingRoom.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMeetingRoom(Long id, MeetingRoom meetingRoom) {
        log.info("更新会议室，ID：{}, 名称：{}", id, meetingRoom.getName());
        
        MeetingRoom existingRoom = getById(id);
        if (existingRoom == null) {
            log.error("更新会议室失败，会议室不存在，ID：{}", id);
            return false;
        }
        
        meetingRoom.setId(id);
        return updateById(meetingRoom);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMeetingRoom(Long id) {
        log.info("删除会议室，ID：{}", id);
        return removeById(id);
    }

    @Override
    public MeetingRoomVO getMeetingRoomDetail(Long id) {
        log.info("获取会议室详情，ID：{}", id);
        
        MeetingRoom meetingRoom = getById(id);
        if (meetingRoom == null) {
            return null;
        }
        
        return roomConvert.toVO(meetingRoom);
    }

    @Override
    public IPage<MeetingRoomVO> pageMeetingRooms(Page<MeetingRoom> page, Integer statusCode) {
        log.info("分页查询会议室，状态：{}", statusCode);
        
        LambdaQueryWrapper<MeetingRoom> queryWrapper = new LambdaQueryWrapper<>();
        if (statusCode != null) {
            queryWrapper.eq(MeetingRoom::getStatus, statusCode);
        }
        
        IPage<MeetingRoom> result = page(page, queryWrapper);
        
        return result.convert(roomConvert::toVO);
    }

    @Override
    public List<MeetingRoomVO> listAvailableRooms(LocalDateTime startTime, LocalDateTime endTime, Integer capacity) {
        log.info("查询可用会议室，开始时间：{}，结束时间：{}，最小容量：{}", startTime, endTime, capacity);
        
        // 假设mapper中有查询可用会议室的方法
        List<MeetingRoom> rooms = list(new LambdaQueryWrapper<MeetingRoom>()
                .eq(MeetingRoom::getStatus, 1)  // 状态为启用的会议室
                .ge(capacity != null, MeetingRoom::getCapacity, capacity));  // 容量大于等于要求
        
        List<MeetingRoomVO> result = new ArrayList<>();
        for (MeetingRoom room : rooms) {
            MeetingRoomVO vo = roomConvert.toVO(room);
            try {
                // 通过反射设置available属性
                java.lang.reflect.Field field = vo.getClass().getDeclaredField("available");
                field.setAccessible(true);
                field.set(vo, Boolean.TRUE);
            } catch (Exception e) {
                log.error("设置会议室可用状态失败", e);
            }
            result.add(vo);
        }
        
        return result;
    }

    @Override
    public List<MeetingRoomVO> listByManagerId(Long managerId) {
        log.info("查询管理员的会议室，管理员ID：{}", managerId);
        
        LambdaQueryWrapper<MeetingRoom> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MeetingRoom::getManagerId, managerId);
        
        List<MeetingRoom> rooms = list(queryWrapper);
        return rooms.stream()
                .map(roomConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, Integer statusCode) {
        log.info("更新会议室状态，ID：{}，状态码：{}", id, statusCode);
        
        MeetingRoom meetingRoom = getById(id);
        if (meetingRoom == null) {
            log.error("更新会议室状态失败，会议室不存在，ID：{}", id);
            return false;
        }
        
        meetingRoom.setStatus(statusCode);
        return updateById(meetingRoom);
    }

    @Override
    public boolean checkAvailability(Long id, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("检查会议室可用性，ID：{}，开始时间：{}，结束时间：{}", id, startTime, endTime);
        
        // 首先检查会议室是否存在且状态为启用
        MeetingRoom room = getById(id);
        if (room == null || room.getStatus() != 1) {
            return false;
        }
        
        // 检查该时间段是否有其他日程占用
        // 实际实现应该查询日程表，检查是否有与该会议室相关的日程
        // 这里简化处理，假设所有会议室都可用
        return true;
    }
    
    // 以下是实现BaseService接口的方法
    
    @Override
    public MeetingRoom getById(Long id) {
        return super.getById(id);
    }
    
    @Override
    public List<MeetingRoom> list(QueryWrapper<MeetingRoom> wrapper) {
        return super.list(wrapper);
    }
    
    @Override
    public Page<MeetingRoom> page(Page<MeetingRoom> page, QueryWrapper<MeetingRoom> wrapper) {
        return super.page(page, wrapper);
    }
    
    @Override
    public long count(QueryWrapper<MeetingRoom> wrapper) {
        return super.count(wrapper);
    }
    
    @Override
    public boolean save(MeetingRoom entity) {
        return super.save(entity);
    }
    
    @Override
    public boolean saveBatch(List<MeetingRoom> entities) {
        return super.saveBatch(entities);
    }
    
    @Override
    public boolean update(MeetingRoom entity) {
        return super.updateById(entity);
    }
    
    @Override
    public boolean updateBatch(List<MeetingRoom> entities) {
        return super.updateBatchById(entities);
    }
    
    @Override
    public boolean remove(Long id) {
        return super.removeById(id);
    }
    
    @Override
    public boolean removeBatch(List<Long> ids) {
        return super.removeByIds(ids);
    }
    
    @Override
    public boolean exists(QueryWrapper<MeetingRoom> wrapper) {
        return count(wrapper) > 0;
    }

    @Override
    public Long getCurrentTenantId() {
        return null; // TODO: 实现获取当前租户ID的逻辑
    }

    @Override
    public Long getCurrentUserId() {
        return null; // TODO: 实现获取当前用户ID的逻辑
    }

    @Override
    public String getCurrentUsername() {
        return null; // TODO: 实现获取当前用户名的逻辑
    }
}