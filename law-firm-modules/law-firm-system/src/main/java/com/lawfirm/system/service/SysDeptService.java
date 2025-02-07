package com.lawfirm.system.service;

import com.lawfirm.common.data.service.BaseService;
import com.lawfirm.model.system.dto.SysDeptDTO;
import com.lawfirm.model.system.vo.SysDeptVO;
import com.lawfirm.model.system.entity.SysDept;
import java.util.List;

/**
 * 部门服务接口
 */
public interface SysDeptService extends BaseService<SysDept, SysDeptVO> {
    
    /**
     * 创建部门
     */
    SysDeptVO create(SysDeptDTO dto);
    
    /**
     * 更新部门
     */
    SysDeptVO update(SysDeptDTO dto);
    
    /**
     * 构建部门树
     */
    List<SysDeptVO> buildDeptTree(List<SysDeptVO> depts);
    
    /**
     * 根据角色ID查询部门列表
     */
    List<Long> selectDeptListByRoleId(Long roleId);
    
    /**
     * 校验部门名称是否唯一
     */
    boolean checkDeptNameUnique(SysDeptDTO dept);
    
    /**
     * 是否存在部门子节点
     */
    boolean hasChildByDeptId(Long deptId);
    
    /**
     * 查询部门是否存在用户
     */
    boolean checkDeptExistUser(Long deptId);

    /**
     * 根据父ID查询部门列表
     */
    List<SysDeptVO> listByParentId(Long parentId);
} 