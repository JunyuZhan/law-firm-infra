package com.lawfirm.model.task.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工作任务标签展示对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkTaskTagVO extends BaseVO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 标签名称
     */
    private String name;
    
    /**
     * 标签描述
     */
    private String description;
    
    /**
     * 标签颜色
     */
    private String color;
    
    /**
     * 使用次数
     */
    private Integer usageCount;
    
    /**
     * 创建人姓名
     */
    private String creatorName;
} 