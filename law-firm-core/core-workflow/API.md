# 工作流模块API文档

## 流程定义管理API

### 部署流程定义
```http
POST /api/workflow/process-definitions/deploy
Content-Type: multipart/form-data

请求参数:
- file: 流程定义文件(必填)
- name: 部署名称(可选)
- category: 流程分类(可选)
- tenantId: 租户ID(可选)

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "id": "1001",                    // 流程定义ID
        "key": "leave-process",          // 流程定义key
        "name": "请假流程",              // 流程定义名称
        "version": 1,                    // 版本号
        "deploymentId": "2001",          // 部署ID
        "resourceName": "leave.bpmn20.xml", // 资源名称
        "tenantId": "default"            // 租户ID
    }
}
```

### 删除流程定义
```http
DELETE /api/workflow/process-definitions/{deploymentId}

请求参数:
- deploymentId: 部署ID(路径参数,必填)
- cascade: 是否级联删除(查询参数,可选,默认false)

响应结果:
{
    "code": 200,
    "message": "success"
}
```

### 获取流程定义
```http
GET /api/workflow/process-definitions/{processDefinitionId}

请求参数:
- processDefinitionId: 流程定义ID(路径参数,必填)

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "id": "1001",
        "key": "leave-process",
        "name": "请假流程",
        "version": 1,
        "deploymentId": "2001",
        "resourceName": "leave.bpmn20.xml",
        "diagramResourceName": "leave.leave-process.png",
        "description": "员工请假流程",
        "suspended": false,
        "tenantId": "default"
    }
}
```

### 查询流程定义列表
```http
GET /api/workflow/process-definitions

请求参数:
- key: 流程定义key(查询参数,可选)
- name: 流程定义名称(查询参数,可选)
- category: 流程分类(查询参数,可选)
- tenantId: 租户ID(查询参数,可选)
- latestVersion: 是否只查询最新版本(查询参数,可选,默认true)
- pageNum: 页码(查询参数,可选,默认1)
- pageSize: 每页大小(查询参数,可选,默认10)

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "total": 100,
        "list": [
            {
                "id": "1001",
                "key": "leave-process",
                "name": "请假流程",
                "version": 1,
                "deploymentId": "2001",
                "resourceName": "leave.bpmn20.xml",
                "tenantId": "default"
            }
        ]
    }
}
```

## 流程实例管理API

### 启动流程实例
```http
POST /api/workflow/process-instances/start

请求参数:
{
    "processDefinitionId": "1001",      // 流程定义ID(必填)
    "businessKey": "LEAVE-2024-001",    // 业务key(可选)
    "variables": {                      // 流程变量(可选)
        "days": 3,
        "reason": "年假"
    },
    "startUserId": "zhangsan"          // 启动用户(可选)
}

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "id": "3001",                   // 流程实例ID
        "processDefinitionId": "1001",  // 流程定义ID
        "businessKey": "LEAVE-2024-001", // 业务key
        "startTime": "2024-01-20 10:00:00", // 启动时间
        "startUserId": "zhangsan"      // 启动用户
    }
}
```

### 挂起流程实例
```http
PUT /api/workflow/process-instances/{processInstanceId}/suspend

请求参数:
- processInstanceId: 流程实例ID(路径参数,必填)

响应结果:
{
    "code": 200,
    "message": "success"
}
```

### 激活流程实例
```http
PUT /api/workflow/process-instances/{processInstanceId}/activate

请求参数:
- processInstanceId: 流程实例ID(路径参数,必填)

响应结果:
{
    "code": 200,
    "message": "success"
}
```

### 终止流程实例
```http
DELETE /api/workflow/process-instances/{processInstanceId}

请求参数:
- processInstanceId: 流程实例ID(路径参数,必填)
- deleteReason: 删除原因(查询参数,可选)

响应结果:
{
    "code": 200,
    "message": "success"
}
```

## 任务管理API

