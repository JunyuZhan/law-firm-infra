# 通知公告接口

## 1. 获取通知公告列表

### 接口说明
- 接口路径：`GET /api/system/notice/list`
- 接口说明：分页获取通知公告列表

### 请求参数
```json
{
    "pageNum": 1,                 // 当前页码
    "pageSize": 10,              // 每页条数
    "noticeTitle": "",           // 公告标题
    "noticeType": "",            // 公告类型（1-通知 2-公告）
    "createBy": "",              // 创建人
    "status": 0,                 // 状态（0-正常 1-关闭）
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
                "noticeId": 1,            // 公告ID
                "noticeTitle": "放假通知", // 公告标题
                "noticeType": "1",        // 公告类型（1-通知 2-公告）
                "noticeContent": "五一放假通知...", // 公告内容
                "status": "0",            // 公告状态（0-正常 1-关闭）
                "remark": "",             // 备注
                "createBy": "admin",      // 创建者
                "createTime": "2024-01-01 00:00:00", // 创建时间
                "updateBy": "admin",      // 更新者
                "updateTime": "2024-01-01 00:00:00"  // 更新时间
            }
        ],
        "total": 100,
        "pageNum": 1,
        "pageSize": 10
    },
    "success": true
}
```

## 2. 获取通知公告详情

### 接口说明
- 接口路径：`GET /api/system/notice/{noticeId}`
- 接口说明：获取通知公告详细信息

### 请求参数
- noticeId：公告ID (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "noticeId": 1,
        "noticeTitle": "放假通知",
        "noticeType": "1",
        "noticeContent": "五一放假通知...",
        "status": "0",
        "remark": "",
        "createBy": "admin",
        "createTime": "2024-01-01 00:00:00",
        "updateBy": "admin",
        "updateTime": "2024-01-01 00:00:00"
    },
    "success": true
}
```

## 3. 新增通知公告

### 接口说明
- 接口路径：`POST /api/system/notice`
- 接口说明：新增通知公告

### 请求参数
```json
{
    "noticeTitle": "放假通知",     // 公告标题
    "noticeType": "1",           // 公告类型（1-通知 2-公告）
    "noticeContent": "五一放假通知...", // 公告内容
    "status": "0",               // 公告状态（0-正常 1-关闭）
    "remark": ""                 // 备注
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

## 4. 修改通知公告

### 接口说明
- 接口路径：`PUT /api/system/notice`
- 接口说明：修改通知公告

### 请求参数
```json
{
    "noticeId": 1,               // 公告ID
    "noticeTitle": "放假通知",     // 公告标题
    "noticeType": "1",           // 公告类型（1-通知 2-公告）
    "noticeContent": "五一放假通知...", // 公告内容
    "status": "0",               // 公告状态（0-正常 1-关闭）
    "remark": ""                 // 备注
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

## 5. 删除通知公告

### 接口说明
- 接口路径：`DELETE /api/system/notice/{noticeIds}`
- 接口说明：删除通知公告(支持批量)

### 请求参数
- noticeIds：公告ID，多个以逗号分隔 (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "删除成功",
    "success": true
}
```

## 6. 获取最新通知

### 接口说明
- 接口路径：`GET /api/system/notice/latest`
- 接口说明：获取最新通知公告列表（用于首页展示）

### 请求参数
```json
{
    "limit": 5                   // 获取条数
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "noticeId": 1,
            "noticeTitle": "放假通知",
            "noticeType": "1",
            "noticeContent": "五一放假通知...",
            "createTime": "2024-01-01 00:00:00"
        }
    ],
    "success": true
}
``` 