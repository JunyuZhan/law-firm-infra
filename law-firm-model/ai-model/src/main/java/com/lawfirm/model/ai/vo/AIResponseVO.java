package com.lawfirm.model.ai.vo;

import com.lawfirm.model.base.vo.BaseVO;
import java.io.Serializable;
import java.util.Map;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AI响应视图对象
 * 用于封装AI服务返回给前端的响应数据
 */
public class AIResponseVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "输出数据")
    private String outputData; // 输出数据
    @Schema(description = "状态")
    private String status; // 状态
    @Schema(description = "是否成功")
    private boolean success; // 是否成功
    @Schema(description = "消息")
    private String message; // 消息
    @Schema(description = "数据")
    private transient Map<String, Object> data; // 数据
    
    // 新增字段以支持CoreAIServiceImpl
    @Schema(description = "响应内容")
    private String content; // 响应内容
    @Schema(description = "时间戳")
    private Long timestamp; // 时间戳

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
    
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
    
    // 新增字段的getter/setter方法
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
} 