### 获取任务
```http
GET /api/workflow/tasks/{taskId}

请求参数:
- taskId: 任务ID(路径参数,必填)

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "id": "3001",                    // 任务ID
        "name": "经理审批",              // 任务名称
        "description": "请审批请假申请", // 任务描述
        "processInstanceId": "4001",     // 流程实例ID
        "processDefinitionId": "1001",   // 流程定义ID
        "taskDefinitionKey": "approve",  // 任务定义键
        "assignee": "manager1",          // 办理人
        "owner": "admin",                // 所有者
        "delegationState": "PENDING",    // 委托状态
        "dueDate": "2024-02-20T10:00:00", // 到期时间
        "createTime": "2024-02-19T10:00:00", // 创建时间
        "tenantId": "default",           // 租户ID
        "formKey": "leave-approve-form", // 表单标识
        "priority": 50,                  // 优先级
        "category": "approval"           // 分类
    }
}
```

### 查询任务列表
```http
GET /api/workflow/tasks

请求参数:
- processInstanceId: 流程实例ID(查询参数,可选)
- taskDefinitionKey: 任务定义键(查询参数,可选)
- assignee: 办理人(查询参数,可选)
- owner: 所有者(查询参数,可选)
- tenantId: 租户ID(查询参数,可选)

响应结果:
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "id": "3001",
            "name": "经理审批",
            "description": "请审批请假申请",
            "processInstanceId": "4001",
            "processDefinitionId": "1001",
            "taskDefinitionKey": "approve",
            "assignee": "manager1",
            "owner": "admin",
            "delegationState": "PENDING",
            "dueDate": "2024-02-20T10:00:00",
            "createTime": "2024-02-19T10:00:00",
            "tenantId": "default",
            "formKey": "leave-approve-form",
            "priority": 50,
            "category": "approval"
        }
    ]
}
```

### 认领任务
```http
POST /api/workflow/tasks/{taskId}/claim

请求参数:
- taskId: 任务ID(路径参数,必填)
- userId: 用户ID(查询参数,必填)

响应结果:
{
    "code": 200,
    "message": "success"
}
```

### 取消认领任务
```http
POST /api/workflow/tasks/{taskId}/unclaim

请求参数:
- taskId: 任务ID(路径参数,必填)

响应结果:
{
    "code": 200,
    "message": "success"
}
```

### 完成任务
```http
POST /api/workflow/tasks/{taskId}/complete

请求参数:
- taskId: 任务ID(路径参数,必填)
- variables: 流程变量(请求体,可选)

请求体示例:
{
    "approved": true,
    "comment": "同意请假申请"
}

响应结果:
{
    "code": 200,
    "message": "success"
}
```

### 委托任务
```http
POST /api/workflow/tasks/{taskId}/delegate

请求参数:
- taskId: 任务ID(路径参数,必填)
- userId: 用户ID(查询参数,必填)

响应结果:
{
    "code": 200,
    "message": "success"
}
```

### 转办任务
```http
POST /api/workflow/tasks/{taskId}/transfer

请求参数:
- taskId: 任务ID(路径参数,必填)
- userId: 用户ID(查询参数,必填)

响应结果:
{
    "code": 200,
    "message": "success"
}
```

### 设置任务处理人
```http
POST /api/workflow/tasks/{taskId}/assignee

请求参数:
- taskId: 任务ID(路径参数,必填)
- userId: 用户ID(查询参数,必填)

响应结果:
{
    "code": 200,
    "message": "success"
}
```

### 添加任务候选人
```http
POST /api/workflow/tasks/{taskId}/candidate-users

请求参数:
- taskId: 任务ID(路径参数,必填)
- userId: 用户ID(查询参数,必填)

响应结果:
{
    "code": 200,
    "message": "success"
}
```

### 删除任务候选人
```http
DELETE /api/workflow/tasks/{taskId}/candidate-users/{userId}

请求参数:
- taskId: 任务ID(路径参数,必填)
- userId: 用户ID(路径参数,必填)

响应结果:
{
    "code": 200,
    "message": "success"
}
```

### 添加任务候选组
```http
POST /api/workflow/tasks/{taskId}/candidate-groups

请求参数:
- taskId: 任务ID(路径参数,必填)
- groupId: 组ID(查询参数,必填)

响应结果:
{
    "code": 200,
    "message": "success"
}
```

