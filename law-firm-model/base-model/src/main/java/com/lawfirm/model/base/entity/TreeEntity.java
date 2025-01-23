package com.lawfirm.model.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class TreeEntity extends BaseEntity {

    @NotBlank(message = "名称不能为空")
    @Size(max = 100, message = "名称长度不能超过100个字符")
    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String ancestors;  // 祖先节点

    private Long parentId;     // 父节点ID

    @Column(nullable = false)
    private Integer level = 1; // 层级

    @Column(nullable = false)
    private Boolean leaf = true; // 是否叶子节点
} 