# 案件进展管理接口

## 1. 获取进展列表

### 接口说明
- 接口路径：`GET /api/case/progress/list`
- 接口说明：分页获取案件进展列表

### 请求参数
```json
{
    "pageNum": 1,                 // 当前页码
    "pageSize": 10,              // 每页条数
    "caseId": 1,                 // 案件ID
    "progressType": "",          // 进展类型（1-立案 2-开庭 3-调解 4-判决 5-执行 6-其他）
    "createBy": "",              // 创建人
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
                "progressId": 1,         // 进展ID
                "caseId": 1,             // 案件ID
                "progressType": "2",      // 进展类型
                "progressTitle": "第一次开庭", // 进展标题
                "progressContent": "案件于2024年1月10日上午9点在北京市第一中级人民法院开庭审理...", // 进展内容
                "progressDate": "2024-01-10", // 进展日期
                "nextPlan": "等待法院判决", // 下一步计划
                "attachments": [         // 相关附件
                    {
                        "fileId": 1,
                        "fileName": "开庭笔录.pdf",
                        "fileUrl": "http://example.com/file/xxx.pdf"
                    }
                ],
                "createBy": "王律师",     // 创建人
                "createTime": "2024-01-10 12:00:00"
            }
        ],
        "total": 100,
        "pageNum": 1,
        "pageSize": 10
    },
    "success": true
}
```

## 2. 获取进展详情

### 接口说明
- 接口路径：`GET /api/case/progress/{progressId}`
- 接口说明：获取案件进展详细信息

### 请求参数
- progressId：进展ID (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "progressId": 1,
        "caseId": 1,
        "progressType": "2",
        "progressTitle": "第一次开庭",
        "progressContent": "案件于2024年1月10日上午9点在北京市第一中级人民法院开庭审理...",
        "progressDate": "2024-01-10",
        "courtInfo": {           // 开庭信息（进展类型为开庭时）
            "courtTime": "2024-01-10 09:00:00", // 开庭时间
            "courtRoom": "第一法庭",    // 法庭
            "judge": "张法官",         // 承办法官
            "clerk": "李书记员",       // 书记员
            "participants": [         // 参与人员
                {
                    "name": "张三",
                    "role": "原告",
                    "lawyer": "王律师"
                },
                {
                    "name": "李四",
                    "role": "被告",
                    "lawyer": "赵律师"
                }
            ]
        },
        "mediationInfo": {      // 调解信息（进展类型为调解时）
            "mediator": "王调解员", // 调解员
            "result": "达成调解协议", // 调解结果
            "agreement": "双方同意..." // 调解协议内容
        },
        "judgmentInfo": {       // 判决信息（进展类型为判决时）
            "judgmentResult": "原告胜诉", // 判决结果
            "judgmentContent": "判决书内容...", // 判决内容
            "appealDeadline": "2024-02-10" // 上诉期限
        },
        "nextPlan": "等待法院判决",
        "attachments": [
            {
                "fileId": 1,
                "fileName": "开庭笔录.pdf",
                "fileUrl": "http://example.com/file/xxx.pdf"
            }
        ],
        "remark": "",           // 备注
        "createBy": "王律师",
        "createTime": "2024-01-10 12:00:00",
        "updateBy": "王律师",
        "updateTime": "2024-01-10 12:00:00"
    },
    "success": true
}
```

## 3. 新增进展

### 接口说明
- 接口路径：`POST /api/case/progress`
- 接口说明：新增案件进展

### 请求参数
```json
{
    "caseId": 1,                 // 案件ID
    "progressType": "2",         // 进展类型
    "progressTitle": "第一次开庭", // 进展标题
    "progressContent": "案件于2024年1月10日上午9点在北京市第一中级人民法院开庭审理...", // 进展内容
    "progressDate": "2024-01-10", // 进展日期
    "courtInfo": {              // 开庭信息（进展类型为开庭时）
        "courtTime": "2024-01-10 09:00:00",
        "courtRoom": "第一法庭",
        "judge": "张法官",
        "clerk": "李书记员",
        "participants": [
            {
                "name": "张三",
                "role": "原告",
                "lawyer": "王律师"
            },
            {
                "name": "李四",
                "role": "被告",
                "lawyer": "赵律师"
            }
        ]
    },
    "nextPlan": "等待法院判决",   // 下一步计划
    "attachments": [            // 相关附件
        {
            "fileName": "开庭笔录.pdf",
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

## 4. 更新进展

### 接口说明
- 接口路径：`PUT /api/case/progress`
- 接口说明：更新案件进展信息

### 请求参数
```json
{
    "progressId": 1,            // 进展ID
    "progressType": "2",        // 进展类型
    "progressTitle": "第一次开庭",
    "progressContent": "案件于2024年1月10日上午9点在北京市第一中级人民法院开庭审理...",
    "progressDate": "2024-01-10",
    "courtInfo": {
        "courtTime": "2024-01-10 09:00:00",
        "courtRoom": "第一法庭",
        "judge": "张法官",
        "clerk": "李书记员",
        "participants": [
            {
                "name": "张三",
                "role": "原告",
                "lawyer": "王律师"
            },
            {
                "name": "李四",
                "role": "被告",
                "lawyer": "赵律师"
            }
        ]
    },
    "nextPlan": "等待法院判决",
    "attachments": [
        {
            "fileId": 1,
            "fileName": "开庭笔录.pdf",
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

## 5. 删除进展

### 接口说明
- 接口路径：`DELETE /api/case/progress/{progressIds}`
- 接口说明：删除案件进展(支持批量)

### 请求参数
- progressIds：进展ID，多个以逗号分隔 (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "删除成功",
    "success": true
}
```

## 6. 获取案件进展时间线

### 接口说明
- 接口路径：`GET /api/case/progress/timeline/{caseId}`
- 接口说明：获取案件进展时间线

### 请求参数
- caseId：案件ID (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "date": "2024-01",
            "items": [
                {
                    "progressId": 1,
                    "progressType": "2",
                    "progressTitle": "第一次开庭",
                    "progressContent": "案件于2024年1月10日上午9点在北京市第一中级人民法院开庭审理...",
                    "progressDate": "2024-01-10",
                    "createBy": "王律师",
                    "createTime": "2024-01-10 12:00:00"
                }
            ]
        }
    ],
    "success": true
}
``` 