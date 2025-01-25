package com.lawfirm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统部门Mapper接口
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 查询子部门列表
     */
    @Select("SELECT * FROM sys_dept WHERE parent_id = #{parentId} AND deleted = 0 ORDER BY order_num")
    List<SysDept> selectChildren(Long parentId);

    /**
     * 查询部门路径
     */
    @Select("WITH RECURSIVE dept_path AS (" +
            "  SELECT id, parent_id, name, 1 as level FROM sys_dept WHERE id = #{id} AND deleted = 0" +
            "  UNION ALL" +
            "  SELECT d.id, d.parent_id, d.name, dp.level + 1" +
            "  FROM sys_dept d" +
            "  INNER JOIN dept_path dp ON d.id = dp.parent_id" +
            "  WHERE d.deleted = 0" +
            ")" +
            "SELECT * FROM dept_path ORDER BY level DESC")
    List<SysDept> selectPath(Long id);

    /**
     * 查询部门树
     */
    @Select("WITH RECURSIVE dept_tree AS (" +
            "  SELECT id, parent_id, name, 0 as level" +
            "  FROM sys_dept" +
            "  WHERE parent_id IS NULL AND deleted = 0" +
            "  UNION ALL" +
            "  SELECT d.id, d.parent_id, d.name, dt.level + 1" +
            "  FROM sys_dept d" +
            "  INNER JOIN dept_tree dt ON d.parent_id = dt.id" +
            "  WHERE d.deleted = 0" +
            ")" +
            "SELECT * FROM dept_tree ORDER BY level, order_num")
    List<SysDept> selectTree();
} 