package com.lawfirm.model.message.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.BaseModel;
import com.lawfirm.model.message.enums.MessageStatusEnum;
import com.lawfirm.model.message.enums.MessageTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 消息基类
 */
@Data
@TableName("base_message")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BaseMessage extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 消息编号
     */
    @TableField("message_no")
    private String messageNo;

    /**
     * 消息标题
     */
    @TableField("title")
    private String title;

    /**
     * 消息内容
     */
    @TableField("content")
    private String content;

    /**
     * 消息类型
     * @see MessageTypeEnum
     */
    @TableField("message_type")
    private MessageTypeEnum messageType;

    /**
     * 消息状态
     * @see MessageStatusEnum
     */
    @TableField("status")
    private MessageStatusEnum status;

    /**
     * 发送者ID
     */
    @TableField("sender_id")
    private Long senderId;

    /**
     * 发送者名称
     */
    @TableField("sender_name")
    private String senderName;

    /**
     * 发送者类型 1-系统 2-用户
     */
    @TableField("sender_type")
    private Integer senderType;

    /**
     * 接收者列表
     */
    @TableField(exist = false)
    private List<String> receivers;

    /**
     * 接收者ID
     */
    @TableField("receiver_id")
    private Long receiverId;

    /**
     * 接收者名称
     */
    @TableField("receiver_name")
    private String receiverName;

    /**
     * 接收者类型 1-用户 2-角色 3-部门 4-全部
     */
    @TableField("receiver_type")
    private Integer receiverType;

    /**
     * 发送时间
     */
    @TableField("send_time")
    private LocalDateTime sendTime;

    /**
     * 阅读时间
     */
    @TableField("read_time")
    private LocalDateTime readTime;

    /**
     * 处理时间
     */
    @TableField("process_time")
    private LocalDateTime processTime;

    /**
     * 优先级 1-低 2-中 3-高
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 是否需要确认 0-否 1-是
     */
    @TableField("need_confirm")
    private Integer needConfirm;

    /**
     * 确认时间
     */
    @TableField("confirm_time")
    private LocalDateTime confirmTime;

    /**
     * 消息配置（JSON格式）
     */
    @TableField("message_config")
    private String messageConfig;

    /**
     * 关联业务ID
     */
    @TableField("business_id")
    private Long businessId;

    /**
     * 关联业务类型
     */
    @TableField("business_type")
    private String businessType;

    public void setType(MessageTypeEnum type) {
        this.messageType = type;
    }

    public MessageTypeEnum getType() {
        return this.messageType;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public List<String> getReceivers() {
        return this.receivers;
    }
} 