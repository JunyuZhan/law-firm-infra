package com.lawfirm.model.organization.entity;

import com.lawfirm.model.base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "law_firm")
@EqualsAndHashCode(callSuper = true)
public class LawFirm extends BaseEntity {

    @NotBlank(message = "律所名称不能为空")
    @Size(max = 100, message = "律所名称长度不能超过100个字符")
    @Column(nullable = false, length = 100)
    private String name;  // 律所名称

    @Size(max = 50, message = "统一社会信用代码长度不能超过50个字符")
    @Column(length = 50)
    private String socialCreditCode;  // 统一社会信用代码

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

    @Size(max = 100, message = "网站长度不能超过100个字符")
    @Column(length = 100)
    private String website;  // 网站

    @Size(max = 50, message = "法定代表人长度不能超过50个字符")
    @Column(length = 50)
    private String legalRepresentative;  // 法定代表人

    @Size(max = 500, message = "简介长度不能超过500个字符")
    @Column(length = 500)
    private String description;  // 简介

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Column(length = 500)
    private String remark;  // 备注
} 