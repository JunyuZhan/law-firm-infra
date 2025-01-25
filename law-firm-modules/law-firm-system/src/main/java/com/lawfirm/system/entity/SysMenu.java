package com.lawfirm.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统菜单实体类
 */
@Data
@TableName("sys_menu")
public class SysMenu {

    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单标题
     */
    private String title;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 菜单类型（1-目录 2-菜单 3-按钮）
     */
    private Integer type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序号
     */
    private Integer orderNum;

    /**
     * 是否外链（0-否 1-是）
     */
    private Boolean isFrame;

    /**
     * 是否缓存（0-否 1-是）
     */
    private Boolean isCache;

    /**
     * 是否可见（0-否 1-是）
     */
    private Boolean visible;

    /**
     * 状态（0-禁用 1-正常）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;
} 