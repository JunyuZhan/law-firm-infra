# 🧪 API接口测试指南

## 📋 **测试前准备**

### 1. 确保后端运行
```bash
cd D:\weidi\law-firm-infra
mvn spring-boot:run
# 后端应运行在: http://localhost:8080
```

### 2. 使用API测试工具
推荐使用：Postman、Insomnia、或VS Code的REST Client插件

## 🔑 **认证相关API测试**

### 1. 获取验证码
```http
GET http://localhost:8080/api/v1/auth/getCaptcha
```

**期望响应**:
```json
{
  "code": 200,
  "data": {
    "captchaKey": "xxx-xxx-xxx",
    "captchaImage": "data:image/png;base64,xxx"
  },
  "message": "操作成功",
  "success": true,
  "result": {...}
}
```

### 2. 用户登录 (最关键)
```http
POST http://localhost:8080/api/v1/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}
```

**期望响应**:
```json
{
  "code": 200,
  "data": {
    "userId": 1,
    "username": "admin",
    "token": {
      "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
      "tokenType": "Bearer",
      "expiresIn": 7200,
      "refreshToken": "xxx"
    }
  },
  "message": "操作成功", 
  "success": true,
  "result": {...}
}
```

### 3. 获取用户信息 (需要登录token)
```http
GET http://localhost:8080/api/v1/users/getUserInfo
Authorization: Bearer {从登录响应中获取的accessToken}
```

**期望响应**:
```json
{
  "code": 200,
  "data": {
    "userId": 1,
    "username": "admin",
    "realName": "admin",
    "avatar": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
    "desc": "系统用户",
    "roles": [
      {"roleName": "管理员", "value": "admin"}
    ],
    "permissions": ["*:*:*"],
    "mobile": null,
    "email": null,
    "status": 0
  },
  "success": true,
  "result": {...}
}
```

### 4. 用户登出
```http
GET http://localhost:8080/api/v1/auth/logout
Authorization: Bearer {token}
```

### 5. 刷新Token
```http
POST http://localhost:8080/api/v1/auth/refreshToken
Content-Type: application/json

{
  "refreshToken": "{从登录响应中获取的refreshToken}"
}
```

## 🏢 **客户管理API测试**

### 1. 获取客户列表
```http
GET http://localhost:8080/api/v1/clients/list
Authorization: Bearer {token}
```

### 2. 获取客户详情
```http
GET http://localhost:8080/api/v1/clients/1
Authorization: Bearer {token}
```

### 3. 创建客户
```http
POST http://localhost:8080/api/v1/clients
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "测试客户",
  "type": 1,
  "phone": "13800138000",
  "email": "test@example.com",
  "address": "测试地址"
}
```

## ⚠️ **常见问题排查**

### 1. 跨域问题
如果遇到CORS错误，检查后端是否配置了跨域支持。

### 2. 401未授权
- 检查token是否正确
- 检查Authorization头格式: `Bearer {token}`
- 检查token是否过期

### 3. 404接口不存在
- 检查URL路径是否正确
- 检查后端控制器是否启动
- 检查HTTP方法是否匹配

### 4. 500服务器错误
- 检查后端控制台日志
- 检查数据库连接
- 检查必填参数是否传递

## 🎯 **前端集成检查清单**

### 登录流程检查
- [ ] 验证码获取正常
- [ ] 登录请求成功
- [ ] Token正确保存到localStorage
- [ ] 用户信息获取成功
- [ ] 登出功能正常

### API路径检查
- [ ] 所有API都包含 `/api/v1` 前缀
- [ ] HTTP方法正确（GET/POST/PUT/DELETE）
- [ ] 认证header正确添加

### 响应处理检查
- [ ] 正确解析 `CommonResult` 格式
- [ ] 成功时从 `data` 或 `result` 获取数据
- [ ] 错误时正确处理 `message` 字段
- [ ] 响应拦截器配置正确

## 💡 **调试技巧**

1. **使用浏览器开发者工具**查看网络请求
2. **检查后端控制台日志**了解服务器端错误
3. **使用API测试工具**先验证接口再集成
4. **逐步测试**，先认证再业务接口

## 📞 **测试联系人**

如果测试过程中遇到问题：
1. 首先查看后端控制台日志
2. 检查数据库是否正常运行  
3. 验证API路径和参数是否正确
4. 如果问题依然存在，请提供详细的错误信息 