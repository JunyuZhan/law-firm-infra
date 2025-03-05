package com.lawfirm.model.auth.mapper;

import com.lawfirm.model.auth.entity.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门数据访问接口
 * 
 * @author lawfirm
 */
public interface DepartmentMapper {
    
    /**
     * 根据ID查询部门
     * 
     * @param id 部门ID
     * @return 部门实体
     */
    Department selectById(Long id);
    
    /**
     * 根据部门编码查询部门
     * 
     * @param code 部门编码
     * @return 部门实体
     */
    Department selectByCode(String code);
    
    /**
     * 查询所有部门
     * 
     * @return 部门列表
     */
    List<Department> selectAll();
    
    /**
     * 查询部门树
     * 
     * @return 部门列表（树形结构）
     */
    List<Department> selectTree();
    
    /**
     * 根据父ID查询部门
     * 
     * @param parentId 父部门ID
     * @return 部门列表
     */
    List<Department> selectByParentId(Long parentId);
    
    /**
     * 根据类型查询部门
     * 
     * @param type 部门类型
     * @return 部门列表
     */
    List<Department> selectByType(Integer type);
    
    /**
     * 分页查询部门列表
     * 
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param name 部门名称（可选）
     * @param type 部门类型（可选）
     * @return 部门列表
     */
    List<Department> selectPage(@Param("pageNum") Integer pageNum, 
                              @Param("pageSize") Integer pageSize,
                              @Param("name") String name,
                              @Param("type") Integer type);
    
    /**
     * 查询部门总数
     * 
     * @param name 部门名称（可选）
     * @param type 部门类型（可选）
     * @return 部门总数
     */
    int selectCount(@Param("name") String name, @Param("type") Integer type);
    
    /**
     * 新增部门
     * 
     * @param department 部门实体
     * @return 影响行数
     */
    int insert(Department department);
    
    /**
     * 更新部门
     * 
     * @param department 部门实体
     * @return 影响行数
     */
    int update(Department department);
    
    /**
     * 删除部门
     * 
     * @param id 部门ID
     * @return 影响行数
     */
    int deleteById(Long id);
    
    /**
     * 批量删除部门
     * 
     * @param ids 部门ID数组
     * @return 影响行数
     */
    int deleteBatchByIds(@Param("ids") List<Long> ids);
    
    /**
     * 查询用户所属的部门
     * 
     * @param userId 用户ID
     * @return 部门实体
     */
    Department selectByUserId(Long userId);
} 