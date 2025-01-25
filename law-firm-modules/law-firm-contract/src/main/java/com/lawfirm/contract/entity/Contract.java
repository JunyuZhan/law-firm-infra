package com.lawfirm.contract.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 合同实体类
 */
@Data
@TableName("contract")
public class Contract {
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 合同编号
     */
    private String contractNo;
    
    /**
     * 合同名称
     */
    private String name;
    
    /**
     * 合同类型
     */
    private Integer type;
    
    /**
     * 合同金额
     */
    private BigDecimal amount;
    
    /**
     * 签订日期
     */
    private LocalDateTime signDate;
    
    /**
     * 生效日期
     */
    private LocalDateTime effectiveDate;
    
    /**
     * 到期日期
     */
    private LocalDateTime expiryDate;
    
    /**
     * 客户ID
     */
    private Long clientId;
    
    /**
     * 客户名称
     */
    private String clientName;
    
    /**
     * 律师ID
     */
    private Long lawyerId;
    
    /**
     * 律师姓名
     */
    private String lawyerName;
    
    /**
     * 分支机构ID
     */
    private Long branchId;
    
    /**
     * 部门ID
     */
    private Long departmentId;
    
    /**
     * 合同状态
     */
    private Integer status;
    
    /**
     * 文件ID
     */
    private String fileId;
    
    /**
     * 合同描述
     */
    private String description;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 创建人
     */
    private String createBy;
    
    /**
     * 更新人
     */
    private String updateBy;
    
    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;
} 