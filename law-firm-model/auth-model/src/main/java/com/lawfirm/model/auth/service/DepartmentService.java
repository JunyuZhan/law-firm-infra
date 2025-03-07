package com.lawfirm.model.auth.service;

import com.lawfirm.model.auth.dto.department.DepartmentCreateDTO;
import com.lawfirm.model.auth.dto.department.DepartmentQueryDTO;
import com.lawfirm.model.auth.dto.department.DepartmentUpdateDTO;
import com.lawfirm.model.auth.vo.DepartmentVO;

import java.util.List;

/**
 * 部门服务接口
 */
public interface DepartmentService {
    
    /**
     * 创建部门
     *
     * @param createDTO 创建参数
     * @return 部门ID
     */
    Long createDepartment(DepartmentCreateDTO createDTO);
    
    /**
     * 更新部门
     *
     * @param id        部门ID
     * @param updateDTO 更新参数
     */
    void updateDepartment(Long id, DepartmentUpdateDTO updateDTO);
    
    /**
     * 删除部门
     *
     * @param id 部门ID
     */
    void deleteDepartment(Long id);
    
    /**
     * 获取部门详情
     *
     * @param id 部门ID
     * @return 部门视图对象
     */
    DepartmentVO getDepartmentById(Long id);
    
    /**
     * 根据条件查询部门列表
     *
     * @param queryDTO 查询条件
     * @return 部门列表
     */
    List<DepartmentVO> listDepartments(DepartmentQueryDTO queryDTO);
    
    /**
     * 获取部门树结构
     *
     * @return 部门树列表
     */
    List<DepartmentVO> getDepartmentTree();
} 