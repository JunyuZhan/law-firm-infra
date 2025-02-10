package com.lawfirm.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 登录配置
 */
@Configuration
@ConfigurationProperties(prefix = "auth.login")
public class LoginConfig {

    /**
     * 登录失败最大次数
     */
    private int maxFailCount = 5;

    /**
     * 登录失败锁定时间（分钟）
     */
    private int lockTime = 30;

    /**
     * 验证码有效期（分钟）
     */
    private int captchaExpire = 5;

    /**
     * 记住我有效期（天）
     */
    private int rememberMeDays = 7;

    /**
     * 单点登录开关
     */
    private boolean singleLogin = true;

    /**
     * IP限制开关
     */
    private boolean ipLimit = true;

    /**
     * 同一IP最大登录次数
     */
    private int ipMaxCount = 100;

    /**
     * IP限制时间（分钟）
     */
    private int ipLimitTime = 60;

    // Getters and Setters
    public int getMaxFailCount() {
        return maxFailCount;
    }

    public void setMaxFailCount(int maxFailCount) {
        this.maxFailCount = maxFailCount;
    }

    public int getLockTime() {
        return lockTime;
    }

    public void setLockTime(int lockTime) {
        this.lockTime = lockTime;
    }

    public int getCaptchaExpire() {
        return captchaExpire;
    }

    public void setCaptchaExpire(int captchaExpire) {
        this.captchaExpire = captchaExpire;
    }

    public int getRememberMeDays() {
        return rememberMeDays;
    }

    public void setRememberMeDays(int rememberMeDays) {
        this.rememberMeDays = rememberMeDays;
    }

    public boolean isSingleLogin() {
        return singleLogin;
    }

    public void setSingleLogin(boolean singleLogin) {
        this.singleLogin = singleLogin;
    }

    public boolean isIpLimit() {
        return ipLimit;
    }

    public void setIpLimit(boolean ipLimit) {
        this.ipLimit = ipLimit;
    }

    public int getIpMaxCount() {
        return ipMaxCount;
    }

    public void setIpMaxCount(int ipMaxCount) {
        this.ipMaxCount = ipMaxCount;
    }

    public int getIpLimitTime() {
        return ipLimitTime;
    }

    public void setIpLimitTime(int ipLimitTime) {
        this.ipLimitTime = ipLimitTime;
    }
} 