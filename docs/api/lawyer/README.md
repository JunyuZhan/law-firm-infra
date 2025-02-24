# 律师端API文档

## 接口规范

### 1. 基础规范
- 基础路径：`/api/lawyer`
- 请求方法：GET、POST、PUT、DELETE
- 请求格式：application/json
- 响应格式：application/json

### 2. 请求头
```http
Authorization: Bearer {token}
Content-Type: application/json
Accept: application/json
X-Tenant-Id: {tenantId}
```

### 3. 响应格式
```json
{
    "code": 200,
    "message": "success",
    "data": {
        // 响应数据
    }
}
```

## 认证接口

### 1. 律师登录
```http
POST /api/lawyer/auth/login

Request:
{
    "username": "lawyer001",
    "password": "123456",
    "captcha": "1234",
    "captchaKey": "abcd"
}

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
        "userInfo": {
            "id": 1001,
            "username": "lawyer001",
            "realName": "张三",
            "licenseNumber": "13101200001",
            "title": "高级律师",
            "department": "民商事部",
            "specialties": ["公司法", "合同法"]
        }
    }
}
```

### 2. 获取个人信息
```http
GET /api/lawyer/auth/info

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "id": 1001,
        "username": "lawyer001",
        "realName": "张三",
        "licenseNumber": "13101200001",
        "title": "高级律师",
        "department": "民商事部",
        "specialties": ["公司法", "合同法"],
        "email": "zhangsan@lawfirm.com",
        "mobile": "13800138000",
        "avatar": "http://example.com/avatar/1001.jpg"
    }
}
```

## 案件管理接口

### 1. 案件列表查询
```http
GET /api/lawyer/case/list
?pageNum=1
&pageSize=10
&status=PROCESSING
&type=CIVIL
&keyword=合同纠纷

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "pageNum": 1,
        "pageSize": 10,
        "total": 100,
        "list": [
            {
                "id": 2001,
                "caseNumber": "CASE202402001",
                "caseName": "张某与李某合同纠纷案",
                "caseType": "CIVIL",
                "status": "PROCESSING",
                "priority": "HIGH",
                "client": {
                    "id": 3001,
                    "name": "张某"
                },
                "filingDate": "2024-02-01",
                "nextHearing": "2024-03-01",
                "updateTime": "2024-02-09 10:00:00"
            }
        ]
    }
}
```

### 2. 案件详情查询
```http
GET /api/lawyer/case/{id}

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "id": 2001,
        "caseNumber": "CASE202402001",
        "caseName": "张某与李某合同纠纷案",
        "caseType": "CIVIL",
        "status": "PROCESSING",
        "priority": "HIGH",
        "description": "关于某公司合同履行纠纷...",
        "client": {
            "id": 3001,
            "name": "张某",
            "type": "PERSONAL",
            "contact": "13900139000"
        },
        "oppositeParty": {
            "name": "李某",
            "contact": "13800138000"
        },
        "lawyers": [
            {
                "id": 1001,
                "name": "张三",
                "role": "LEAD"
            }
        ],
        "filingDate": "2024-02-01",
        "nextHearing": "2024-03-01",
        "court": {
            "name": "北京市第一中级人民法院",
            "department": "民事审判第一庭"
        },
        "documents": [
            {
                "id": 4001,
                "name": "起诉状.pdf",
                "type": "COMPLAINT",
                "uploadTime": "2024-02-01 10:00:00"
            }
        ],
        "schedules": [
            {
                "id": 5001,
                "type": "HEARING",
                "title": "开庭",
                "startTime": "2024-03-01 09:00:00",
                "location": "第三法庭"
            }
        ]
    }
}
```

### 3. 案件进展更新
```http
POST /api/lawyer/case/{id}/progress

Request:
{
    "stage": "COURT_HEARING",
    "content": "完成第一次开庭",
    "result": "法院要求双方补充证据",
    "nextPlan": "准备补充证据材料",
    "attachments": [
        {
            "name": "庭审笔录.pdf",
            "url": "http://example.com/file/1.pdf"
        }
    ]
}

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "id": 6001,
        "caseId": 2001,
        "stage": "COURT_HEARING",
        "content": "完成第一次开庭",
        "updateTime": "2024-02-09 10:00:00"
    }
}
```

