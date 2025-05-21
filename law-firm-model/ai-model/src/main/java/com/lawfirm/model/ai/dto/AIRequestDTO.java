package com.lawfirm.model.ai.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import java.io.Serializable;
import java.util.Map;

/**
 * AI请求数据传输对象
 * 用于封装发送给AI服务的请求数据
 */
public class AIRequestDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String inputData; // 输入数据
    private String modelVersion; // 模型版本
    private String action; // 操作类型
    private String modelName; // 模型名称
    private Map<String, Object> params; // 请求参数

    // 构造函数
    public AIRequestDTO(String inputData, String modelVersion) {
        this.inputData = inputData;
        this.modelVersion = modelVersion;
    }
    
    public AIRequestDTO() {
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
    
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
} 