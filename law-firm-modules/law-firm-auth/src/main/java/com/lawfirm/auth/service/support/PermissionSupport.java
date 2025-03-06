package com.lawfirm.auth.service.support;

import com.lawfirm.model.auth.entity.Permission;
import com.lawfirm.model.auth.vo.PermissionVO;
import lombok.RequiredArgsConstructor;
import com.lawfirm.common.util.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

/**
 * 权限支持类
 * 提供权限相关的辅助方法
 */
@Component
@RequiredArgsConstructor
public class PermissionSupport {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 将权限实体转换为视图对象
     *
     * @param permission 权限实体
     * @return 权限视图对象
     */
    public PermissionVO convertToVO(Permission permission) {
        if (permission == null) {
            return null;
        }
        
        PermissionVO vo = new PermissionVO();
        BeanUtils.copyProperties(permission, vo);
        
        // 设置权限类型名称
        vo.setPermissionTypeName(getPermissionTypeName(permission.getPermissionType()));
        
        // 设置状态名称
        vo.setStatusName(permission.getStatus() == 1 ? "启用" : "禁用");
        
        // 格式化时间
        if (permission.getCreatedTime() != null) {
            vo.setCreateTime(permission.getCreatedTime().format(DATE_TIME_FORMATTER));
        }
        if (permission.getUpdatedTime() != null) {
            vo.setUpdateTime(permission.getUpdatedTime().format(DATE_TIME_FORMATTER));
        }
        
        return vo;
    }
    
    /**
     * 获取权限类型名称
     *
     * @param permissionType 权限类型
     * @return 权限类型名称
     */
    private String getPermissionTypeName(Integer permissionType) {
        if (permissionType == null) {
            return "";
        }
        
        switch (permissionType) {
            case 1:
                return "菜单";
            case 2:
                return "按钮";
            case 3:
                return "接口";
            default:
                return "未知";
        }
    }
}