## 客户管理接口

### 1. 客户列表查询
```http
GET /api/lawyer/client/list
?pageNum=1
&pageSize=10
&type=PERSONAL
&keyword=张某

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "pageNum": 1,
        "pageSize": 10,
        "total": 100,
        "list": [
            {
                "id": 3001,
                "clientNumber": "CLT202402001",
                "clientName": "张某",
                "clientType": "PERSONAL",
                "status": "ACTIVE",
                "contactName": "张某",
                "contactPhone": "13900139000",
                "createTime": "2024-02-01 10:00:00"
            }
        ]
    }
}
```

### 2. 客户详情查询
```http
GET /api/lawyer/client/{id}

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "id": 3001,
        "clientNumber": "CLT202402001",
        "clientName": "张某",
        "clientType": "PERSONAL",
        "status": "ACTIVE",
        "idCard": "110101199001011234",
        "contactName": "张某",
        "contactPhone": "13900139000",
        "email": "zhangmou@example.com",
        "address": "北京市朝阳区",
        "cases": [
            {
                "id": 2001,
                "caseNumber": "CASE202402001",
                "caseName": "张某与李某合同纠纷案",
                "status": "PROCESSING"
            }
        ],
        "contracts": [
            {
                "id": 7001,
                "contractNumber": "CTR202402001",
                "contractName": "法律服务合同",
                "status": "ACTIVE"
            }
        ],
        "followRecords": [
            {
                "id": 8001,
                "type": "VISIT",
                "content": "客户回访",
                "followTime": "2024-02-08 14:00:00",
                "follower": "张三"
            }
        ]
    }
}
```

## 日程管理接口

### 1. 日程列表查询
```http
GET /api/lawyer/schedule/list
?startDate=2024-02-01
&endDate=2024-02-29
&type=HEARING

Response:
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "id": 5001,
            "type": "HEARING",
            "title": "张某案件开庭",
            "startTime": "2024-02-15 09:00:00",
            "endTime": "2024-02-15 12:00:00",
            "location": "北京市第一中级人民法院第三法庭",
            "relatedCase": {
                "id": 2001,
                "caseNumber": "CASE202402001",
                "caseName": "张某与李某合同纠纷案"
            }
        }
    ]
}
```

### 2. 日程创建
```http
POST /api/lawyer/schedule

Request:
{
    "type": "MEETING",
    "title": "客户会谈",
    "startTime": "2024-02-10 14:00:00",
    "endTime": "2024-02-10 16:00:00",
    "location": "会议室A",
    "participants": [1001, 1002],
    "relatedCase": 2001,
    "description": "讨论案件进展",
    "reminder": {
        "type": "BEFORE_15_MINUTES",
        "channels": ["SYSTEM", "EMAIL"]
    }
}

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "id": 5002,
        "type": "MEETING",
        "title": "客户会谈",
        "startTime": "2024-02-10 14:00:00"
    }
}
```

## 统计分析接口

### 1. 案件统计
```http
GET /api/lawyer/statistics/case
?startDate=2024-01-01
&endDate=2024-01-31

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "total": 50,
        "byType": {
            "CIVIL": 30,
            "CRIMINAL": 10,
            "ADMINISTRATIVE": 10
        },
        "byStatus": {
            "PENDING": 10,
            "PROCESSING": 30,
            "COMPLETED": 10
        },
        "trend": [
            {
                "date": "2024-01-01",
                "count": 2
            }
        ]
    }
}
```

## 错误码说明

| 错误码 | 说明 | 处理建议 |
|--------|------|----------|
| 200 | 成功 | - |
| 400 | 请求参数错误 | 检查请求参数 |
| 401 | 未认证 | 重新登录 |
| 403 | 无权限 | 申请权限 |
| 404 | 资源不存在 | 检查请求资源 |
| 500 | 服务器错误 | 联系管理员 | 