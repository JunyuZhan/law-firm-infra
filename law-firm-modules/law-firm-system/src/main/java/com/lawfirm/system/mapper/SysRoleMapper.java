package com.lawfirm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统角色Mapper接口
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户ID查询角色列表
     */
    @Select("SELECT r.* FROM sys_role r" +
            " INNER JOIN sys_user_role ur ON r.id = ur.role_id" +
            " WHERE ur.user_id = #{userId} AND r.deleted = 0")
    List<SysRole> selectByUserId(Long userId);

    /**
     * 根据角色编码查询角色
     */
    @Select("SELECT * FROM sys_role WHERE code = #{code} AND deleted = 0")
    SysRole selectByCode(String code);

    /**
     * 查询默认角色
     */
    @Select("SELECT * FROM sys_role WHERE is_default = 1 AND deleted = 0")
    List<SysRole> selectDefaultRoles();
} 