# API服务层设计

## 一、目录结构
```
law-firm-api/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/lawfirm/
│       │       ├── config/                    # 配置类
│       │       │   ├── mybatis/               # MyBatis配置
│       │       │   ├── redis/                 # Redis配置
│       │       │   ├── security/              # 安全配置
│       │       │   ├── swagger/               # 接口文档配置
│       │       │   └── web/                   # Web配置
│       │       │
│       │       ├── controller/                # 控制器
│       │       │   ├── system/                # 系统管理
│       │       │   │   ├── auth/             # 认证相关
│       │       │   │   │   ├── LoginController.java
│       │       │   │   │   └── CaptchaController.java
│       │       │   │   ├── user/             # 用户管理
│       │       │   │   ├── role/             # 角色管理
│       │       │   │   ├── menu/             # 菜单管理
│       │       │   │   ├── dept/             # 部门管理
│       │       │   │   ├── dict/             # 字典管理
│       │       │   │   ├── log/              # 日志管理
│       │       │   │   └── notice/           # 通知公告
│       │       │   │
│       │       │   ├── dashboard/            # 仪表盘
│       │       │   │   ├── WorkbenchController.java
│       │       │   │   ├── AnalysisController.java
│       │       │   │   └── MonitorController.java
│       │       │   │
│       │       │   ├── case/                 # 案件管理
│       │       │   │   ├── CaseController.java      # 案件管理
│       │       │   │   ├── CaseTypeController.java  # 案件类型
│       │       │   │   ├── CaseFlowController.java  # 案件流程
│       │       │   │   ├── CaseDocController.java   # 案件文档
│       │       │   │   ├── CasePartyController.java # 当事人管理
│       │       │   │   ├── CaseScheduleController.java # 案件日程
│       │       │   │   ├── CaseEvidenceController.java # 证据管理
│       │       │   │   └── CaseArchiveController.java # 归档管理
│       │       │   │
│       │       │   ├── client/              # 客户管理
│       │       │   │   ├── ClientController.java    # 客户管理
│       │       │   │   ├── ContactController.java   # 联系人管理
│       │       │   │   ├── ClientFollowController.java # 客户跟进
│       │       │   │   ├── ClientGroupController.java  # 客户分组
│       │       │   │   └── ClientAnalysisController.java # 客户分析
│       │       │   │
│       │       │   ├── contract/            # 合同管理
│       │       │   │   ├── ContractController.java  # 合同管理
│       │       │   │   ├── ContractTypeController.java # 合同类型
│       │       │   │   ├── ContractTemplateController.java # 合同模板
│       │       │   │   ├── ContractReviewController.java # 合同审批
│       │       │   │   └── ContractArchiveController.java # 归档管理
│       │       │   │
│       │       │   ├── document/            # 文档管理
│       │       │   │   ├── DocController.java       # 文档管理
│       │       │   │   ├── DocCategoryController.java # 文档分类
│       │       │   │   ├── DocTemplateController.java # 文档模板
│       │       │   │   ├── DocVersionController.java  # 版本管理
│       │       │   │   └── DocShareController.java    # 文档共享
│       │       │   │
│       │       │   ├── finance/             # 财务管理
│       │       │   │   ├── BillingController.java   # 账单管理
│       │       │   │   ├── InvoiceController.java   # 发票管理
│       │       │   │   ├── ExpenseController.java   # 费用管理
│       │       │   │   ├── PaymentController.java   # 收付款管理
│       │       │   │   └── StatementController.java # 财务报表
│       │       │   │
│       │       │   ├── personnel/             # 人事管理
│       │       │   │   ├── PersonnelController.java    # 人事基础信息
│       │       │   │   ├── LawyerController.java       # 律师管理
│       │       │   │   ├── EmployeeController.java     # 员工管理
│       │       │   │   ├── AttendanceController.java   # 考勤管理
│       │       │   │   ├── LeaveController.java        # 请假管理
│       │       │   │   ├── PerformanceController.java  # 绩效管理
│       │       │   │   ├── SalaryController.java       # 薪资管理
│       │       │   │   ├── TrainingController.java     # 培训管理
│       │       │   │   ├── RecruitController.java      # 招聘管理
│       │       │   │   └── ContractController.java     # 劳动合同管理
│       │       │   │
│       │       │   ├── knowledge/          # 知识管理
│       │       │   │   ├── ArticleController.java   # 文章管理
│       │       │   │   ├── CategoryController.java  # 分类管理
│       │       │   │   ├── TagController.java       # 标签管理
│       │       │   │   └── SearchController.java    # 知识检索
│       │       │   │
│       │       │   ├── workflow/           # 工作流管理
│       │       │   │   ├── ProcessController.java   # 流程管理
│       │       │   │   ├── TaskController.java      # 任务管理
│       │       │   │   ├── FormController.java      # 表单管理
│       │       │   │   └── HistoryController.java   # 历史记录
│       │       │   │
│       │       │   ├── message/            # 消息管理
│       │       │   │   ├── MessageController.java   # 消息管理
│       │       │   │   ├── NotifyController.java    # 通知管理
│       │       │   │   └── EmailController.java     # 邮件管理
│       │       │   │
│       │       │   ├── schedule/           # 日程管理
│       │       │   │   ├── ScheduleController.java  # 日程管理
│       │       │   │   ├── CalendarController.java  # 日历管理
│       │       │   │   └── MeetingController.java   # 会议管理
│       │       │   │
│       │       │   ├── conflict/           # 利益冲突管理
│       │       │   │   ├── ConflictController.java  # 冲突管理
│       │       │   │   └── CheckController.java     # 冲突检查
│       │       │   │
│       │       │   ├── seal/              # 印章管理
│       │       │   │   ├── SealController.java      # 印章管理
│       │       │   │   ├── ApplyController.java     # 用印申请
│       │       │   │   └── RecordController.java    # 用印记录
│       │       │   │
│       │       │   ├── asset/             # 资产管理
│       │       │   │   ├── AssetController.java     # 资产管理
│       │       │   │   ├── PurchaseController.java  # 采购管理
│       │       │   │   └── MaintenanceController.java # 维护管理
│       │       │   │
│       │       │   ├── archive/           # 档案管理
│       │       │   │   ├── ArchiveController.java   # 档案管理
│       │       │   │   ├── BorrowController.java    # 借阅管理
│       │       │   │   └── DestroyController.java   # 销毁管理
│       │       │   │
│       │       │   └── common/            # 通用功能
│       │       │       ├── UploadController.java    # 文件上传
│       │       │       └── DictDataController.java  # 字典数据
│       │       │
│       │       ├── model/                   # 数据模型
│       │       │   ├── entity/             # 实体类
│       │       │   ├── dto/                # 数据传输对象
│       │       │   │   ├── request/        # 请求对象
│       │       │   │   └── response/       # 响应对象
│       │       │   └── vo/                 # 视图对象
│       │       │
│       │       ├── service/                # 服务层
│       │       │   ├── system/            # 系统服务
│       │       │   │   ├── auth/
│       │       │   │   ├── user/
│       │       │   │   ├── role/
│       │       │   │   └── menu/
│       │       │   │
│       │       │   ├── case/              # 案件服务
│       │       │   └── client/            # 客户服务
│       │       │
│       │       ├── common/                # 公共组件
│       │       │   ├── annotation/        # 自定义注解
│       │       │   ├── aspect/           # 切面类
│       │       │   ├── constant/         # 常量定义
│       │       │   ├── exception/        # 异常处理
│       │       │   ├── utils/            # 工具类
│       │       │   └── response/         # 响应封装
│       │       │
│       │       └── framework/            # 框架功能
│       │           ├── security/         # 安全框架
│       │           ├── mybatis/          # MyBatis功能
│       │           └── redis/            # Redis功能
│       │
│       └── resources/
│           ├── mapper/                   # MyBatis映射文件
│           ├── application.yml           # 应用配置
│           ├── application-dev.yml       # 开发环境配置
│           └── application-prod.yml      # 生产环境配置
│
└── pom.xml                              # 项目依赖管理
```

