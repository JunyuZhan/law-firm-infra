package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.note.NoteTypeEnum;
import com.lawfirm.model.cases.enums.note.NoteVisibilityEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 案件笔记视图对象
 * 
 * 包含笔记的基本信息，如笔记标题、内容、类型、可见性等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseNoteVO extends BaseVO {

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
     * 笔记标题
     */
    private String noteTitle;

    /**
     * 笔记内容
     */
    private String noteContent;

    /**
     * 笔记类型
     */
    private NoteTypeEnum noteType;

    /**
     * 可见性级别
     */
    private NoteVisibilityEnum visibilityLevel;

    /**
     * 创建人ID
     */
    private Long creatorId;

    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 最后修改人ID
     */
    private Long lastModifierId;

    /**
     * 最后修改人姓名
     */
    private String lastModifierName;

    /**
     * 最后修改时间
     */
    private LocalDateTime lastModifiedTime;

    /**
     * 是否重要
     */
    private Boolean isImportant;

    /**
     * 是否置顶
     */
    private Boolean isPinned;

    /**
     * 是否已归档
     */
    private Boolean isArchived;

    /**
     * 归档时间
     */
    private LocalDateTime archiveTime;

    /**
     * 可见人IDs（逗号分隔）
     */
    private String visibleToIds;

    /**
     * 可见人姓名（逗号分隔）
     */
    private String visibleToNames;

    /**
     * 关联文档IDs（逗号分隔）
     */
    private String documentIds;

    /**
     * 关联事件IDs（逗号分隔）
     */
    private String eventIds;

    /**
     * 关联任务IDs（逗号分隔）
     */
    private String taskIds;

    /**
     * 标签（逗号分隔）
     */
    private String tags;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 获取笔记类型枚举
     */
    public Integer getNoteType() {
        return this.noteType != null ? this.noteType.getValue() : null;
    }

    /**
     * 设置笔记类型
     */
    public CaseNoteVO setNoteType(Integer noteType) {
        this.noteType = NoteTypeEnum.valueOf(noteType);
        return this;
    }

    /**
     * 获取可见性级别
     */
    public Integer getVisibilityLevel() {
        return this.visibilityLevel != null ? this.visibilityLevel.getValue() : null;
    }

    /**
     * 设置可见性级别
     */
    public CaseNoteVO setVisibilityLevel(Integer visibilityLevel) {
        this.visibilityLevel = NoteVisibilityEnum.valueOf(visibilityLevel);
        return this;
    }

    /**
     * 判断笔记是否重要
     */
    public boolean isImportant() {
        return Boolean.TRUE.equals(this.isImportant);
    }

    /**
     * 判断笔记是否置顶
     */
    public boolean isPinned() {
        return Boolean.TRUE.equals(this.isPinned);
    }

    /**
     * 判断笔记是否已归档
     */
    public boolean isArchived() {
        return Boolean.TRUE.equals(this.isArchived);
    }

    /**
     * 判断笔记是否为私人笔记
     */
    public boolean isPrivate() {
        return this.visibilityLevel != null && 
               this.visibilityLevel == NoteVisibilityEnum.PRIVATE;
    }

    /**
     * 判断笔记是否为团队可见
     */
    public boolean isTeamVisible() {
        return this.visibilityLevel != null && 
               this.visibilityLevel == NoteVisibilityEnum.TEAM;
    }

    /**
     * 判断笔记是否为公开笔记
     */
    public boolean isPublic() {
        return this.visibilityLevel != null && 
               this.visibilityLevel == NoteVisibilityEnum.PUBLIC;
    }

    /**
     * 判断笔记是否为指定人员可见
     */
    public boolean isSpecificVisible() {
        return this.visibilityLevel != null && 
               this.visibilityLevel == NoteVisibilityEnum.SPECIFIC_USERS;
    }

    /**
     * 获取可见人ID数组
     */
    public Long[] getVisibleToIdArray() {
        if (visibleToIds == null || visibleToIds.isEmpty()) {
            return new Long[0];
        }
        
        String[] idStrings = visibleToIds.split(",");
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
     * 获取可见人姓名数组
     */
    public String[] getVisibleToNameArray() {
        if (visibleToNames == null || visibleToNames.isEmpty()) {
            return new String[0];
        }
        
        return visibleToNames.split(",");
    }

    /**
     * 添加可见人
     */
    public CaseNoteVO addVisibleTo(Long id, String name) {
        if (id == null) {
            return this;
        }
        
        // 处理可见人ID
        if (visibleToIds == null || visibleToIds.isEmpty()) {
            visibleToIds = id.toString();
        } else if (!visibleToIds.contains(id.toString())) {
            visibleToIds += "," + id;
        }
        
        // 处理可见人姓名
        if (name != null && !name.isEmpty()) {
            if (visibleToNames == null || visibleToNames.isEmpty()) {
                visibleToNames = name;
            } else if (!visibleToNames.contains(name)) {
                visibleToNames += "," + name;
            }
        }
        
        return this;
    }

    /**
     * 移除可见人
     */
    public CaseNoteVO removeVisibleTo(Long id) {
        if (id == null || visibleToIds == null || visibleToIds.isEmpty()) {
            return this;
        }
        
        Long[] ids = getVisibleToIdArray();
        String[] names = getVisibleToNameArray();
        
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
        
        visibleToIds = newIds.toString();
        visibleToNames = newNames.toString();
        
        return this;
    }

    /**
     * 获取标签数组
     */
    public String[] getTagArray() {
        if (tags == null || tags.isEmpty()) {
            return new String[0];
        }
        
        return tags.split(",");
    }

    /**
     * 添加标签
     */
    public CaseNoteVO addTag(String tag) {
        if (tag == null || tag.isEmpty()) {
            return this;
        }
        
        if (tags == null || tags.isEmpty()) {
            tags = tag;
        } else if (!tags.contains(tag)) {
            tags += "," + tag;
        }
        
        return this;
    }

    /**
     * 移除标签
     */
    public CaseNoteVO removeTag(String tag) {
        if (tag == null || tag.isEmpty() || tags == null || tags.isEmpty()) {
            return this;
        }
        
        String[] tagArray = getTagArray();
        StringBuilder newTags = new StringBuilder();
        
        for (String t : tagArray) {
            if (!tag.equals(t)) {
                if (newTags.length() > 0) {
                    newTags.append(",");
                }
                newTags.append(t);
            }
        }
        
        tags = newTags.toString();
        
        return this;
    }

    /**
     * 获取笔记内容摘要（前100个字符）
     */
    public String getContentSummary() {
        if (noteContent == null || noteContent.isEmpty()) {
            return "";
        }
        
        if (noteContent.length() <= 100) {
            return noteContent;
        }
        
        return noteContent.substring(0, 100) + "...";
    }

    /**
     * 获取笔记字数
     */
    public int getWordCount() {
        if (noteContent == null || noteContent.isEmpty()) {
            return 0;
        }
        
        return noteContent.length();
    }
} 