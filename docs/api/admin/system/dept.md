# 部门管理接口

## 1. 获取部门列表

### 接口说明
- 接口路径：`GET /api/system/dept/list`
- 接口说明：获取部门列表(树形结构)

### 请求参数
```json
{
    "deptName": "",              // 部门名称
    "status": 1,                 // 状态(0-禁用，1-正常)
    "parentId": 0               // 父部门ID
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "deptId": 1,
            "parentId": 0,
            "deptName": "总公司",
            "orderNum": 1,             // 显示顺序
            "leader": "张三",           // 负责人
            "phone": "13800138000",    // 联系电话
            "email": "zhangsan@example.com", // 邮箱
            "status": "0",             // 部门状态（0正常 1停用）
            "createTime": "2024-01-01 00:00:00",
            "children": [
                {
                    "deptId": 2,
                    "parentId": 1,
                    "deptName": "研发部",
                    "orderNum": 1,
                    "leader": "李四",
                    "phone": "13800138001",
                    "email": "lisi@example.com",
                    "status": "0",
                    "createTime": "2024-01-01 00:00:00"
                }
            ]
        }
    ],
    "success": true
}
```

## 2. 获取部门详情

### 接口说明
- 接口路径：`GET /api/system/dept/{deptId}`
- 接口说明：获取部门详细信息

### 请求参数
- deptId：部门ID (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "deptId": 1,
        "parentId": 0,
        "deptName": "总公司",
        "orderNum": 1,
        "leader": "张三",
        "phone": "13800138000",
        "email": "zhangsan@example.com",
        "status": "0",
        "createTime": "2024-01-01 00:00:00"
    },
    "success": true
}
```

## 3. 新增部门

### 接口说明
- 接口路径：`POST /api/system/dept`
- 接口说明：新增部门信息

### 请求参数
```json
{
    "parentId": 0,               // 父部门ID
    "deptName": "研发部",         // 部门名称
    "orderNum": 1,              // 显示顺序
    "leader": "李四",            // 负责人
    "phone": "13800138001",     // 联系电话
    "email": "lisi@example.com", // 邮箱
    "status": "0"               // 部门状态（0正常 1停用）
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

## 4. 修改部门

### 接口说明
- 接口路径：`PUT /api/system/dept`
- 接口说明：修改部门信息

### 请求参数
```json
{
    "deptId": 1,                // 部门ID
    "parentId": 0,              // 父部门ID
    "deptName": "研发部",        // 部门名称
    "orderNum": 1,              // 显示顺序
    "leader": "李四",           // 负责人
    "phone": "13800138001",    // 联系电话
    "email": "lisi@example.com", // 邮箱
    "status": "0"              // 部门状态（0正常 1停用）
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

## 5. 删除部门

### 接口说明
- 接口路径：`DELETE /api/system/dept/{deptId}`
- 接口说明：删除部门信息

### 请求参数
- deptId：部门ID (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "删除成功",
    "success": true
}
```

## 6. 获取部门下拉树列表

### 接口说明
- 接口路径：`GET /api/system/dept/treeselect`
- 接口说明：获取部门下拉树列表

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
            "label": "总公司",
            "children": [
                {
                    "id": 2,
                    "label": "研发部"
                }
            ]
        }
    ],
    "success": true
}
```

## 7. 获取角色部门树

### 接口说明
- 接口路径：`GET /api/system/dept/roleDeptTreeselect/{roleId}`
- 接口说明：获取角色部门权限树

### 请求参数
- roleId：角色ID (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "depts": [              // 部门树
            {
                "id": 1,
                "label": "总公司",
                "children": [
                    {
                        "id": 2,
                        "label": "研发部"
                    }
                ]
            }
        ],
        "checkedKeys": [1, 2]   // 选中的部门ID列表
    },
    "success": true
}
``` 