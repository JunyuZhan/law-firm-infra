package com.lawfirm.model.document.dto.document;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文档编辑操作DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DocumentEditDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 编辑操作类型
     */
    private String operationType;

    /**
     * 编辑位置（行号）
     */
    private Integer lineNumber;

    /**
     * 编辑位置（列号）
     */
    private Integer columnNumber;

    /**
     * 编辑内容
     */
    private String content;

    /**
     * 编辑用户ID
     */
    private Long userId;

    /**
     * 编辑时间戳
     */
    private Long timestamp;

    /**
     * 编辑会话ID
     */
    private String sessionId;

    /**
     * 文档版本号
     */
    private String version;

    /**
     * 冲突解决策略
     */
    private String conflictStrategy;

    /**
     * 是否为自动保存
     */
    private Boolean isAutoSave;
} 