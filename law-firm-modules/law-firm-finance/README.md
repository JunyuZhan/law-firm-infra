# 律所财务管理模块（law-firm-finance）

## 1. 模块说明
`law-firm-finance`模块负责律所的财务管理功能实现，基于 finance-model 模块定义的数据模型和接口进行开发。本模块主要实现以下功能：
- 账户管理（AccountService）
- 交易管理（TransactionService）
- 发票管理（InvoiceService）
- 预算管理（BudgetService）
- 费用管理（FeeService）
- 收入管理（IncomeService）
- 支出管理（ExpenseService）
- 应收账款管理（ReceivableService）
- 付款计划管理（PaymentPlanService）
- 账单记录管理（BillingRecordService）
- 成本中心管理（CostCenterService）
- 财务报表管理（FinancialReportService）
- 合同财务管理（ContractFinanceService）

## 2. 模块依赖
```xml
<dependencies>
    <!-- 数据模型层依赖 -->
    <dependency>
        <groupId>com.lawfirm</groupId>
        <artifactId>finance-model</artifactId>
        <version>${project.version}</version>
    </dependency>
    
    <!-- 核心层依赖 -->
    <dependency>
        <groupId>com.lawfirm</groupId>
        <artifactId>core-audit</artifactId>
        <version>${project.version}</version>
    </dependency>
    
    <!-- 通用层依赖 -->
    <dependency>
        <groupId>com.lawfirm</groupId>
        <artifactId>common-data</artifactId>
        <version>${project.version}</version>
    </dependency>
    <dependency>
        <groupId>com.lawfirm</groupId>
        <artifactId>common-cache</artifactId>
        <version>${project.version}</version>
    </dependency>
</dependencies>
```

## 3. 服务实现

### 3.1 账户服务实现
```java
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;
    
    @Override
    public AccountDTO getAccount(Long id) {
        // 实现账户查询逻辑
    }
    
    @Override
    public void updateAccountStatus(Long id, AccountStatusEnum status) {
        // 实现账户状态更新逻辑
    }
}
```

### 3.2 交易服务实现
```java
@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionMapper transactionMapper;
    
    @Override
    public TransactionDTO createTransaction(TransactionCreateDTO dto) {
        // 实现交易创建逻辑
    }
    
    @Override
    public List<TransactionDTO> getTransactionsByType(TransactionTypeEnum type) {
        // 实现交易查询逻辑
    }
}
```

### 3.3 发票服务实现
```java
@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceMapper invoiceMapper;
    
    @Override
    public InvoiceDTO createInvoice(InvoiceCreateDTO dto) {
        // 实现发票创建逻辑
    }
    
    @Override
    public void updateInvoiceStatus(Long id, InvoiceStatusEnum status) {
        // 实现发票状态更新逻辑
    }
}
```

## 4. 目录结构
```
law-firm-finance/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── config/              # 配置类
│   │   │   │   ├── FinanceConfig    # 财务模块配置
│   │   │   │   ├── CacheConfig      # 缓存配置
│   │   │   │   └── EventConfig      # 事件配置
│   │   │   │
│   │   │   ├── service/             # 服务实现层
│   │   │   │   └── impl/            # 服务实现类
│   │   │   │       ├── AccountServiceImpl
│   │   │   │       ├── TransactionServiceImpl
│   │   │   │       ├── InvoiceServiceImpl
│   │   │   │       ├── BudgetServiceImpl
│   │   │   │       ├── FeeServiceImpl
│   │   │   │       ├── IncomeServiceImpl
│   │   │   │       ├── ExpenseServiceImpl
│   │   │   │       ├── ReceivableServiceImpl
│   │   │   │       ├── PaymentPlanServiceImpl
│   │   │   │       ├── BillingRecordServiceImpl
│   │   │   │       ├── CostCenterServiceImpl
│   │   │   │       ├── FinancialReportServiceImpl
│   │   │   │       └── ContractFinanceServiceImpl
│   │   │   │
│   │   │   ├── event/               # 事件处理
│   │   │   │   ├── listener/        # 事件监听器
│   │   │   │   └── publisher/       # 事件发布器
│   │   │   │
│   │   │   └── controller/          # 控制器层
│   │   │       ├── AccountController
│   │   │       ├── TransactionController
│   │   │       ├── InvoiceController
│   │   │       ├── BudgetController
│   │   │       ├── FeeController
│   │   │       ├── IncomeController
│   │   │       ├── ExpenseController
│   │   │       ├── ReceivableController
│   │   │       ├── PaymentPlanController
│   │   │       ├── BillingRecordController
│   │   │       ├── CostCenterController
│   │   │       ├── FinancialReportController
│   │   │       └── ContractFinanceController
│   │   │
│   │   └── resources/
│   │       ├── application.yml      # 应用配置
│   │       └── application-dev.yml  # 开发环境配置
│   │
│   └── test/                        # 测试代码
│       └── java/
│           └── service/             # 服务测试
```

## 5. 配置说明

### 5.1 应用配置
```yaml
finance:
  # 缓存配置
  cache:
    enabled: true
    expire-time: 3600
  # 事件配置
  event:
    async: true
    retry-times: 3
  # 业务配置
  business:
    max-page-size: 1000
    export-limit: 10000
```

### 5.2 缓存配置
```java
@Configuration
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        // 配置缓存管理器
    }
}
```

## 6. 开发规范

### 6.1 代码规范
- 严格遵循 finance-model 模块定义的接口和常量
- 使用 finance-model 模块定义的枚举类型
- 遵循阿里巴巴Java开发规范
- 使用Lombok简化代码
- 统一异常处理
- 统一日志记录

### 6.2 接口规范
- 严格实现 finance-model 模块定义的服务接口
- 遵循RESTful API设计规范
- 统一响应格式
- 统一错误码
- 接口版本控制

### 6.3 测试规范
- 单元测试覆盖率>80%
- 集成测试覆盖主要业务流程
- 性能测试关注响应时间

## 7. 注意事项

### 7.1 开发注意事项
- 所有业务逻辑必须基于 finance-model 模块定义的接口实现
- 使用 finance-model 模块定义的枚举类型和常量
- 使用缓存优化查询性能
- 异步处理耗时操作
- 做好异常处理和日志记录

### 7.2 部署注意事项
- 确保依赖服务可用
- 配置正确的缓存参数
- 设置合适的线程池参数
- 配置监控和告警

## 8. 版本说明

### 8.1 当前版本
- 版本号：1.0.0
- 发布日期：2024-03-18

### 8.2 更新记录
- 2024-03-18：初始版本发布