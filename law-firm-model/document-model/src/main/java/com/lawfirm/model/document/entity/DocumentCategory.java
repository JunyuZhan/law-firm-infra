package com.lawfirm.model.document.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 文档分类实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "doc_document_category")
public class DocumentCategory extends ModelBaseEntity<DocumentCategory> {

    @NotNull(message = "分类名称不能为空")
    @Size(max = 100, message = "分类名称长度不能超过100个字符")
    @Column(nullable = false, length = 100)
    private String name;          // 分类名称

    @NotNull(message = "分类编码不能为空")
    @Size(max = 50, message = "分类编码长度不能超过50个字符")
    @Column(nullable = false, length = 50, unique = true)
    private String code;          // 分类编码

    @Column
    private Long parentId;        // 父分类ID

    @Column(length = 500)
    private String path;          // 分类路径

    @Column
    private Integer level;        // 层级

    @Column
    private Integer sort = 0;     // 排序号

    @Size(max = 500, message = "描述长度不能超过500个字符")
    @Column(length = 500)
    private String description;   // 描述

    @NotNull(message = "律所ID不能为空")
    @Column(nullable = false)
    private Long lawFirmId;      // 律所ID
} 