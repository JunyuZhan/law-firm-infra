package com.lawfirm.model.message.entity.system;

import com.lawfirm.model.message.entity.base.BaseNotify;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统通知实体类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SystemNotify extends BaseNotify {
    /**
     * 通知级别
     */
    private Integer level;

    /**
     * 通知图标
     */
    private String icon;

    /**
     * 通知链接
     */
    private String link;

    /**
     * 通知参数
     */
    private String params;
} 