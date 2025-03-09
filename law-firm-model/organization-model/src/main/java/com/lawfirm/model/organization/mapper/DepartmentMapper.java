package com.lawfirm.model.organization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.organization.entity.department.Department;
import java.util.List;

/**
 * 部门数据访问接口
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 根据部门编码查询
     *
     * @param code 部门编码
     * @return 部门信息
     */
    Department selectByCode(String code);

    /**
     * 查询子部门列表
     *
     * @param parentId 父部门ID
     * @return 子部门列表
     */
    List<Department> selectByParentId(Long parentId);

    /**
     * 根据部门类型查询
     *
     * @param type 部门类型
     * @return 部门列表
     */
    List<Department> selectByType(String type);

    /**
     * 根据律所ID查询部门列表
     *
     * @param firmId 律所ID
     * @return 部门列表
     */
    List<Department> selectByFirmId(Long firmId);
} 