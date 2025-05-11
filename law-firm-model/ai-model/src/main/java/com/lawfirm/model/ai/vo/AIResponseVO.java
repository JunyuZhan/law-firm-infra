package com.lawfirm.model.ai.vo;

import com.lawfirm.model.base.vo.BaseVO;
import java.io.Serializable;

/**
 * AI响应视图对象
 * 用于封装AI服务返回给前端的响应数据
 */
public class AIResponseVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String outputData; // 输出数据
    private String status; // 状态

    // 构造函数
    public AIResponseVO(String outputData, String status) {
        this.outputData = outputData;
        this.status = status;
    }
    
    public AIResponseVO() {
        // 默认无参构造函数
    }

    // Getter和Setter
    public String getOutputData() {
        return outputData;
    }

    public void setOutputData(String outputData) {
        this.outputData = outputData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
} 