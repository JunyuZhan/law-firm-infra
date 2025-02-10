package com.lawfirm.model.document.vo;

import com.lawfirm.model.document.enums.DocumentEditStatusEnum;
import com.lawfirm.model.document.vo.base.DocumentEditBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文档编辑会话视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DocumentEditSessionVO extends DocumentEditBaseVO {
    
    private String documentName;      // 文档名称
    private DocumentEditStatusEnum editStatus;  // 编辑状态
    private String lockData;          // 锁定数据
} 