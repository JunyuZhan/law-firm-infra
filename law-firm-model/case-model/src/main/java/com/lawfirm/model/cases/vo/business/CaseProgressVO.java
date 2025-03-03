package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.base.CaseProgressEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 案件进展视图对象
 * 
 * 包含案件进展的基本信息，如进展类型、进展描述、操作信息等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseProgressVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 案件编号
     */
    private String caseNumber;

    /**
     * 主要进展
     */
    private CaseProgressEnum mainProgress;

    /**
     * 子进展
     */
    private CaseProgressEnum subProgress;

    /**
     * 进展标题
     */
    private String progressTitle;

    /**
     * 进展描述
     */
    private String progressDescription;

    /**
     * 进展时间
     */
    private transient LocalDateTime progressTime;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 操作人角色
     */
    private String operatorRole;

    /**
     * 操作人部门
     */
    private String operatorDepartment;

    /**
     * 相关人员IDs（逗号分隔）
     */
    private String relatedPersonIds;

    /**
     * 相关人员姓名（逗号分隔）
     */
    private String relatedPersonNames;

    /**
     * 相关文档IDs（逗号分隔）
     */
    private String relatedDocumentIds;

    /**
     * 相关文档名称（逗号分隔）
     */
    private String relatedDocumentNames;

    /**
     * 相关事件IDs（逗号分隔）
     */
    private String relatedEventIds;

    /**
     * 相关事件名称（逗号分隔）
     */
    private String relatedEventNames;

    /**
     * 相关任务IDs（逗号分隔）
     */
    private String relatedTaskIds;

    /**
     * 相关任务名称（逗号分隔）
     */
    private String relatedTaskNames;

    /**
     * 是否重要进展
     */
    private Boolean isImportant;

    /**
     * 是否需要关注
     */
    private Boolean needAttention;

    /**
     * 是否已确认
     */
    private Boolean isConfirmed;

    /**
     * 确认人ID
     */
    private Long confirmerId;

    /**
     * 确认人姓名
     */
    private String confirmerName;

    /**
     * 确认时间
     */
    private transient LocalDateTime confirmTime;

    /**
     * 确认备注
     */
    private String confirmRemarks;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 获取相关人员ID数组
     */
    public Long[] getRelatedPersonIdArray() {
        if (relatedPersonIds == null || relatedPersonIds.isEmpty()) {
            return new Long[0];
        }
        
        String[] idStrings = relatedPersonIds.split(",");
        Long[] ids = new Long[idStrings.length];
        
        for (int i = 0; i < idStrings.length; i++) {
            try {
                ids[i] = Long.parseLong(idStrings[i].trim());
            } catch (NumberFormatException e) {
                ids[i] = null;
            }
        }
        
        return ids;
    }

    /**
     * 获取相关人员姓名数组
     */
    public String[] getRelatedPersonNameArray() {
        if (relatedPersonNames == null || relatedPersonNames.isEmpty()) {
            return new String[0];
        }
        
        return relatedPersonNames.split(",");
    }

    /**
     * 添加相关人员
     */
    public CaseProgressVO addRelatedPerson(Long id, String name) {
        if (id == null) {
            return this;
        }
        
        // 处理相关人员ID
        if (relatedPersonIds == null || relatedPersonIds.isEmpty()) {
            relatedPersonIds = id.toString();
        } else if (!relatedPersonIds.contains(id.toString())) {
            relatedPersonIds += "," + id;
        }
        
        // 处理相关人员姓名
        if (name != null && !name.isEmpty()) {
            if (relatedPersonNames == null || relatedPersonNames.isEmpty()) {
                relatedPersonNames = name;
            } else if (!relatedPersonNames.contains(name)) {
                relatedPersonNames += "," + name;
            }
        }
        
        return this;
    }

    /**
     * 移除相关人员
     */
    public CaseProgressVO removeRelatedPerson(Long id) {
        if (id == null || relatedPersonIds == null || relatedPersonIds.isEmpty()) {
            return this;
        }
        
        Long[] ids = getRelatedPersonIdArray();
        String[] names = getRelatedPersonNameArray();
        
        StringBuilder newIds = new StringBuilder();
        StringBuilder newNames = new StringBuilder();
        
        for (int i = 0; i < ids.length; i++) {
            if (!id.equals(ids[i])) {
                if (newIds.length() > 0) {
                    newIds.append(",");
                }
                newIds.append(ids[i]);
                
                if (i < names.length) {
                    if (newNames.length() > 0) {
                        newNames.append(",");
                    }
                    newNames.append(names[i]);
                }
            }
        }
        
        relatedPersonIds = newIds.toString();
        relatedPersonNames = newNames.toString();
        
        return this;
    }

    /**
     * 获取相关文档ID数组
     */
    public Long[] getRelatedDocumentIdArray() {
        if (relatedDocumentIds == null || relatedDocumentIds.isEmpty()) {
            return new Long[0];
        }
        
        String[] idStrings = relatedDocumentIds.split(",");
        Long[] ids = new Long[idStrings.length];
        
        for (int i = 0; i < idStrings.length; i++) {
            try {
                ids[i] = Long.parseLong(idStrings[i].trim());
            } catch (NumberFormatException e) {
                ids[i] = null;
            }
        }
        
        return ids;
    }

    /**
     * 获取相关文档名称数组
     */
    public String[] getRelatedDocumentNameArray() {
        if (relatedDocumentNames == null || relatedDocumentNames.isEmpty()) {
            return new String[0];
        }
        
        return relatedDocumentNames.split(",");
    }

    /**
     * 添加相关文档
     */
    public CaseProgressVO addRelatedDocument(Long id, String name) {
        if (id == null) {
            return this;
        }
        
        // 处理相关文档ID
        if (relatedDocumentIds == null || relatedDocumentIds.isEmpty()) {
            relatedDocumentIds = id.toString();
        } else if (!relatedDocumentIds.contains(id.toString())) {
            relatedDocumentIds += "," + id;
        }
        
        // 处理相关文档名称
        if (name != null && !name.isEmpty()) {
            if (relatedDocumentNames == null || relatedDocumentNames.isEmpty()) {
                relatedDocumentNames = name;
            } else if (!relatedDocumentNames.contains(name)) {
                relatedDocumentNames += "," + name;
            }
        }
        
        return this;
    }

    /**
     * 移除相关文档
     */
    public CaseProgressVO removeRelatedDocument(Long id) {
        if (id == null || relatedDocumentIds == null || relatedDocumentIds.isEmpty()) {
            return this;
        }
        
        Long[] ids = getRelatedDocumentIdArray();
        String[] names = getRelatedDocumentNameArray();
        
        StringBuilder newIds = new StringBuilder();
        StringBuilder newNames = new StringBuilder();
        
        for (int i = 0; i < ids.length; i++) {
            if (!id.equals(ids[i])) {
                if (newIds.length() > 0) {
                    newIds.append(",");
                }
                newIds.append(ids[i]);
                
                if (i < names.length) {
                    if (newNames.length() > 0) {
                        newNames.append(",");
                    }
                    newNames.append(names[i]);
                }
            }
        }
        
        relatedDocumentIds = newIds.toString();
        relatedDocumentNames = newNames.toString();
        
        return this;
    }

    /**
     * 判断是否为重要进展
     */
    public boolean isImportant() {
        return Boolean.TRUE.equals(this.isImportant);
    }

    /**
     * 判断是否需要关注
     */
    public boolean needAttention() {
        return Boolean.TRUE.equals(this.needAttention);
    }

    /**
     * 判断是否已确认
     */
    public boolean isConfirmed() {
        return Boolean.TRUE.equals(this.isConfirmed);
    }

    /**
     * 判断是否为初始阶段进展
     */
    public boolean isInitialStage() {
        return this.mainProgress != null && this.mainProgress.isInitialStage();
    }

    /**
     * 判断是否为证据准备阶段进展
     */
    public boolean isEvidenceStage() {
        return this.mainProgress != null && this.mainProgress.isEvidenceStage();
    }

    /**
     * 判断是否为文书准备阶段进展
     */
    public boolean isDocumentStage() {
        return this.mainProgress != null && this.mainProgress.isDocumentStage();
    }

    /**
     * 判断是否为立案阶段进展
     */
    public boolean isFilingStage() {
        return this.mainProgress != null && this.mainProgress.isFilingStage();
    }

    /**
     * 判断是否为庭审阶段进展
     */
    public boolean isTrialStage() {
        return this.mainProgress != null && this.mainProgress.isTrialStage();
    }

    /**
     * 判断是否为结案阶段进展
     */
    public boolean isClosingStage() {
        return this.mainProgress != null && this.mainProgress.isClosingStage();
    }
} 