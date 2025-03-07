package com.lawfirm.auth.service.support;

import com.lawfirm.model.auth.entity.Permission;
import com.lawfirm.model.auth.enums.PermissionTypeEnum;
import com.lawfirm.model.auth.vo.PermissionVO;
import com.lawfirm.model.base.enums.StatusEnum;
import lombok.RequiredArgsConstructor;
import com.lawfirm.common.util.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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
        BeanUtils.copyProperties(permission, vo, Permission.class, PermissionVO.class);
        
        // 设置权限类型名称 - 使用枚举代替硬编码switch
        vo.setPermissionTypeName(getPermissionTypeName(permission.getPermissionType()));
        
        // 设置状态名称 - 使用枚举代替硬编码条件判断
        vo.setStatusName(getStatusName(permission.getStatus()));
        
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
     * 获取状态名称
     *
     * @param status 状态编码
     * @return 状态名称
     */
    private String getStatusName(Integer status) {
        if (status == null) {
            return StatusEnum.ENABLED.getDescription();
        }
        
        return Arrays.stream(StatusEnum.values())
                .filter(e -> e.getValue().equals(status))
                .findFirst()
                .map(StatusEnum::getDescription)
                .orElse(StatusEnum.ENABLED.getDescription());
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
        
        return Arrays.stream(PermissionTypeEnum.values())
                .filter(e -> e.getValue().equals(permissionType))
                .findFirst()
                .map(PermissionTypeEnum::getDescription)
                .orElse("未知");
    }
}
