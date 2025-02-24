# 案件基本信息管理接口

## 1. 获取案件列表

### 接口说明
- 接口路径：`GET /api/case/list`
- 接口说明：分页获取案件列表

### 请求参数
```json
{
    "pageNum": 1,                 // 当前页码
    "pageSize": 10,              // 每页条数
    "caseNumber": "",            // 案件编号
    "caseName": "",              // 案件名称
    "caseType": "",              // 案件类型（1-民事案件 2-刑事案件 3-行政案件 4-非诉业务）
    "status": "",                // 案件状态（1-待立案 2-进行中 3-已结案 4-已归档）
    "priority": "",              // 优先级（1-普通 2-紧急 3-特急）
    "leadLawyerId": "",          // 主办律师ID
    "clientId": "",              // 委托人ID
    "dateRange": [              // 立案时间范围
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
                "caseId": 1,                // 案件ID
                "caseNumber": "CASE202401001", // 案件编号
                "caseName": "张三诉李四合同纠纷案", // 案件名称
                "caseType": "1",            // 案件类型
                "status": "2",              // 案件状态
                "priority": "1",            // 优先级
                "leadLawyerId": 1,          // 主办律师ID
                "leadLawyerName": "王律师",   // 主办律师姓名
                "clientId": 1,              // 委托人ID
                "clientName": "张三",        // 委托人姓名
                "filingDate": "2024-01-01", // 立案日期
                "closingDate": null,        // 结案日期
                "courtName": "北京市第一中级人民法院", // 承办法院
                "caseAmount": 100000.00,    // 标的金额
                "description": "合同纠纷案件...", // 案件描述
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

## 2. 获取案件详情

### 接口说明
- 接口路径：`GET /api/case/{caseId}`
- 接口说明：获取案件详细信息

### 请求参数
- caseId：案件ID (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "caseId": 1,
        "caseNumber": "CASE202401001",
        "caseName": "张三诉李四合同纠纷案",
        "caseType": "1",
        "status": "2",
        "priority": "1",
        "leadLawyerId": 1,
        "leadLawyerName": "王律师",
        "assistLawyers": [          // 协办律师列表
            {
                "lawyerId": 2,
                "lawyerName": "李律师"
            }
        ],
        "clientId": 1,
        "clientName": "张三",
        "oppositeParties": [        // 对方当事人列表
            {
                "partyId": 1,
                "partyName": "李四",
                "partyType": "1",    // 当事人类型（1-自然人 2-法人）
                "contact": "13800138000"
            }
        ],
        "filingDate": "2024-01-01",
        "closingDate": null,
        "courtName": "北京市第一中级人民法院",
        "judgeName": "张法官",        // 承办法官
        "judgeContact": "010-12345678", // 法官联系方式
        "caseAmount": 100000.00,
        "caseReason": "合同纠纷",     // 案由
        "description": "合同纠纷案件...",
        "remark": "",               // 备注
        "createTime": "2024-01-01 00:00:00",
        "updateTime": "2024-01-01 00:00:00"
    },
    "success": true
}
```

## 3. 新增案件

### 接口说明
- 接口路径：`POST /api/case`
- 接口说明：新增案件信息

### 请求参数
```json
{
    "caseName": "张三诉李四合同纠纷案", // 案件名称
    "caseType": "1",               // 案件类型
    "priority": "1",               // 优先级
    "leadLawyerId": 1,             // 主办律师ID
    "assistLawyerIds": [2],        // 协办律师ID列表
    "clientId": 1,                 // 委托人ID
    "oppositeParties": [           // 对方当事人列表
        {
            "partyName": "李四",
            "partyType": "1",
            "contact": "13800138000"
        }
    ],
    "filingDate": "2024-01-01",    // 立案日期
    "courtName": "北京市第一中级人民法院",
    "judgeName": "张法官",
    "judgeContact": "010-12345678",
    "caseAmount": 100000.00,
    "caseReason": "合同纠纷",
    "description": "合同纠纷案件...",
    "remark": ""
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "新增成功",
    "data": {
        "caseId": 1,
        "caseNumber": "CASE202401001"
    },
    "success": true
}
```

## 4. 更新案件

### 接口说明
- 接口路径：`PUT /api/case`
- 接口说明：更新案件信息

### 请求参数
```json
{
    "caseId": 1,                   // 案件ID
    "caseName": "张三诉李四合同纠纷案",
    "caseType": "1",
    "priority": "1",
    "leadLawyerId": 1,
    "assistLawyerIds": [2],
    "oppositeParties": [
        {
            "partyId": 1,
            "partyName": "李四",
            "partyType": "1",
            "contact": "13800138000"
        }
    ],
    "courtName": "北京市第一中级人民法院",
    "judgeName": "张法官",
    "judgeContact": "010-12345678",
    "caseAmount": 100000.00,
    "caseReason": "合同纠纷",
    "description": "合同纠纷案件...",
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

## 5. 删除案件

### 接口说明
- 接口路径：`DELETE /api/case/{caseIds}`
- 接口说明：删除案件信息(支持批量)

### 请求参数
- caseIds：案件ID，多个以逗号分隔 (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "删除成功",
    "success": true
}
```

## 6. 更新案件状态

### 接口说明
- 接口路径：`PUT /api/case/status`
- 接口说明：更新案件状态

### 请求参数
```json
{
    "caseId": 1,                // 案件ID
    "status": "2",              // 案件状态
    "statusRemark": "案件进行中"  // 状态说明
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

## 7. 案件结案

### 接口说明
- 接口路径：`PUT /api/case/close`
- 接口说明：案件结案

### 请求参数
```json
{
    "caseId": 1,                // 案件ID
    "closingDate": "2024-12-31", // 结案日期
    "closingResult": "胜诉",     // 结案结果
    "closingRemark": "案件已完结" // 结案说明
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "结案成功",
    "success": true
}
```

## 8. 案件归档

### 接口说明
- 接口路径：`PUT /api/case/archive`
- 接口说明：案件归档

### 请求参数
```json
{
    "caseId": 1,                // 案件ID
    "archiveNumber": "ARC202401001", // 归档编号
    "archiveDate": "2024-12-31", // 归档日期
    "archiveRemark": "案件归档"   // 归档说明
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "归档成功",
    "success": true
}
``` 