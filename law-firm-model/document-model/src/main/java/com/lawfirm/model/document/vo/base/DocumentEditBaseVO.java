package com.lawfirm.model.document.vo.base;

import com.lawfirm.model.document.enums.DocumentLockScopeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文档编辑基础视图对象
 */
@Data
@Accessors(chain = true)
public class DocumentEditBaseVO {
    
    private Long documentId;              // 文档ID
    private Long userId;                  // 锁定用户ID
    private String userName;              // 锁定用户名称
    private String sessionId;             // 会话ID
    private String clientInfo;            // 客户端信息
    private DocumentLockScopeEnum lockScope;  // 锁定范围
    private LocalDateTime lockTime;       // 锁定时间
    private LocalDateTime expireTime;     // 过期时间
    private Boolean locked;               // 是否被锁定
    private Boolean lockedByMe;           // 是否被当前用户锁定
} 