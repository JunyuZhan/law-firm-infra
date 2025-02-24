# 用户管理接口

## 1. 获取用户列表

### 接口说明
- 接口路径：`GET /api/system/user/list`
- 接口说明：分页获取用户列表

### 请求参数
```json
{
    "pageNum": 1,                 // 当前页码
    "pageSize": 10,              // 每页条数
    "keyword": "",               // 关键字搜索(用户名/姓名)
    "deptId": 1,                // 部门ID
    "status": 1,                // 状态(0-禁用，1-正常)
    "dateRange": [              // 创建时间范围
        "2024-01-01 00:00:00",
        "2024-12-31 23:59:59"
    ]
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "list": [
            {
                "userId": 1,
                "username": "admin",
                "realName": "系统管理员",
                "deptId": 1,
                "deptName": "总公司",
                "email": "admin@example.com",
                "mobile": "13800138000",
                "status": 1,
                "createTime": "2024-01-01 00:00:00",
                "updateTime": "2024-01-01 00:00:00"
            }
        ],
        "total": 100,
        "pageNum": 1,
        "pageSize": 10
    },
    "success": true
}
```

## 2. 获取用户详情

### 接口说明
- 接口路径：`GET /api/system/user/{userId}`
- 接口说明：获取用户详细信息

### 请求参数
- userId：用户ID (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "userId": 1,
        "username": "admin",
        "realName": "系统管理员",
        "deptId": 1,
        "deptName": "总公司",
        "email": "admin@example.com",
        "mobile": "13800138000",
        "status": 1,
        "roleIds": [1, 2],      // 角色ID列表
        "postIds": [1],         // 岗位ID列表
        "remark": "备注信息"
    },
    "success": true
}
```

## 3. 新增用户

### 接口说明
- 接口路径：`POST /api/system/user`
- 接口说明：新增用户信息

### 请求参数
```json
{
    "username": "test",           // 用户名
    "password": "123456",         // 密码
    "realName": "测试用户",       // 真实姓名
    "deptId": 1,                 // 部门ID
    "email": "test@example.com", // 邮箱
    "mobile": "13800138001",     // 手机号
    "status": 1,                 // 状态
    "roleIds": [1, 2],           // 角色ID列表
    "postIds": [1],              // 岗位ID列表
    "remark": "备注信息"          // 备注
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "新增成功",
    "success": true
}
```

## 4. 修改用户

### 接口说明
- 接口路径：`PUT /api/system/user`
- 接口说明：修改用户信息

### 请求参数
```json
{
    "userId": 1,                 // 用户ID
    "realName": "测试用户",       // 真实姓名
    "deptId": 1,                 // 部门ID
    "email": "test@example.com", // 邮箱
    "mobile": "13800138001",     // 手机号
    "status": 1,                 // 状态
    "roleIds": [1, 2],           // 角色ID列表
    "postIds": [1],              // 岗位ID列表
    "remark": "备注信息"          // 备注
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "修改成功",
    "success": true
}
```

## 5. 删除用户

### 接口说明
- 接口路径：`DELETE /api/system/user/{userIds}`
- 接口说明：删除用户信息(支持批量)

### 请求参数
- userIds：用户ID，多个以逗号分隔 (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "删除成功",
    "success": true
}
```

## 6. 重置密码

### 接口说明
- 接口路径：`PUT /api/system/user/resetPwd`
- 接口说明：重置用户密码

### 请求参数
```json
{
    "userId": 1,                // 用户ID
    "password": "123456"        // 新密码
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "重置成功",
    "success": true
}
```

## 7. 修改状态

### 接口说明
- 接口路径：`PUT /api/system/user/changeStatus`
- 接口说明：修改用户状态

### 请求参数
```json
{
    "userId": 1,                // 用户ID
    "status": 1                 // 状态(0-禁用，1-正常)
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "修改成功",
    "success": true
}
``` 