package com.lawfirm.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.auth.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    /**
     * 根据用户名查询用户
     */
    @Select("SELECT * FROM auth_user WHERE username = #{username} AND del_flag = 0 LIMIT 1")
    User selectByUsername(@Param("username") String username);
    
    /**
     * 判断用户名是否存在
     */
    @Select("SELECT COUNT(*) FROM auth_user WHERE username = #{username} AND del_flag = 0")
    boolean existsByUsername(@Param("username") String username);
    
    /**
     * 删除用户角色关联
     */
    @Delete("DELETE FROM auth_user_role WHERE user_id = #{userId}")
    void deleteUserRoles(@Param("userId") Long userId);
    
    /**
     * 批量插入用户角色关联
     */
    @Insert("<script>" +
            "INSERT INTO auth_user_role(user_id, role_id) VALUES " +
            "<foreach collection='roleIds' item='roleId' separator=','>" +
            "(#{userId}, #{roleId})" +
            "</foreach>" +
            "</script>")
    void insertUserRoles(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
    
    /**
     * 查询用户的角色ID列表
     */
    @Select("SELECT role_id FROM auth_user_role WHERE user_id = #{userId}")
    List<Long> selectUserRoleIds(@Param("userId") Long userId);
    
    /**
     * 根据部门ID查询用户列表
     */
    @Select("SELECT u.* FROM auth_user u " +
            "INNER JOIN auth_user_dept ud ON u.id = ud.user_id " +
            "WHERE ud.dept_id = #{deptId} AND u.del_flag = 0")
    List<User> selectByDeptId(@Param("deptId") Long deptId);
    
    /**
     * 根据角色ID查询用户列表
     */
    @Select("SELECT u.* FROM auth_user u " +
            "INNER JOIN auth_user_role ur ON u.id = ur.user_id " +
            "WHERE ur.role_id = #{roleId} AND u.del_flag = 0")
    List<User> selectByRoleId(@Param("roleId") Long roleId);
} 