package com.lawfirm.model.auth.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    private String createTime;
    
    /**
     * 更新时间
     */
    private String updateTime;
    
    /**
     * 子用户组
     */
    private List<UserGroupVO> children;
    
    /**
     * 用户列表
     */
    private List<UserVO> users;
} 