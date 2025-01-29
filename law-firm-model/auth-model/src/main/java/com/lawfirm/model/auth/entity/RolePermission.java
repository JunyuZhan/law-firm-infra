package com.lawfirm.model.auth.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "auth_role_permission")
@EqualsAndHashCode(callSuper = true)
public class RolePermission extends ModelBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;
} 