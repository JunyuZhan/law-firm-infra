# API调用示例

## 客户管理接口

### 1. 创建客户

#### 请求示例
```http
POST /staff/lawyer/client HTTP/1.1
Host: api.lawfirm.com
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
    "name": "张三",
    "type": 1,
    "contact": "李四",
    "phone": "13800138000",
    "email": "zhangsan@example.com",
    "address": "北京市朝阳区xxx街xxx号",
    "remark": "重要客户"
}
```

#### 响应示例
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "id": 1,
        "name": "张三",
        "type": 1,
        "typeName": "个人客户",
        "contact": "李四",
        "phone": "13800138000",
        "email": "zhangsan@example.com",
        "address": "北京市朝阳区xxx街xxx号",
        "status": 1,
        "statusName": "正常",
        "remark": "重要客户",
        "createTime": "2024-02-06 10:00:00",
        "updateTime": "2024-02-06 10:00:00"
    }
}
```

### 2. 分页查询客户

#### 请求示例
```http
GET /staff/lawyer/client/page?pageNum=1&pageSize=10&type=1&keyword=张三 HTTP/1.1
Host: api.lawfirm.com
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### 响应示例
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "total": 1,
        "list": [
            {
                "id": 1,
                "name": "张三",
                "type": 1,
                "typeName": "个人客户",
                "contact": "李四",
                "phone": "13800138000",
                "email": "zhangsan@example.com",
                "address": "北京市朝阳区xxx街xxx号",
                "status": 1,
                "statusName": "正常",
                "remark": "重要客户",
                "createTime": "2024-02-06 10:00:00",
                "updateTime": "2024-02-06 10:00:00"
            }
        ],
        "pageNum": 1,
        "pageSize": 10,
        "pages": 1
    }
}
```

### 3. 更新客户

#### 请求示例
```http
PUT /staff/lawyer/client/1 HTTP/1.1
Host: api.lawfirm.com
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
    "name": "张三",
    "type": 1,
    "contact": "李四",
    "phone": "13800138000",
    "email": "zhangsan@example.com",
    "address": "北京市朝阳区xxx街xxx号",
    "remark": "重要客户，已签约"
}
```

#### 响应示例
```json
{
    "code": 200,
    "message": "success"
}
```

### 4. 删除客户

#### 请求示例
```http
DELETE /staff/lawyer/client/1 HTTP/1.1
Host: api.lawfirm.com
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### 响应示例
```json
{
    "code": 200,
    "message": "success"
}
```

### 5. 获取客户详情

#### 请求示例
```http
GET /staff/lawyer/client/1 HTTP/1.1
Host: api.lawfirm.com
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### 响应示例
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "id": 1,
        "name": "张三",
        "type": 1,
        "typeName": "个人客户",
        "contact": "李四",
        "phone": "13800138000",
        "email": "zhangsan@example.com",
        "address": "北京市朝阳区xxx街xxx号",
        "status": 1,
        "statusName": "正常",
        "remark": "重要客户",
        "createTime": "2024-02-06 10:00:00",
        "updateTime": "2024-02-06 10:00:00"
    }
}
```

### 6. 冲突检查

#### 请求示例
```http
POST /staff/lawyer/client/conflict-check HTTP/1.1
Host: api.lawfirm.com
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
    "name": "张三",
    "type": 1,
    "contact": "李四",
    "phone": "13800138000",
    "email": "zhangsan@example.com"
}
```

#### 响应示例
```json
{
    "code": 200,
    "message": "success",
    "data": false
}
```

## 调用示例代码

### Java
```java
// 创建客户
public ClientResponse createClient() {
    String url = "http://api.lawfirm.com/staff/lawyer/client";
    
    ClientCreateRequest request = new ClientCreateRequest();
    request.setName("张三");
    request.setType(1);
    request.setContact("李四");
    request.setPhone("13800138000");
    request.setEmail("zhangsan@example.com");
    request.setAddress("北京市朝阳区xxx街xxx号");
    request.setRemark("重要客户");
    
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(getToken());
    headers.setContentType(MediaType.APPLICATION_JSON);
    
    HttpEntity<ClientCreateRequest> entity = new HttpEntity<>(request, headers);
    
    ResponseEntity<Result<ClientResponse>> response = restTemplate.exchange(
        url,
        HttpMethod.POST,
        entity,
        new ParameterizedTypeReference<Result<ClientResponse>>() {}
    );
    
    return response.getBody().getData();
}

// 分页查询客户
public PageResult<ClientResponse> queryClients() {
    String url = "http://api.lawfirm.com/staff/lawyer/client/page?pageNum={pageNum}&pageSize={pageSize}&type={type}&keyword={keyword}";
    
    Map<String, Object> params = new HashMap<>();
    params.put("pageNum", 1);
    params.put("pageSize", 10);
    params.put("type", 1);
    params.put("keyword", "张三");
    
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(getToken());
    
    HttpEntity<?> entity = new HttpEntity<>(headers);
    
    ResponseEntity<Result<PageResult<ClientResponse>>> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        entity,
        new ParameterizedTypeReference<Result<PageResult<ClientResponse>>>() {},
        params
    );
    
    return response.getBody().getData();
}
```

### Python
```python
import requests

# 创建客户
def create_client():
    url = "http://api.lawfirm.com/staff/lawyer/client"
    
    headers = {
        "Authorization": f"Bearer {get_token()}",
        "Content-Type": "application/json"
    }
    
    data = {
        "name": "张三",
        "type": 1,
        "contact": "李四",
        "phone": "13800138000",
        "email": "zhangsan@example.com",
        "address": "北京市朝阳区xxx街xxx号",
        "remark": "重要客户"
    }
    
    response = requests.post(url, headers=headers, json=data)
    return response.json()

# 分页查询客户
def query_clients():
    url = "http://api.lawfirm.com/staff/lawyer/client/page"
    
    headers = {
        "Authorization": f"Bearer {get_token()}"
    }
    
    params = {
        "pageNum": 1,
        "pageSize": 10,
        "type": 1,
        "keyword": "张三"
    }
    
    response = requests.get(url, headers=headers, params=params)
    return response.json()
```

### cURL
```bash
# 创建客户
curl -X POST "http://api.lawfirm.com/staff/lawyer/client" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "name": "张三",
    "type": 1,
    "contact": "李四",
    "phone": "13800138000",
    "email": "zhangsan@example.com",
    "address": "北京市朝阳区xxx街xxx号",
    "remark": "重要客户"
}'

# 分页查询客户
curl -X GET "http://api.lawfirm.com/staff/lawyer/client/page?pageNum=1&pageSize=10&type=1&keyword=张三" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
``` 