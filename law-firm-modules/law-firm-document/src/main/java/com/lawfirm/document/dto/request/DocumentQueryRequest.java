package com.lawfirm.document.dto.request;

import com.lawfirm.common.core.base.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文档查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentQueryRequest extends PageParam {
    
    /**
     * 文档编号
     */
    private String docNo;
    
    /**
     * 文档名称
     */
    private String docName;
    
    /**
     * 文档类型
     */
    private Integer docType;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 关键词
     */
    private String keywords;
} 