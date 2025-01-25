package com.lawfirm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统用户Mapper接口
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据部门ID查询用户列表
     */
    @Select("SELECT * FROM sys_user WHERE dept_id = #{deptId} AND deleted = 0")
    List<SysUser> selectByDeptId(Long deptId);

    /**
     * 根据角色ID查询用户列表
     */
    @Select("SELECT u.* FROM sys_user u" +
            " INNER JOIN sys_user_role ur ON u.id = ur.user_id" +
            " WHERE ur.role_id = #{roleId} AND u.deleted = 0")
    List<SysUser> selectByRoleId(Long roleId);

    /**
     * 根据用户名查询用户
     */
    @Select("SELECT * FROM sys_user WHERE username = #{username} AND deleted = 0")
    SysUser selectByUsername(String username);
} 