# 客户管理模块

本模块是律所管理系统的客户管理实现模块，基于`law-firm-model/client-model`数据模型，提供律所客户管理的完整业务功能实现。

## 模块功能

- 客户信息管理：创建、查询、更新和删除客户信息
- 联系人管理：管理客户的多个联系人
- 客户分类与标签：多维度客户分类和标签管理
- 客户跟进记录：跟进计划制定与执行记录
- 客户导入导出：批量数据导入与导出
- 数据统计分析：客户相关数据统计与分析

## 开发计划

开发顺序按照项目构建和依赖关系进行排列：

### 第一阶段：基础设施（初始化）

1. **配置与常量**
   - 模块配置类（ClientConfig、CacheConfig）
   - 常量定义（已完成）
   - 工具类（ClientConverter）

2. **基础设施配置**
   - 缓存配置实现
   - 拦截器配置
   - 数据源配置

### 第二阶段：核心业务功能

3. **客户管理服务实现**
   - ClientServiceImpl：实现客户增删改查基础功能
   - ContactServiceImpl：实现联系人管理基础功能
   - 相关控制器实现

4. **分类与标签管理**
   - CategoryServiceImpl：实现分类管理
   - TagServiceImpl：实现标签管理
   - 相关控制器实现

### 第三阶段：扩展业务功能

5. **客户跟进管理**
   - FollowUpServiceImpl：实现跟进记录管理
   - 跟进提醒实现
   - 相关控制器实现

6. **数据导入导出**
   - 导入策略实现
   - 导出功能实现
   - 相关控制器实现

### 第四阶段：优化与分析

7. **缓存优化**
   - 客户数据缓存实现
   - 分类与标签缓存实现

8. **数据统计与分析**
   - 客户统计实现
   - 跟进效果分析实现
   - 相关控制器实现

## 目录结构

```
law-firm-modules/law-firm-client/
├── src/main/java/com/lawfirm/client/
│   ├── config/                     # 配置类（先开发）
│   │   ├── ClientConfig.java       # 基础配置
│   │   └── CacheConfig.java        # 缓存配置
│   │
│   ├── constant/                   # 常量定义（已完成）
│   │   ├── ClientModuleConstant.java  # 模块常量
│   │   └── CacheConstant.java      # 缓存常量
│   │
│   ├── util/                       # 工具类
│   │   └── ClientConverter.java    # 对象转换工具
│   │
│   ├── service/                    # 服务层
│   │   ├── impl/                   # 接口实现
│   │   │   ├── ClientServiceImpl.java   # 客户服务实现
│   │   │   ├── ContactServiceImpl.java  # 联系人服务实现
│   │   │   ├── CategoryServiceImpl.java # 分类服务实现
│   │   │   ├── TagServiceImpl.java      # 标签服务实现
│   │   │   └── FollowUpServiceImpl.java # 跟进服务实现
│   │   │
│   │   └── strategy/               # 策略实现
│   │       ├── follow/             # 跟进策略
│   │       └── import/             # 导入策略
│   │
│   ├── controller/                 # 控制层
│   │   ├── ClientController.java   # 客户控制器
│   │   ├── ContactController.java  # 联系人控制器
│   │   ├── CategoryController.java # 分类控制器
│   │   ├── TagController.java      # 标签控制器
│   │   ├── FollowUpController.java # 跟进控制器
│   │   └── ImportExportController.java # 导入导出控制器
│   │
│   └── task/                       # 定时任务
│       └── FollowUpReminderTask.java  # 跟进提醒任务
│
└── src/main/resources/
    ├── mapper/                     # MyBatis映射文件（仅用于复杂查询）
    └── templates/                  # 模板文件
        └── import_template.xlsx    # 导入模板
```

## 技术实现要点

### 1. 基于MyBatis-Plus的服务实现

本模块使用MyBatis-Plus框架实现数据访问，所有Service实现类均继承自`ServiceImpl`基类：

```java
@Service
@RequiredArgsConstructor
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements ClientService {
    
    // 自动注入Mapper
    private final ClientMapper clientMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createClient(ClientCreateDTO dto) {
        // 数据转换与验证
        Client client = convertAndValidate(dto);
        
        // 使用继承的save方法保存数据，无需手动编写SQL
        save(client);
        
        // 处理关联数据
        handleRelatedData(client.getId(), dto);
        
        return client.getId();
    }
    
    // 其他方法...
}
```

**注意**：由于继承了ServiceImpl，基础的CRUD操作不需要额外的XML映射文件。只有复杂查询、多表关联等特殊场景才需要在mapper目录下创建XML映射文件。

### 2. 缓存实现

```java
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        // 配置Redis缓存管理器
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(defaultCacheConfig())
                .withInitialCacheConfigurations(customCacheConfigs())
                .build();
        return cacheManager;
    }
    
    private RedisCacheConfiguration defaultCacheConfig() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
    
    private Map<String, RedisCacheConfiguration> customCacheConfigs() {
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        
        // 客户信息缓存配置
        configMap.put("clientCache", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(24)));
        
        // 列表缓存配置
        configMap.put("listCache", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30)));
        
        return configMap;
    }
}
```

## 核心业务功能

### 1. 客户服务实现

客户服务实现基于`ClientService`接口，提供以下核心功能：

- 创建客户：验证、保存客户信息及关联数据
- 更新客户：客户信息修改与状态变更
- 获取客户：支持ID、编号、条件查询
- 分页查询：支持多条件组合查询
- 删除客户：逻辑删除与关联数据处理

### 2. 联系人服务实现

联系人服务实现基于`ContactService`接口，提供以下核心功能：

- 创建联系人：支持默认联系人设置
- 获取联系人：支持获取客户的所有联系人
- 设置默认联系人：调整客户默认联系人
- 更新与删除联系人

### 3. 跟进记录实现

跟进记录服务基于`FollowUpService`接口，提供以下核心功能：

- 创建跟进计划：支持不同客户类型的跟进周期
- 记录跟进结果：支持多种跟进方式和结果记录
- 跟进提醒：自动提醒待跟进的客户
- 跟进统计：客户跟进频次与效果统计

### 4. 分类与标签实现

分类与标签服务分别基于`CategoryService`和`TagService`接口，提供以下功能：

- 分类管理：支持多级分类结构
- 标签管理：支持多种标签类型
- 客户关联：客户与分类、标签的关联管理

## 关键业务场景

### 场景1：新客户录入

1. 录入客户基本信息
2. 添加联系人信息
3. 设置分类与标签
4. 创建初始跟进计划

### 场景2：客户跟进管理

1. 查看待跟进客户列表
2. 记录跟进情况
3. 设置下次跟进计划
4. 评估客户价值变化

### 场景3：客户数据批量导入

1. 下载导入模板
2. 填写客户数据
3. 上传数据文件
4. 验证并导入数据
5. 查看导入结果报告

## 注意事项

1. 客户编号全局唯一，系统自动生成
2. 客户名称在同一分类下不允许重复
3. 客户默认状态为"正常"，可设置为"禁用"
4. 默认联系人必须设置，且只能有一个
5. 删除客户为逻辑删除，不物理删除数据
6. 跟进提醒需配置定时任务支持
7. 由于使用MyBatis-Plus的ServiceImpl，大部分基础CRUD操作不需要手写SQL
