# 工作流引擎API文档

## 1. 流程模板管理API

### 1.1 部署流程模板
```http
POST /api/workflow/templates
Content-Type: multipart/form-data

请求参数:
- file: BPMN流程定义文件(必填)
- name: 模板名称(必填)
- key: 模板标识(必填)
- category: 模板分类(可选)

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "id": "template_001",        // 模板ID
        "key": "contract_approve",   // 模板标识
        "name": "合同审批流程",      // 模板名称
        "category": "contract",      // 模板分类
        "version": 1,               // 版本号
        "createTime": "2024-03-04 10:00:00"  // 创建时间
    }
}
```

### 1.2 更新流程模板
```http
PUT /api/workflow/templates/{id}
Content-Type: multipart/form-data

请求参数:
- file: BPMN流程定义文件(必填)
- name: 模板名称(可选)
- category: 模板分类(可选)

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "id": "template_001",
        "version": 2,               // 版本号自动加1
        "updateTime": "2024-03-04 11:00:00"
    }
}
```

### 1.3 获取流程模板
```http
GET /api/workflow/templates/{id}

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "id": "template_001",
        "key": "contract_approve",
        "name": "合同审批流程",
        "category": "contract",
        "version": 2,
        "createTime": "2024-03-04 10:00:00",
        "updateTime": "2024-03-04 11:00:00",
        "bpmnXml": "<?xml version=\"1.0\" ...>"  // BPMN XML内容
    }
}
```

### 1.4 查询流程模板列表
```http
GET /api/workflow/templates
请求参数:
- key: 模板标识(可选)
- name: 模板名称(可选)
- category: 模板分类(可选)
- current: 当前页(可选，默认1)
- size: 每页条数(可选，默认10)

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "total": 100,
        "records": [{
            "id": "template_001",
            "key": "contract_approve",
            "name": "合同审批流程",
            "category": "contract",
            "version": 2,
            "createTime": "2024-03-04 10:00:00"
        }]
    }
}
```

## 2. 流程实例管理API

### 2.1 启动流程实例
```http
POST /api/workflow/processes
请求参数:
{
    "templateKey": "contract_approve",  // 流程模板标识(必填)
    "businessKey": "CONTRACT_001",      // 业务标识(必填)
    "variables": {                      // 流程变量(可选)
        "contractId": "001",
        "amount": 10000,
        "department": "法务部"
    },
    "startUserId": "user_001"          // 发起人ID(必填)
}

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "processInstanceId": "process_001",  // 流程实例ID
        "templateKey": "contract_approve",   // 流程模板标识
        "businessKey": "CONTRACT_001",       // 业务标识
        "startTime": "2024-03-04 14:00:00", // 启动时间
        "startUserId": "user_001"           // 发起人ID
    }
}
```

### 2.2 查询流程实例
```http
GET /api/workflow/processes/{processInstanceId}

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "processInstanceId": "process_001",
        "templateKey": "contract_approve",
        "businessKey": "CONTRACT_001",
        "status": "RUNNING",              // 流程状态
        "startTime": "2024-03-04 14:00:00",
        "endTime": null,                  // 结束时间
        "duration": 3600,                 // 持续时间(秒)
        "startUserId": "user_001",
        "currentTasks": [{                // 当前任务节点
            "taskId": "task_001",
            "taskKey": "manager_approve",
            "taskName": "经理审批",
            "assignee": "manager_001",
            "createTime": "2024-03-04 14:00:00"
        }]
    }
}
```

### 2.3 终止流程实例
```http
DELETE /api/workflow/processes/{processInstanceId}
请求参数:
- reason: 终止原因(可选)

响应结果:
{
    "code": 200,
    "message": "success"
}
```

## 3. 流程任务API

### 3.1 查询待办任务
```http
GET /api/workflow/tasks/todo
请求参数:
- assignee: 处理人ID(必填)
- processKey: 流程标识(可选)
- current: 当前页(可选，默认1)
- size: 每页条数(可选，默认10)

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "total": 50,
        "records": [{
            "taskId": "task_001",
            "taskName": "经理审批",
            "processInstanceId": "process_001",
            "businessKey": "CONTRACT_001",
            "createTime": "2024-03-04 14:00:00",
            "dueDate": "2024-03-05 14:00:00",  // 截止时间
            "variables": {                      // 任务变量
                "contractId": "001",
                "amount": 10000
            }
        }]
    }
}
```