## 二、关键实现

### 1. 统一响应格式（对接vben）
```java
@Data
public class Result<T> {
    private Integer code;      // 状态码
    private String message;    // 提示信息
    private T data;           // 数据
    private Boolean success;   // 成功标志
    
    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getMessage());
        result.setData(data);
        result.setSuccess(true);
        return result;
    }
}
```

### 2. 认证授权接口（对接vben）
```java
@RestController
@RequestMapping("/api")
public class AuthController {
    // 登录接口
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        // 返回token和用户基本信息
    }
    
    // 获取用户信息
    @GetMapping("/getUserInfo")
    public Result<UserInfo> getUserInfo() {
        // 返回用户详细信息，包含角色等
    }
    
    // 获取权限编码
    @GetMapping("/getPermCode")
    public Result<List<String>> getPermCode() {
        // 返回权限编码列表
    }
    
    // 获取菜单
    @GetMapping("/getMenuList")
    public Result<List<Menu>> getMenuList() {
        // 返回菜单树形结构
    }
}
```

### 3. 统一分页查询
```java
@Data
public class PageQuery {
    @Min(1)
    private Integer pageNum = 1;     // 当前页码
    @Min(1)
    private Integer pageSize = 10;   // 每页条数
    private String keyword;          // 关键字搜索
    private String sortField;        // 排序字段
    private String sortOrder;        // 排序方式
}

@Data
public class PageResult<T> {
    private List<T> list;           // 数据列表
    private Long total;             // 总记录数
    private Integer pageNum;        // 当前页码
    private Integer pageSize;       // 每页条数
}
```