### 删除任务候选组
```http
DELETE /api/workflow/tasks/{taskId}/candidate-groups/{groupId}

请求参数:
- taskId: 任务ID(路径参数,必填)
- groupId: 组ID(路径参数,必填)

响应结果:
{
    "code": 200,
    "message": "success"
}
```

### 获取历史任务
```http
GET /api/workflow/tasks/history/{taskId}

请求参数:
- taskId: 任务ID(路径参数,必填)

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "id": "3001",                    // 任务ID
        "name": "经理审批",              // 任务名称
        "description": "请审批请假申请", // 任务描述
        "processInstanceId": "4001",     // 流程实例ID
        "processDefinitionId": "1001",   // 流程定义ID
        "taskDefinitionKey": "approve",  // 任务定义键
        "assignee": "manager1",          // 办理人
        "owner": "admin",                // 所有者
        "startTime": "2024-02-19T10:00:00", // 开始时间
        "endTime": "2024-02-19T11:00:00",   // 结束时间
        "durationInMillis": 3600000,     // 持续时间(毫秒)
        "deleteReason": "completed",     // 删除原因
        "tenantId": "default"           // 租户ID
    }
}
```

### 查询历史任务列表
```http
GET /api/workflow/tasks/history

请求参数:
- processInstanceId: 流程实例ID(查询参数,可选)
- taskDefinitionKey: 任务定义键(查询参数,可选)
- assignee: 办理人(查询参数,可选)
- owner: 所有者(查询参数,可选)
- tenantId: 租户ID(查询参数,可选)
- finished: 是否已完成(查询参数,可选,默认false)

响应结果:
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "id": "3001",
            "name": "经理审批",
            "description": "请审批请假申请",
            "processInstanceId": "4001",
            "processDefinitionId": "1001",
            "taskDefinitionKey": "approve",
            "assignee": "manager1",
            "owner": "admin",
            "startTime": "2024-02-19T10:00:00",
            "endTime": "2024-02-19T11:00:00",
            "durationInMillis": 3600000,
            "deleteReason": "completed",
            "tenantId": "default"
        }
    ]
}
```

## 表单管理API

### 创建表单
```http
POST /api/workflow/forms

请求参数:
{
    "key": "leave-form",          // 表单key(必填)
    "name": "请假申请表",         // 表单名称(必填)
    "category": "leave",          // 表单分类(可选)
    "content": {                  // 表单内容(必填)
        "fields": [
            {
                "id": "days",
                "type": "number",
                "label": "请假天数",
                "required": true
            },
            {
                "id": "reason",
                "type": "text",
                "label": "请假原因",
                "required": true
            }
        ]
    },
    "tenantId": "default"        // 租户ID(可选)
}

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "id": "5001",            // 表单ID
        "key": "leave-form",     // 表单key
        "name": "请假申请表",    // 表单名称
        "category": "leave",     // 表单分类
        "tenantId": "default"   // 租户ID
    }
}
```

### 获取表单
```http
GET /api/workflow/forms/{formId}

请求参数:
- formId: 表单ID(路径参数,必填)

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "id": "5001",
        "key": "leave-form",
        "name": "请假申请表",
        "category": "leave",
        "content": {
            "fields": [
                {
                    "id": "days",
                    "type": "number",
                    "label": "请假天数",
                    "required": true
                },
                {
                    "id": "reason",
                    "type": "text",
                    "label": "请假原因",
                    "required": true
                }
            ]
        },
        "tenantId": "default"
    }
}
```

### 提交表单
```http
POST /api/workflow/forms/{formId}/submit

请求参数:
- formId: 表单ID(路径参数,必填)

请求体:
{
    "taskId": "4001",           // 任务ID(必填)
    "variables": {              // 表单变量(必填)
        "days": 3,
        "reason": "年假"
    }
}

响应结果:
{
    "code": 200,
    "message": "success"
}
```

## 错误码说明

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未授权 |
| 403 | 禁止访问 |
| 404 | 资源不存在 |
| 500 | 系统内部错误 |

## 注意事项

1. 所有接口都需要在请求头中携带token进行认证
2. 日期时间格式统一使用"yyyy-MM-dd HH:mm:ss"
3. 分页查询参数pageNum从1开始
4. 文件上传大小限制为10MB
5. 接口调用频率限制为100次/分钟 