package com.lawfirm.model.organization.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.organization.enums.PositionTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "org_position")
@EqualsAndHashCode(callSuper = true)
public class Position extends ModelBaseEntity {

    @NotNull(message = "律所ID不能为空")
    @Column(nullable = false)
    private Long lawFirmId;  // 所属律所ID

    @NotBlank(message = "职位名称不能为空")
    @Size(max = 100, message = "职位名称长度不能超过100个字符")
    @Column(nullable = false, length = 100)
    private String name;  // 职位名称

    @Size(max = 50, message = "职位编号长度不能超过50个字符")
    @Column(length = 50)
    private String positionCode;  // 职位编号

    @NotNull(message = "职位类型不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PositionTypeEnum positionType;  // 职位类型

    private Integer level;  // 职级

    @Size(max = 500, message = "职责说明长度不能超过500个字符")
    @Column(length = 500)
    private String duty;  // 职责说明

    @Size(max = 500, message = "任职要求长度不能超过500个字符")
    @Column(length = 500)
    private String requirement;  // 任职要求

    private Integer sortOrder = 0;  // 排序号

    private Boolean enabled = true;  // 是否启用

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Column(length = 500)
    private String remark;  // 备注
} 