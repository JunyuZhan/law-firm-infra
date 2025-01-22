package com.lawfirm.common.cache.constant;

/**
 * 缓存常量
 */
public class CacheConstants {

    /**
     * 缓存键前缀
     */
    public static final String CACHE_KEY_PREFIX = "law_firm:";

    /**
     * 验证码缓存键前缀
     */
    public static final String CAPTCHA_CODE_KEY = CACHE_KEY_PREFIX + "captcha:";

    /**
     * 登录用户缓存键前缀
     */
    public static final String LOGIN_USER_KEY = CACHE_KEY_PREFIX + "login_user:";

    /**
     * 限流缓存键前缀
     */
    public static final String RATE_LIMIT_KEY = CACHE_KEY_PREFIX + "rate_limit:";

    /**
     * 防重提交缓存键前缀
     */
    public static final String REPEAT_SUBMIT_KEY = CACHE_KEY_PREFIX + "repeat_submit:";

    /**
     * 登录账户密码错误次数缓存键前缀
     */
    public static final String PWD_ERR_CNT_KEY = CACHE_KEY_PREFIX + "pwd_err_cnt:";

    /**
     * 登录IP黑名单缓存键前缀
     */
    public static final String LOGIN_IP_BLACK_LIST_KEY = CACHE_KEY_PREFIX + "login_ip_black_list:";

    /**
     * 字典缓存键前缀
     */
    public static final String DICT_KEY = CACHE_KEY_PREFIX + "dict:";

    /**
     * 参数缓存键前缀
     */
    public static final String CONFIG_KEY = CACHE_KEY_PREFIX + "config:";

    /**
     * 缓存有效期，默认720（分钟）
     */
    public static final long EXPIRATION = 720;

    /**
     * 缓存刷新时间，默认120（分钟）
     */
    public static final long REFRESH_TIME = 120;

    /**
     * 密码最大错误次数
     */
    public static final int PASSWORD_MAX_RETRY_COUNT = 5;

    /**
     * 密码锁定时间，默认10（分钟）
     */
    public static final long PASSWORD_LOCK_TIME = 10;
} 