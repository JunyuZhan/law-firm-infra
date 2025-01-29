package com.lawfirm.model.personnel.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.personnel.enums.ClientTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "per_client")
@EqualsAndHashCode(callSuper = true)
public class Client extends ModelBaseEntity {

    @NotNull(message = "律所ID不能为空")
    @Column(nullable = false)
    private Long lawFirmId;  // 所属律所ID

    @NotBlank(message = "客户名称不能为空")
    @Size(max = 100, message = "客户名称长度不能超过100个字符")
    @Column(nullable = false, length = 100)
    private String name;  // 客户名称

    @Size(max = 50, message = "客户编号长度不能超过50个字符")
    @Column(length = 50)
    private String clientCode;  // 客户编号

    @NotNull(message = "客户类型不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ClientTypeEnum clientType;  // 客户类型

    @Size(max = 18, message = "证件号码长度不能超过18个字符")
    @Column(length = 18)
    private String identificationNumber;  // 证件号码

    @Size(max = 20, message = "证件类型长度不能超过20个字符")
    @Column(length = 20)
    private String identificationType;  // 证件类型

    @Size(max = 200, message = "地址长度不能超过200个字符")
    @Column(length = 200)
    private String address;  // 地址

    @Size(max = 20, message = "联系电话长度不能超过20个字符")
    @Column(length = 20)
    private String phone;  // 联系电话

    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    @Column(length = 100)
    private String email;  // 邮箱

    @Size(max = 50, message = "联系人长度不能超过50个字符")
    @Column(length = 50)
    private String contactPerson;  // 联系人

    @Size(max = 20, message = "联系人电话长度不能超过20个字符")
    @Column(length = 20)
    private String contactPhone;  // 联系人电话

    @Size(max = 100, message = "所属行业长度不能超过100个字符")
    @Column(length = 100)
    private String industry;  // 所属行业

    private LocalDate firstContactDate;  // 首次接触日期

    @Size(max = 500, message = "客户来源长度不能超过500个字符")
    @Column(length = 500)
    private String source;  // 客户来源

    private Long responsibleLawyerId;  // 负责律师ID

    @Size(max = 50, message = "负责律师姓名长度不能超过50个字符")
    @Column(length = 50)
    private String responsibleLawyerName;  // 负责律师姓名

    private Boolean isVip = false;  // 是否重点客户

    @Size(max = 500, message = "合作历史长度不能超过500个字符")
    @Column(length = 500)
    private String cooperationHistory;  // 合作历史

    private Boolean enabled = true;  // 是否启用

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Column(length = 500)
    private String remark;  // 备注
} 