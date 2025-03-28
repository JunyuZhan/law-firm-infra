package com.lawfirm.model.workflow.dto.task;

import com.lawfirm.model.base.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 任务创建DTO
 * 
 * @author JunyuZhan
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "任务创建参数")
public class TaskCreateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空")
    @Schema(description = "任务名称")
    private String name;

    /**
     * 任务类型
     */
    @NotNull(message = "任务类型不能为空")
    @Schema(description = "任务类型")
    private Integer type;

    /**
     * 流程ID
     */
    @Schema(description = "流程ID")
    private String processId;

    /**
     * 流程编号
     */
    @Schema(description = "流程编号")
    private String processNo;

    /**
     * 任务描述
     */
    @Schema(description = "任务描述")
    private String description;

    /**
     * 处理人ID
     */
    @Schema(description = "处理人ID")
    private String assigneeId;

    /**
     * 处理人名称
     */
    @Schema(description = "处理人名称")
    private String assigneeName;

    /**
     * 优先级
     */
    @Schema(description = "优先级")
    private Integer priority;

    /**
     * 截止时间
     */
    @Schema(description = "截止时间")
    private LocalDateTime dueDate;

    /**
     * 自定义序列化逻辑
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // 保存时间戳
        long dueTimeEpoch = dueDate != null ? dueDate.toInstant(ZoneOffset.UTC).toEpochMilli() : 0;
        
        // 执行默认序列化
        out.defaultWriteObject();
        
        // 写入时间戳
        out.writeLong(dueTimeEpoch);
    }
    
    /**
     * 自定义反序列化逻辑
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // 执行默认反序列化
        in.defaultReadObject();
        
        // 读取时间戳并转换回LocalDateTime
        long dueTimeEpoch = in.readLong();
        
        if (dueTimeEpoch > 0) {
            this.dueDate = LocalDateTime.ofEpochSecond(dueTimeEpoch, 0, ZoneOffset.UTC);
        }
    }
} 
