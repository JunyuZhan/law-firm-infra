package com.lawfirm.model.organization.service;

import com.lawfirm.common.data.service.BaseService;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.model.organization.entity.Department;
import com.lawfirm.model.organization.vo.DepartmentVO;

import java.util.List;

/**
 * 部门服务接口
 */
public interface DepartmentService extends BaseService<Department, DepartmentVO> {

    /**
     * 创建部门
     */
    @OperationLog(description = "创建部门", operationType = "INSERT")
    DepartmentVO create(DepartmentVO vo);

    /**
     * 更新部门
     */
    @OperationLog(description = "更新部门", operationType = "UPDATE")
    DepartmentVO update(DepartmentVO vo);

    /**
     * 删除部门
     */
    @OperationLog(description = "删除部门", operationType = "DELETE")
    void delete(Long id);

    /**
     * 查询子部门列表
     */
    List<Department> listChildren(Long parentId);

    /**
     * 查询部门路径（从根部门到当前部门的路径）
     */
    List<Department> getPath(Long id);

    /**
     * 查询部门树
     */
    List<DepartmentVO> getTree();

    /**
     * 移动部门
     */
    @OperationLog(description = "移动部门", operationType = "UPDATE")
    void moveDepartment(Long id, Long newParentId);

    /**
     * 调整部门顺序
     */
    @OperationLog(description = "调整部门顺序", operationType = "UPDATE")
    void reorderDepartment(Long id, Integer newOrder);

    /**
     * 获取角色关联的部门列表
     */
    List<DepartmentVO> getRoleDepartments(Long roleId);

    /**
     * 获取用户关联的部门列表
     */
    List<DepartmentVO> getUserDepartments(Long userId);

    /**
     * 获取子部门
     */
    List<DepartmentVO> getChildren(Long parentId);

    /**
     * 更新部门状态
     */
    void updateStatus(Long id, Boolean enabled);

    /**
     * 更新部门排序
     */
    void updateSort(Long id, Integer sortOrder);

    /**
     * 更新上级部门
     */
    void updateParent(Long id, Long parentId);

    /**
     * 检查部门名称是否存在
     */
    boolean checkNameExists(String name, Long parentId);

    /**
     * 检查是否有子部门
     */
    boolean hasChildren(Long id);
} 