package com.lawfirm.model.organization.service.department;

import com.lawfirm.model.organization.dto.department.DepartmentDTO;
import com.lawfirm.model.organization.vo.department.DepartmentVO;

import java.util.List;

/**
 * 部门服务接口
 */
public interface DepartmentService {
    
    /**
     * 创建部门
     *
     * @param dto 部门信息
     * @return 部门ID
     */
    Long createDepartment(DepartmentDTO dto);

    /**
     * 更新部门信息
     *
     * @param dto 部门信息
     */
    void updateDepartment(DepartmentDTO dto);

    /**
     * 获取部门详情
     *
     * @param id 部门ID
     * @return 部门详情
     */
    DepartmentVO getDepartment(Long id);

    /**
     * 获取部门树形结构
     *
     * @param firmId 律所ID
     * @param type 部门类型（可选）
     * @param status 状态（可选）
     * @return 部门树
     */
    List<DepartmentVO> getDepartmentTree(Long firmId, Integer type, Integer status);

    /**
     * 获取部门列表（扁平结构）
     *
     * @param firmId 律所ID
     * @param type 部门类型（可选）
     * @param status 状态（可选）
     * @return 部门列表
     */
    List<DepartmentVO> listDepartments(Long firmId, Integer type, Integer status);

    /**
     * 更新部门状态
     *
     * @param id 部门ID
     * @param status 状态
     */
    void updateStatus(Long id, Integer status);

    /**
     * 更新部门负责人
     *
     * @param id 部门ID
     * @param managerId 负责人ID
     * @param managerName 负责人姓名
     */
    void updateManager(Long id, Long managerId, String managerName);

    /**
     * 移动部门
     *
     * @param id 部门ID
     * @param targetParentId 目标父部门ID
     */
    void moveDepartment(Long id, Long targetParentId);

    /**
     * 获取子部门列表
     *
     * @param parentId 父部门ID
     * @param status 状态（可选）
     * @return 子部门列表
     */
    List<DepartmentVO> listChildren(Long parentId, Integer status);

    /**
     * 统计部门数量
     *
     * @param firmId 律所ID
     * @param type 部门类型（可选）
     * @param status 状态（可选）
     * @return 数量
     */
    Integer countDepartments(Long firmId, Integer type, Integer status);

    /**
     * 检查部门编码是否已存在
     *
     * @param code 部门编码
     * @param firmId 律所ID
     * @param excludeId 排除的部门ID（用于更新时检查）
     * @return 是否存在
     */
    Boolean checkCodeExists(String code, Long firmId, Long excludeId);

    /**
     * 检查部门名称是否已存在
     *
     * @param name 部门名称
     * @param firmId 律所ID
     * @param parentId 父部门ID
     * @param excludeId 排除的部门ID（用于更新时检查）
     * @return 是否存在
     */
    Boolean checkNameExists(String name, Long firmId, Long parentId, Long excludeId);

    /**
     * 获取部门的所有上级部门
     *
     * @param id 部门ID
     * @return 上级部门列表（从顶级到直接父级的顺序）
     */
    List<DepartmentVO> getAncestors(Long id);

    /**
     * 获取部门的所有下级部门
     *
     * @param id 部门ID
     * @return 下级部门列表
     */
    List<DepartmentVO> getDescendants(Long id);
} 