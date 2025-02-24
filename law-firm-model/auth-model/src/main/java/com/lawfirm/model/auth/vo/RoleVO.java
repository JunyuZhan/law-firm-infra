package com.lawfirm.model.auth.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 角色视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RoleVO extends BaseVO {
    
    /**
     * 角色名称
     */
    private String name;
    
    /**
     * 角色编码
     */
    private String code;
    
    /**
     * 角色类型（0-系统角色，1-自定义角色）
     */
    private Integer type;
    
    /**
     * 数据范围（0-全部数据，1-本部门及以下数据，2-本部门数据，3-个人数据，4-自定义数据）
     */
    private Integer dataScope;
    
    /**
     * 显示顺序
     */
    private Integer sort;
    
    /**
     * 状态（0-正常，1-禁用）
     */
    private Integer status;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 权限ID列表
     */
    private List<Long> permissionIds;
    
    /**
     * 权限名称列表
     */
    private List<String> permissionNames;
    
    /**
     * 用户数量
     */
    private Long userCount;
} 