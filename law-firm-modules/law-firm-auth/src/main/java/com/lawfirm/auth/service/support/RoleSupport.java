package com.lawfirm.auth.service.support;

import com.lawfirm.model.auth.entity.Role;
import com.lawfirm.model.auth.vo.RoleVO;
import lombok.RequiredArgsConstructor;
import com.lawfirm.common.util.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

/**
 * 角色支持类
 * 提供角色相关的辅助方法
 */
@Component
@RequiredArgsConstructor
public class RoleSupport {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 将角色实体转换为视图对象
     *
     * @param role 角色实体
     * @return 角色视图对象
     */
    public RoleVO convertToVO(Role role) {
        if (role == null) {
            return null;
        }
        
        RoleVO vo = new RoleVO();
        BeanUtils.copyProperties(role, vo);
        
        // 设置状态名称
        vo.setStatusName(role.getStatus() == 1 ? "启用" : "禁用");
        
        // 格式化时间
        if (role.getCreatedTime() != null) {
            vo.setCreateTime(role.getCreatedTime().format(DATE_TIME_FORMATTER));
        }
        if (role.getUpdatedTime() != null) {
            vo.setUpdateTime(role.getUpdatedTime().format(DATE_TIME_FORMATTER));
        }
        
        return vo;
    }
}
