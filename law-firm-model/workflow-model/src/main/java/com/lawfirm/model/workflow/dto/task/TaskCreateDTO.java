package com.lawfirm.model.workflow.dto.task;

import com.lawfirm.model.base.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * @author claude
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "任务创建参数")
public class TaskCreateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("任务名称")
    @NotBlank(message = "任务名称不能为空")
    private String taskName;

    @ApiModelProperty("任务类型")
    @NotNull(message = "任务类型不能为空")
    private Integer taskType;

    @ApiModelProperty("流程实例ID")
    private String processInstanceId;

    @ApiModelProperty("任务描述")
    private String description;

    @ApiModelProperty("处理人ID")
    private Long handlerId;

    @ApiModelProperty("处理人名称")
    private String handlerName;

    @ApiModelProperty("优先级")
    private Integer priority;

    @ApiModelProperty("截止时间")
    private Date dueDate;

    /**
     * 自定义序列化逻辑
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // 保存时间戳
        long dueTimeEpoch = dueDate != null ? dueDate.toInstant().toEpochMilli() : 0;
        
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
            this.dueDate = new Date(dueTimeEpoch);
        }
    }
} 