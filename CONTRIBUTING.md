# 贡献指南

## 开发流程

### 1. 分支管理
- `master`: 主分支，用于生产环境
- `develop`: 开发分支，用于开发环境
- `feature/*`: 功能分支，用于开发新功能
- `hotfix/*`: 修复分支，用于修复生产环境bug
- `release/*`: 发布分支，用于准备发布版本

### 2. 提交规范
提交信息格式：
```
<type>(<scope>): <subject>

<body>

<footer>
```

类型（type）：
- feat: 新功能
- fix: 修复bug
- docs: 文档更新
- style: 代码格式调整
- refactor: 重构代码
- test: 测试相关
- chore: 构建过程或辅助工具的变动

### 3. 代码规范

#### Java代码规范
- 使用阿里巴巴Java开发手册规范
- 代码格式化使用IDE默认格式
- 类、方法、变量命名要有意义
- 必须添加适当的注释

#### API文档规范
- 使用OpenAPI 3.0规范
- 所有接口必须添加文档注解
- 必须包含接口描述、参数说明、响应说明
- 建议添加请求示例和响应示例

示例：
```java
@Operation(summary = "创建客户", description = "创建新的客户信息")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "创建成功",
        content = @Content(schema = @Schema(implementation = ClientResponse.class))),
    @ApiResponse(responseCode = "400", description = "请求参数错误"),
    @ApiResponse(responseCode = "401", description = "未授权"),
    @ApiResponse(responseCode = "403", description = "无权限访问")
})
@PostMapping
@PreAuthorize("hasAuthority('lawyer:client:create')")
public Result<ClientResponse> create(
        @Parameter(description = "客户创建参数", required = true,
            schema = @Schema(implementation = ClientCreateRequest.class))
        @RequestBody @Validated ClientCreateRequest request) {
    return clientService.create(request);
}
```

#### 数据库规范
- 表名使用小写字母，单词间用下划线分隔
- 必须包含id、create_time、update_time、deleted字段
- 字段名使用小写字母，单词间用下划线分隔
- 必须有注释说明

### 4. 开发流程

#### 4.1 获取任务
1. 在Issue中认领任务
2. 从develop分支创建功能分支
```bash
git checkout develop
git pull
git checkout -b feature/xxx
```

#### 4.2 本地开发
1. 编写代码
2. 编写测试
3. 本地测试
4. 提交代码
```bash
git add .
git commit -m "feat(module): add new feature"
```

#### 4.3 代码审查
1. 推送代码到远程仓库
```bash
git push origin feature/xxx
```

2. 创建Pull Request
- 标题：简要描述改动
- 内容：详细描述改动原因和影响
- 关联相关Issue

3. 代码审查
- 等待审查者审查代码
- 根据反馈修改代码
- 所有问题解决后合并代码

### 5. 测试规范

#### 5.1 单元测试
- 使用JUnit 5编写测试
- 测试覆盖率要求80%以上
- 测试方法命名规范：should_ExpectedBehavior_When_StateUnderTest

示例：
```java
@Test
void should_ReturnClient_When_CreateWithValidData() {
    // given
    ClientCreateRequest request = new ClientCreateRequest();
    request.setName("测试客户");
    request.setType(1);

    // when
    ClientResponse response = clientService.create(request);

    // then
    assertNotNull(response);
    assertEquals("测试客户", response.getName());
    assertEquals(1, response.getType());
}
```

#### 5.2 接口测试
- 使用Postman或JUnit进行接口测试
- 测试所有正常和异常场景
- 验证响应状态码和数据格式

### 6. 文档维护

#### 6.1 接口文档
- 实时更新OpenAPI文档
- 添加详细的接口说明
- 提供请求和响应示例

#### 6.2 架构文档
- 更新系统架构图
- 更新数据库设计文档
- 更新部署文档

#### 6.3 使用文档
- 更新用户使用手册
- 更新常见问题解答
- 更新错误码说明

### 7. 发布流程

#### 7.1 准备发布
1. 创建发布分支
```bash
git checkout develop
git checkout -b release/1.0.0
```

2. 更新版本号
3. 更新CHANGELOG.md
4. 执行完整测试

#### 7.2 执行发布
1. 合并到master分支
```bash
git checkout master
git merge release/1.0.0
git tag -a v1.0.0 -m "release 1.0.0"
```

2. 合并回develop分支
```bash
git checkout develop
git merge release/1.0.0
```

3. 删除发布分支
```bash
git branch -d release/1.0.0
```

### 8. 问题反馈
- 在GitHub Issues中提交问题
- 描述问题现象
- 提供复现步骤
- 附上相关日志和截图

## 联系我们
- 邮件：support@lawfirm.com
- 微信群：添加管理员微信 