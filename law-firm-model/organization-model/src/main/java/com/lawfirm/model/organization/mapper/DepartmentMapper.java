package com.lawfirm.model.organization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.organization.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 部门数据访问接口
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 根据角色ID查询部门列表
     */
    List<Department> selectByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据用户ID查询部门列表
     */
    List<Department> selectByUserId(@Param("userId") Long userId);

    /**
     * 查询子部门列表
     */
    @Select("SELECT * FROM org_department WHERE parent_id = #{parentId} AND deleted = false ORDER BY sort_order")
    List<Department> selectChildren(@Param("parentId") Long parentId);

    /**
     * 查询部门路径（从根部门到当前部门的路径）
     */
    @Select("WITH RECURSIVE dept_path AS (" +
            "  SELECT id, parent_id, name, 1 as level FROM org_department WHERE id = #{id} AND deleted = false" +
            "  UNION ALL" +
            "  SELECT d.id, d.parent_id, d.name, dp.level + 1" +
            "  FROM org_department d" +
            "  INNER JOIN dept_path dp ON d.id = dp.parent_id" +
            "  WHERE d.deleted = false" +
            ")" +
            "SELECT * FROM dept_path ORDER BY level DESC")
    List<Department> selectPath(@Param("id") Long id);

    /**
     * 查询部门树
     */
    @Select("WITH RECURSIVE dept_tree AS (" +
            "  SELECT id, parent_id, name, 0 as level" +
            "  FROM org_department" +
            "  WHERE parent_id IS NULL AND deleted = false" +
            "  UNION ALL" +
            "  SELECT d.id, d.parent_id, d.name, dt.level + 1" +
            "  FROM org_department d" +
            "  INNER JOIN dept_tree dt ON d.parent_id = dt.id" +
            "  WHERE d.deleted = false" +
            ")" +
            "SELECT * FROM dept_tree ORDER BY level, sort_order")
    List<Department> selectTree();
} 