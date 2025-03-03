package com.lawfirm.model.contract.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 合同附件实体类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("contract_attachment")
public class ContractAttachment extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    
    @TableField("contract_id")
    private Long contractId;       // 合同ID
    
    @TableField("file_name")
    private String fileName;        // 附件文件名
    
    @TableField("file_path")
    private String filePath;        // 附件路径
    
    @TableField("file_type")
    private String fileType;        // 附件类型
} 