### 4. 权限注解
```java
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermissions {
    String[] value();    // 权限编码
    Logical logical() default Logical.AND;
}
```

### 5. 异常处理
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        return Result.error(e.getCode(), e.getMessage());
    }
}
```

### 6. Core层功能实现

#### 6.1 消息处理 (core-message)
```java
@Service
public class MessageService {
    // 同步消息发送
    public void sendMessage(Message message) {
        // 处理同步消息
    }
    
    // 异步消息发送
    @Async
    public void sendAsyncMessage(Message message) {
        // 处理异步消息
    }
    
    // 延时消息发送
    public void sendDelayMessage(Message message, long delay) {
        // 处理延时消息
    }
}
```

#### 6.2 存储管理 (core-storage)
```java
@Service
public class StorageService {
    // 文件上传
    public FileInfo upload(MultipartFile file, String path) {
        // 处理文件上传
    }
    
    // 文件下载
    public void download(String fileId, HttpServletResponse response) {
        // 处理文件下载
    }
    
    // 文件管理
    public void manage(String fileId, FileOperation operation) {
        // 处理文件管理操作
    }
}
```

#### 6.3 工作流引擎 (core-workflow)
```java
@Service
public class WorkflowService {
    // 启动流程
    public ProcessInstance startProcess(String processKey, Map<String, Object> variables) {
        // 启动工作流程
    }
    
    // 处理任务
    public void completeTask(String taskId, Map<String, Object> variables) {
        // 处理工作流任务
    }
    
    // 查询流程
    public List<Task> queryTasks(String assignee) {
        // 查询待办任务
    }
}
```

#### 6.4 搜索引擎 (core-search)
```java
@Service
public class SearchService {
    // 索引文档
    public void indexDocument(String index, Document document) {
        // 索引文档
    }
    
    // 搜索文档
    public SearchResult search(String index, SearchQuery query) {
        // 搜索文档
    }
    
    // 聚合分析
    public AggregationResult aggregate(String index, AggregationQuery query) {
        // 聚合分析
    }
}
```

## 三、配置说明

### 1. 应用配置
```yaml
server:
  port: 8080

spring:
  application:
    name: law-firm-api
    
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/law_firm
    username: root
    password: root
    
  redis:
    host: localhost
    port: 6379
```

### 2. 安全配置
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.authorizeHttpRequests()
            .requestMatchers("/api/login", "/api/captcha").permitAll()
            .anyRequest().authenticated()
            .and()
            .csrf().disable()
            .cors();
        return http.build();
    }
}
```

## 四、开发规范

1. **命名规范**
   - Controller类名以Controller结尾
   - Service类名以Service结尾
   - 实体类直接使用名词
   - 方法名使用动词开头

2. **接口规范**
   - 统一使用RESTful风格
   - 请求响应格式统一
   - 接口版本控制
   - 统一的错误码

3. **注释规范**
   - 类注释：说明类的用途
   - 方法注释：说明参数和返回值
   - 关键代码注释
   - 统一的注释格式

4. **安全规范**
   - 统一的认证机制
   - 细粒度的权限控制
   - 数据脱敏处理
   - 安全审计日志

## 五、注意事项

1. **性能优化**
   - 合理使用缓存
   - 避免大事务
   - 分页查询优化
   - 批量操作优化

2. **安全措施**
   - XSS防护
   - SQL注入防护
   - CSRF防护
   - 敏感数据加密

3. **日志记录**
   - 操作日志
   - 异常日志
   - 性能日志
   - 安全日志
