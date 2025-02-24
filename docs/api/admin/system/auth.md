# 认证授权接口

## 1. 登录认证

### 接口说明
- 接口路径：`POST /api/auth/login`
- 接口说明：用户登录认证，获取访问令牌

### 请求参数
```json
{
    "username": "admin",        // 用户名
    "password": "123456",       // 密码
    "captcha": "1234",         // 验证码
    "captchaKey": "abcd"       // 验证码key
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",          // 访问令牌
        "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",    // 刷新令牌
        "tokenType": "Bearer",                        // 令牌类型
        "expiresIn": 7200                            // 过期时间(秒)
    },
    "success": true
}
```

## 2. 获取用户信息

### 接口说明
- 接口路径：`GET /api/auth/getUserInfo`
- 接口说明：获取当前登录用户信息

### 请求参数
无

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "userId": 1,
        "username": "admin",
        "realName": "系统管理员",
        "avatar": "http://example.com/avatar.jpg",
        "roles": ["admin"],
        "permissions": ["system:user:list", "system:user:add"],
        "deptId": 1,
        "email": "admin@example.com",
        "mobile": "13800138000",
        "status": 1
    },
    "success": true
}
```

## 3. 获取权限编码

### 接口说明
- 接口路径：`GET /api/auth/getPermCode`
- 接口说明：获取当前用户的权限编码列表

### 请求参数
无

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": [
        "system:user:list",
        "system:user:add",
        "system:user:edit",
        "system:user:delete"
    ],
    "success": true
}
```

## 4. 获取菜单列表

### 接口说明
- 接口路径：`GET /api/auth/getMenuList`
- 接口说明：获取当前用户的菜单列表

### 请求参数
无

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "id": 1,
            "parentId": 0,
            "name": "系统管理",
            "path": "/system",
            "component": "Layout",
            "meta": {
                "title": "系统管理",
                "icon": "system",
                "orderNo": 1
            },
            "children": [
                {
                    "id": 2,
                    "parentId": 1,
                    "name": "用户管理",
                    "path": "user",
                    "component": "/system/user/index",
                    "meta": {
                        "title": "用户管理",
                        "icon": "user",
                        "orderNo": 1
                    }
                }
            ]
        }
    ],
    "success": true
}
```

## 5. 刷新令牌

### 接口说明
- 接口路径：`POST /api/auth/refresh`
- 接口说明：使用刷新令牌获取新的访问令牌

### 请求参数
```json
{
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."    // 刷新令牌
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",          // 新的访问令牌
        "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",    // 新的刷新令牌
        "tokenType": "Bearer",                        // 令牌类型
        "expiresIn": 7200                            // 过期时间(秒)
    },
    "success": true
}
```

## 6. 退出登录

### 接口说明
- 接口路径：`POST /api/auth/logout`
- 接口说明：退出登录，清除令牌

### 请求参数
无

### 响应结果
```json
{
    "code": 200,
    "message": "退出成功",
    "success": true
}
``` 