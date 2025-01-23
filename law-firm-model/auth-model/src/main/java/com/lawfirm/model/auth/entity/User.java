package com.lawfirm.model.auth.entity;

import com.lawfirm.model.base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "auth_user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 30, message = "用户名长度必须在4-30个字符之间")
    @Column(unique = true, nullable = false, length = 30)
    private String username;

    @NotBlank(message = "密码不能为空")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "姓名不能为空")
    @Column(nullable = false, length = 50)
    private String realName;

    @Email(message = "邮箱格式不正确")
    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String mobile;

    @Column(length = 500)
    private String avatar;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserType userType = UserType.NORMAL;

    private LocalDateTime lastLoginTime;

    @Column(length = 50)
    private String lastLoginIp;

    public enum UserType {
        ADMIN,    // 管理员
        NORMAL    // 普通用户
    }
} 