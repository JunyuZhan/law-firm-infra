package com.lawfirm.model.document.query;

import com.lawfirm.model.base.query.PageQuery;
import com.lawfirm.model.document.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentQuery extends PageQuery {

    private String documentNumber;  // 文档编号

    private String documentName;    // 文档名称

    private DocumentTypeEnum documentType;  // 文档类型

    private DocumentSecurityLevelEnum securityLevel;  // 文档密级

    private DocumentStatusEnum status;  // 文档状态

    private Long lawFirmId;  // 律所ID

    private Long caseId;     // 案件ID

    private String keywords;  // 关键词搜索

    private String fileType;  // 文件类型

    private LocalDateTime createTimeStart;  // 创建时间起始

    private LocalDateTime createTimeEnd;    // 创建时间结束

    private LocalDateTime lastModifiedTimeStart;  // 最后修改时间起始

    private LocalDateTime lastModifiedTimeEnd;    // 最后修改时间结束

    private String lastModifiedBy;  // 最后修改人
} 