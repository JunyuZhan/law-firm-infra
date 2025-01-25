package com.lawfirm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统菜单Mapper接口
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据角色ID查询菜单列表
     */
    @Select("SELECT m.* FROM sys_menu m" +
            " INNER JOIN sys_role_menu rm ON m.id = rm.menu_id" +
            " WHERE rm.role_id = #{roleId} AND m.deleted = 0" +
            " ORDER BY m.order_num")
    List<SysMenu> selectByRoleId(Long roleId);

    /**
     * 根据用户ID查询菜单列表
     */
    @Select("SELECT DISTINCT m.* FROM sys_menu m" +
            " INNER JOIN sys_role_menu rm ON m.id = rm.menu_id" +
            " INNER JOIN sys_user_role ur ON rm.role_id = ur.role_id" +
            " WHERE ur.user_id = #{userId} AND m.deleted = 0" +
            " ORDER BY m.order_num")
    List<SysMenu> selectByUserId(Long userId);

    /**
     * 查询所有可见的菜单列表
     */
    @Select("SELECT * FROM sys_menu WHERE visible = 1 AND deleted = 0 ORDER BY order_num")
    List<SysMenu> selectVisible();

    /**
     * 查询子菜单列表
     */
    @Select("SELECT * FROM sys_menu WHERE parent_id = #{parentId} AND deleted = 0 ORDER BY order_num")
    List<SysMenu> selectChildren(Long parentId);
} 