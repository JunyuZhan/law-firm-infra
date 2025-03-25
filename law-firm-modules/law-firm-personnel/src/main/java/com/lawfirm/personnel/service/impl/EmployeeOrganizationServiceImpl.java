package com.lawfirm.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lawfirm.model.personnel.entity.relation.EmployeeOrganizationRelation;
import com.lawfirm.model.personnel.entity.history.EmployeePositionHistory;
import com.lawfirm.model.personnel.mapper.EmployeeOrganizationRelationMapper;
import com.lawfirm.model.personnel.mapper.EmployeePositionHistoryMapper;
import com.lawfirm.model.personnel.service.EmployeeOrganizationService;
import com.lawfirm.common.core.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 人事员工组织关系服务实现类
 */
@Slf4j
@Service("personnelEmployeeOrgServiceImpl")
@RequiredArgsConstructor
public class EmployeeOrganizationServiceImpl implements EmployeeOrganizationService {

    private final EmployeeOrganizationRelationMapper relationMapper;
    
    private final EmployeePositionHistoryMapper positionHistoryMapper;
    
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Long getPrimaryOrganizationId(Long employeeId) {
        if (employeeId == null) {
            return null;
        }
        
        LambdaQueryWrapper<EmployeeOrganizationRelation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EmployeeOrganizationRelation::getEmployeeId, employeeId)
                .eq(EmployeeOrganizationRelation::getIsPrimary, true)
                .eq(EmployeeOrganizationRelation::getStatus, 1)
                .isNull(EmployeeOrganizationRelation::getEndDate)
                .or()
                .ge(EmployeeOrganizationRelation::getEndDate, LocalDate.now());
        