### 3.2 完成任务
```http
POST /api/workflow/tasks/{taskId}/complete
请求参数:
{
    "approved": true,           // 审批结果
    "comment": "同意",          // 处理意见
    "variables": {              // 任务变量
        "approveTime": "2024-03-04 15:00:00"
    }
}

响应结果:
{
    "code": 200,
    "message": "success"
}
```

### 3.3 转办任务
```http
POST /api/workflow/tasks/{taskId}/transfer
请求参数:
{
    "targetUserId": "manager_002",  // 目标用户ID
    "reason": "出差转办"            // 转办原因
}

响应结果:
{
    "code": 200,
    "message": "success"
}
```

### 3.4 委派任务
```http
POST /api/workflow/tasks/{taskId}/delegate
请求参数:
{
    "targetUserId": "manager_002",  // 目标用户ID
    "reason": "临时委派"            // 委派原因
}

响应结果:
{
    "code": 200,
    "message": "success"
}
```

## 4. 流程历史API

### 4.1 查询流程历史
```http
GET /api/workflow/history/process/{processInstanceId}

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "processInstanceId": "process_001",
        "templateKey": "contract_approve",
        "businessKey": "CONTRACT_001",
        "startTime": "2024-03-04 14:00:00",
        "endTime": "2024-03-04 16:00:00",
        "duration": 7200,
        "status": "COMPLETED",
        "variables": {
            "contractId": "001",
            "amount": 10000
        },
        "tasks": [{                        // 任务历史
            "taskId": "task_001",
            "taskName": "经理审批",
            "assignee": "manager_001",
            "startTime": "2024-03-04 14:00:00",
            "endTime": "2024-03-04 15:00:00",
            "result": "approved",
            "comment": "同意"
        }]
    }
}
```

### 4.2 查询已办任务
```http
GET /api/workflow/history/tasks
请求参数:
- userId: 用户ID(必填)
- processKey: 流程标识(可选)
- startTime: 开始时间(可选)
- endTime: 结束时间(可选)
- current: 当前页(可选，默认1)
- size: 每页条数(可选，默认10)

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "total": 100,
        "records": [{
            "taskId": "task_001",
            "taskName": "经理审批",
            "processInstanceId": "process_001",
            "businessKey": "CONTRACT_001",
            "startTime": "2024-03-04 14:00:00",
            "endTime": "2024-03-04 15:00:00",
            "result": "approved",
            "comment": "同意"
        }]
    }
}
```

## 5. 流程监控API

### 5.1 查询运行中的流程
```http
GET /api/workflow/monitor/running
请求参数:
- templateKey: 流程模板标识(可选)
- startTimeBegin: 启动时间起始(可选)
- startTimeEnd: 启动时间结束(可选)
- current: 当前页(可选，默认1)
- size: 每页条数(可选，默认10)

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "total": 50,
        "records": [{
            "processInstanceId": "process_001",
            "templateKey": "contract_approve",
            "businessKey": "CONTRACT_001",
            "startTime": "2024-03-04 14:00:00",
            "duration": 3600,
            "currentTask": "经理审批"
        }]
    }
}
```

### 5.2 查询流程统计信息
```http
GET /api/workflow/monitor/statistics
请求参数:
- templateKey: 流程模板标识(必填)
- timeRange: 统计周期(可选，默认last7days)

响应结果:
{
    "code": 200,
    "message": "success",
    "data": {
        "total": 100,           // 总流程数
        "running": 30,          // 运行中
        "completed": 65,        // 已完成
        "terminated": 5,        // 已终止
        "averageDuration": 7200,// 平均耗时(秒)
        "taskStatistics": [{    // 任务节点统计
            "taskKey": "manager_approve",
            "taskName": "经理审批",
            "count": 100,
            "averageDuration": 3600,
            "completedCount": 80,
            "pendingCount": 20
        }]
    }
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