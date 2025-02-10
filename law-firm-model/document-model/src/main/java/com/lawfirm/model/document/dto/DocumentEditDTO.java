package com.lawfirm.model.document.dto;

import com.lawfirm.model.document.dto.base.DocumentEditBaseDTO;
import com.lawfirm.model.document.enums.DocumentLockScopeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文档编辑数据传输对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DocumentEditDTO extends DocumentEditBaseDTO {
    
    @NotNull(message = "文档ID不能为空")
    private Long documentId;              // 文档ID
    
    private DocumentLockScopeEnum lockScope;  // 锁定范围
    private Integer lockDuration;         // 锁定时长（分钟）
    private String clientInfo;            // 客户端信息
    private String sessionId;             // 会话ID
} 