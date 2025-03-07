package com.lawfirm.auth.service.support;

import com.lawfirm.common.security.crypto.SensitiveDataService;
import com.lawfirm.model.auth.entity.User;
import com.lawfirm.model.auth.enums.GenderEnum;
import com.lawfirm.model.auth.enums.UserStatusEnum;
import com.lawfirm.model.auth.enums.UserTypeEnum;
import com.lawfirm.model.auth.vo.UserVO;
import lombok.RequiredArgsConstructor;
import com.lawfirm.common.util.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * 用户支持类
 * 提供用户相关的辅助方法
 */
@Component
@RequiredArgsConstructor
public class UserSupport {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final SensitiveDataService sensitiveDataService;
    
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
        BeanUtils.copyProperties(user, vo, User.class, UserVO.class);
        
        // 设置状态名称 - 使用枚举代替硬编码条件判断
        vo.setStatusName(getStatusName(user.getStatus()));
        
        // 设置性别名称 - 使用枚举代替硬编码switch
        vo.setGenderName(getGenderName(user.getGender()));
        
        // 设置用户类型名称 - 使用枚举代替硬编码switch
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
        
        // 脱敏敏感信息
        if (StringUtils.isNotBlank(vo.getMobile())) {
            vo.setMobile(sensitiveDataService.maskPhoneNumber(vo.getMobile()));
        }
        if (StringUtils.isNotBlank(vo.getEmail())) {
            vo.setEmail(sensitiveDataService.maskEmail(vo.getEmail()));
        }
        if (StringUtils.isNotBlank(vo.getIdCard())) {
            vo.setIdCard(sensitiveDataService.maskIdCard(vo.getIdCard()));
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
            return UserStatusEnum.NORMAL.getDescription();
        }
        
        return Arrays.stream(UserStatusEnum.values())
                .filter(e -> e.getValue().equals(status))
                .findFirst()
                .map(UserStatusEnum::getDescription)
                .orElse(UserStatusEnum.NORMAL.getDescription());
    }
    
    /**
     * 获取性别名称
     *
     * @param gender 性别编码
     * @return 性别名称
     */
    private String getGenderName(Integer gender) {
        if (gender == null) {
            return GenderEnum.UNKNOWN.getDescription();
        }
        
        return Arrays.stream(GenderEnum.values())
                .filter(e -> e.getValue().equals(gender))
                .findFirst()
                .map(GenderEnum::getDescription)
                .orElse(GenderEnum.UNKNOWN.getDescription());
    }
    
    /**
     * 获取用户类型名称
     *
     * @param userType 用户类型枚举
     * @return 用户类型名称
     */
    private String getUserTypeName(UserTypeEnum userType) {
        if (userType == null) {
            return "未知";
        }
        
        return userType.getDescription();
    }
}
