# Common Test Module

## 模块说明
`common-test` 是法律事务管理系统的测试基础设施模块，提供了统一的测试框架和抽象类。该模块采用抽象类和接口的方式实现，为其他模块提供测试的基础架构。

## 核心组件

### 1. BaseTest
抽象测试基类，提供基础的测试配置和生命周期方法：
```java
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public abstract class BaseTest {
    protected abstract void beforeTest();
    protected abstract void afterTest();
    protected abstract void clearTestData();
}
```

### 2. TestApplication
抽象测试应用类，提供测试应用程序的基础配置：
```java
@SpringBootApplication
public abstract class TestApplication {
    protected abstract void configureTestApplication();
    protected abstract void initializeTestEnvironment();
}
```

### 3. TestConfig
测试配置接口，定义测试环境配置的标准：
```java
@Configuration
public interface TestConfig {
    void configureTestDataSource();
    void configureTestTransactionManager();
    void configureTestEnvironment();
}
```

## 功能特性

### 1. 统一的测试基础设施
- 标准化的测试配置
- 统一的事务管理
- 一致的测试环境配置

### 2. 生命周期管理
- 测试前准备
- 测试后清理
- 测试数据管理

### 3. 测试环境配置
- 数据源配置
- 事务管理器配置
- 环境变量配置

## 使用方法

### 1. 引入依赖
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-test</artifactId>
    <version>${project.version}</version>
    <scope>test</scope>
</dependency>
```

### 2. 创建测试类
```java
public class YourTest extends BaseTest {
    @Override
    protected void beforeTest() {
        // 实现测试前的准备工作
    }

    @Override
    protected void afterTest() {
        // 实现测试后的清理工作
    }

    @Override
    protected void clearTestData() {
        // 实现测试数据的清理
    }

    @Test
    void yourTestMethod() {
        // 编写测试用例
    }
}
```

### 3. 配置测试环境
```java
@Configuration
public class YourTestConfig implements TestConfig {
    @Override
    public void configureTestDataSource() {
        // 配置测试数据源
    }

    @Override
    public void configureTestTransactionManager() {
        // 配置测试事务管理器
    }

    @Override
    public void configureTestEnvironment() {
        // 配置测试环境
    }
}
```

## 测试覆盖

模块包含完整的单元测试，覆盖了以下场景：
- BaseTest的注解和抽象方法验证
- TestApplication的注解和抽象方法验证
- TestConfig的接口方法验证

测试统计：
- 总测试用例：9个
- 测试覆盖率：100%
- 测试类型：单元测试

## 依赖说明

主要依赖：
- spring-boot-starter-test：测试框架支持
- spring-boot-starter：Spring Boot基础支持
- spring-security-test：安全测试支持
- spring-tx：事务管理支持
- junit-jupiter：测试运行时
- mockito：Mock框架

## 注意事项

1. 抽象类使用
   - 必须实现所有抽象方法
   - 遵循测试生命周期
   - 正确管理测试数据

2. 事务管理
   - 默认开启事务
   - 测试方法自动回滚
   - 可通过注解控制事务行为

3. 测试环境
   - 使用test profile
   - 独立的测试配置
   - 避免影响生产环境

## 维护说明

1. 代码结构
   - 只包含抽象类和接口
   - 不包含具体实现
   - 保持简洁和通用性

2. 测试规范
   - 确保抽象方法定义完整
   - 维护测试用例覆盖率
   - 及时更新文档 