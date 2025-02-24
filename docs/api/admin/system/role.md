# 角色管理接口

## 1. 获取角色列表

### 接口说明
- 接口路径：`GET /api/system/role/list`
- 接口说明：分页获取角色列表

### 请求参数
```json
{
    "pageNum": 1,                 // 当前页码
    "pageSize": 10,              // 每页条数
    "roleName": "",              // 角色名称
    "roleKey": "",               // 角色标识
    "status": 1,                 // 状态(0-禁用，1-正常)
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
                "roleId": 1,
                "roleName": "超级管理员",
                "roleKey": "admin",
                "roleSort": 1,
                "dataScope": "1",
                "status": 1,
                "createTime": "2024-01-01 00:00:00",
                "updateTime": "2024-01-01 00:00:00",
                "remark": "超级管理员"
            }
        ],
        "total": 100,
        "pageNum": 1,
        "pageSize": 10
    },
    "success": true
}
```

## 2. 获取角色详情

### 接口说明
- 接口路径：`GET /api/system/role/{roleId}`
- 接口说明：获取角色详细信息

### 请求参数
- roleId：角色ID (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "roleId": 1,
        "roleName": "超级管理员",
        "roleKey": "admin",
        "roleSort": 1,
        "dataScope": "1",
        "menuCheckStrictly": true,
        "deptCheckStrictly": true,
        "status": 1,
        "menuIds": [1, 2, 3],     // 菜单ID列表
        "deptIds": [1, 2],        // 部门ID列表
        "remark": "超级管理员"
    },
    "success": true
}
```

## 3. 新增角色

### 接口说明
- 接口路径：`POST /api/system/role`
- 接口说明：新增角色信息

### 请求参数
```json
{
    "roleName": "测试角色",        // 角色名称
    "roleKey": "test",           // 角色标识
    "roleSort": 2,               // 显示顺序
    "dataScope": "1",            // 数据范围(1-全部数据权限 2-自定数据权限 3-本部门数据权限 4-本部门及以下数据权限 5-仅本人数据权限)
    "menuCheckStrictly": true,   // 菜单树选择项是否关联显示
    "deptCheckStrictly": true,   // 部门树选择项是否关联显示
    "status": 1,                 // 状态
    "menuIds": [1, 2, 3],        // 菜单ID列表
    "deptIds": [1, 2],           // 部门ID列表
    "remark": "测试角色"           // 备注
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

## 4. 修改角色

### 接口说明
- 接口路径：`PUT /api/system/role`
- 接口说明：修改角色信息

### 请求参数
```json
{
    "roleId": 1,                 // 角色ID
    "roleName": "测试角色",        // 角色名称
    "roleKey": "test",           // 角色标识
    "roleSort": 2,               // 显示顺序
    "dataScope": "1",            // 数据范围
    "menuCheckStrictly": true,   // 菜单树选择项是否关联显示
    "deptCheckStrictly": true,   // 部门树选择项是否关联显示
    "status": 1,                 // 状态
    "menuIds": [1, 2, 3],        // 菜单ID列表
    "deptIds": [1, 2],           // 部门ID列表
    "remark": "测试角色"           // 备注
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

## 5. 删除角色

### 接口说明
- 接口路径：`DELETE /api/system/role/{roleIds}`
- 接口说明：删除角色信息(支持批量)

### 请求参数
- roleIds：角色ID，多个以逗号分隔 (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "删除成功",
    "success": true
}
```

## 6. 修改状态

### 接口说明
- 接口路径：`PUT /api/system/role/changeStatus`
- 接口说明：修改角色状态

### 请求参数
```json
{
    "roleId": 1,                // 角色ID
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

## 7. 获取角色选择框列表

### 接口说明
- 接口路径：`GET /api/system/role/optionselect`
- 接口说明：获取角色选择框列表

### 请求参数
无

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "roleId": 1,
            "roleName": "超级管理员",
            "roleKey": "admin"
        }
    ],
    "success": true
}
``` 