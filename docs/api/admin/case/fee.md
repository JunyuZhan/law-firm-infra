# 案件费用管理接口

## 1. 获取费用列表

### 接口说明
- 接口路径：`GET /api/case/fee/list`
- 接口说明：分页获取案件费用列表

### 请求参数
```json
{
    "pageNum": 1,                 // 当前页码
    "pageSize": 10,              // 每页条数
    "caseId": 1,                 // 案件ID
    "feeType": "",              // 费用类型（1-律师费 2-诉讼费 3-保全费 4-鉴定费 5-其他费用）
    "status": "",               // 费用状态（1-待收取 2-部分收取 3-已收取 4-已退回）
    "createBy": "",             // 创建人
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
                "feeId": 1,              // 费用ID
                "caseId": 1,             // 案件ID
                "feeType": "1",          // 费用类型
                "feeName": "代理费",      // 费用名称
                "feeAmount": 50000.00,   // 费用金额
                "receivedAmount": 30000.00, // 已收金额
                "remainAmount": 20000.00, // 剩余金额
                "status": "2",           // 费用状态
                "dueDate": "2024-02-01", // 应收日期
                "paymentMethod": "1",    // 支付方式（1-现金 2-转账 3-支票）
                "remark": "",            // 备注
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

## 2. 获取费用详情

### 接口说明
- 接口路径：`GET /api/case/fee/{feeId}`
- 接口说明：获取案件费用详细信息

### 请求参数
- feeId：费用ID (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "feeId": 1,
        "caseId": 1,
        "feeType": "1",
        "feeName": "代理费",
        "feeAmount": 50000.00,
        "receivedAmount": 30000.00,
        "remainAmount": 20000.00,
        "status": "2",
        "dueDate": "2024-02-01",
        "paymentMethod": "1",
        "paymentPlan": [         // 收款计划
            {
                "planId": 1,
                "planAmount": 30000.00,
                "planDate": "2024-01-15",
                "status": "1"    // 计划状态（1-待收取 2-已收取）
            },
            {
                "planId": 2,
                "planAmount": 20000.00,
                "planDate": "2024-02-15",
                "status": "1"
            }
        ],
        "paymentRecords": [      // 收款记录
            {
                "recordId": 1,
                "recordAmount": 30000.00,
                "recordDate": "2024-01-15",
                "paymentMethod": "1",
                "paymentAccount": "6222********1234", // 付款账号
                "paymentProof": "http://example.com/file/xxx.pdf", // 付款凭证
                "remark": "首期款",
                "createBy": "王律师",
                "createTime": "2024-01-15 10:00:00"
            }
        ],
        "attachments": [         // 相关附件
            {
                "fileId": 1,
                "fileName": "委托协议.pdf",
                "fileUrl": "http://example.com/file/xxx.pdf"
            }
        ],
        "remark": "",
        "createBy": "王律师",
        "createTime": "2024-01-01 00:00:00",
        "updateBy": "王律师",
        "updateTime": "2024-01-15 10:00:00"
    },
    "success": true
}
```

## 3. 新增费用

### 接口说明
- 接口路径：`POST /api/case/fee`
- 接口说明：新增案件费用

### 请求参数
```json
{
    "caseId": 1,                // 案件ID
    "feeType": "1",            // 费用类型
    "feeName": "代理费",        // 费用名称
    "feeAmount": 50000.00,     // 费用金额
    "dueDate": "2024-02-01",   // 应收日期
    "paymentMethod": "1",      // 支付方式
    "paymentPlan": [           // 收款计划
        {
            "planAmount": 30000.00,
            "planDate": "2024-01-15"
        },
        {
            "planAmount": 20000.00,
            "planDate": "2024-02-15"
        }
    ],
    "attachments": [           // 相关附件
        {
            "fileName": "委托协议.pdf",
            "fileUrl": "http://example.com/file/xxx.pdf"
        }
    ],
    "remark": ""              // 备注
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

## 4. 更新费用

### 接口说明
- 接口路径：`PUT /api/case/fee`
- 接口说明：更新案件费用信息

### 请求参数
```json
{
    "feeId": 1,                // 费用ID
    "feeType": "1",            // 费用类型
    "feeName": "代理费",        // 费用名称
    "feeAmount": 50000.00,     // 费用金额
    "dueDate": "2024-02-01",   // 应收日期
    "paymentMethod": "1",      // 支付方式
    "paymentPlan": [           // 收款计划
        {
            "planId": 1,
            "planAmount": 30000.00,
            "planDate": "2024-01-15"
        },
        {
            "planId": 2,
            "planAmount": 20000.00,
            "planDate": "2024-02-15"
        }
    ],
    "attachments": [           // 相关附件
        {
            "fileId": 1,
            "fileName": "委托协议.pdf",
            "fileUrl": "http://example.com/file/xxx.pdf"
        }
    ],
    "remark": ""              // 备注
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

## 5. 删除费用

### 接口说明
- 接口路径：`DELETE /api/case/fee/{feeIds}`
- 接口说明：删除案件费用(支持批量)

### 请求参数
- feeIds：费用ID，多个以逗号分隔 (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "删除成功",
    "success": true
}
```

## 6. 收款登记

### 接口说明
- 接口路径：`POST /api/case/fee/receive`
- 接口说明：登记收款记录

### 请求参数
```json
{
    "feeId": 1,                // 费用ID
    "recordAmount": 30000.00,  // 收款金额
    "recordDate": "2024-01-15", // 收款日期
    "paymentMethod": "1",      // 支付方式
    "paymentAccount": "6222********1234", // 付款账号
    "paymentProof": "http://example.com/file/xxx.pdf", // 付款凭证
    "remark": "首期款"         // 备注
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "登记成功",
    "success": true
}
```

## 7. 费用统计

### 接口说明
- 接口路径：`GET /api/case/fee/statistics`
- 接口说明：获取案件费用统计信息

### 请求参数
```json
{
    "caseId": 1,              // 案件ID（可选）
    "dateRange": [           // 统计时间范围
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
        "totalAmount": 100000.00,    // 费用总额
        "receivedAmount": 60000.00,  // 已收总额
        "remainAmount": 40000.00,    // 待收总额
        "typeStats": [               // 费用类型统计
            {
                "feeType": "1",
                "typeName": "律师费",
                "amount": 50000.00,
                "count": 5
            }
        ],
        "monthlyStats": [            // 月度统计
            {
                "month": "2024-01",
                "amount": 30000.00,
                "count": 3
            }
        ]
    },
    "success": true
}
``` 