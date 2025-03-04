# 安全框架模块 (Common Security)

## 模块说明
安全框架模块提供了律师事务所管理系统的安全基础设施层，定义了认证、授权、审计、加密等安全相关的抽象接口。作为基础设施层的重要组成部分，本模块只包含基础的、通用的、抽象的安全接口定义，不包含任何具体实现。

## 目录结构
```
common-security/
├── annotation/        # 安全注解定义
│   ├── RequiresPermissions.java  # 权限要求注解
│   ├── RequiresRoles.java        # 角色要求注解
│   └── Logical.java              # 注解逻辑运算枚举
├── authentication/    # 认证相关接口
│   ├── Authentication.java       # 认证信息接口
│   └── AuthenticationProvider.java # 认证提供者接口
├── authorization/     # 授权相关接口
│   └── Authorization.java        # 授权接口
├── audit/            # 安全审计接口
│   ├── SecurityAudit.java       # 审计基础接口
│   └── AuditEventPublisher.java # 审计事件发布接口
├── token/            # 令牌相关接口
│   └── TokenService.java        # 令牌服务接口
├── context/          # 安全上下文
│   └── SecurityContext.java      # 安全上下文接口
├── crypto/           # 加密相关接口
│   ├── EncryptionService.java    # 加密服务接口
│   └── SensitiveDataService.java # 数据脱敏服务接口
├── core/             # 核心服务接口
│   └── SecurityService.java      # 安全核心服务接口
└── constants/        # 安全常量定义
    └── SecurityConstants.java     # 安全相关常量
```

## 核心功能

### 1. 认证体系
认证体系提供了用户身份验证的抽象接口：
```java
public interface Authentication {
    Object getPrincipal();      // 获取认证主体
    Object getCredentials();    // 获取认证凭证
    boolean isAuthenticated();  // 是否已认证
}

public interface AuthenticationProvider {
    Authentication authenticate(Authentication authentication);
    boolean supports(Class<?> authentication);
}
```

### 2. 授权体系
授权体系提供了权限控制的抽象接口和注解：
```java
public interface Authorization {
    boolean hasPermission(String permission);  // 权限检查
    boolean hasRole(String role);              // 角色检查
}

@interface RequiresPermissions {
    String[] value();                          // 权限标识
    Logical logical() default Logical.AND;      // 逻辑运算
}
```

### 3. 安全审计
提供了安全相关事件的审计接口：
```java
public interface SecurityAudit {
    void logAuthenticationEvent(Object principal, String eventType, boolean result);
    void logAuthorizationEvent(Object principal, String permission, boolean result);
    void logOperationEvent(Object principal, String operation, String resource);
}

public interface AuditEventPublisher {
    void publishAuthenticationEvent(Object principal, String eventType, boolean result);
    // ...
}
```

### 4. 令牌服务
提供了令牌的生成、验证和解析服务：
```java
public interface TokenService {
    String generateToken(Authentication authentication);
    boolean validateToken(String token);
    Authentication getAuthenticationFromToken(String token);
}
```

### 5. 安全上下文
提供了安全信息的统一访问接口：
```java
public interface SecurityContext {
    Authentication getAuthentication();  // 获取认证信息
    Authorization getAuthorization();    // 获取授权信息
}

public interface SecurityService {
    SecurityContext getSecurityContext();
    void setAuthentication(Authentication authentication);
    void setAuthorization(Authorization authorization);
    void clearContext();
}
```

### 6. 加密服务
提供了加密和数据脱敏相关的接口：
```java
public interface EncryptionService {
    String encrypt(String plaintext);    // 加密
    String decrypt(String ciphertext);   // 解密
    String hashPassword(String password); // 密码哈希
}

public interface SensitiveDataService {
    String maskPhoneNumber(String phoneNumber); // 手机号脱敏
    String maskIdCard(String idCard);           // 身份证号脱敏
}
```

## 设计说明

1. **接口设计原则**
   - 保持接口的基础性、通用性和抽象性
   - 遵循单一职责原则
   - 接口粒度适中
   - 避免与具体业务耦合

2. **扩展性考虑**
   - 支持多种认证方式
   - 灵活的权限控制
   - 可扩展的审计系统
   - 可扩展的加密算法
   - 支持自定义脱敏规则

3. **使用建议**
   - 实现类遵循接口契约
   - 注意线程安全
   - 合理处理异常
   - 遵循最小权限原则
   - 重要操作需要审计

## 版本说明

### 当前版本
- 版本号：1.0.0
- 发布日期：2024-02-14
- 主要特性：
  - 完整的认证授权抽象
  - 灵活的权限控制
  - 统一的加密服务
  - 通用的数据脱敏
  - 完整的安全审计
  - 令牌管理服务

### 数据脱敏服务

数据脱敏服务(SensitiveDataService)提供了多种敏感信息脱敏方法，可用于日志记录、数据展示等场景。

#### 引入依赖

```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-security</artifactId>
</dependency>
```

#### 使用示例

```java
import com.lawfirm.common.security.crypto.SensitiveDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private SensitiveDataService sensitiveDataService;
    
    public void processUserInfo(String name, String idCard, String phone) {
        // 对敏感信息进行脱敏
        String maskedName = sensitiveDataService.maskName(name);
        String maskedIdCard = sensitiveDataService.maskIdCard(idCard);
        String maskedPhone = sensitiveDataService.maskPhoneNumber(phone);
        
        // 记录日志时使用脱敏后的信息
        log.info("处理用户信息: 姓名={}, 身份证={}, 手机号={}", 
                maskedName, maskedIdCard, maskedPhone);
                
        // 业务处理...
    }
}
```

#### 支持的脱敏方法

| 方法 | 描述 | 示例 |
|------|------|------|
| maskPhoneNumber | 手机号脱敏，保留前3位和后4位 | 134****8888 |
| maskIdCard | 身份证号脱敏，保留前6位和后4位 | 310101********1234 |
| maskBankCard | 银行卡号脱敏，仅显示后4位 | ************1234 |
| maskEmail | 邮箱脱敏，保留首字符和域名 | t***t@example.com |
| maskName | 姓名脱敏，仅显示姓氏 | 张** |
| mask | 自定义脱敏规则 | 自定义前后保留位数 | 