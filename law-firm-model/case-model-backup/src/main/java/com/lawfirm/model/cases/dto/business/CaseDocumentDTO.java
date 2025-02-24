package com.lawfirm.model.cases.dto.business;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.enums.doc.DocumentSecurityLevelEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 案件文档DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseDocumentDTO extends BaseDTO {

    /**
     * 案件ID
     */
    @NotNull(message = "案件ID不能为空")
    private Long caseId;
    
    /**
     * 文档名称
     */
    @NotBlank(message = "文档名称不能为空")
    @Size(max = 200, message = "文档名称长度不能超过200个字符")
    private String documentName;
    
    /**
     * 文档路径
     */
    @NotBlank(message = "文档路径不能为空")
    private String documentPath;
    
    /**
     * 文档类型
     */
    @NotBlank(message = "文档类型不能为空")
    private String documentType;
    
    /**
     * 文档描述
     */
    @Size(max = 500, message = "文档描述长度不能超过500个字符")
    private String description;
    
    /**
     * 文件大小（字节）
     */
    @NotNull(message = "文件大小不能为空")
    private Long fileSize;
    
    /**
     * 文件类型
     */
    @NotBlank(message = "文件类型不能为空")
    private String fileType;
    
    /**
     * 安全级别
     */
    @NotNull(message = "安全级别不能为空")
    private DocumentSecurityLevelEnum securityLevel;
    
    /**
     * 是否加密
     */
    private Boolean encrypted = false;
    
    /**
     * 版本号
     */
    private String version;
    
    /**
     * 过期时间
     */
    private LocalDateTime expiryTime;
    
    /**
     * 是否需要审批
     */
    private Boolean needApproval = false;
    
    /**
     * 是否已审批
     */
    private Boolean approved = false;
    
    /**
     * 审批人
     */
    private String approver;
    
    /**
     * 审批时间
     */
    private LocalDateTime approvalTime;
    
    /**
     * 审批评论
     */
    private String approvalComment;
    
    /**
     * 是否允许下载
     */
    private Boolean allowDownload = true;
    
    /**
     * 是否允许打印
     */
    private Boolean allowPrint = true;
    
    /**
     * MD5
     */
    private String md5;
    
    /**
     * 内容
     */
    private byte[] content;
    
    /**
     * 创建人
     */
    private String createBy;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新人
     */
    private String updateBy;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 上传人ID
     */
    private Long uploaderId;

    /**
     * 是否为最新版本
     */
    private Boolean isLatest;

    /**
     * 备注
     */
    private String remark;
} 