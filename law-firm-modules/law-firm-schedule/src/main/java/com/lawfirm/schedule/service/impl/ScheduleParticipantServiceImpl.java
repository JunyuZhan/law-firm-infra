package com.lawfirm.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.schedule.dto.ScheduleParticipantDTO;
import com.lawfirm.model.schedule.entity.ScheduleParticipant;
import com.lawfirm.model.schedule.mapper.ScheduleParticipantMapper;
import com.lawfirm.model.schedule.service.ScheduleParticipantService;
import com.lawfirm.model.schedule.vo.ScheduleParticipantVO;
import com.lawfirm.schedule.converter.ScheduleParticipantConvert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 日程参与者服务实现类
 */
@Service("scheduleParticipantService")
@RequiredArgsConstructor
@Slf4j
public class ScheduleParticipantServiceImpl extends ServiceImpl<ScheduleParticipantMapper, ScheduleParticipant> implements ScheduleParticipantService {

    private final ScheduleParticipantConvert participantConvert;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addParticipant(Long scheduleId, ScheduleParticipantDTO participantDTO) {
        log.info("添加日程参与者，日程ID：{}，参与者ID：{}", scheduleId, participantDTO.getParticipantId());
        
        participantDTO.setScheduleId(scheduleId);
        ScheduleParticipant participant = participantConvert.toEntity(participantDTO);
        save(participant);
        return participant.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addParticipants(Long scheduleId, List<ScheduleParticipantDTO> participantDTOs) {
        log.info("批量添加日程参与者，日程ID：{}，参与者数量：{}", scheduleId, participantDTOs.size());
        
        List<ScheduleParticipant> participants = participantDTOs.stream()
                .peek(dto -> dto.setScheduleId(scheduleId))
                .map(participantConvert::toEntity)
                .collect(Collectors.toList());
        
        return saveBatch(participants);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateParticipant(Long id, ScheduleParticipantDTO participantDTO) {
        log.info("更新日程参与者，ID：{}", id);
        
        ScheduleParticipant participant = getById(id);
        if (participant == null) {
            log.error("更新日程参与者失败，参与者不存在，ID：{}", id);
            return false;
        }
        
        participant = participantConvert.updateEntity(participant, participantDTO);
        return updateById(participant);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeParticipant(Long id) {
        log.info("删除日程参与者，ID：{}", id);
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByScheduleId(Long scheduleId) {
        log.info("删除日程的所有参与者，日程ID：{}", scheduleId);
        
        LambdaQueryWrapper<ScheduleParticipant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleParticipant::getScheduleId, scheduleId);
        
        return remove(queryWrapper);
    }

    @Override
    public List<ScheduleParticipantVO> listByScheduleId(Long scheduleId) {
        log.info("查询日程的参与者列表，日程ID：{}", scheduleId);
        
        LambdaQueryWrapper<ScheduleParticipant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleParticipant::getScheduleId, scheduleId);
        
        List<ScheduleParticipant> participants = list(queryWrapper);
        return participants.stream()
                .map(participantConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleParticipantVO> listByParticipantId(Long participantId) {
        log.info("查询用户参与的日程列表，参与者ID：{}", participantId);
        
        List<ScheduleParticipant> participants = baseMapper.findByParticipantId(participantId);
        return participants.stream()
                .map(participantConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateResponseStatus(Long id, Integer responseStatus) {
        log.info("更新日程参与者响应状态，ID：{}，状态：{}", id, responseStatus);
        
        ScheduleParticipant participant = getById(id);
        if (participant == null) {
            log.error("更新响应状态失败，参与者不存在，ID：{}", id);
            return false;
        }
        
        participant.setResponseStatus(responseStatus);
        return updateById(participant);
    }

    @Override
    public ScheduleParticipantVO getParticipantDetail(Long id) {
        log.info("获取日程参与者详情，ID：{}", id);
        
        ScheduleParticipant participant = getById(id);
        if (participant == null) {
            return null;
        }
        
        return participantConvert.toVO(participant);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean respondToInvitation(Long id, Integer responseStatus, String comments) {
        log.info("响应日程邀请，ID：{}，响应状态：{}", id, responseStatus);
        
        ScheduleParticipant participant = getById(id);
        if (participant == null) {
            log.error("响应日程邀请失败，参与者不存在，ID：{}", id);
            return false;
        }
        
        participant.setResponseStatus(responseStatus);
        participant.setComments(comments);
        return updateById(participant);
    }
    
    @Override
    public List<Long> getScheduleIdsByUserId(Long userId) {
        log.info("获取用户参与的所有日程ID，用户ID：{}", userId);
        
        return baseMapper.findScheduleIdsByParticipantId(userId);
    }

    // 以下是实现BaseService接口的方法
    
    @Override
    public boolean save(ScheduleParticipant entity) {
        return super.save(entity);
    }
    
    @Override
    public boolean saveBatch(List<ScheduleParticipant> entities) {
        return super.saveBatch(entities);
    }
    
    @Override
    public boolean update(ScheduleParticipant entity) {
        return super.updateById(entity);
    }
    
    @Override
    public boolean updateBatch(List<ScheduleParticipant> entities) {
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
    public ScheduleParticipant getById(Long id) {
        return super.getById(id);
    }
    
    @Override
    public List<ScheduleParticipant> list(QueryWrapper<ScheduleParticipant> wrapper) {
        return super.list(wrapper);
    }
    
    @Override
    public Page<ScheduleParticipant> page(Page<ScheduleParticipant> page, QueryWrapper<ScheduleParticipant> wrapper) {
        return super.page(page, wrapper);
    }
    
    @Override
    public long count(QueryWrapper<ScheduleParticipant> wrapper) {
        return super.count(wrapper);
    }
    
    @Override
    public boolean exists(QueryWrapper<ScheduleParticipant> wrapper) {
        return super.count(wrapper) > 0;
    }
} 