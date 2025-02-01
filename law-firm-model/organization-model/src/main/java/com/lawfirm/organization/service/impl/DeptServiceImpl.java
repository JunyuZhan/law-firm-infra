package com.lawfirm.organization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.organization.mapper.DeptMapper;
import com.lawfirm.organization.model.entity.Dept;
import com.lawfirm.organization.model.dto.DeptDTO;
import com.lawfirm.organization.service.DeptService;
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
public class DeptServiceImpl extends BaseServiceImpl<DeptMapper, Dept, DeptDTO> implements DeptService {

    private final DeptMapper deptMapper;

    @Override
    public DeptDTO createDTO() {
        return new DeptDTO();
    }

    @Override
    public Dept createEntity() {
        return new Dept();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Dept dept) {
        // 检查部门名称是否重复
        if (existsByDeptName(dept.getDeptName(), dept.getParentId())) {
            throw new BusinessException("部门名称已存在");
        }

        // 设置祖级列表
        if (dept.getParentId() != null) {
            Dept parent = getById(dept.getParentId());
            if (parent == null) {
                throw new BusinessException("父部门不存在");
            }
            dept.setAncestors(parent.getAncestors() + "," + parent.getId());
        } else {
            dept.setAncestors("0");
        }

        save(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Dept dept) {
        // 检查部门名称是否重复
        if (existsByDeptName(dept.getDeptName(), dept.getParentId(), dept.getId())) {
            throw new BusinessException("部门名称已存在");
        }

        // 更新祖级列表
        if (dept.getParentId() != null) {
            Dept parent = getById(dept.getParentId());
            if (parent == null) {
                throw new BusinessException("父部门不存在");
            }
            dept.setAncestors(parent.getAncestors() + "," + parent.getId());
        } else {
            dept.setAncestors("0");
        }

        updateById(dept);

        // 更新子部门的祖级列表
        List<Dept> children = listChildren(dept.getId());
        for (Dept child : children) {
            child.setAncestors(dept.getAncestors() + "," + dept.getId());
            updateById(child);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 检查是否存在子部门
        if (hasChildren(id)) {
            throw new BusinessException("存在子部门,不允许删除");
        }

        removeById(id);
    }

    @Override
    public List<Dept> listChildren(Long parentId) {
        return deptMapper.selectChildren(parentId);
    }

    @Override
    public List<Dept> getPath(Long id) {
        return deptMapper.selectPath(id);
    }

    @Override
    public List<Dept> getTree() {
        return deptMapper.selectTree();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveDept(Long id, Long parentId) {
        // 检查父部门是否存在
        if (parentId != null && !exists(new LambdaQueryWrapper<Dept>().eq(Dept::getId, parentId))) {
            throw new BusinessException("父部门不存在");
        }

        // 检查是否将部门移动到其子部门下
        if (isChildOf(parentId, id)) {
            throw new BusinessException("不能将部门移动到其子部门下");
        }

        Dept dept = getById(id);
        dept.setParentId(parentId);

        // 更新祖级列表
        if (parentId != null) {
            Dept parent = getById(parentId);
            dept.setAncestors(parent.getAncestors() + "," + parent.getId());
        } else {
            dept.setAncestors("0");
        }

        updateById(dept);

        // 更新子部门的祖级列表
        List<Dept> children = listChildren(id);
        for (Dept child : children) {
            child.setAncestors(dept.getAncestors() + "," + dept.getId());
            updateById(child);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reorderDept(Long id, Integer orderNum) {
        Dept dept = getById(id);
        dept.setOrderNum(orderNum);
        updateById(dept);
    }

    @Override
    public List<DeptDTO> getDeptTree() {
        return convertToTree(list());
    }

    @Override
    public List<DeptDTO> getRoleDepts(Long roleId) {
        List<Dept> depts = deptMapper.selectByRoleId(roleId);
        return depts.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<DeptDTO> getUserDepts(Long userId) {
        List<Dept> depts = deptMapper.selectByUserId(userId);
        return depts.stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * 检查部门名称是否存在
     */
    private boolean existsByDeptName(String deptName, Long parentId) {
        LambdaQueryWrapper<Dept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dept::getDeptName, deptName)
                .eq(Dept::getParentId, parentId);
        return exists(wrapper);
    }

    /**
     * 检查部门名称是否存在(排除自身)
     */
    private boolean existsByDeptName(String deptName, Long parentId, Long excludeId) {
        LambdaQueryWrapper<Dept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dept::getDeptName, deptName)
                .eq(Dept::getParentId, parentId)
                .ne(Dept::getId, excludeId);
        return exists(wrapper);
    }

    /**
     * 检查是否存在子部门
     */
    private boolean hasChildren(Long id) {
        LambdaQueryWrapper<Dept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dept::getParentId, id);
        return exists(wrapper);
    }

    /**
     * 检查部门是否是另一个部门的子部门
     */
    private boolean isChildOf(Long parentId, Long childId) {
        if (parentId == null) {
            return false;
        }
        List<Dept> path = getPath(childId);
        return path.stream().anyMatch(dept -> dept.getId().equals(parentId));
    }

    /**
     * 将部门列表转换为树形结构
     */
    private List<DeptDTO> convertToTree(List<Dept> depts) {
        List<DeptDTO> deptDTOs = depts.stream().map(this::toDTO).collect(Collectors.toList());
        Map<Long, DeptDTO> deptMap = deptDTOs.stream().collect(Collectors.toMap(DeptDTO::getId, dept -> dept));
        List<DeptDTO> roots = new ArrayList<>();

        for (DeptDTO dept : deptDTOs) {
            if (dept.getParentId() == null) {
                roots.add(dept);
            } else {
                DeptDTO parent = deptMap.get(dept.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(dept);
                }
            }
        }

        return roots;
    }
} 
