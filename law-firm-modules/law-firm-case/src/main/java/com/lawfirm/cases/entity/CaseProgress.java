package com.lawfirm.cases.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.cases.enums.CaseProgressStageEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 案件进展记录实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("case_progress")
public class CaseProgress extends BaseEntity {

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 进展阶段
     */
    private CaseProgressStageEnum stage;

    /**
     * 进展内容
     */
    private String content;

    /**
     * 处理人ID
     */
    private Long handlerId;

    /**
     * 处理人姓名
     */
    private String handlerName;

    /**
     * 处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 下一步计划
     */
    private String nextPlan;

    /**
     * 计划完成时间
     */
    private LocalDateTime planTime;

    /**
     * 附件ID
     */
    private String fileId;
} 