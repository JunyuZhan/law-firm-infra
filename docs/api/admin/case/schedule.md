# 案件日程管理接口

## 1. 获取日程列表

### 接口说明
- 接口路径：`GET /api/case/schedule/list`
- 接口说明：分页获取案件日程列表

### 请求参数
```json
{
    "pageNum": 1,                 // 当前页码
    "pageSize": 10,              // 每页条数
    "caseId": 1,                 // 案件ID
    "scheduleType": "",          // 日程类型（1-开庭 2-会见 3-调解 4-谈判 5-其他）
    "status": "",                // 日程状态（1-未开始 2-进行中 3-已完成 4-已取消）
    "priority": "",              // 优先级（1-普通 2-重要 3-紧急）
    "createBy": "",              // 创建人
    "dateRange": [              // 日程时间范围
        "2024-01-01",
        "2024-12-31"
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
                "scheduleId": 1,         // 日程ID
                "caseId": 1,             // 案件ID
                "caseName": "张三诉李四合同纠纷案", // 案件名称
                "scheduleType": "1",      // 日程类型
                "scheduleTitle": "第一次开庭", // 日程标题
                "startTime": "2024-01-10 09:00:00", // 开始时间
                "endTime": "2024-01-10 11:00:00",   // 结束时间
                "location": "北京市第一中级人民法院第一法庭", // 地点
                "status": "1",           // 日程状态
                "priority": "2",         // 优先级
                "participants": [        // 参与人员
                    {
                        "userId": 1,
                        "userName": "王律师",
                        "userType": "1"  // 人员类型（1-律师 2-当事人 3-其他）
                    }
                ],
                "reminder": {            // 提醒设置
                    "reminderType": "1", // 提醒类型（1-不提醒 2-定时提醒）
                    "reminderTime": 30,  // 提前提醒时间（分钟）
                    "notifyType": ["email", "system"] // 通知方式
                },
                "createBy": "王律师",     // 创建人
                "createTime": "2024-01-01 00:00:00"
            }
        ],
        "total": 100,
        "pageNum": 1,
        "pageSize": 10
    },
    "success": true
}
```

## 2. 获取日程详情

### 接口说明
- 接口路径：`GET /api/case/schedule/{scheduleId}`
- 接口说明：获取案件日程详细信息

