# 案件文档管理接口

## 1. 获取文档列表

### 接口说明
- 接口路径：`GET /api/case/document/list`
- 接口说明：分页获取案件文档列表

### 请求参数
```json
{
    "pageNum": 1,                 // 当前页码
    "pageSize": 10,              // 每页条数
    "caseId": 1,                 // 案件ID
    "documentName": "",          // 文档名称
    "documentType": "",          // 文档类型（1-起诉状 2-答辩状 3-证据材料 4-法院文书 5-其他文书）
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
                "documentId": 1,          // 文档ID
                "caseId": 1,              // 案件ID
                "documentName": "起诉状.pdf", // 文档名称
                "documentType": "1",       // 文档类型
                "fileSize": 1024,         // 文件大小(KB)
                "fileUrl": "http://example.com/file/xxx.pdf", // 文件URL
                "securityLevel": "1",      // 安全等级（1-公开 2-内部 3-保密）
                "version": "1.0",         // 版本号
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

## 2. 获取文档详情

### 接口说明
- 接口路径：`GET /api/case/document/{documentId}`
- 接口说明：获取文档详细信息

### 请求参数
- documentId：文档ID (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "documentId": 1,
        "caseId": 1,
        "documentName": "起诉状.pdf",
        "documentType": "1",
        "fileSize": 1024,
        "fileUrl": "http://example.com/file/xxx.pdf",
        "securityLevel": "1",
        "version": "1.0",
        "keywords": ["合同纠纷", "违约"], // 关键词
        "description": "原告起诉状",      // 文档描述
        "remark": "",
        "createBy": "admin",
        "createTime": "2024-01-01 00:00:00",
        "updateBy": "admin",
        "updateTime": "2024-01-01 00:00:00"
    },
    "success": true
}
```

## 3. 上传文档

### 接口说明
- 接口路径：`POST /api/case/document/upload`
- 接口说明：上传案件文档

### 请求参数
```json
{
    "caseId": 1,                 // 案件ID
    "documentType": "1",         // 文档类型
    "securityLevel": "1",        // 安全等级
    "keywords": ["合同纠纷", "违约"], // 关键词
    "description": "原告起诉状",    // 文档描述
    "remark": "",                // 备注
    "file": "文件流"              // 文件对象
}
```

### 响应结果
```json
{
    "code": 200,
    "message": "上传成功",
    "data": {
        "documentId": 1,
        "documentName": "起诉状.pdf",
        "fileUrl": "http://example.com/file/xxx.pdf"
    },
    "success": true
}
```

## 4. 更新文档信息

### 接口说明
- 接口路径：`PUT /api/case/document`
- 接口说明：更新文档信息（不包含文件内容）

### 请求参数
```json
{
    "documentId": 1,             // 文档ID
    "documentName": "起诉状.pdf",  // 文档名称
    "documentType": "1",         // 文档类型
    "securityLevel": "1",        // 安全等级
    "keywords": ["合同纠纷", "违约"], // 关键词
    "description": "原告起诉状",    // 文档描述
    "remark": ""                 // 备注
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

## 5. 删除文档

### 接口说明
- 接口路径：`DELETE /api/case/document/{documentIds}`
- 接口说明：删除案件文档(支持批量)

### 请求参数
- documentIds：文档ID，多个以逗号分隔 (路径参数)

### 响应结果
```json
{
    "code": 200,
    "message": "删除成功",
    "success": true
}
```

## 6. 下载文档

### 接口说明
- 接口路径：`GET /api/case/document/download/{documentId}`
- 接口说明：下载案件文档

### 请求参数
- documentId：文档ID (路径参数)

### 响应结果
文件流

## 7. 文档版本管理

### 7.1 上传新版本

#### 接口说明
- 接口路径：`POST /api/case/document/version/{documentId}`
- 接口说明：上传文档新版本

#### 请求参数
```json
{
    "versionDesc": "修改了部分内容", // 版本说明
    "file": "文件流"              // 文件对象
}
```

#### 响应结果
```json
{
    "code": 200,
    "message": "上传成功",
    "data": {
        "version": "2.0",
        "fileUrl": "http://example.com/file/xxx.pdf"
    },
    "success": true
}
```

### 7.2 获取版本历史

#### 接口说明
- 接口路径：`GET /api/case/document/versions/{documentId}`
- 接口说明：获取文档版本历史

#### 请求参数
- documentId：文档ID (路径参数)

#### 响应结果
```json
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "versionId": 1,
            "version": "2.0",
            "fileUrl": "http://example.com/file/xxx.pdf",
            "versionDesc": "修改了部分内容",
            "createBy": "admin",
            "createTime": "2024-01-01 00:00:00"
        },
        {
            "versionId": 2,
            "version": "1.0",
            "fileUrl": "http://example.com/file/xxx.pdf",
            "versionDesc": "初始版本",
            "createBy": "admin",
            "createTime": "2024-01-01 00:00:00"
        }
    ],
    "success": true
}
``` 