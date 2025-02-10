package com.lawfirm.model.contract.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.contract.constants.ContractConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 合同附件实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = ContractConstants.TableNames.CONTRACT_ATTACHMENT)
public class ContractAttachment extends ModelBaseEntity<ContractAttachment> {

    @NotNull(message = "合同ID不能为空")
    private Long contractId;  // 合同ID

    @NotBlank(message = "附件名称不能为空")
    @Size(max = ContractConstants.FieldLength.ATTACHMENT_NAME, message = "附件名称长度不能超过{max}个字符")
    @Column(nullable = false, length = ContractConstants.FieldLength.ATTACHMENT_NAME)
    private String attachmentName;  // 附件名称

    private String attachmentType;  // 附件类型
    private String fileType;        // 文件类型
    private Long fileSize;          // 文件大小
    private String filePath;        // 文件路径
    private String md5;             // 文件MD5值

    private Long uploaderId;        // 上传人ID
    private String uploaderName;    // 上传人姓名
    private LocalDateTime uploadTime;  // 上传时间

    private Boolean isConfidential;  // 是否保密
    private String securityLevel;    // 安全级别
    private Boolean isRequired;      // 是否必要附件
    private Boolean isTemplate;      // 是否模板文件
    private Integer version;         // 版本号

    @Size(max = ContractConstants.FieldLength.REMARK, message = "备注长度不能超过{max}个字符")
    @Column(length = ContractConstants.FieldLength.REMARK)
    private String remark;  // 备注
}

