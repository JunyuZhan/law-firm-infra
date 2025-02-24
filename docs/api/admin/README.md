# 管理端API文档

## 认证授权接口

### 1. 登录认证
```http
POST /api/admin/auth/login

Request:
{
    "username": "admin",
    "password": "123456",
    "captcha": "1234",
    "captchaKey": "abcd"
}

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
    }
}
```

### 2. 获取验证码
```http
GET /api/admin/auth/captcha

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "captchaKey": "abcd",
        "captchaImage": "base64图片数据"
    }
}
```

### 3. 获取用户信息
```http
GET /api/admin/auth/info

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "user": {
            "userId": 1,
            "username": "admin",
            "realName": "管理员",
            "avatar": "http://example.com/avatar.jpg",
            "roles": ["admin"],
            "permissions": ["system:user:list"]
        }
    }
}
```

## 用户管理接口

### 1. 用户列表
```http
GET /api/admin/user/list
?pageNum=1
&pageSize=10
&username=admin
&status=1

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "pageNum": 1,
        "pageSize": 10,
        "total": 100,
        "list": [
            {
                "userId": 1,
                "username": "admin",
                "realName": "管理员",
                "email": "admin@example.com",
                "mobile": "13800138000",
                "status": 1,
                "createTime": "2024-02-09 10:00:00"
            }
        ]
    }
}
```

### 2. 添加用户
```http
POST /api/admin/user/add

Request:
{
    "username": "test",
    "password": "123456",
    "realName": "测试用户",
    "email": "test@example.com",
    "mobile": "13800138001",
    "roleIds": [1, 2],
    "status": 1
}

Response:
{
    "code": 200,
    "message": "success"
}
```

### 3. 修改用户
```http
PUT /api/admin/user/update

Request:
{
    "userId": 1,
    "realName": "测试用户",
    "email": "test@example.com",
    "mobile": "13800138001",
    "roleIds": [1, 2],
    "status": 1
}

Response:
{
    "code": 200,
    "message": "success"
}
```

## 角色管理接口

### 1. 角色列表
```http
GET /api/admin/role/list
?pageNum=1
&pageSize=10
&roleName=admin

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "pageNum": 1,
        "pageSize": 10,
        "total": 100,
        "list": [
            {
                "roleId": 1,
                "roleName": "管理员",
                "roleCode": "admin",
                "status": 1,
                "createTime": "2024-02-09 10:00:00"
            }
        ]
    }
}
```

### 2. 添加角色
```http
POST /api/admin/role/add

Request:
{
    "roleName": "测试角色",
    "roleCode": "test",
    "menuIds": [1, 2, 3],
    "status": 1,
    "remark": "测试角色"
}

Response:
{
    "code": 200,
    "message": "success"
}
```

### 3. 修改角色
```http
PUT /api/admin/role/update

Request:
{
    "roleId": 1,
    "roleName": "测试角色",
    "roleCode": "test",
    "menuIds": [1, 2, 3],
    "status": 1,
    "remark": "测试角色"
}

Response:
{
    "code": 200,
    "message": "success"
}
```

## 菜单管理接口

### 1. 菜单列表
```http
GET /api/admin/menu/list

Response:
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "menuId": 1,
            "menuName": "系统管理",
            "parentId": 0,
            "orderNum": 1,
            "path": "system",
            "component": "Layout",
            "type": "M",
            "perms": "",
            "icon": "system",
            "children": [
                {
                    "menuId": 2,
                    "menuName": "用户管理",
                    "parentId": 1,
                    "orderNum": 1,
                    "path": "user",
                    "component": "system/user/index",
                    "type": "C",
                    "perms": "system:user:list",
                    "icon": "user"
                }
            ]
        }
    ]
}
```

### 2. 添加菜单
```http
POST /api/admin/menu/add

Request:
{
    "menuName": "测试菜单",
    "parentId": 1,
    "orderNum": 1,
    "path": "test",
    "component": "test/index",
    "type": "C",
    "perms": "test:list",
    "icon": "test",
    "status": 1
}

Response:
{
    "code": 200,
    "message": "success"
}
```

### 3. 修改菜单
```http
PUT /api/admin/menu/update

Request:
{
    "menuId": 1,
    "menuName": "测试菜单",
    "parentId": 1,
    "orderNum": 1,
    "path": "test",
    "component": "test/index",
    "type": "C",
    "perms": "test:list",
    "icon": "test",
    "status": 1
}

Response:
{
    "code": 200,
    "message": "success"
}
```

## 字典管理接口

### 1. 字典类型列表
```http
GET /api/admin/dict/type/list
?pageNum=1
&pageSize=10
&dictName=系统状态

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "pageNum": 1,
        "pageSize": 10,
        "total": 100,
        "list": [
            {
                "dictId": 1,
                "dictName": "系统状态",
                "dictType": "sys_normal_disable",
                "status": 1,
                "createTime": "2024-02-09 10:00:00"
            }
        ]
    }
}
```

### 2. 字典数据列表
```http
GET /api/admin/dict/data/list
?pageNum=1
&pageSize=10
&dictType=sys_normal_disable

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "pageNum": 1,
        "pageSize": 10,
        "total": 100,
        "list": [
            {
                "dictCode": 1,
                "dictLabel": "正常",
                "dictValue": "1",
                "dictType": "sys_normal_disable",
                "listClass": "success",
                "status": 1
            }
        ]
    }
}
```

## 系统监控接口

### 1. 在线用户
```http
GET /api/admin/monitor/online
?pageNum=1
&pageSize=10
&ipaddr=127.0.0.1
&username=admin

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "pageNum": 1,
        "pageSize": 10,
        "total": 100,
        "list": [
            {
                "tokenId": "xxx",
                "username": "admin",
                "ipaddr": "127.0.0.1",
                "loginLocation": "XX XX",
                "browser": "Chrome",
                "os": "Windows 10",
                "loginTime": "2024-02-09 10:00:00"
            }
        ]
    }
}
```

### 2. 操作日志
```http
GET /api/admin/monitor/operlog
?pageNum=1
&pageSize=10
&operName=admin

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "pageNum": 1,
        "pageSize": 10,
        "total": 100,
        "list": [
            {
                "operId": 1,
                "title": "用户管理",
                "businessType": 1,
                "method": "com.xxx.controller.UserController.add()",
                "requestMethod": "POST",
                "operatorType": 1,
                "operName": "admin",
                "operUrl": "/system/user",
                "operIp": "127.0.0.1",
                "operLocation": "XX XX",
                "operParam": "{}",
                "jsonResult": "{}",
                "status": 0,
                "errorMsg": "",
                "operTime": "2024-02-09 10:00:00"
            }
        ]
    }
}
``` 