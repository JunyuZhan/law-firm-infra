package com.lawfirm.auth.service;

/**
 * 登录限制服务接口
 */
public interface LoginLimitService {

    /**
     * 记录登录失败
     *
     * @param username 用户名
     * @param ip IP地址
     */
    void recordLoginFail(String username, String ip);

    /**
     * 记录登录成功
     *
     * @param username 用户名
     * @param ip IP地址
     */
    void recordLoginSuccess(String username, String ip);

    /**
     * 检查用户是否被锁定
     *
     * @param username 用户名
     * @return 是否被锁定
     */
    boolean isUserLocked(String username);

    /**
     * 检查IP是否被限制
     *
     * @param ip IP地址
     * @return 是否被限制
     */
    boolean isIpLimited(String ip);

    /**
     * 清除用户登录限制
     *
     * @param username 用户名
     */
    void clearUserLimit(String username);

    /**
     * 清除IP限制
     *
     * @param ip IP地址
     */
    void clearIpLimit(String ip);
} 