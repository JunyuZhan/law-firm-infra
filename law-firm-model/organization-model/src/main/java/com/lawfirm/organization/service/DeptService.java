package com.lawfirm.organization.service;

import com.lawfirm.common.data.service.BaseService;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.organization.model.entity.Dept;
import com.lawfirm.organization.model.dto.DeptDTO;

import java.util.List;

/**
 * 部门服务接口
 */
public interface DeptService extends BaseService<Dept, DeptDTO> {

    /**
     * 创建部门
     */
    @OperationLog(description = "创建部门", operationType = "INSERT")
    void create(Dept dept);

    /**
     * 更新部门
     */
    @OperationLog(description = "更新部门", operationType = "UPDATE")
    void update(Dept dept);

    /**
     * 删除部门
     */
    @OperationLog(description = "删除部门", operationType = "DELETE")
    void delete(Long id);

    /**
     * 查询子部门列表
     */
    List<Dept> listChildren(Long parentId);

    /**
     * 查询部门路径
     */
    List<Dept> getPath(Long id);

    /**
     * 查询部门树
     */
    List<Dept> getTree();

    /**
     * 移动部门
     */
    @OperationLog(description = "移动部门", operationType = "UPDATE")
    void moveDept(Long id, Long parentId);

    /**
     * 调整部门顺序
     */
    @OperationLog(description = "调整部门顺序", operationType = "UPDATE")
    void reorderDept(Long id, Integer orderNum);

    /**
     * 获取部门树
     */
    List<DeptDTO> getDeptTree();

    /**
     * 获取角色部门列表
     */
    List<DeptDTO> getRoleDepts(Long roleId);

    /**
     * 获取用户部门列表
     */
    List<DeptDTO> getUserDepts(Long userId);
} 