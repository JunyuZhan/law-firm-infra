package com.lawfirm.auth.service.support;

import com.lawfirm.model.auth.entity.Role;
import com.lawfirm.model.auth.vo.RoleVO;
import com.lawfirm.model.base.enums.StatusEnum;
import lombok.RequiredArgsConstructor;
import com.lawfirm.common.util.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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
        BeanUtils.copyProperties(role, vo, Role.class, RoleVO.class);
        
        // 设置状态名称 - 使用枚举代替硬编码条件判断
        vo.setStatusName(getStatusName(role.getStatus()));
        
        // 格式化时间
        if (role.getCreatedTime() != null) {
            vo.setCreateTime(role.getCreatedTime().format(DATE_TIME_FORMATTER));
        }
        if (role.getUpdatedTime() != null) {
            vo.setUpdateTime(role.getUpdatedTime().format(DATE_TIME_FORMATTER));
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
}
