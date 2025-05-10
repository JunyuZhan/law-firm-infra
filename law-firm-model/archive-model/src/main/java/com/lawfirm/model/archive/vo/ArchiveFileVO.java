package com.lawfirm.model.archive.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 档案文件VO
 */
@Data
@Schema(description = "档案文件VO")
public class ArchiveFileVO {

    /**
     * 文件ID
     */
    @Schema(description = "文件ID")
    private String id;
    
    /**
     * 档案标题
     */
    @Schema(description = "档案标题")
    private String title;
    
    /**
     * 档案类型编码
     */
    @Schema(description = "档案类型编码")
    private Integer archiveTypeCode;
    
    /**
     * 档案类型名称
     */
    @Schema(description = "档案类型名称")
    private String archiveTypeName;
    
    /**
     * 档案状态编码
     */
    @Schema(description = "档案状态编码")
    private Integer archiveStatusCode;
    
    /**
     * 档案状态名称
     */
    @Schema(description = "档案状态名称")
    private String archiveStatusName;
    
    /**
     * 档案编号
     */
    @Schema(description = "档案编号")
    private String archiveNo;
    
    /**
     * 业务ID
     */
    @Schema(description = "业务ID")
    private String businessId;
    
    /**
     * 业务类型
     */
    @Schema(description = "业务类型")
    private String businessType;
    
    /**
     * 文件路径
     */
    @Schema(description = "文件路径")
    private String filePath;
    
    /**
     * 文件大小（KB）
     */
    @Schema(description = "文件大小（KB）")
    private Long fileSize;
    
    /**
     * 文件类型
     */
    @Schema(description = "文件类型")
    private String fileType;
    
    /**
     * 存储位置编码
     */
    @Schema(description = "存储位置编码")
    private String storageLocation;
    
    /**
     * 归档时间
     */
    @Schema(description = "归档时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime archiveTime;
    
    /**
     * 归档人姓名
     */
    @Schema(description = "归档人姓名")
    private String archiveUserName;
    
    /**
     * 借阅状态（0-未借出，1-已借出）
     */
    @Schema(description = "借阅状态")
    private Integer borrowStatus;
    
    /**
     * 借阅状态描述
     */
    @Schema(description = "借阅状态描述")
    private String borrowStatusDesc;
    
    /**
     * 当前借阅人姓名
     */
    @Schema(description = "当前借阅人姓名")
    private String currentBorrowerName;
    
    /**
     * 借出时间
     */
    @Schema(description = "借出时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime borrowTime;
    
    /**
     * 预计归还时间
     */
    @Schema(description = "预计归还时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expectedReturnTime;
    
    /**
     * 文件预览URL
     */
    @Schema(description = "文件预览URL")
    private String previewUrl;
    
    /**
     * 文件下载URL
     */
    @Schema(description = "文件下载URL")
    private String downloadUrl;
    
    /**
     * 文件顺序号
     */
    @Schema(description = "文件顺序号")
    private Integer sort;
} 