### 请求参数
- scheduleId：日程ID (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "scheduleId": 1,
        "caseId": 1,
        "caseName": "张三诉李四合同纠纷案",
        "scheduleType": "1",
        "scheduleTitle": "第一次开庭",
        "scheduleContent": "案件第一次开庭...", // 日程内容
        "startTime": "2024-01-10 09:00:00",
        "endTime": "2024-01-10 11:00:00",
        "location": "北京市第一中级人民法院第一法庭",
        "status": "1",
        "priority": "2",
        "participants": [
            {
                "userId": 1,
                "userName": "王律师",
                "userType": "1",
                "contact": "13800138000",
                "email": "lawyer@example.com"
            }
        ],
        "reminder": {
            "reminderType": "2",
            "reminderTime": 30,
            "notifyType": ["email", "system"]
        },
        "courtInfo": {           // 开庭信息（日程类型为开庭时）
            "courtRoom": "第一法庭",
            "judge": "张法官",
            "clerk": "李书记员",
            "caseNumber": "（2024）京01民初1号"
        },
        "attachments": [         // 相关附件
            {
                "fileId": 1,
                "fileName": "开庭通知书.pdf",
                "fileUrl": "http://example.com/file/xxx.pdf"
            }
        ],
        "remark": "",           // 备注
        "createBy": "王律师",
        "createTime": "2024-01-01 00:00:00",
        "updateBy": "王律师",
        "updateTime": "2024-01-01 00:00:00"
    },
    "success": true
}
```

## 3. 新增日程

### 接口说明
- 接口路径：`POST /api/case/schedule`
- 接口说明：新增案件日程

### 请求参数
```json
{
    "caseId": 1,                // 案件ID
    "scheduleType": "1",        // 日程类型
    "scheduleTitle": "第一次开庭", // 日程标题
    "scheduleContent": "案件第一次开庭...", // 日程内容
    "startTime": "2024-01-10 09:00:00", // 开始时间
    "endTime": "2024-01-10 11:00:00",   // 结束时间
    "location": "北京市第一中级人民法院第一法庭", // 地点
    "priority": "2",            // 优先级
    "participants": [           // 参与人员
        {
            "userId": 1,
            "userType": "1",
            "contact": "13800138000",
            "email": "lawyer@example.com"
        }
    ],
    "reminder": {               // 提醒设置
        "reminderType": "2",
        "reminderTime": 30,
        "notifyType": ["email", "system"]
    },
    "courtInfo": {              // 开庭信息
        "courtRoom": "第一法庭",
        "judge": "张法官",
        "clerk": "李书记员",
        "caseNumber": "（2024）京01民初1号"
    },
    "attachments": [            // 相关附件
        {
            "fileName": "开庭通知书.pdf",
            "fileUrl": "http://example.com/file/xxx.pdf"
        }
    ],
    "remark": ""               // 备注
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

## 4. 更新日程

### 接口说明
- 接口路径：`PUT /api/case/schedule`
- 接口说明：更新案件日程信息

### 请求参数
```json
{
    "scheduleId": 1,            // 日程ID
    "scheduleType": "1",        // 日程类型
    "scheduleTitle": "第一次开庭",
    "scheduleContent": "案件第一次开庭...",
    "startTime": "2024-01-10 09:00:00",
    "endTime": "2024-01-10 11:00:00",
    "location": "北京市第一中级人民法院第一法庭",
    "priority": "2",
    "participants": [
        {
            "userId": 1,
            "userType": "1",
            "contact": "13800138000",
            "email": "lawyer@example.com"
        }
    ],
    "reminder": {
        "reminderType": "2",
        "reminderTime": 30,
        "notifyType": ["email", "system"]
    },
    "courtInfo": {
        "courtRoom": "第一法庭",
        "judge": "张法官",
        "clerk": "李书记员",
        "caseNumber": "（2024）京01民初1号"
    },
    "attachments": [
        {
            "fileId": 1,
            "fileName": "开庭通知书.pdf",
            "fileUrl": "http://example.com/file/xxx.pdf"
        }
    ],
    "remark": ""
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "更新成功",
    "success": true
}
```

## 5. 删除日程

### 接口说明
- 接口路径：`DELETE /api/case/schedule/{scheduleIds}`
- 接口说明：删除案件日程(支持批量)

### 请求参数
- scheduleIds：日程ID，多个以逗号分隔 (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "删除成功",
    "success": true
}
```

## 6. 更新日程状态

### 接口说明
- 接口路径：`PUT /api/case/schedule/status`
- 接口说明：更新日程状态

### 请求参数
```json
{
    "scheduleId": 1,           // 日程ID
    "status": "2",             // 日程状态
    "statusRemark": "正在进行中" // 状态说明
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "更新成功",
    "success": true
}
```

## 7. 获取日历视图

### 接口说明
- 接口路径：`GET /api/case/schedule/calendar`
- 接口说明：获取日历视图的日程数据

### 请求参数
```json
{
    "caseId": 1,              // 案件ID（可选）
    "dateRange": [           // 日期范围
        "2024-01-01",
        "2024-01-31"
    ],
    "scheduleType": "",      // 日程类型（可选）
    "priority": ""          // 优先级（可选）
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "2024-01-10": [      // 按日期分组
            {
                "scheduleId": 1,
                "scheduleType": "1",
                "scheduleTitle": "第一次开庭",
                "startTime": "2024-01-10 09:00:00",
                "endTime": "2024-01-10 11:00:00",
                "location": "北京市第一中级人民法院第一法庭",
                "status": "1",
                "priority": "2"
            }
        ]
    },
    "success": true
}
``` 