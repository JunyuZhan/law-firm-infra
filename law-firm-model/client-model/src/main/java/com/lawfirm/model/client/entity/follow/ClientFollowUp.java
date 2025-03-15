package com.lawfirm.model.client.entity.follow;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 客户跟进记录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("client_follow_up")
public class ClientFollowUp extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    @TableField("client_id")
    private Long clientId;

    /**
     * 跟进类型（电话、邮件、拜访等）
     */
    @TableField("follow_up_type")
    private String followUpType;

    /**
     * 跟进内容
     */
    @TableField("content")
    private String content;
    
    /**
     * 跟进结果
     */
    @TableField("result")
    private String result;
    
    /**
     * 关联业务ID（案件/合同）
     */
    @TableField("business_id")
    private Long businessId;
    
    /**
     * 业务类型
     */
    @TableField("business_type")
    private String businessType;
    
    /**
     * 下次跟进时间
     */
    @TableField("next_follow_time")
    private Date nextFollowTime;
    
    /**
     * 是否需要提醒（0-否，1-是）
     */
    @TableField("is_remind")
    private Integer isRemind;
    
    /**
     * 提醒时间
     */
    @TableField("remind_time")
    private Date remindTime;
    
    /**
     * 提醒方式（邮件、短信等）
     */
    @TableField("remind_type")
    private String remindType;
    
    /**
     * 负责人ID
     */
    @TableField("assignee_id")
    private Long assigneeId;

    /**
     * 获取跟进时间（兼容性方法，转换为LocalDateTime）
     * @return 跟进时间
     */
    public LocalDateTime getFollowUpTime() {
        if (nextFollowTime == null) {
            return null;
        }
        return nextFollowTime.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
    
    /**
     * 设置跟进时间（兼容性方法，将LocalDateTime转换为Date）
     * @param followUpTime 跟进时间
     */
    public void setFollowUpTime(LocalDateTime followUpTime) {
        if (followUpTime == null) {
            this.nextFollowTime = null;
            return;
        }
        this.nextFollowTime = Date.from(followUpTime.atZone(ZoneId.systemDefault()).toInstant());
    }
} 