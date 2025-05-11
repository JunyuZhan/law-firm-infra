package com.lawfirm.model.ai.vo;

import com.lawfirm.model.base.vo.BaseVO;
import java.io.Serializable;

/**
 * AI请求视图对象
 * 用于封装前端发送的AI请求数据
 */
public class AIRequestVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String inputData; // 输入数据
    private String modelVersion; // 模型版本

    // 构造函数
    public AIRequestVO(String inputData, String modelVersion) {
        this.inputData = inputData;
        this.modelVersion = modelVersion;
    }
    
    public AIRequestVO() {
        // 默认无参构造函数
    }

    // Getter和Setter
    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }
} 