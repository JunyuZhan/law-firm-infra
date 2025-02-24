package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.note.NoteTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件笔记VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CaseNoteVO extends BaseVO {
    private Long caseId;
    private NoteTypeEnum noteType;
    private String title;
    private String content;
    private List<String> tags;
    private List<String> attachments;
    private Boolean isPrivate;
    private List<String> sharedWith;
    private Boolean needApproval;
    private Boolean approved;
    private String approver;
    private LocalDateTime approvalTime;
    private String approvalComment;
    private Integer version;
} 