# Common Util 通用工具模块

## 模块说明
通用工具模块，提供各种基础工具类，不包含具体的业务逻辑。作为其他模块的基础依赖，提供字符串处理、JSON处理、文件操作、HTTP请求、加密解密等通用功能。

## 核心类说明

### 1. 字符串工具类
- **StringExtUtils**: 扩展的字符串工具类
  - 驼峰命名和下划线命名的互相转换
  - 字符串截断
  - 字符串格式化
  - 中文字符处理

### 2. JSON工具类
- **JsonUtils**: JSON处理工具类
  - 对象与JSON字符串的互相转换
  - JSON数组处理
  - 复杂对象的序列化与反序列化
  - 格式化输出

### 3. 文件工具类
- **FileUtils**: 文件操作工具类
  - 文件读写
  - 文件压缩
  - 文件类型判断
  - 文件大小计算

### 4. HTTP工具类
- **HttpUtils**: HTTP请求工具类
  - GET/POST请求
  - 参数处理
  - 异常处理
  - 响应处理

### 5. 加密工具类
- **CryptoUtils**: 加密解密工具类
  - AES加密
  - DES加密
  - RSA加密
  - 数字签名

### 6. 验证工具类
- **Assert**: 参数验证工具类
  - 空值检查
  - 长度验证
  - 范围检查
  - 类型验证

## 目录结构
```
com.lawfirm.common.util
├── BaseUtils.java
├── string
│   └── StringExtUtils.java
├── json
│   └── JsonUtils.java
├── file
│   └── FileUtils.java
├── http
│   └── HttpUtils.java
├── crypto
│   ├── CryptoUtils.java
│   └── SignatureUtils.java
├── compress
│   └── CompressUtils.java
├── excel
│   └── ExcelUtils.java
└── validate
    ├── Assert.java
    └── ValidationException.java
```

## 主要功能

### 1. 字符串处理
- 驼峰命名与下划线命名转换
- 字符串截断和格式化
- 中文字符处理
- 字符串长度计算

### 2. JSON处理
- 对象序列化与反序列化
- JSON格式化
- 复杂对象转换
- 集合类型处理

### 3. 文件操作
- 文件读写
- 文件压缩
- 文件类型判断
- 文件大小计算

### 4. HTTP请求
- GET/POST请求
- 参数处理
- 异常处理
- 响应处理

### 5. 加密解密
- 对称加密(AES/DES)
- 非对称加密(RSA)
- 数字签名
- 哈希计算

### 6. 参数验证
- 空值检查
- 长度验证
- 范围检查
- 类型验证

## 依赖说明

### 1. Spring Boot 相关依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### 2. 工具类库
```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
</dependency>
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-collections4</artifactId>
</dependency>
```

### 3. HTTP客户端
```xml
<dependency>
    <groupId>org.apache.httpcomponents.client5</groupId>
    <artifactId>httpclient5</artifactId>
</dependency>
```

### 4. Excel处理
```xml
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
</dependency>
```

## 使用说明

### 1. 引入依赖
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-util</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 字符串处理
```java
// 驼峰转下划线
String underscore = StringExtUtils.camelToUnderscore("userName");  // user_name

// 下划线转驼峰
String camel = StringExtUtils.underscoreToCamel("user_name");     // userName
```

### 3. JSON处理
```java
// 对象转JSON
String json = JsonUtils.toJsonString(object);

// JSON转对象
User user = JsonUtils.parseObject(json, User.class);
```

### 4. 文件操作
```java
// 读取文件
String content = FileUtils.readFile("test.txt");

// 写入文件
FileUtils.writeFile("test.txt", "content");
```

### 5. HTTP请求
```java
// GET请求
String result = HttpUtils.get("http://example.com");

// POST请求
String response = HttpUtils.post("http://example.com", params);
```

### 6. 参数验证
```java
// 非空验证
Assert.notNull(object, ResultCode.VALIDATION_ERROR);

// 长度验证
Assert.lengthInRange(str, 0, 100, ResultCode.VALIDATION_ERROR);
```

## 测试说明

### 1. 测试覆盖
- 测试用例总数：79个
- 测试覆盖率要求：80%以上
- 测试模块：
  - 字符串工具测试
  - JSON工具测试
  - 文件工具测试
  - HTTP工具测试
  - 加密工具测试
  - 验证工具测试

### 2. 测试执行
```bash
# 执行所有测试
mvn test

# 生成测试覆盖率报告
mvn test jacoco:report
```

### 3. 测试报告
- 测试结果：全部通过
- 测试覆盖率：符合要求
- 性能指标：响应时间在预期范围内

## 维护者
- 维护团队：基础架构组
- 联系邮箱：xxx@xxx.com 