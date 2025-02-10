package com.lawfirm.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lawfirm.common.core.enums.StatusEnum;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * 系统菜单实体类
 */
@Getter
@Setter
@TableName("sys_menu")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysMenu extends ModelBaseEntity<SysMenu> {

    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String name;

    /**
     * 父菜单ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 显示顺序
     */
    @TableField("order_num")
    private Integer orderNum;

    /**
     * 路由地址
     */
    @TableField("path")
    private String path;

    /**
     * 组件路径
     */
    @TableField("component")
    private String component;

    /**
     * 路由参数
     */
    @TableField("query")
    private String query;

    /**
     * 是否为外链（0是 1否）
     */
    @TableField("is_frame")
    private Integer isFrame;

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    @TableField("is_cache")
    private Boolean isCache;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @TableField("menu_type")
    private String menuType;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    @TableField("visible")
    private String visible;

    /**
     * 菜单状态（0正常 1停用）
     */
    @TableField("status")
    private StatusEnum status;

    /**
     * 权限标识
     */
    @TableField("perms")
    private String perms;

    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;

    @TableField(exist = false)
    private List<SysMenu> children;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @Override
    public SysMenu setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }

    @Override
    public SysMenu setId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public void setStatus(StatusEnum status) {
        super.setStatus(status);
    }
}