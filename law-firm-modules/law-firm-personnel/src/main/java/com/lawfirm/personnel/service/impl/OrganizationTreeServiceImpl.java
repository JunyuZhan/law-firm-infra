package com.lawfirm.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lawfirm.model.organization.entity.department.Department;
import com.lawfirm.model.organization.enums.DepartmentTypeEnum;
import com.lawfirm.model.organization.mapper.DepartmentMapper;
import com.lawfirm.model.organization.service.OrganizationTreeService;
import com.lawfirm.model.organization.vo.OrganizationTreeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 组织树服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationTreeServiceImpl implements OrganizationTreeService {

    private final DepartmentMapper departmentMapper;

    @Override
    public OrganizationTreeVO getTree(Long rootId) {
        log.info("获取组织树: rootId={}", rootId);
        Department rootDept = departmentMapper.selectById(rootId);
        if (rootDept == null) {
            return null;
        }
        
        OrganizationTreeVO root = convertToVO(rootDept);
        buildTree(root);
        return root;
    }

    @Override
    public String getPath(Long id) {
        log.info("获取组织路径: id={}", id);
        List<OrganizationTreeVO> parents = getAllParents(id);
        if (parents.isEmpty()) {
            Department dept = departmentMapper.selectById(id);
            return dept != null ? dept.getName() : "";
        }
        
        Collections.reverse(parents);
        StringBuilder path = new StringBuilder();
        for (OrganizationTreeVO parent : parents) {
            path.append(parent.getName()).append("/");
        }
        
        Department dept = departmentMapper.selectById(id);
        if (dept != null) {
            path.append(dept.getName());
        }
        
        return path.toString();
    }

    @Override
    public int getLevel(Long id) {
        log.info("获取组织层级: id={}", id);
        return getAllParents(id).size() + 1;
    }

    @Override
    public List<OrganizationTreeVO> getChildren(Long parentId) {
        log.info("获取子组织: parentId={}", parentId);
        List<Department> depts = departmentMapper.selectByParentId(parentId);
        return depts.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public OrganizationTreeVO getParent(Long id) {
        log.info("获取父组织: id={}", id);
        Department dept = departmentMapper.selectById(id);
        if (dept == null || dept.getParentId() == null) {
            return null;
        }
        
        Department parent = departmentMapper.selectById(dept.getParentId());
        return parent != null ? convertToVO(parent) : null;
    }

    @Override
    public List<OrganizationTreeVO> getAllParents(Long id) {
        log.info("获取所有父组织: id={}", id);
        List<OrganizationTreeVO> parents = new ArrayList<>();
        Department dept = departmentMapper.selectById(id);
        
        while (dept != null && dept.getParentId() != null) {
            dept = departmentMapper.selectById(dept.getParentId());
            if (dept != null) {
                parents.add(convertToVO(dept));
            }
        }
        
        return parents;
    }

    @Override
    public void move(Long id, Long targetParentId) {
        log.info("移动组织: id={}, targetParentId={}", id, targetParentId);
        Department dept = departmentMapper.selectById(id);
        if (dept == null) {
            return;
        }
        
        // 检查是否有循环依赖
        if (isChild(id, targetParentId)) {
            throw new IllegalArgumentException("不能将组织移动到其子组织下");
        }
        
        dept.setParentId(targetParentId);
        departmentMapper.updateById(dept);
    }

    @Override
    public boolean isChild(Long parentId, Long childId) {
        log.info("检查是否是子组织: parentId={}, childId={}", parentId, childId);
        if (parentId.equals(childId)) {
            return false;
        }
        
        List<Long> childIds = getAllChildIds(parentId);
        return childIds.contains(childId);
    }

    @Override
    public boolean isParent(Long childId, Long parentId) {
        log.info("检查是否是父组织: childId={}, parentId={}", childId, parentId);
        if (childId.equals(parentId)) {
            return false;
        }
        
        List<Long> parentIds = getAllParentIds(childId);
        return parentIds.contains(parentId);
    }

    @Override
    public List<Long> getAllChildIds(Long parentId) {
        log.info("获取所有子组织ID: parentId={}", parentId);
        List<Long> childIds = new ArrayList<>();
        List<Department> children = departmentMapper.selectByParentId(parentId);
        
        for (Department child : children) {
            childIds.add(child.getId());
            childIds.addAll(getAllChildIds(child.getId()));
        }
        
        return childIds;
    }

    @Override
    public List<Long> getAllParentIds(Long childId) {
        log.info("获取所有父组织ID: childId={}", childId);
        List<Long> parentIds = new ArrayList<>();
        Department dept = departmentMapper.selectById(childId);
        
        while (dept != null && dept.getParentId() != null) {
            parentIds.add(dept.getParentId());
            dept = departmentMapper.selectById(dept.getParentId());
        }
        
        return parentIds;
    }

    @Override
    public OrganizationTreeVO getRoot(Long id) {
        log.info("获取根组织: id={}", id);
        Department dept = departmentMapper.selectById(id);
        if (dept == null) {
            return null;
        }
        
        while (dept.getParentId() != null) {
            Department parent = departmentMapper.selectById(dept.getParentId());
            if (parent == null) {
                break;
            }
            dept = parent;
        }
        
        return convertToVO(dept);
    }

    @Override
    public Long getRootId(Long id) {
        log.info("获取根组织ID: id={}", id);
        OrganizationTreeVO root = getRoot(id);
        return root != null ? root.getId() : null;
    }

    @Override
    public boolean hasCycle(Long id) {
        log.info("检查组织树是否有环: id={}", id);
        Set<Long> visited = new HashSet<>();
        Department dept = departmentMapper.selectById(id);
        
        while (dept != null && dept.getParentId() != null) {
            if (visited.contains(dept.getParentId())) {
                return true;
            }
            
            visited.add(dept.getId());
            dept = departmentMapper.selectById(dept.getParentId());
        }
        
        return false;
    }

    @Override
    public int getDepth(Long rootId) {
        log.info("获取组织树深度: rootId={}", rootId);
        Department root = departmentMapper.selectById(rootId);
        if (root == null) {
            return 0;
        }
        
        return calculateDepth(rootId);
    }

    @Override
    public List<OrganizationTreeVO> getSiblings(Long id) {
        log.info("获取兄弟组织: id={}", id);
        Department dept = departmentMapper.selectById(id);
        if (dept == null || dept.getParentId() == null) {
            return Collections.emptyList();
        }
        
        LambdaQueryWrapper<Department> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Department::getParentId, dept.getParentId())
                   .ne(Department::getId, id);
        
        List<Department> siblings = departmentMapper.selectList(queryWrapper);
        return siblings.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrganizationTreeVO> getLeaves(Long rootId) {
        log.info("获取叶子节点: rootId={}", rootId);
        List<OrganizationTreeVO> leaves = new ArrayList<>();
        collectLeaves(rootId, leaves);
        return leaves;
    }

    @Override
    public boolean isLeaf(Long id) {
        log.info("检查是否是叶子节点: id={}", id);
        List<Department> children = departmentMapper.selectByParentId(id);
        return children.isEmpty();
    }
    
    /**
     * 将部门实体转换为组织树VO
     */
    private OrganizationTreeVO convertToVO(Department dept) {
        if (dept == null) {
            return null;
        }
        
        OrganizationTreeVO vo = new OrganizationTreeVO();
        vo.setId(dept.getId());
        vo.setCode(dept.getCode());
        vo.setName(dept.getName());
        vo.setType(dept.getType() != null ? dept.getType().getValue() : null);
        vo.setTypeDescription(dept.getType() != null ? dept.getType().getDescription() : "");
        vo.setParentId(dept.getParentId());
        vo.setLevel(getLevel(dept.getId()));
        vo.setSortOrder(dept.getSortOrder());
        vo.setStatus(dept.getStatus());
        vo.setStatusDescription(dept.getStatus() == 1 ? "启用" : "禁用");
        vo.setRemark(dept.getRemark());
        vo.setHasChildren(!isLeaf(dept.getId()));
        vo.setIsLeaf(isLeaf(dept.getId()));
        vo.setPath(getPath(dept.getId()));
        
        // 获取父组织名称
        if (dept.getParentId() != null) {
            Department parent = departmentMapper.selectById(dept.getParentId());
            if (parent != null) {
                vo.setParentName(parent.getName());
            }
        }
        
        return vo;
    }
    
    /**
     * 构建组织树
     */
    private void buildTree(OrganizationTreeVO root) {
        List<Department> children = departmentMapper.selectByParentId(root.getId());
        
        for (Department child : children) {
            OrganizationTreeVO childVO = convertToVO(child);
            root.getChildren().add(childVO);
            buildTree(childVO);
        }
    }
    
    /**
     * 计算组织树深度
     */
    private int calculateDepth(Long rootId) {
        if (isLeaf(rootId)) {
            return 1;
        }
        
        List<Department> children = departmentMapper.selectByParentId(rootId);
        int maxChildDepth = 0;
        
        for (Department child : children) {
            int childDepth = calculateDepth(child.getId());
            maxChildDepth = Math.max(maxChildDepth, childDepth);
        }
        
        return maxChildDepth + 1;
    }
    
    /**
     * 收集叶子节点
     */
    private void collectLeaves(Long id, List<OrganizationTreeVO> leaves) {
        if (isLeaf(id)) {
            Department dept = departmentMapper.selectById(id);
            if (dept != null) {
                leaves.add(convertToVO(dept));
            }
            return;
        }
        
        List<Department> children = departmentMapper.selectByParentId(id);
        for (Department child : children) {
            collectLeaves(child.getId(), leaves);
        }
    }
    
    /**
     * 获取组织类型描述
     */
    private String getTypeDescription(Integer typeValue) {
        if (typeValue == null) {
            return "";
        }
        
        DepartmentTypeEnum type = DepartmentTypeEnum.valueOf(typeValue);
        if (type != null) {
            return type.getDescription();
        }
        
        switch (typeValue) {
            case 1:
                return "律所";
            case 2:
                return "部门";
            case 3:
                return "团队";
            case 4:
                return "职能中心";
            case 5:
                return "分所";
            default:
                return "未知";
        }
    }
} 