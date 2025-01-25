package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.exception.BusinessException;
import com.lawfirm.model.system.entity.SysDept;
import com.lawfirm.system.mapper.SysDeptMapper;
import com.lawfirm.system.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统部门服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysDeptMapper deptMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createDept(SysDept dept) {
        // 校验上级部门是否存在
        if (dept.getParentId() != null && dept.getParentId() != 0L) {
            if (!existsById(dept.getParentId())) {
                throw new BusinessException("上级部门不存在");
            }
        }
        
        // 保存部门
        save(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDept(SysDept dept) {
        // 校验部门是否存在
        if (!existsById(dept.getId())) {
            throw new BusinessException("部门不存在");
        }

        // 校验上级部门是否存在
        if (dept.getParentId() != null && dept.getParentId() != 0L) {
            if (!existsById(dept.getParentId())) {
                throw new BusinessException("上级部门不存在");
            }
        }
        
        // 更新部门
        updateById(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDept(Long id) {
        // 校验部门是否存在
        if (!existsById(id)) {
            throw new BusinessException("部门不存在");
        }

        // 校验是否存在子部门
        if (hasChildren(id)) {
            throw new BusinessException("存在子部门,不能删除");
        }
        
        // 删除部门
        removeById(id);
    }

    @Override
    public List<SysDept> listChildren(Long parentId) {
        return deptMapper.selectChildren(parentId);
    }

    @Override
    public List<SysDept> getPath(Long id) {
        return deptMapper.selectPath(id);
    }

    @Override
    public List<SysDept> getTree() {
        return deptMapper.selectTree();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveDept(Long id, Long parentId) {
        // 校验部门是否存在
        SysDept dept = getById(id);
        if (dept == null) {
            throw new BusinessException("部门不存在");
        }

        // 校验上级部门是否存在
        if (parentId != null && parentId != 0L) {
            if (!existsById(parentId)) {
                throw new BusinessException("上级部门不存在");
            }
        }

        // 更新上级部门ID
        dept.setParentId(parentId);
        updateById(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reorderDept(Long id, Integer orderNum) {
        // 校验部门是否存在
        SysDept dept = getById(id);
        if (dept == null) {
            throw new BusinessException("部门不存在");
        }

        // 更新排序号
        dept.setOrderNum(orderNum);
        updateById(dept);
    }

    /**
     * 判断是否存在子部门
     */
    private boolean hasChildren(Long id) {
        return lambdaQuery()
                .eq(SysDept::getParentId, id)
                .exists();
    }
} 