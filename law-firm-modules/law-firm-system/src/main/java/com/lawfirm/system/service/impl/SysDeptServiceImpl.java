package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.model.system.entity.SysDept;
import com.lawfirm.model.system.dto.SysDeptDTO;
import com.lawfirm.model.system.vo.SysDeptVO;
import com.lawfirm.system.mapper.SysDeptMapper;
import com.lawfirm.system.service.SysDeptService;
import com.lawfirm.common.data.vo.BaseVO;
import com.lawfirm.common.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptMapper, SysDept, SysDeptVO> implements SysDeptService {

    private final SysDeptMapper deptMapper;

    @Override
    protected SysDept createEntity() {
        return new SysDept();
    }

    @Override
    protected SysDeptVO createVO() {
        return new SysDeptVO();
    }

    private SysDeptVO dtoToVO(SysDeptDTO dto) {
        if (dto == null) {
            return null;
        }
        SysDeptVO vo = createVO();
        BeanUtils.copyProperties(dto, vo);
        return vo;
    }

    private SysDept dtoToEntity(SysDeptDTO dto) {
        if (dto == null) {
            return null;
        }
        SysDept entity = createEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public SysDeptVO entityToVO(SysDept entity) {
        if (entity == null) {
            return null;
        }
        SysDeptVO vo = createVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public SysDept voToEntity(SysDeptVO vo) {
        if (vo == null) {
            return null;
        }
        SysDept entity = createEntity();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }

    @Override
    public List<SysDeptVO> buildDeptTree(List<SysDeptVO> depts) {
        if (CollectionUtils.isEmpty(depts)) {
            return new ArrayList<>();
        }

        List<SysDeptVO> returnList = new ArrayList<>();
        List<Long> tempList = depts.stream().map(SysDeptVO::getId).collect(Collectors.toList());
        for (SysDeptVO dept : depts) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    @Override
    public List<Long> selectDeptListByRoleId(Long roleId) {
        return deptMapper.selectDeptListByRoleId(roleId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkDeptNameUnique(SysDeptDTO dept) {
        Long deptId = dept.getId() == null ? -1L : dept.getId();
        SysDept info = deptMapper.selectOne(new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getName, dept.getName())
                .eq(SysDept::getParentId, dept.getParentId()));
        return info == null || info.getId().equals(deptId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasChildByDeptId(Long deptId) {
        return deptMapper.selectCount(new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getParentId, deptId)) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkDeptExistUser(Long deptId) {
        return deptMapper.checkDeptExistUser(deptId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDeptVO> list, SysDeptVO t) {
        // 得到子节点列表
        List<SysDeptVO> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDeptVO tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDeptVO> getChildList(List<SysDeptVO> list, SysDeptVO t) {
        return list.stream()
                .filter(n -> n.getParentId().equals(t.getId()))
                .collect(Collectors.toList());
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDeptVO> list, SysDeptVO t) {
        return !getChildList(list, t).isEmpty();
    }

    @Override
    public List<SysDeptVO> listByParentId(Long parentId) {
        List<SysDept> depts = deptMapper.selectList(new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getParentId, parentId));
        return entityListToVOList(depts);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDeptVO create(SysDeptDTO dto) {
        // 检查部门名称是否已存在
        if (!checkDeptNameUnique(dto)) {
            throw new BusinessException("部门名称已存在");
        }
        
        SysDept entity = dtoToEntity(dto);
        save(entity);
        return entityToVO(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDeptVO update(SysDeptDTO dto) {
        // 检查部门是否存在
        SysDept existingDept = getById(dto.getId());
        if (existingDept == null) {
            throw new BusinessException("部门不存在");
        }

        // 检查部门名称是否已存在
        if (!checkDeptNameUnique(dto)) {
            throw new BusinessException("部门名称已存在");
        }

        SysDept entity = dtoToEntity(dto);
        updateById(entity);
        return entityToVO(entity);
    }
} 