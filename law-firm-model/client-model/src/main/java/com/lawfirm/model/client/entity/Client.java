package com.lawfirm.model.client.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.client.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "client_info")
@EqualsAndHashCode(callSuper = true)
public class Client extends ModelBaseEntity {

    @NotBlank(message = "客户编号不能为空")
    @Size(max = 50, message = "客户编号长度不能超过50个字符")
    @Column(nullable = false, length = 50, unique = true)
    private String clientNumber;

    @NotBlank(message = "客户名称不能为空")
    @Size(max = 100, message = "客户名称长度不能超过100个字符")
    @Column(nullable = false, length = 100)
    private String clientName;

    @NotNull(message = "客户类型不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ClientTypeEnum clientType;

    @NotNull(message = "客户状态不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ClientStatusEnum clientStatus = ClientStatusEnum.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ClientLevelEnum clientLevel = ClientLevelEnum.REGULAR;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ClientSourceEnum clientSource;

    // 联系人信息
    @Size(max = 50, message = "联系人姓名长度不能超过50个字符")
    @Column(length = 50)
    private String contactName;

    @Size(max = 20, message = "联系电话长度不能超过20个字符")
    @Column(length = 20)
    private String contactPhone;

    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    @Column(length = 100)
    private String contactEmail;

    // 证件信息
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private IdTypeEnum idType;

    @Size(max = 50, message = "证件号码长度不能超过50个字符")
    @Column(length = 50)
    private String idNumber;

    // 地址信息
    @Size(max = 50, message = "省份长度不能超过50个字符")
    @Column(length = 50)
    private String province;

    @Size(max = 50, message = "城市长度不能超过50个字符")
    @Column(length = 50)
    private String city;

    @Size(max = 200, message = "详细地址长度不能超过200个字符")
    @Column(length = 200)
    private String address;

    @Size(max = 20, message = "邮政编码长度不能超过20个字符")
    @Column(length = 20)
    private String postalCode;

    // 行业信息
    @Size(max = 100, message = "行业名称长度不能超过100个字符")
    @Column(length = 100)
    private String industry;

    @Size(max = 500, message = "描述信息长度不能超过500个字符")
    @Column(length = 500)
    private String description;
} 