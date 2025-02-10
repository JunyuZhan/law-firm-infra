package com.lawfirm.model.organization.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 分支机构实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "branch")
@EqualsAndHashCode(callSuper = true)
public class Branch extends ModelBaseEntity<Branch> {

    @NotNull(message = "律所ID不能为空")
    @Column(nullable = false)
    private Long lawFirmId;  // 所属律所ID

    @NotBlank(message = "分支机构名称不能为空")
    @Size(max = 100, message = "分支机构名称长度不能超过100个字符")
    @Column(nullable = false, length = 100)
    private String name;  // 分支机构名称

    @Size(max = 50, message = "分支机构编号长度不能超过50个字符")
    @Column(length = 50)
    private String branchCode;  // 分支机构编号

    @Size(max = 50, message = "执业许可证号长度不能超过50个字符")
    @Column(length = 50)
    private String licenseNumber;  // 执业许可证号

    @Size(max = 200, message = "地址长度不能超过200个字符")
    @Column(length = 200)
    private String address;  // 地址

    @Size(max = 50, message = "联系电话长度不能超过50个字符")
    @Column(length = 50)
    private String phone;  // 联系电话

    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    @Column(length = 100)
    private String email;  // 邮箱

    @Size(max = 50, message = "负责人长度不能超过50个字符")
    @Column(length = 50)
    private String manager;  // 负责人

    @Size(max = 500, message = "简介长度不能超过500个字符")
    @Column(length = 500)
    private String description;  // 简介

    private Boolean enabled = true;  // 是否启用

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Column(length = 500)
    private String remark;  // 备注
} 