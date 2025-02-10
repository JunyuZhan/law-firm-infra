package com.lawfirm.model.document.dto.base;

import com.lawfirm.model.document.enums.DocumentLockScopeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文档编辑基础数据传输对象
 */
@Data
@Accessors(chain = true)
public class DocumentEditBaseDTO {
    
    @NotNull(message = "文档ID不能为空")
    private Long documentId;              // 文档ID
    
    private String sessionId;             // 会话ID
    private String clientInfo;            // 客户端信息
    private DocumentLockScopeEnum lockScope;  // 锁定范围
} 