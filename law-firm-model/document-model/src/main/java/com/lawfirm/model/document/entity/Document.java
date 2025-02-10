package com.lawfirm.model.document.entity;

import java.time.LocalDateTime;

import com.lawfirm.common.core.enums.StatusEnum;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.document.enums.DocumentSecurityLevelEnum;
import com.lawfirm.model.document.enums.DocumentTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 文档实体
 */
@Getter
@Setter
@Accessors(chain = true)
public class Document extends ModelBaseEntity<Document> {

    private String documentNumber;  // 文档编号
    private String documentName;    // 文档名称
    private DocumentTypeEnum documentType;  // 文档类型
    private DocumentSecurityLevelEnum securityLevel;  // 文档密级
    private Long lawFirmId;        // 律所ID
    private Long caseId;           // 案件ID
    private String filePath;       // 文件路径
    private Long fileSize;         // 文件大小
    private String fileType;       // 文件类型
    private String fileHash;       // 文件Hash
    private String keywords;       // 关键词
    private String summary;        // 文档摘要
    private Integer version;       // 版本号
    private LocalDateTime lastModifiedTime;  // 最后修改时间
    private String lastModifiedBy;  // 最后修改人

    @Override
    public Document setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }

    @Override
    public Document setId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public void setStatus(StatusEnum status) {
        super.setStatus(status);
    }

    @Override
    public Document setVersion(Integer version) {
        super.setVersion(version);
        return this;
    }
} 