package com.lawfirm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单Mapper接口
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据角色ID查询菜单
     */
    List<SysMenu> selectByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据用户ID查询菜单
     */
    List<SysMenu> selectByUserId(@Param("userId") Long userId);
}