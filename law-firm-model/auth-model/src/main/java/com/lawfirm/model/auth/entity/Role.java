package com.lawfirm.model.auth.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "auth_role")
@EqualsAndHashCode(callSuper = true)
public class Role extends ModelBaseEntity {

    @NotBlank(message = "角色名称不能为空")
    @Size(max = 50, message = "角色名称长度不能超过50个字符")
    @Column(nullable = false, length = 50)
    private String name;

    @NotBlank(message = "角色编码不能为空")
    @Size(max = 30, message = "角色编码长度不能超过30个字符")
    @Column(nullable = false, length = 30, unique = true)
    private String code;

    @Column(length = 200)
    private String description;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoleType roleType = RoleType.CUSTOM;

    @Column(nullable = false)
    private Integer dataScope = 1; // 数据权限范围

    public enum RoleType {
        SYSTEM,   // 系统角色
        CUSTOM    // 自定义角色
    }
} 