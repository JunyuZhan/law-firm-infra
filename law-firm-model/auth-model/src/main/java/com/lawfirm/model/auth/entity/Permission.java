package com.lawfirm.model.auth.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "auth_permission")
@EqualsAndHashCode(callSuper = true)
public class Permission extends ModelBaseEntity {

    @NotBlank(message = "权限名称不能为空")
    @Size(max = 50, message = "权限名称长度不能超过50个字符")
    @Column(nullable = false, length = 50)
    private String name;

    @NotBlank(message = "权限编码不能为空")
    @Size(max = 100, message = "权限编码长度不能超过100个字符")
    @Column(nullable = false, length = 100, unique = true)
    private String code;

    @Column(length = 200)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PermissionType type = PermissionType.FUNCTION;

    @Column(nullable = false)
    private Boolean enabled = true;

    public enum PermissionType {
        MENU,      // 菜单权限
        FUNCTION,  // 功能权限
        API,       // API权限
        DATA       // 数据权限
    }
} 