# 字典管理接口

## 1. 获取字典类型列表

### 接口说明
- 接口路径：`GET /api/system/dict/type/list`
- 接口说明：分页获取字典类型列表

### 请求参数
```json
{
    "pageNum": 1,                 // 当前页码
    "pageSize": 10,              // 每页条数
    "dictName": "",              // 字典名称
    "dictType": "",              // 字典类型
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
                "dictId": 1,
                "dictName": "用户性别",
                "dictType": "sys_user_sex",
                "status": "0",
                "createBy": "admin",
                "createTime": "2024-01-01 00:00:00",
                "updateBy": "admin",
                "updateTime": "2024-01-01 00:00:00",
                "remark": "用户性别列表"
            }
        ],
        "total": 100,
        "pageNum": 1,
        "pageSize": 10
    },
    "success": true
}
```

## 2. 获取字典类型详情

### 接口说明
- 接口路径：`GET /api/system/dict/type/{dictId}`
- 接口说明：获取字典类型详细信息

### 请求参数
- dictId：字典ID (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "dictId": 1,
        "dictName": "用户性别",
        "dictType": "sys_user_sex",
        "status": "0",
        "createBy": "admin",
        "createTime": "2024-01-01 00:00:00",
        "updateBy": "admin",
        "updateTime": "2024-01-01 00:00:00",
        "remark": "用户性别列表"
    },
    "success": true
}
```

## 3. 新增字典类型

### 接口说明
- 接口路径：`POST /api/system/dict/type`
- 接口说明：新增字典类型

### 请求参数
```json
{
    "dictName": "用户性别",        // 字典名称
    "dictType": "sys_user_sex",  // 字典类型
    "status": "0",               // 状态（0正常 1停用）
    "remark": "用户性别列表"       // 备注
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

## 4. 修改字典类型

### 接口说明
- 接口路径：`PUT /api/system/dict/type`
- 接口说明：修改字典类型

### 请求参数
```json
{
    "dictId": 1,                 // 字典ID
    "dictName": "用户性别",        // 字典名称
    "dictType": "sys_user_sex",  // 字典类型
    "status": "0",               // 状态（0正常 1停用）
    "remark": "用户性别列表"       // 备注
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

## 5. 删除字典类型

### 接口说明
- 接口路径：`DELETE /api/system/dict/type/{dictIds}`
- 接口说明：删除字典类型(支持批量)

### 请求参数
- dictIds：字典ID，多个以逗号分隔 (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "删除成功",
    "success": true
}
```

## 6. 获取字典数据列表

### 接口说明
- 接口路径：`GET /api/system/dict/data/list`
- 接口说明：分页获取字典数据列表

### 请求参数
```json
{
    "pageNum": 1,                 // 当前页码
    "pageSize": 10,              // 每页条数
    "dictType": "sys_user_sex",  // 字典类型
    "dictLabel": "",             // 字典标签
    "status": 1                  // 状态(0-禁用，1-正常)
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
                "dictCode": 1,
                "dictSort": 1,
                "dictLabel": "男",
                "dictValue": "0",
                "dictType": "sys_user_sex",
                "cssClass": "",
                "listClass": "default",
                "isDefault": "Y",
                "status": "0",
                "createBy": "admin",
                "createTime": "2024-01-01 00:00:00",
                "updateBy": "admin",
                "updateTime": "2024-01-01 00:00:00",
                "remark": "性别男"
            }
        ],
        "total": 100,
        "pageNum": 1,
        "pageSize": 10
    },
    "success": true
}
```

## 7. 根据字典类型查询字典数据

### 接口说明
- 接口路径：`GET /api/system/dict/data/type/{dictType}`
- 接口说明：根据字典类型查询字典数据

### 请求参数
- dictType：字典类型 (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "dictCode": 1,
            "dictSort": 1,
            "dictLabel": "男",
            "dictValue": "0",
            "dictType": "sys_user_sex",
            "cssClass": "",
            "listClass": "default",
            "isDefault": "Y",
            "status": "0",
            "remark": "性别男"
        },
        {
            "dictCode": 2,
            "dictSort": 2,
            "dictLabel": "女",
            "dictValue": "1",
            "dictType": "sys_user_sex",
            "cssClass": "",
            "listClass": "default",
            "isDefault": "N",
            "status": "0",
            "remark": "性别女"
        }
    ],
    "success": true
}
``` 