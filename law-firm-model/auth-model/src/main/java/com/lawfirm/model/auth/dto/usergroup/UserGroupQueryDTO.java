package com.lawfirm.model.auth.dto.usergroup;

import com.lawfirm.model.base.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户组查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserGroupQueryDTO extends PageDTO<UserGroupQueryDTO> {
    
    private static final long serialVersionUID = 1L;
    
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
     * 状态（0-正常，1-禁用）
     */
    private Integer status;
} 