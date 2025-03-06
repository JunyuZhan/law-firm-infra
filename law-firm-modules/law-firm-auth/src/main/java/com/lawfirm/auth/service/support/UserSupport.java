package com.lawfirm.auth.service.support;

import com.lawfirm.model.auth.entity.User;
import com.lawfirm.model.auth.vo.UserVO;
import lombok.RequiredArgsConstructor;
import com.lawfirm.common.util.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

/**
 * 用户支持类
 * 提供用户相关的辅助方法
 */
@Component
@RequiredArgsConstructor
public class UserSupport {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 将用户实体转换为视图对象
     *
     * @param user 用户实体
     * @return 用户视图对象
     */
    public UserVO convertToVO(User user) {
        if (user == null) {
            return null;
        }
        
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        
        // 设置状态名称
        vo.setStatusName(user.getStatus() == 1 ? "启用" : "禁用");
        
        // 设置性别名称
        vo.setGenderName(getGenderName(user.getGender()));
        
        // 设置用户类型名称
        vo.setUserTypeName(getUserTypeName(user.getUserType()));
        
        // 格式化时间
        if (user.getCreatedTime() != null) {
            vo.setCreateTime(user.getCreatedTime().format(DATE_TIME_FORMATTER));
        }
        if (user.getUpdatedTime() != null) {
            vo.setUpdateTime(user.getUpdatedTime().format(DATE_TIME_FORMATTER));
        }
        if (user.getLastLoginTime() != null) {
            vo.setLastLoginTime(user.getLastLoginTime().format(DATE_TIME_FORMATTER));
        }
        
        return vo;
    }
    
    /**
     * 获取性别名称
     *
     * @param gender 性别编码
     * @return 性别名称
     */
    private String getGenderName(Integer gender) {
        if (gender == null) {
            return "未知";
        }
        
        switch (gender) {
            case 1:
                return "男";
            case 2:
                return "女";
            default:
                return "未知";
        }
    }
    
    /**
     * 获取用户类型名称
     *
     * @param userType 用户类型编码
     * @return 用户类型名称
     */
    private String getUserTypeName(Integer userType) {
        if (userType == null) {
            return "未知";
        }
        
        switch (userType) {
            case 1:
                return "系统用户";
            case 2:
                return "客户用户";
            default:
                return "未知";
        }
    }
}
