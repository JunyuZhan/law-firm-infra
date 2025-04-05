package com.lawfirm.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.personnel.vo.EmployeeVO;
import com.lawfirm.model.schedule.dto.ScheduleParticipantDTO;
import com.lawfirm.model.schedule.entity.ScheduleParticipant;
import com.lawfirm.model.schedule.entity.enums.ParticipantType;
import com.lawfirm.model.schedule.entity.enums.ResponseStatus;
import com.lawfirm.model.schedule.mapper.ScheduleParticipantMapper;
import com.lawfirm.model.schedule.service.ScheduleParticipantService;
import com.lawfirm.model.schedule.vo.ScheduleParticipantVO;
import com.lawfirm.schedule.converter.ScheduleParticipantConvert;
import com.lawfirm.schedule.integration.PersonnelIntegration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 日程参与者服务实现类
 */
@Service("scheduleParticipantService")
@RequiredArgsConstructor
@Slf4j
public class ScheduleParticipantServiceImpl extends ServiceImpl<ScheduleParticipantMapper, ScheduleParticipant> implements ScheduleParticipantService {

    private final ScheduleParticipantConvert participantConvert;
    private final PersonnelIntegration personnelIntegration;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addParticipant(Long scheduleId, ScheduleParticipantDTO participantDTO) {
        log.info("添加日程参与者，日程ID：{}，参与者ID：{}", scheduleId, participantDTO.getParticipantId());
        
        // 验证参与者是否存在
        if (!personnelIntegration.employeeExists(participantDTO.getParticipantId())) {
            log.error("添加日程参与者失败，参与者不存在，参与者ID：{}", participantDTO.getParticipantId());
            return false;
        }
        
        participantDTO.setScheduleId(scheduleId);
        ScheduleParticipant participant = participantConvert.toEntity(participantDTO);
        return save(participant);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addParticipants(Long scheduleId, List<ScheduleParticipantDTO> participantDTOs) {
        log.info("批量添加日程参与者，日程ID：{}，参与者数量：{}", scheduleId, participantDTOs.size());
        
        // 验证参与者是否存在
        Set<Long> participantIds = participantDTOs.stream()
                .map(ScheduleParticipantDTO::getParticipantId)
                .collect(Collectors.toSet());
        
        for (Long participantId : participantIds) {
            if (!personnelIntegration.employeeExists(participantId)) {
                log.error("批量添加日程参与者失败，参与者不存在，参与者ID：{}", participantId);
                throw new IllegalArgumentException("参与者不存在：" + participantId);
            }
        }
        
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
        
        participantConvert.updateEntity(participant, participantDTO);
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
        List<ScheduleParticipantVO> participantVOs = participants.stream()
                .map(participantConvert::toVO)
                .collect(Collectors.toList());
        
        // 填充参与者信息
        fillParticipantEmployeeInfo(participantVOs);
        
        return participantVOs;
    }

    @Override
    public ScheduleParticipantVO getParticipantDetail(Long id) {
        log.info("获取日程参与者详情，ID：{}", id);
        
        ScheduleParticipant participant = getById(id);
        if (participant == null) {
            return null;
        }
        
        ScheduleParticipantVO participantVO = participantConvert.toVO(participant);
        
        // 填充参与者信息
        fillParticipantEmployeeInfo(participantVO);
        
        return participantVO;
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
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchAddParticipants(Long scheduleId, List<Long> userIds) {
        log.info("批量添加日程参与者，日程ID：{}，参与者数量：{}", scheduleId, userIds.size());
        
        if (userIds.isEmpty()) {
            return 0;
        }
        
        // 验证用户是否存在
        for (Long userId : userIds) {
            if (!personnelIntegration.employeeExists(userId)) {
                log.error("批量添加日程参与者失败，参与者不存在，参与者ID：{}", userId);
                throw new IllegalArgumentException("参与者不存在：" + userId);
            }
        }
        
        // 构建参与者实体列表
        List<ScheduleParticipant> participants = userIds.stream()
                .map(userId -> {
                    ScheduleParticipant participant = new ScheduleParticipant();
                    participant.setScheduleId(scheduleId);
                    participant.setParticipantId(userId);
                    // 默认设置为必要参与者
                    participant.setParticipantType(ParticipantType.REQUIRED.getCode());
                    // 默认设置为未回复状态
                    participant.setResponseStatus(ResponseStatus.PENDING.getCode());
                    return participant;
                })
                .collect(Collectors.toList());
        
        // 保存实体
        boolean success = saveBatch(participants);
        return success ? participants.size() : 0;
    }
    
    /**
     * 填充参与者员工信息
     * 
     * @param participantVOs 参与者VO列表
     */
    private void fillParticipantEmployeeInfo(List<ScheduleParticipantVO> participantVOs) {
        if (participantVOs == null || participantVOs.isEmpty()) {
            return;
        }
        
        Set<Long> participantIds = participantVOs.stream()
                .map(ScheduleParticipantVO::getParticipantId)
                .collect(Collectors.toSet());
        
        Map<Long, EmployeeVO> employeeMap = personnelIntegration.getEmployeesInfo(participantIds);
        
        participantVOs.forEach(vo -> {
            EmployeeVO employee = employeeMap.get(vo.getParticipantId());
            if (employee != null) {
                vo.setParticipantName(employee.getName());
                vo.setParticipantAvatar(employee.getAvatar());
                vo.setParticipantDepartment(employee.getDepartmentName());
                vo.setParticipantPosition(employee.getPositionName());
            }
        });
    }
    
    /**
     * 填充参与者员工信息
     * 
     * @param participantVO 参与者VO
     */
    private void fillParticipantEmployeeInfo(ScheduleParticipantVO participantVO) {
        if (participantVO == null) {
            return;
        }
        
        EmployeeVO employee = personnelIntegration.getEmployeeInfo(participantVO.getParticipantId());
        if (employee != null) {
            participantVO.setParticipantName(employee.getName());
            participantVO.setParticipantAvatar(employee.getAvatar());
            participantVO.setParticipantDepartment(employee.getDepartmentName());
            participantVO.setParticipantPosition(employee.getPositionName());
        }
    }
    
    // 以下是实现BaseService接口的方法
    
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
    public boolean exists(QueryWrapper<ScheduleParticipant> wrapper) {
        return count(wrapper) > 0;
    }

    /**
     * 批量移除参与者
     *
     * @param scheduleId 日程ID
     * @param userIds    用户ID列表
     * @return 移除的参与者数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchRemoveParticipants(Long scheduleId, List<Long> userIds) {
        log.info("批量移除日程参与者，日程ID：{}，参与者数量：{}", scheduleId, userIds.size());
        
        if (userIds.isEmpty()) {
            return 0;
        }
        
        // 构建查询条件
        LambdaQueryWrapper<ScheduleParticipant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleParticipant::getScheduleId, scheduleId)
                .in(ScheduleParticipant::getParticipantId, userIds);
        
        // 查询匹配的参与者记录
        List<ScheduleParticipant> participants = list(queryWrapper);
        if (participants.isEmpty()) {
            return 0;
        }
        
        // 获取参与者ID列表
        List<Long> participantIds = participants.stream()
                .map(ScheduleParticipant::getId)
                .collect(Collectors.toList());
        
        // 批量删除
        boolean success = removeByIds(participantIds);
        return success ? participants.size() : 0;
    }

    /**
     * 更新参与状态
     *
     * @param scheduleId 日程ID
     * @param userId     用户ID
     * @param status     状态
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long scheduleId, Long userId, Integer status) {
        log.info("更新参与状态，日程ID：{}，用户ID：{}，状态：{}", scheduleId, userId, status);
        
        // 查询参与者记录
        LambdaQueryWrapper<ScheduleParticipant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleParticipant::getScheduleId, scheduleId)
                .eq(ScheduleParticipant::getParticipantId, userId);
        
        ScheduleParticipant participant = getOne(queryWrapper);
        if (participant == null) {
            log.error("更新参与状态失败，参与者不存在，日程ID：{}，用户ID：{}", scheduleId, userId);
            return false;
        }
        
        // 更新状态
        participant.setResponseStatus(status);
        return updateById(participant);
    }

    /**
     * 获取参与者详情
     *
     * @param id 参与者ID
     * @return 参与者详情
     */
    @Override
    public ScheduleParticipantVO getDetail(Long id) {
        log.info("获取参与者详情，ID：{}", id);
        
        // 查询参与者
        ScheduleParticipant participant = getById(id);
        if (participant == null) {
            return null;
        }
        
        // 转换为VO
        ScheduleParticipantVO participantVO = participantConvert.toVO(participant);
        
        // 填充参与者信息
        fillParticipantEmployeeInfo(participantVO);
        
        return participantVO;
    }

    /**
     * 分页查询参与者
     *
     * @param pageNum    页码
     * @param pageSize   每页大小
     * @param scheduleId 日程ID
     * @param userId     用户ID
     * @param role       角色
     * @param status     状态
     * @return 分页结果
     */
    @Override
    public Page<ScheduleParticipantVO> pageParticipants(Integer pageNum, Integer pageSize, Long scheduleId, Long userId, Integer role, Integer status) {
        log.info("分页查询参与者，页码：{}，每页大小：{}，日程ID：{}，用户ID：{}，角色：{}，状态：{}", pageNum, pageSize, scheduleId, userId, role, status);
        
        // 构建查询条件
        LambdaQueryWrapper<ScheduleParticipant> queryWrapper = new LambdaQueryWrapper<>();
        if (scheduleId != null) {
            queryWrapper.eq(ScheduleParticipant::getScheduleId, scheduleId);
        }
        if (userId != null) {
            queryWrapper.eq(ScheduleParticipant::getParticipantId, userId);
        }
        if (role != null) {
            queryWrapper.eq(ScheduleParticipant::getParticipantType, role);
        }
        if (status != null) {
            queryWrapper.eq(ScheduleParticipant::getResponseStatus, status);
        }
        
        // 分页查询
        Page<ScheduleParticipant> page = new Page<>(pageNum, pageSize);
        page = page(page, queryWrapper);
        
        // 转换为VO
        Page<ScheduleParticipantVO> resultPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        if (page.getRecords().isEmpty()) {
            return resultPage;
        }
        
        // 转换记录
        List<ScheduleParticipantVO> records = page.getRecords().stream()
                .map(participantConvert::toVO)
                .collect(Collectors.toList());
        
        // 填充参与者信息
        fillParticipantEmployeeInfo(records);
        
        resultPage.setRecords(records);
        return resultPage;
    }

    /**
     * 检查用户是否参与日程
     *
     * @param scheduleId 日程ID
     * @param userId     用户ID
     * @return 是否参与
     */
    @Override
    public boolean isParticipated(Long scheduleId, Long userId) {
        log.info("检查用户是否参与日程，日程ID：{}，用户ID：{}", scheduleId, userId);
        
        // 构建查询条件
        LambdaQueryWrapper<ScheduleParticipant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleParticipant::getScheduleId, scheduleId)
                .eq(ScheduleParticipant::getParticipantId, userId);
        
        // 查询是否存在
        return exists(queryWrapper);
    }
}