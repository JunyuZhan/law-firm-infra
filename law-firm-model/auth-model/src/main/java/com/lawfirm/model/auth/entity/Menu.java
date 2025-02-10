package com.lawfirm.model.auth.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.common.core.enums.StatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "auth_menu")
@EqualsAndHashCode(callSuper = true)
public class Menu extends ModelBaseEntity<Menu> {

    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 100)
    private String path;

    @Column(length = 100)
    private String component;

    @Column(length = 50)
    private String icon;

    @Column(nullable = false)
    private Integer sort = 0;

    @Column(nullable = false)
    private Boolean visible = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MenuType type = MenuType.MENU;

    @Column(length = 200)
    private String permission;

    @Override
    public Menu setSort(Integer sort) {
        super.setSort(sort);
        return this;
    }

    @Override
    public Menu setId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public Menu setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }

    @Override
    public void setStatus(StatusEnum status) {
        super.setStatus(status);
    }

    public enum MenuType {
        DIRECTORY,  // 目录
        MENU,      // 菜单
        BUTTON     // 按钮
    }
} 