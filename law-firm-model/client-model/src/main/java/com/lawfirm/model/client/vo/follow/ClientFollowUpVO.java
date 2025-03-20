package com.lawfirm.model.client.vo.follow;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 客户跟进记录VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientFollowUpVO extends BaseVO {

    /**
     * 客户ID
     */
    private Long clientId;

    /**
     * 跟进类型（电话、邮件、拜访等）
     */
    private String followUpType;

    /**
     * 跟进内容
     */
    private String content;
    
    /**
     * 跟进结果
     */
    private String result;
    
    /**
     * 关联业务ID（案件/合同）
     */
    private Long businessId;
    
    /**
     * 业务类型
     */
    private String businessType;
    
    /**
     * 下次跟进时间
     */
    private LocalDateTime nextFollowTime;
    
    /**
     * 是否需要提醒（0-否，1-是）
     */
    private Integer isRemind;
    
    /**
     * 提醒时间
     */
    private LocalDateTime remindTime;
    
    /**
     * 提醒方式（邮件、短信等）
     */
    private String remindType;
    
    /**
     * 负责人ID
     */
    private Long assigneeId;
} 