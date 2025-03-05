package com.lawfirm.model.auth.mapper;

import com.lawfirm.model.auth.entity.UserDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户部门关联数据访问接口
 */
@Mapper
public interface UserDepartmentMapper {
    
    /**
     * 根据ID查询用户部门关联
     *
     * @param id 用户部门关联ID
     * @return 用户部门关联信息
     */
    UserDepartment selectById(Long id);
    
    /**
     * 根据用户ID查询部门ID列表
     *
     * @param userId 用户ID
     * @return 部门ID列表
     */
    List<Long> selectDepartmentIdsByUserId(Long userId);
    
    /**
     * 根据用户ID查询用户部门关联列表
     *
     * @param userId 用户ID
     * @return 用户部门关联列表
     */
    List<UserDepartment> selectByUserId(Long userId);
    
    /**
     * 根据部门ID查询用户ID列表
     *
     * @param departmentId 部门ID
     * @return 用户ID列表
     */
    List<Long> selectUserIdsByDepartmentId(Long departmentId);
    
    /**
     * 查询用户的主部门ID
     *
     * @param userId 用户ID
     * @return 主部门ID
     */
    Long selectPrimaryDepartmentId(Long userId);
    
    /**
     * 添加用户部门关联
     *
     * @param userDepartment 用户部门关联信息
     * @return 影响行数
     */
    int insert(UserDepartment userDepartment);
    
    /**
     * 批量添加用户部门关联
     *
     * @param userId       用户ID
     * @param departmentIds 部门ID列表
     * @param isPrimary    是否主部门（针对第一个部门）
     * @return 影响行数
     */
    int batchInsert(@Param("userId") Long userId, @Param("departmentIds") List<Long> departmentIds, @Param("isPrimary") Integer isPrimary);
    
    /**
     * 根据ID删除用户部门关联
     *
     * @param id 用户部门关联ID
     * @return 影响行数
     */
    int deleteById(Long id);
    
    /**
     * 根据用户ID删除用户部门关联
     *
     * @param userId 用户ID
     * @return 影响行数
     */
    int deleteByUserId(Long userId);
    
    /**
     * 根据部门ID删除用户部门关联
     *
     * @param departmentId 部门ID
     * @return 影响行数
     */
    int deleteByDepartmentId(Long departmentId);
    
    /**
     * 根据用户ID和部门ID删除用户部门关联
     *
     * @param userId      用户ID
     * @param departmentId 部门ID
     * @return 影响行数
     */
    int deleteByUserIdAndDepartmentId(@Param("userId") Long userId, @Param("departmentId") Long departmentId);
    
    /**
     * 设置用户的主部门
     *
     * @param userId      用户ID
     * @param departmentId 部门ID
     * @return 影响行数
     */
    int setPrimaryDepartment(@Param("userId") Long userId, @Param("departmentId") Long departmentId);
} 