package com.lawfirm.model.personnel.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "per_contact")
@EqualsAndHashCode(callSuper = true)
public class Contact extends ModelBaseEntity<Contact> {

    @NotNull(message = "关联ID不能为空")
    @Column(nullable = false)
    private Long relatedId;  // 关联ID（员工ID或客户ID）

    @Size(max = 20, message = "关联类型长度不能超过20个字符")
    @Column(length = 20)
    private String relatedType;  // 关联类型（EMPLOYEE/CLIENT）

    @Size(max = 50, message = "联系人长度不能超过50个字符")
    @Column(length = 50)
    private String contactName;  // 联系人

    @Size(max = 20, message = "联系类型长度不能超过20个字符")
    @Column(length = 20)
    private String contactType;  // 联系类型（手机/固话/邮箱等）

    @Size(max = 100, message = "联系方式长度不能超过100个字符")
    @Column(length = 100)
    private String contactValue;  // 联系方式值

    @Size(max = 20, message = "是否主要联系方式长度不能超过20个字符")
    @Column(length = 20)
    private String isPrimary;  // 是否主要联系方式

    private Integer sortOrder = 0;  // 排序号

    private Boolean enabled = true;  // 是否启用

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Column(length = 500)
    private String remark;  // 备注
} 