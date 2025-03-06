package com.lawfirm.model.auth.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户组视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserGroupVO extends BaseVO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户组ID
     */
    private Long id;
    
    /**
     * 用户组名称
     */
    private String name;
    
    /**
     * 用户组编码
     */
    private String code;
    
    /**
     * 父级ID
     */
    private Long parentId;
    
    /**
     * 父级名称
     */
    private String parentName;
    
    /**
     * 显示顺序
     */
    private Integer sort;
    
    /**
     * 状态（0-正常，1-禁用）
     */
    private Integer status;
    
    /**
     * 状态名称
     */
    private String statusName;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Override
    public LocalDateTime getCreateTime() {
        return super.getCreateTime();
    }
    
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Override
    public LocalDateTime getUpdateTime() {
        return super.getUpdateTime();
    }
    
    /**
     * 子用户组
     */
    private transient List<UserGroupVO> children;
    
    /**
     * 用户列表
     */
    private transient List<UserVO> users;
} 