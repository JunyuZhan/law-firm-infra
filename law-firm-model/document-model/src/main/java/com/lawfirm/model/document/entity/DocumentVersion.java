package com.lawfirm.model.document.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "doc_document_version")
public class DocumentVersion extends ModelBaseEntity<DocumentVersion> {

    @NotNull(message = "文档ID不能为空")
    @Column(nullable = false)
    private Long documentId;  // 关联的文档ID

    @NotNull(message = "版本号不能为空")
    @Column(nullable = false)
    private Integer version;  // 版本号

    @Size(max = 500, message = "文件路径长度不能超过500个字符")
    @Column(length = 500)
    private String filePath;  // 该版本的文件存储路径

    private Long fileSize;    // 文件大小（字节）

    @Size(max = 100, message = "文件Hash长度不能超过100个字符")
    @Column(length = 100)
    private String fileHash;  // 文件Hash值

    @Size(max = 500, message = "变更说明长度不能超过500个字符")
    @Column(length = 500)
    private String changeLog;  // 变更说明

    private LocalDateTime modifiedTime;  // 修改时间

    @Size(max = 50, message = "修改人长度不能超过50个字符")
    @Column(length = 50)
    private String modifiedBy;  // 修改人

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Column(length = 500)
    private String remark;  // 备注信息
} 