        EmployeeOrganizationRelation relation = relationMapper.selectOne(queryWrapper);
        return relation != null ? relation.getOrganizationId() : null;
    }

    @Override
    public List<Long> getEmployeeOrganizationIds(Long employeeId) {
        if (employeeId == null) {
            return List.of();
        }
        
        LambdaQueryWrapper<EmployeeOrganizationRelation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EmployeeOrganizationRelation::getEmployeeId, employeeId)
                .eq(EmployeeOrganizationRelation::getStatus, 1)
                .and(w -> w.isNull(EmployeeOrganizationRelation::getEndDate)
                        .or()
                        .ge(EmployeeOrganizationRelation::getEndDate, LocalDate.now()));
        
        List<EmployeeOrganizationRelation> relations = relationMapper.selectList(queryWrapper);
        
        return relations.stream()
                .map(EmployeeOrganizationRelation::getOrganizationId)
                .collect(Collectors.toList());
    }

    @Override
    public Long getEmployeePositionId(Long employeeId, Long organizationId) {
        if (employeeId == null || organizationId == null) {
            return null;
        }
        
        LambdaQueryWrapper<EmployeeOrganizationRelation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EmployeeOrganizationRelation::getEmployeeId, employeeId)
                .eq(EmployeeOrganizationRelation::getOrganizationId, organizationId)
                .eq(EmployeeOrganizationRelation::getStatus, 1)
                .and(w -> w.isNull(EmployeeOrganizationRelation::getEndDate)
                        .or()
                        .ge(EmployeeOrganizationRelation::getEndDate, LocalDate.now()));
        
        EmployeeOrganizationRelation relation = relationMapper.selectOne(queryWrapper);
        return relation != null ? relation.getPositionId() : null;
    }

    @Override
    public List<Long> getOrganizationEmployeeIds(Long organizationId, boolean includeSubOrgs) {
        if (organizationId == null) {
            return List.of();
        }
        
        // TODO: 如果includeSubOrgs为true，需要先获取所有子组织ID
        List<Long> orgIds = List.of(organizationId);
        if (includeSubOrgs) {
            // 获取子组织ID的逻辑，需要调用organization-model中的服务
            // 此处暂时只处理当前组织
        }
        
        LambdaQueryWrapper<EmployeeOrganizationRelation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(EmployeeOrganizationRelation::getOrganizationId, orgIds)
                .eq(EmployeeOrganizationRelation::getStatus, 1)
                .and(w -> w.isNull(EmployeeOrganizationRelation::getEndDate)
                        .or()
                        .ge(EmployeeOrganizationRelation::getEndDate, LocalDate.now()));
        
        List<EmployeeOrganizationRelation> relations = relationMapper.selectList(queryWrapper);
        
        return relations.stream()
                .map(EmployeeOrganizationRelation::getEmployeeId)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignEmployeeToOrganization(Long employeeId, Long organizationId, Long positionId, 
                                               boolean isPrimary, LocalDate startDate, LocalDate endDate) {
        // 1. 验证参数
        if (employeeId == null) {
            throw new ValidationException("员工ID不能为空");
        }
        
        if (organizationId == null) {
            throw new ValidationException("组织ID不能为空");
        }
        
        if (positionId == null) {
            throw new ValidationException("职位ID不能为空");
        }
        
        if (startDate == null) {
            startDate = LocalDate.now();
        }
        
        // 2. 检查是否已存在关联关系
        LambdaQueryWrapper<EmployeeOrganizationRelation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EmployeeOrganizationRelation::getEmployeeId, employeeId)
                .eq(EmployeeOrganizationRelation::getOrganizationId, organizationId)
                .eq(EmployeeOrganizationRelation::getStatus, 1)
                .and(w -> w.isNull(EmployeeOrganizationRelation::getEndDate)
                        .or()
                        .ge(EmployeeOrganizationRelation::getEndDate, LocalDate.now()));
        
        EmployeeOrganizationRelation existingRelation = relationMapper.selectOne(queryWrapper);
        
        // 如果存在，则更新
        if (existingRelation != null) {
            existingRelation.setPositionId(positionId);
            existingRelation.setIsPrimary(isPrimary);
            existingRelation.setStartDate(startDate);
            existingRelation.setEndDate(endDate);
            relationMapper.updateById(existingRelation);
            
            // 发布员工组织更新事件
            // eventPublisher.publishEvent(new EmployeeOrganizationUpdatedEvent(existingRelation));
            
            return true;
        }
        
        // 3. 如果指定为主要组织，需要将其他关联关系设置为非主要
        if (isPrimary) {
            updateOtherPrimaryRelations(employeeId);
        }
        
        // 4. 创建新的关联关系
        EmployeeOrganizationRelation relation = new EmployeeOrganizationRelation();
        relation.setEmployeeId(employeeId);
        relation.setOrganizationId(organizationId);
        relation.setPositionId(positionId);
        relation.setIsPrimary(isPrimary);
        relation.setStartDate(startDate);
        relation.setEndDate(endDate);
        relation.setStatus(1);
        
        relationMapper.insert(relation);
        
        // 发布员工组织分配事件
        // eventPublisher.publishEvent(new EmployeeOrganizationAssignedEvent(relation));
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeEmployeeFromOrganization(Long employeeId, Long organizationId, LocalDate endDate) {
        // 1. 验证参数
        if (employeeId == null) {
            throw new ValidationException("员工ID不能为空");
        }
        
        if (organizationId == null) {
            throw new ValidationException("组织ID不能为空");
        }
        
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        
        // 2. 查询现有关联关系
        LambdaQueryWrapper<EmployeeOrganizationRelation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EmployeeOrganizationRelation::getEmployeeId, employeeId)
                .eq(EmployeeOrganizationRelation::getOrganizationId, organizationId)
                .eq(EmployeeOrganizationRelation::getStatus, 1)
                .and(w -> w.isNull(EmployeeOrganizationRelation::getEndDate)
                        .or()
                        .ge(EmployeeOrganizationRelation::getEndDate, LocalDate.now()));
        
        EmployeeOrganizationRelation relation = relationMapper.selectOne(queryWrapper);
        
        if (relation == null) {
            return false;
        }
        
        // 3. 设置关联关系结束日期
        relation.setEndDate(endDate);
        relationMapper.updateById(relation);
        
        // 4. 如果是主要组织，需要重新分配主要组织
        if (relation.getIsPrimary()) {
            reassignPrimaryOrganization(employeeId);
        }
        
        // 发布员工组织移除事件
        // eventPublisher.publishEvent(new EmployeeOrganizationRemovedEvent(relation));
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateEmployeePosition(Long employeeId, Long organizationId, Long positionId, String reason) {
        // 1. 验证参数
        if (employeeId == null) {
            throw new ValidationException("员工ID不能为空");
        }
        
        if (organizationId == null) {
            throw new ValidationException("组织ID不能为空");
        }
        
        if (positionId == null) {
            throw new ValidationException("职位ID不能为空");
        }
        
        // 2. 查询现有关联关系
        LambdaQueryWrapper<EmployeeOrganizationRelation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EmployeeOrganizationRelation::getEmployeeId, employeeId)
                .eq(EmployeeOrganizationRelation::getOrganizationId, organizationId)
                .eq(EmployeeOrganizationRelation::getStatus, 1)
                .and(w -> w.isNull(EmployeeOrganizationRelation::getEndDate)
                        .or()
                        .ge(EmployeeOrganizationRelation::getEndDate, LocalDate.now()));
        
        EmployeeOrganizationRelation relation = relationMapper.selectOne(queryWrapper);
        
        if (relation == null) {
            throw new ValidationException("员工与组织的关联关系不存在");
        }
        
        // 3. 记录职位变更历史
        savePositionChangeHistory(employeeId, organizationId, relation.getPositionId(), positionId, reason);
        
        // 4. 更新职位ID
        relation.setPositionId(positionId);
        relationMapper.updateById(relation);
        
        // 发布员工职位变更事件
        // eventPublisher.publishEvent(new EmployeePositionChangedEvent(relation, relation.getPositionId(), positionId));
        
        return true;
    }

    @Override
    public List<Map<String, Object>> getEmployeeOrganizationHistory(Long employeeId) {
        if (employeeId == null) {
            return List.of();
        }
        
        // 查询员工的所有组织关系记录（包括历史记录）
        LambdaQueryWrapper<EmployeeOrganizationRelation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EmployeeOrganizationRelation::getEmployeeId, employeeId)
                .orderByDesc(EmployeeOrganizationRelation::getStartDate);
        
        List<EmployeeOrganizationRelation> relations = relationMapper.selectList(queryWrapper);
        
        // 转换为Map列表
        return relations.stream()
                .map(relation -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("relationId", relation.getId());
                    map.put("employeeId", relation.getEmployeeId());
                    map.put("organizationId", relation.getOrganizationId());
                    map.put("positionId", relation.getPositionId());
                    map.put("isPrimary", relation.getIsPrimary());
                    map.put("startDate", relation.getStartDate());
                    map.put("endDate", relation.getEndDate());
                    map.put("status", relation.getStatus());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean isEmployeeInOrganization(Long employeeId, Long organizationId, boolean checkSubOrgs) {
        if (employeeId == null || organizationId == null) {
            return false;
        }
        
        // 首先检查直接关系
        LambdaQueryWrapper<EmployeeOrganizationRelation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EmployeeOrganizationRelation::getEmployeeId, employeeId)
                .eq(EmployeeOrganizationRelation::getOrganizationId, organizationId)
                .eq(EmployeeOrganizationRelation::getStatus, 1)
                .and(w -> w.isNull(EmployeeOrganizationRelation::getEndDate)
                        .or()
                        .ge(EmployeeOrganizationRelation::getEndDate, LocalDate.now()));
        
        Long count = relationMapper.selectCount(queryWrapper);
        if (count != null && count > 0) {
            return true;
        }
        
        // 如果需要检查子组织，则获取所有子组织ID后再查询
        if (checkSubOrgs) {
            // TODO: 获取子组织ID的逻辑，需要调用organization-model中的服务
            List<Long> subOrgIds = getSubOrganizationIds(organizationId);
            
            if (!subOrgIds.isEmpty()) {
                queryWrapper = Wrappers.lambdaQuery();
                queryWrapper.eq(EmployeeOrganizationRelation::getEmployeeId, employeeId)
                        .in(EmployeeOrganizationRelation::getOrganizationId, subOrgIds)
                        .eq(EmployeeOrganizationRelation::getStatus, 1)
                        .and(w -> w.isNull(EmployeeOrganizationRelation::getEndDate)
                                .or()
                                .ge(EmployeeOrganizationRelation::getEndDate, LocalDate.now()));
                
                count = relationMapper.selectCount(queryWrapper);
                return count != null && count > 0;
            }
        }
        
        return false;
    }

    @Override
    public Map<Long, Long> getEmployeeOrganizationPositions(Long employeeId) {
        if (employeeId == null) {
            return Map.of();
        }
        
        // 查询员工的所有当前有效的组织关系
        LambdaQueryWrapper<EmployeeOrganizationRelation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EmployeeOrganizationRelation::getEmployeeId, employeeId)
                .eq(EmployeeOrganizationRelation::getStatus, 1)
                .and(w -> w.isNull(EmployeeOrganizationRelation::getEndDate)
                        .or()
                        .ge(EmployeeOrganizationRelation::getEndDate, LocalDate.now()))
                .orderByDesc(EmployeeOrganizationRelation::getIsPrimary)
                .orderByAsc(EmployeeOrganizationRelation::getStartDate);
        
        List<EmployeeOrganizationRelation> relations = relationMapper.selectList(queryWrapper);
        
        // 转换为Map: 组织ID -> 职位ID
        return relations.stream()
                .collect(Collectors.toMap(
                    EmployeeOrganizationRelation::getOrganizationId,
                    EmployeeOrganizationRelation::getPositionId,
                    (existing, replacement) -> existing // 如果有重复的组织ID，保留第一个职位ID
                ));
    }
    
    /**
     * 将员工的其他组织关系设置为非主要
     */
    private void updateOtherPrimaryRelations(Long employeeId) {
        if (employeeId == null) {
            return;
        }
        
        LambdaQueryWrapper<EmployeeOrganizationRelation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EmployeeOrganizationRelation::getEmployeeId, employeeId)
                .eq(EmployeeOrganizationRelation::getIsPrimary, true)
                .eq(EmployeeOrganizationRelation::getStatus, 1)
                .and(w -> w.isNull(EmployeeOrganizationRelation::getEndDate)
                        .or()
                        .ge(EmployeeOrganizationRelation::getEndDate, LocalDate.now()));
        
        List<EmployeeOrganizationRelation> relations = relationMapper.selectList(queryWrapper);
        
        for (EmployeeOrganizationRelation relation : relations) {
            relation.setIsPrimary(false);
            relationMapper.updateById(relation);
        }
    }
    
    /**
     * 重新分配员工主要组织
     * 如果当前无主要组织，则将员工最早加入的组织设为主要组织
     */
    private void reassignPrimaryOrganization(Long employeeId) {
        if (employeeId == null) {
            return;
        }
        
        // 查询员工当前所有有效关联关系
        LambdaQueryWrapper<EmployeeOrganizationRelation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EmployeeOrganizationRelation::getEmployeeId, employeeId)
                .eq(EmployeeOrganizationRelation::getStatus, 1)
                .and(w -> w.isNull(EmployeeOrganizationRelation::getEndDate)
                        .or()
                        .ge(EmployeeOrganizationRelation::getEndDate, LocalDate.now()))
                .orderByAsc(EmployeeOrganizationRelation::getStartDate);
        
        List<EmployeeOrganizationRelation> relations = relationMapper.selectList(queryWrapper);
        
        // 检查是否已有主要组织
        boolean hasPrimary = relations.stream()
                .anyMatch(EmployeeOrganizationRelation::getIsPrimary);
        
        // 如果没有主要组织且有组织关系，则将第一个（最早加入的）设为主要
        if (!hasPrimary && !relations.isEmpty()) {
            EmployeeOrganizationRelation firstRelation = relations.get(0);
            firstRelation.setIsPrimary(true);
            relationMapper.updateById(firstRelation);
        }
    }
    
    /**
     * 保存职位变更历史记录
     */
    private void savePositionChangeHistory(Long employeeId, Long organizationId, Long oldPositionId, Long newPositionId, String reason) {
        EmployeePositionHistory history = new EmployeePositionHistory();
        history.setEmployeeId(employeeId);
        history.setOrganizationId(organizationId);
        history.setFromPositionId(oldPositionId);
        history.setToPositionId(newPositionId);
        history.setEffectiveDate(LocalDate.now());
        history.setChangeReason(reason);
        
        positionHistoryMapper.insert(history);
    }
    
    /**
     * 获取组织的所有子组织ID（暂时实现为空）
     * 实际实现需要调用组织模块的服务
     */
    private List<Long> getSubOrganizationIds(Long organizationId) {
        // TODO: 调用organization-model中的服务获取子组织ID
        return new ArrayList<>();
    }
}