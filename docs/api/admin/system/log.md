# 日志管理接口

## 1. 操作日志

### 1.1 获取操作日志列表

#### 接口说明
- 接口路径：`GET /api/system/log/operation/list`
- 接口说明：分页获取操作日志列表

#### 请求参数
```json
{
    "pageNum": 1,                 // 当前页码
    "pageSize": 10,              // 每页条数
    "title": "",                 // 系统模块
    "operName": "",              // 操作人员
    "businessType": "",          // 操作类型（0-其它,1-新增,2-修改,3-删除,4-授权,5-导出,6-导入,7-强退,8-生成代码,9-清空数据）
    "status": 0,                 // 操作状态（0-正常,1-异常）
    "dateRange": [              // 操作时间范围
        "2024-01-01 00:00:00",
        "2024-12-31 23:59:59"
    ]
}
```

#### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "list": [
            {
                "operId": 1,              // 日志主键
                "title": "用户管理",        // 系统模块
                "businessType": 1,         // 业务类型
                "method": "com.lawfirm.system.controller.UserController.add()", // 方法名称
                "requestMethod": "POST",   // 请求方式
                "operatorType": 1,         // 操作类别（0-其它,1-后台用户,2-手机端用户）
                "operName": "admin",       // 操作人员
                "deptName": "研发部",      // 部门名称
                "operUrl": "/system/user", // 请求URL
                "operIp": "127.0.0.1",    // 主机地址
                "operLocation": "内网IP",  // 操作地点
                "operParam": "{\"userId\":1}", // 请求参数
                "jsonResult": "{\"code\":200}", // 返回参数
                "status": 0,              // 操作状态（0正常 1异常）
                "errorMsg": "",           // 错误消息
                "operTime": "2024-01-01 00:00:00" // 操作时间
            }
        ],
        "total": 100,
        "pageNum": 1,
        "pageSize": 10
    },
    "success": true
}
```

### 1.2 删除操作日志

#### 接口说明
- 接口路径：`DELETE /api/system/log/operation/{operIds}`
- 接口说明：删除操作日志(支持批量)

#### 请求参数
- operIds：日志ID，多个以逗号分隔 (路径参数)

#### 响应结果
```json
{
    "code": 200,
    "message": "删除成功",
    "success": true
}
```

### 1.3 清空操作日志

#### 接口说明
- 接口路径：`DELETE /api/system/log/operation/clean`
- 接口说明：清空操作日志

#### 请求参数
无

#### 响应结果
```json
{
    "code": 200,
    "message": "清空成功",
    "success": true
}
```

## 2. 登录日志

### 2.1 获取登录日志列表

#### 接口说明
- 接口路径：`GET /api/system/log/login/list`
- 接口说明：分页获取登录日志列表

#### 请求参数
```json
{
    "pageNum": 1,                 // 当前页码
    "pageSize": 10,              // 每页条数
    "ipaddr": "",                // 登录IP地址
    "userName": "",              // 用户名称
    "status": 0,                 // 状态（0成功 1失败）
    "dateRange": [              // 登录时间范围
        "2024-01-01 00:00:00",
        "2024-12-31 23:59:59"
    ]
}
```

#### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "list": [
            {
                "infoId": 1,             // 访问ID
                "userName": "admin",      // 用户名称
                "ipaddr": "127.0.0.1",   // 登录IP地址
                "loginLocation": "内网IP", // 登录地点
                "browser": "Chrome",      // 浏览器类型
                "os": "Windows 10",       // 操作系统
                "status": 0,             // 登录状态（0成功 1失败）
                "msg": "登录成功",         // 提示消息
                "loginTime": "2024-01-01 00:00:00" // 访问时间
            }
        ],
        "total": 100,
        "pageNum": 1,
        "pageSize": 10
    },
    "success": true
}
```

### 2.2 删除登录日志

#### 接口说明
- 接口路径：`DELETE /api/system/log/login/{infoIds}`
- 接口说明：删除登录日志(支持批量)

#### 请求参数
- infoIds：访问ID，多个以逗号分隔 (路径参数)

#### 响应结果
```json
{
    "code": 200,
    "message": "删除成功",
    "success": true
}
```

### 2.3 清空登录日志

#### 接口说明
- 接口路径：`DELETE /api/system/log/login/clean`
- 接口说明：清空登录日志

#### 请求参数
无

#### 响应结果
```json
{
    "code": 200,
    "message": "清空成功",
    "success": true
}
```

## 3. 在线用户

### 3.1 获取在线用户列表

#### 接口说明
- 接口路径：`GET /api/system/online/list`
- 接口说明：分页获取在线用户列表

#### 请求参数
```json
{
    "pageNum": 1,                 // 当前页码
    "pageSize": 10,              // 每页条数
    "ipaddr": "",                // 登录IP地址
    "userName": ""               // 用户名称
}
```

#### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "list": [
            {
                "tokenId": "xxx",         // 会话编号
                "userName": "admin",      // 用户名称
                "deptName": "研发部",      // 部门名称
                "ipaddr": "127.0.0.1",   // 登录IP地址
                "loginLocation": "内网IP", // 登录地点
                "browser": "Chrome",      // 浏览器类型
                "os": "Windows 10",       // 操作系统
                "loginTime": "2024-01-01 00:00:00" // 登录时间
            }
        ],
        "total": 100,
        "pageNum": 1,
        "pageSize": 10
    },
    "success": true
}
```

### 3.2 强退在线用户

#### 接口说明
- 接口路径：`DELETE /api/system/online/{tokenId}`
- 接口说明：强制退出在线用户

#### 请求参数
- tokenId：会话编号 (路径参数)

#### 响应结果
```json
{
    "code": 200,
    "message": "强退成功",
    "success": true
}
``` 