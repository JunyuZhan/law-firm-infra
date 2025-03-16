package com.lawfirm.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lawfirm.model.organization.dto.position.PositionDTO;
import com.lawfirm.model.organization.entity.base.Position;
import com.lawfirm.model.organization.enums.PositionTypeEnum;
import com.lawfirm.model.organization.mapper.PositionMapper;
import com.lawfirm.personnel.service.ExtendedPositionService;
import com.lawfirm.model.personnel.entity.Employee;
import com.lawfirm.model.personnel.mapper.EmployeeMapper;
import com.lawfirm.personnel.converter.PositionConverter;
import com.lawfirm.common.core.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 职位服务实现类
 * 实现扩展的ExtendedPositionService接口
 */
@Slf4j
@Service
public class PositionServiceImpl implements ExtendedPositionService {

    @Autowired
    private PositionMapper positionMapper;
    
    @Autowired
    private EmployeeMapper employeeMapper;
    
    @Autowired
    private PositionConverter positionConverter;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPosition(PositionDTO positionDTO) {
        // 1. 验证参数
        if (positionDTO == null) {
            throw new ValidationException("职位信息不能为空");
        }
        
        if (!StringUtils.hasText(positionDTO.getName())) {
            throw new ValidationException("职位名称不能为空");
        }
        
        if (positionDTO.getDepartmentId() == null) {
            throw new ValidationException("所属部门ID不能为空");
        }
        
        // 2. 检查同组织下职位名称是否重复
        checkPositionNameDuplicate(positionDTO.getName(), positionDTO.getDepartmentId(), null);
        
        // 3. 转换DTO为实体
        Position position = positionConverter.toEntity(positionDTO);
        
        // 4. 设置默认值
        if (position.getStatus() == null) {
            position.setStatus(1);
        }
        
        // 5. 保存职位信息
        positionMapper.insert(position);
        
        // 6. 发布职位创建事件
        // eventPublisher.publishEvent(new PositionCreatedEvent(position));
        
        return position.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePosition(Long id, PositionDTO positionDTO) {
        // 1. 验证参数
        if (id == null) {
            throw new ValidationException("职位ID不能为空");
        }
        
        if (positionDTO == null) {
            throw new ValidationException("职位信息不能为空");
        }
        
        // 2. 查询职位信息
        Position position = getPositionById(id);
        if (position == null) {
            throw new ValidationException("职位不存在");
        }
        
        // 3. 检查职位名称是否重复（如果修改了名称）
        if (StringUtils.hasText(positionDTO.getName()) && !positionDTO.getName().equals(position.getName())) {
            checkPositionNameDuplicate(positionDTO.getName(), position.getDepartmentId(), id);
        }
        
        // 4. 转换并更新字段
        Position updatedPosition = positionConverter.updateEntity(position, positionDTO);
        
        // 5. 执行更新
        positionMapper.updateById(updatedPosition);
        
        // 6. 发布职位更新事件
        // eventPublisher.publishEvent(new PositionUpdatedEvent(updatedPosition));
        
        return true;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPosition(Position position) {
        // 验证参数
        if (position == null) {
            throw new ValidationException("职位信息不能为空");
        }
        
        if (!StringUtils.hasText(position.getName())) {
            throw new ValidationException("职位名称不能为空");
        }
        
        if (position.getDepartmentId() == null) {
            throw new ValidationException("所属部门ID不能为空");
        }
        
        // 检查同组织下职位名称是否重复
        checkPositionNameDuplicate(position.getName(), position.getDepartmentId(), null);
        
        // 设置默认值
        if (position.getStatus() == null) {
            position.setStatus(1);
        }
        
        // 保存职位信息
        positionMapper.insert(position);
        
        return position.getId();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePosition(Position position) {
        // 验证参数
        if (position == null || position.getId() == null) {
            throw new ValidationException("职位信息不完整");
        }
        
        // 查询职位信息
        Position existingPosition = getPositionById(position.getId());
        if (existingPosition == null) {
            throw new ValidationException("职位不存在");
        }
        
        // 检查职位名称是否重复（如果修改了名称）
        if (StringUtils.hasText(position.getName()) && !position.getName().equals(existingPosition.getName())) {
            checkPositionNameDuplicate(position.getName(), position.getDepartmentId(), position.getId());
        }
        
        // 执行更新
        positionMapper.updateById(position);
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePosition(Long id) {
        // 1. 验证参数
        if (id == null) {
            throw new ValidationException("职位ID不能为空");
        }
        
        // 2. 查询职位信息
        Position position = getPositionById(id);
        if (position == null) {
            throw new ValidationException("职位不存在");
        }
        
        // 3. 检查是否有员工使用该职位
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getPositionId, id);
        Long count = employeeMapper.selectCount(queryWrapper);
        
        if (count != null && count > 0) {
            throw new ValidationException("该职位下还有员工，不能删除");
        }
        
        // 4. 执行逻辑删除
        positionMapper.deleteById(id);
        
        // 5. 发布职位删除事件
        // eventPublisher.publishEvent(new PositionDeletedEvent(position));
        
        return true;
    }

    @Override
    public Position getPositionById(Long id) {
        if (id == null) {
            return null;
        }
        
        return positionMapper.selectById(id);
    }

    @Override
    public PositionDTO getPositionDTOById(Long id) {
        Position position = getPositionById(id);
        return position != null ? positionConverter.toDTO(position) : null;
    }
    
    @Override
    public List<Position> getPositionsByDepartmentId(Long departmentId) {
        if (departmentId == null) {
            return List.of();
        }
        
        LambdaQueryWrapper<Position> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Position::getDepartmentId, departmentId)
                .eq(Position::getStatus, 1)
                .orderByAsc(Position::getSort);
        
        return positionMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<Position> getPositionsByType(PositionTypeEnum type) {
        if (type == null) {
            return List.of();
        }
        
        LambdaQueryWrapper<Position> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Position::getType, type.getValue())
                .eq(Position::getStatus, 1)
                .orderByAsc(Position::getSort);
        
        return positionMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<Position> getAllPositions() {
        LambdaQueryWrapper<Position> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Position::getStatus, 1)
                .orderByAsc(Position::getSort);
        
        return positionMapper.selectList(queryWrapper);
    }

    @Override
    public List<PositionDTO> listPositionsByOrganizationId(Long organizationId) {
        if (organizationId == null) {
            throw new ValidationException("组织ID不能为空");
        }
        
        LambdaQueryWrapper<Position> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Position::getDepartmentId, organizationId)
                .eq(Position::getStatus, 1)
                .orderByAsc(Position::getSort);
        
        List<Position> positions = positionMapper.selectList(queryWrapper);
        
        return positions.stream()
                .map(positionConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean enablePosition(Long id) {
        if (id == null) {
            throw new ValidationException("职位ID不能为空");
        }
        
        Position position = getPositionById(id);
        if (position == null) {
            throw new ValidationException("职位不存在");
        }
        
        position.setStatus(1);
        positionMapper.updateById(position);
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disablePosition(Long id) {
        if (id == null) {
            throw new ValidationException("职位ID不能为空");
        }
        
        Position position = getPositionById(id);
        if (position == null) {
            throw new ValidationException("职位不存在");
        }
        
        // 检查是否有员工使用该职位
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Employee::getPositionId, id)
                .eq(Employee::getStatus, 1);
        Long count = employeeMapper.selectCount(queryWrapper);
        
        if (count != null && count > 0) {
            throw new ValidationException("该职位下还有在职员工，不能禁用");
        }
        
        position.setStatus(0);
        positionMapper.updateById(position);
        
        return true;
    }

    /**
     * 检查职位名称是否重复
     *
     * @param name 职位名称
     * @param departmentId 部门ID
     * @param excludeId 排除的职位ID
     */
    private void checkPositionNameDuplicate(String name, Long departmentId, Long excludeId) {
        LambdaQueryWrapper<Position> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Position::getName, name)
                .eq(Position::getDepartmentId, departmentId);
        
        if (excludeId != null) {
            queryWrapper.ne(Position::getId, excludeId);
        }
        
        Long count = positionMapper.selectCount(queryWrapper);
        
        if (count != null && count > 0) {
            throw new ValidationException("该部门下已存在同名职位");
        }
    }
}
