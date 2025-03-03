package com.lawfirm.model.message.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 系统预警请求DTO
 */
@Data
public class SystemAlertRequest {

    /**
     * 预警级别
     */
    @NotBlank(message = "预警级别不能为空")
    private String level;

    /**
     * 预警标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 预警内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 预警类型
     */
    @NotNull(message = "预警类型不能为空")
    private Integer type;

    /**
     * 接收者列表
     */
    @NotEmpty(message = "接收者不能为空")
    private List<String> receivers;
} 