package com.lawfirm.common.security.core;

import java.io.Serializable;

/**
 * 安全上下文，存储用户的安全信息
 */
public class SecurityContext implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private UserDetails userDetails;
    private String token;

    public SecurityContext() {
    }

    public SecurityContext(UserDetails userDetails, String token) {
        this.userDetails = userDetails;
        this.token = token;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取当前用户ID
     * @return 当前用户ID，未认证时可能返回null
     */
    public Long getCurrentUserId() {
        if (userDetails == null) {
            return null;
        }
        return userDetails.getUserId();
    }
} 