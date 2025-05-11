package com.lawfirm.model.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 认证配置属性类
 */
@Data
@ConfigurationProperties(prefix = "law-firm.auth")
public class AuthProperties {
    
    /**
     * JWT配置
     */
    @NestedConfigurationProperty
    private Jwt jwt = new Jwt();
    
    /**
     * 安全配置
     */
    @NestedConfigurationProperty
    private Security security = new Security();
    
    /**
     * LDAP配置
     */
    @NestedConfigurationProperty
    private Ldap ldap = new Ldap();
    
    /**
     * 短信配置
     */
    @NestedConfigurationProperty
    private Sms sms = new Sms();
    
    /**
     * 邮件配置
     */
    @NestedConfigurationProperty
    private Email email = new Email();
    
    /**
     * 第三方登录配置
     */
    @NestedConfigurationProperty
    private ThirdParty thirdParty = new ThirdParty();
    
    /**
     * JWT配置类
     */
    @Data
    public static class Jwt {
        /**
         * JWT密钥
         */
        private String secret = "defaultSecretKey";
        
        /**
         * 访问令牌过期时间（毫秒）
         */
        private long accessTokenExpiration = 86400000; // 默认24小时
        
        /**
         * 刷新令牌过期时间（毫秒）
         */
        private long refreshTokenExpiration = 604800000; // 默认7天
        
        /**
         * 发行者
         */
        private String issuer = "law-firm";
        
        /**
         * 是否检查令牌是否已撤销
         */
        private boolean checkRevoked = true;
    }
    
    /**
     * 安全配置类
     */
    @Data
    public static class Security {
        /**
         * 最大登录失败次数
         */
        private int maxLoginFailTimes = 5;
        
        /**
         * 登录失败锁定时间（分钟）
         */
        private int loginLockMinutes = 30;
        
        /**
         * 是否开启验证码
         */
        private boolean captchaEnabled = true;
        
        /**
         * 验证码有效期（分钟）
         */
        private int captchaExpireMinutes = 5;
        
        /**
         * 登录失败多少次后开始要求验证码
         */
        private int captchaAfterFailTimes = 3;
        
        /**
         * 密码有效期（天），0表示永不过期
         */
        private int passwordExpiryDays = 90;
        
        /**
         * 密码历史记录数量
         */
        private int passwordHistorySize = 5;
        
        /**
         * 密码最小长度
         */
        private int passwordMinLength = 8;
        
        /**
         * 是否要求密码包含数字
         */
        private boolean passwordRequireDigit = true;
        
        /**
         * 是否要求密码包含小写字母
         */
        private boolean passwordRequireLowercase = true;
        
        /**
         * 是否要求密码包含大写字母
         */
        private boolean passwordRequireUppercase = true;
        
        /**
         * 是否要求密码包含特殊字符
         */
        private boolean passwordRequireSpecialChar = true;
    }
    
    /**
     * LDAP配置类
     */
    @Data
    public static class Ldap {
        /**
         * 是否启用LDAP
         */
        private boolean enabled = false;
        
        /**
         * LDAP URL
         */
        private String url;
        
        /**
         * 基础DN
         */
        private String baseDn;
        
        /**
         * 用户DN模式
         */
        private String userDnPattern;
        
        /**
         * 管理员DN
         */
        private String managerDn;
        
        /**
         * 管理员密码
         */
        private String managerPassword;
    }
    
    /**
     * 短信配置类
     */
    @Data
    public static class Sms {
        /**
         * 是否启用短信验证码
         */
        private boolean enabled = false;
        
        /**
         * 短信验证码有效期（分钟）
         */
        private int expireMinutes = 5;
        
        /**
         * 短信验证码长度
         */
        private int codeLength = 6;
        
        /**
         * 每日短信发送限制
         */
        private int dailyLimit = 10;
    }
    
    /**
     * 邮件配置类
     */
    @Data
    public static class Email {
        /**
         * 是否启用邮件验证
         */
        private boolean enabled = false;
        
        /**
         * 邮件验证码有效期（分钟）
         */
        private int expireMinutes = 30;
        
        /**
         * 验证码长度
         */
        private int codeLength = 6;
    }
    
    /**
     * 第三方登录配置类
     */
    @Data
    public static class ThirdParty {
        /**
         * 是否启用微信登录
         */
        private boolean wechatEnabled = false;
        
        /**
         * 是否启用钉钉登录
         */
        private boolean dingtalkEnabled = false;
    }
} 