package com.lawfirm.model.organization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.model.organization.entity.Department;
import com.lawfirm.model.organization.vo.DepartmentVO;
import com.lawfirm.model.organization.dto.DepartmentDTO;
import com.lawfirm.model.organization.mapper.DepartmentMapper;
import com.lawfirm.model.organization.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 部门服务实现类
 */
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl extends BaseServiceImpl<DepartmentMapper, Department, DepartmentVO> implements DepartmentService {

    private final DepartmentMapper departmentMapper;

    @Override
    protected Department createEntity() {
        return new Department();
    }

    @Override
    protected DepartmentVO createVO() {
        return new DepartmentVO();
    }

    @Override
    public DepartmentVO entityToVO(Department entity) {
        if (entity == null) {
            return null;
        }
        DepartmentVO vo = createVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public Department toEntity(DepartmentVO vo) {
        if (vo == null) {
            return null;
        }
        Department entity = new Department();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }

    @Override
    public DepartmentVO toDTO(Department entity) {
        if (entity == null) {
            return null;
        }
        DepartmentVO vo = createVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public List<DepartmentVO> entityListToVOList(List<Department> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return new ArrayList<>();
        }
        return entityList.stream()
                .map(this::entityToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Department> voListToEntityList(List<DepartmentVO> voList) {
        if (voList == null || voList.isEmpty()) {
            return new ArrayList<>();
        }
        return voList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<DepartmentVO> getTree() {
        List<Department> departments = list();
        List<DepartmentVO> voList = entityListToVOList(departments);
        return buildTree(voList);
    }

    @Override
    public List<DepartmentVO> getChildren(Long parentId) {
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Department::getParentId, parentId);
        List<Department> children = list(wrapper);
        return entityListToVOList(children);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, Boolean enabled) {
        Department department = getById(id);
        if (department != null) {
            department.setEnabled(enabled);
            updateById(department);
        }
    }

    @Override
    @Transactional
    public void updateSort(Long id, Integer sortOrder) {
        Department department = getById(id);
        if (department != null) {
            department.setSortOrder(sortOrder);
            updateById(department);
        }
    }

    @Override
    @Transactional
    public void updateParent(Long id, Long parentId) {
        Department department = getById(id);
        if (department != null) {
            department.setParentId(parentId);
            updateById(department);
        }
    }

    @Override
    public boolean checkNameExists(String name, Long parentId) {
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Department::getName, name)
               .eq(Department::getParentId, parentId);
        return count(wrapper) > 0;
    }

    @Override
    public boolean hasChildren(Long id) {
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Department::getParentId, id);
        return count(wrapper) > 0;
    }

    private List<DepartmentVO> buildTree(List<DepartmentVO> departments) {
        Map<Long, List<DepartmentVO>> parentMap = departments.stream()
                .collect(Collectors.groupingBy(DepartmentVO::getParentId));
                
        departments.forEach(dept -> {
            List<DepartmentVO> children = parentMap.get(dept.getId());
            if (children != null) {
                dept.setChildren(children);
            }
        });
        
        return departments.stream()
                .filter(dept -> dept.getParentId() == null)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DepartmentVO create(DepartmentVO vo) {
        // 检查部门名称是否已存在
        if (checkNameExists(vo.getName(), vo.getParentId())) {
            throw new BusinessException("部门名称已存在");
        }
        
        Department department = toEntity(vo);
        save(department);
        return entityToVO(department);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DepartmentVO update(DepartmentVO vo) {
        Department existingDepartment = getById(vo.getId());
        if (existingDepartment == null) {
            throw new BusinessException("部门不存在");
        }

        // 检查部门名称是否已存在（排除自身）
        if (!existingDepartment.getName().equals(vo.getName()) && 
            checkNameExists(vo.getName(), vo.getParentId())) {
            throw new BusinessException("部门名称已存在");
        }

        Department department = toEntity(vo);
        updateById(department);
        return entityToVO(department);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 检查是否有子部门
        if (hasChildren(id)) {
            throw new BusinessException("存在子部门，无法删除");
        }
        removeById(id);
    }

    @Override
    public List<Department> listChildren(Long parentId) {
        return departmentMapper.selectChildren(parentId);
    }

    @Override
    public List<Department> getPath(Long id) {
        return departmentMapper.selectPath(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveDepartment(Long id, Long newParentId) {
        Department department = getById(id);
        if (department == null) {
            throw new BusinessException("部门不存在");
        }
        
        // 检查新父部门是否存在
        if (newParentId != null && getById(newParentId) == null) {
            throw new BusinessException("目标父部门不存在");
        }
        
        department.setParentId(newParentId);
        updateById(department);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reorderDepartment(Long id, Integer newOrder) {
        Department department = getById(id);
        if (department == null) {
            throw new BusinessException("部门不存在");
        }
        
        department.setSortOrder(newOrder);
        updateById(department);
    }

    @Override
    public List<DepartmentVO> getRoleDepartments(Long roleId) {
        // TODO: 实现获取角色关联的部门列表
        return new ArrayList<>();
    }

    @Override
    public List<DepartmentVO> getUserDepartments(Long userId) {
        // TODO: 实现获取用户关联的部门列表
        return new ArrayList<>();
    }
}