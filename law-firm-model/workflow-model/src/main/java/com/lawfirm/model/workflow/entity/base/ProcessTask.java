package com.lawfirm.model.workflow.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.BaseModel;
import com.lawfirm.model.workflow.enums.TaskTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 任务基类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("workflow_task")
public class ProcessTask extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 任务编号
     */
    @TableField("task_no")
    private String taskNo;

    /**
     * 任务名称
     */
    @TableField("task_name")
    private String taskName;

    /**
     * 任务类型
     * @see TaskTypeEnum
     */
    @TableField("task_type")
    private Integer taskType;

    /**
     * 流程ID
     */
    @TableField("process_id")
    private Long processId;

    /**
     * 流程实例编号
     */
    @TableField("process_no")
    private String processNo;

    /**
     * 任务描述
     */
    @TableField("description")
    private String description;

    /**
     * 处理人ID
     */
    @TableField("handler_id")
    private Long handlerId;

    /**
     * 处理人名称
     */
    @TableField("handler_name")
    private String handlerName;

    /**
     * 处理人类型 1-用户 2-角色 3-部门
     */
    @TableField("handler_type")
    private Integer handlerType;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private transient LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private transient LocalDateTime endTime;

    /**
     * 截止时间
     */
    @TableField("due_time")
    private transient LocalDateTime dueTime;

    /**
     * 任务状态 0-未开始 1-进行中 2-已完成 3-已取消
     */
    @TableField("status")
    private Integer status;

    /**
     * 任务结果
     */
    @TableField("result")
    private String result;

    /**
     * 任务评论
     */
    @TableField("comment")
    private String comment;

    /**
     * 优先级 1-低 2-中 3-高
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 前置任务IDs
     */
    @TableField("prev_task_ids")
    private String prevTaskIds;

    /**
     * 后续任务IDs
     */
    @TableField("next_task_ids")
    private String nextTaskIds;

    /**
     * 任务配置（JSON格式）
     */
    @TableField("task_config")
    private String taskConfig;

    /**
     * 自定义序列化逻辑
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // 保存时间戳
        long startTimeEpoch = startTime != null ? startTime.toEpochSecond(ZoneOffset.UTC) : 0;
        long endTimeEpoch = endTime != null ? endTime.toEpochSecond(ZoneOffset.UTC) : 0;
        long dueTimeEpoch = dueTime != null ? dueTime.toEpochSecond(ZoneOffset.UTC) : 0;
        
        // 执行默认序列化
        out.defaultWriteObject();
        
        // 写入时间戳
        out.writeLong(startTimeEpoch);
        out.writeLong(endTimeEpoch);
        out.writeLong(dueTimeEpoch);
    }
    
    /**
     * 自定义反序列化逻辑
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // 执行默认反序列化
        in.defaultReadObject();
        
        // 读取时间戳并转换回LocalDateTime
        long startTimeEpoch = in.readLong();
        long endTimeEpoch = in.readLong();
        long dueTimeEpoch = in.readLong();
        
        if (startTimeEpoch > 0) {
            this.startTime = LocalDateTime.ofEpochSecond(startTimeEpoch, 0, ZoneOffset.UTC);
        }
        
        if (endTimeEpoch > 0) {
            this.endTime = LocalDateTime.ofEpochSecond(endTimeEpoch, 0, ZoneOffset.UTC);
        }
        
        if (dueTimeEpoch > 0) {
            this.dueTime = LocalDateTime.ofEpochSecond(dueTimeEpoch, 0, ZoneOffset.UTC);
        }
    }
} 