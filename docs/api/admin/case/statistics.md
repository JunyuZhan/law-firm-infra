# 案件统计分析接口

## 1. 案件概览统计

### 接口说明
- 接口路径：`GET /api/case/statistics/overview`
- 接口说明：获取案件概览统计数据

### 请求参数
```json
{
    "dateRange": [           // 统计时间范围
        "2024-01-01",
        "2024-12-31"
    ],
    "lawyerId": 1,          // 律师ID（可选）
    "deptId": 1             // 部门ID（可选）
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "totalCount": 100,           // 案件总数
        "activeCount": 50,           // 在办案件数
        "completedCount": 40,        // 已结案件数
        "archivedCount": 10,         // 已归档案件数
        "thisMonthNewCount": 5,      // 本月新增案件数
        "thisMonthClosedCount": 3,   // 本月结案数
        "totalAmount": 1000000.00,   // 案件总金额
        "receivedAmount": 600000.00, // 已收金额
        "remainAmount": 400000.00,   // 待收金额
        "winRate": 0.85,            // 胜诉率
        "typeStats": [              // 案件类型统计
            {
                "type": "1",
                "typeName": "民事案件",
                "count": 60,
                "amount": 600000.00
            }
        ],
        "statusStats": [           // 案件状态统计
            {
                "status": "2",
                "statusName": "进行中",
                "count": 50
            }
        ],
        "priorityStats": [        // 优先级统计
            {
                "priority": "2",
                "priorityName": "重要",
                "count": 30
            }
        ]
    },
    "success": true
}
```

## 2. 案件趋势分析

### 接口说明
- 接口路径：`GET /api/case/statistics/trend`
- 接口说明：获取案件趋势分析数据

### 请求参数
```json
{
    "dateRange": [           // 统计时间范围
        "2024-01-01",
        "2024-12-31"
    ],
    "dimension": "month",    // 统计维度（day-日 week-周 month-月 quarter-季 year-年）
    "lawyerId": 1,          // 律师ID（可选）
    "deptId": 1             // 部门ID（可选）
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "dimensions": ["2024-01", "2024-02", "2024-03"], // 时间维度
        "series": [
            {
                "name": "新增案件数",
                "data": [10, 15, 12]
            },
            {
                "name": "结案数",
                "data": [8, 12, 10]
            },
            {
                "name": "案件金额",
                "data": [100000.00, 150000.00, 120000.00]
            }
        ]
    },
    "success": true
}
```

## 3. 律师业绩分析

### 接口说明
- 接口路径：`GET /api/case/statistics/performance`
- 接口说明：获取律师业绩分析数据

### 请求参数
```json
{
    "dateRange": [           // 统计时间范围
        "2024-01-01",
        "2024-12-31"
    ],
    "deptId": 1,            // 部门ID（可选）
    "lawyerIds": [1, 2]     // 律师ID列表（可选）
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
                "lawyerId": 1,
                "lawyerName": "王律师",
                "deptName": "民商事部",
                "caseCount": 20,         // 办理案件数
                "activeCount": 10,       // 在办案件数
                "completedCount": 10,    // 已结案件数
                "totalAmount": 500000.00, // 案件总金额
                "receivedAmount": 300000.00, // 已收金额
                "winRate": 0.90,         // 胜诉率
                "clientCount": 15,       // 客户数量
                "avgDuration": 90,       // 平均办案周期（天）
                "monthlyStats": [        // 月度统计
                    {
                        "month": "2024-01",
                        "caseCount": 5,
                        "amount": 150000.00
                    }
                ]
            }
        ],
        "total": {                     // 汇总数据
            "caseCount": 100,
            "totalAmount": 2000000.00,
            "avgWinRate": 0.85
        }
    },
    "success": true
}
```

## 4. 客户分布分析

### 接口说明
- 接口路径：`GET /api/case/statistics/client`
- 接口说明：获取案件客户分布分析数据

### 请求参数
```json
{
    "dateRange": [           // 统计时间范围
        "2024-01-01",
        "2024-12-31"
    ],
    "dimension": "type"      // 统计维度（type-客户类型 industry-行业 area-地区）
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "typeStats": [              // 客户类型统计
            {
                "type": "1",
                "typeName": "企业客户",
                "count": 30,
                "amount": 800000.00,
                "percentage": 0.60
            }
        ],
        "industryStats": [          // 行业统计
            {
                "industry": "1",
                "industryName": "互联网",
                "count": 20,
                "amount": 500000.00,
                "percentage": 0.40
            }
        ],
        "areaStats": [              // 地区统计
            {
                "area": "110000",
                "areaName": "北京市",
                "count": 25,
                "amount": 600000.00,
                "percentage": 0.50
            }
        ]
    },
    "success": true
}
```

## 5. 案件周期分析

### 接口说明
- 接口路径：`GET /api/case/statistics/duration`
- 接口说明：获取案件周期分析数据

### 请求参数
```json
{
    "dateRange": [           // 统计时间范围
        "2024-01-01",
        "2024-12-31"
    ],
    "caseType": "",         // 案件类型（可选）
    "lawyerId": 1           // 律师ID（可选）
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "avgDuration": 120,         // 平均周期（天）
        "minDuration": 30,          // 最短周期
        "maxDuration": 365,         // 最长周期
        "durationDistribution": [   // 周期分布
            {
                "range": "0-30天",
                "count": 10,
                "percentage": 0.20
            },
            {
                "range": "31-90天",
                "count": 25,
                "percentage": 0.50
            },
            {
                "range": "91-180天",
                "count": 10,
                "percentage": 0.20
            },
            {
                "range": "181天以上",
                "count": 5,
                "percentage": 0.10
            }
        ],
        "phaseAnalysis": [         // 阶段分析
            {
                "phase": "立案",
                "avgDuration": 15,  // 平均耗时（天）
                "percentage": 0.125 // 占总周期比例
            },
            {
                "phase": "庭审",
                "avgDuration": 60,
                "percentage": 0.50
            },
            {
                "phase": "判决",
                "avgDuration": 30,
                "percentage": 0.25
            },
            {
                "phase": "结案",
                "avgDuration": 15,
                "percentage": 0.125
            }
        ]
    },
    "success": true
}
```

## 6. 导出统计报告

### 接口说明
- 接口路径：`POST /api/case/statistics/export`
- 接口说明：导出案件统计分析报告

### 请求参数
```json
{
    "dateRange": [           // 统计时间范围
        "2024-01-01",
        "2024-12-31"
    ],
    "reportType": "1",      // 报告类型（1-概览报告 2-业绩报告 3-客户报告 4-周期报告）
    "lawyerId": 1,          // 律师ID（可选）
    "deptId": 1,            // 部门ID（可选）
    "format": "pdf"         // 导出格式（pdf/excel）
}
```

### 响应结果
文件流 