package com.lawfirm.model.archive.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 案件档案实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("archive_case")
public class CaseArchive extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 档案ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 案件ID
     */
    private String caseId;

    /**
     * 案件编号
     */
    private String caseNo;

    /**
     * 案件标题
     */
    private String caseTitle;

    /**
     * 负责律师ID
     */
    private String lawyerId;

    /**
     * 负责律师姓名
     */
    private String lawyerName;

    /**
     * 客户ID
     */
    private String clientId;

    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 案件类型
     */
    private String caseType;

    /**
     * 案件状态
     */
    private String caseStatus;

    /**
     * 案件金额
     */
    private BigDecimal caseAmount;

    /**
     * 案件开始时间
     */
    private LocalDateTime caseStartTime;

    /**
     * 案件结束时间
     */
    private LocalDateTime caseEndTime;

    /**
     * 归档时间
     */
    private LocalDateTime archiveTime;

    /**
     * 归档人ID
     */
    private String archiveUserId;

    /**
     * 归档人姓名
     */
    private String archiveUserName;

    /**
     * 归档备注
     */
    private String archiveRemark;
}