package com.lawfirm.auth.service;

import com.lawfirm.auth.model.LoginUser;

/**
 * 单点登录服务接口
 */
public interface SingleLoginService {

    /**
     * 记录用户的登录信息
     */
    void recordLoginInfo(String token, LoginUser user);

    /**
     * 清除用户登录信息
     */
    void clearLoginInfo(String token, String username);

    /**
     * 查询用户当前的登录数量
     */
    long getLoginCount(String username);

    /**
     * 获取用户当前有效的token
     *
     * @param username 用户名
     * @return token，如果不存在则返回null
     */
    String getCurrentToken(String username);

    /**
     * 判断token是否有效
     *
     * @param username 用户名
     * @param token token
     * @return 是否有效
     */
    boolean isValidToken(String username, String token);
} 