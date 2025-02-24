package com.lawfirm.model.message.entity.system;

import com.lawfirm.model.message.entity.base.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统消息
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SystemMessage extends BaseMessage {

    /**
     * 消息级别 1-提示 2-警告 3-错误
     */
    private Integer level;

    /**
     * 消息来源
     */
    private String source;

    /**
     * 消息模块
     */
    private String module;

    /**
     * 是否广播 0-否 1-是
     */
    private Integer isBroadcast;

    /**
     * 是否持久化 0-否 1-是
     */
    private Integer isPersistent;

    /**
     * 消息分类
     */
    private String category;

    /**
     * 消息标签
     */
    private String tags;
} 