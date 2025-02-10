package com.lawfirm.model.document.vo;

import com.lawfirm.model.document.vo.base.DocumentEditBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文档编辑视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DocumentEditVO extends DocumentEditBaseVO {
    
    private Boolean locked;             // 是否被锁定
    private Boolean lockedByMe;         // 是否被当前用户锁定
} 