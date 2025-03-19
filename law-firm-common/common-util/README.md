# 工具模块 (Common Util)

## 模块说明
通用工具模块，提供各种基础工具类，不包含具体的业务逻辑。作为其他模块的基础依赖，提供字符串处理、JSON处理、文件操作、HTTP请求、加密解密等通用功能。

## 核心类说明

### 1. ID生成工具类
- **IdUtils**: ID生成工具类
  - UUID生成
  - 雪花ID生成
  - 订单号生成
  - 序列号生成

### 2. Bean工具类
- **BeanUtils**: Bean处理工具类
  - 对象属性复制
  - 对象类型转换
  - 对象比较
  - 对象克隆

### 3. 文件工具类
- **FileUtils**: 文件操作工具类
  - 文件读写
  - 文件压缩
  - 文件类型判断
  - 文件大小计算

### 4. 验证工具类
- **Assert**: 参数验证工具类
  - 空值检查
  - 长度验证
  - 范围检查
  - 类型验证

### 5. 字符串工具类
- **StringUtils**: 字符串处理工具类
  - 字符串判空
  - 字符串转换
  - 字符串格式化
  - 字符串匹配

### 6. JSON工具类
- **JsonUtils**: JSON处理工具类
  - 对象序列化
  - 对象反序列化
  - JSON格式化
  - JSON路径

### 7. 图片工具类
- **ImageUtils**: 图片处理工具类
  - 图片压缩
  - 图片裁剪
  - 图片旋转
  - 图片格式转换

### 8. HTTP工具类
- **HttpUtils**: HTTP请求工具类
  - GET/POST请求
  - 参数处理
  - 异常处理
  - 响应处理

### 9. 地理信息工具类
- **GeoUtils**: 地理信息处理工具类
  - 距离计算
  - 坐标转换
  - 区域判断
  - 地理编码

### 10. Excel工具类
- **ExcelUtils**: Excel处理工具类
  - 数据导出
  - 数据导入
  - 模板导出
  - 格式处理

### 11. 日期工具类
- **DateUtils**: 日期处理工具类
  - 日期格式化
  - 日期解析
  - 日期计算
  - 时区转换

### 12. 加密工具类
- **CryptoUtils**: 加密解密工具类
  - MD5加密
  - AES加密
  - RSA加密
  - 数字签名

### 13. 压缩工具类
- **CompressUtils**: 压缩解压工具类
  - 文件压缩
  - 文件解压
  - 字符串压缩
  - 流压缩

### 14. 编码工具类
- **CodecUtils**: 编码解码工具类
  - Base64编码
  - URL编码
  - Unicode编码
  - 字符集转换

### 15. 集合工具类
- **CollUtils**: 集合处理工具类
  - 集合判空
  - 集合转换
  - 集合分组
  - 集合过滤

## 目录结构
```
com.lawfirm.common.util
├── id
│   ├── IdUtils.java
│   └── IdGenerator.java
├── bean
│   └── BeanUtils.java
├── file
│   └── FileUtils.java
├── validate
│   └── Assert.java
├── string
│   └── StringUtils.java
├── json
│   ├── JsonUtils.java
│   └── JsonPathUtils.java
├── image
│   └── ImageUtils.java
├── http
│   └── HttpUtils.java
├── geo
│   └── GeoUtils.java
├── excel
│   └── ExcelUtils.java
├── date
│   └── DateUtils.java
├── crypto
│   └── CryptoUtils.java
├── compress
│   └── CompressUtils.java
├── codec
│   └── CodecUtils.java
└── collection
    └── CollUtils.java
```

## 主要功能

### 1. ID生成
- UUID生成
- 雪花ID生成
- 订单号生成
- 序列号生成

### 2. Bean处理
- 对象属性复制
- 对象类型转换
- 对象比较
- 对象克隆

### 3. 文件操作
- 文件读写
- 文件压缩
- 文件类型判断
- 文件大小计算

### 4. 参数验证
- 空值检查
- 长度验证
- 范围检查
- 类型验证

### 5. 字符串处理
- 字符串判空
- 字符串转换
- 字符串格式化
- 字符串匹配

### 6. JSON处理
- 对象序列化
- 对象反序列化
- JSON格式化
- JSON路径

### 7. 图片处理
- 图片压缩
- 图片裁剪
- 图片旋转
- 图片格式转换

### 8. HTTP请求
- GET/POST请求
- 参数处理
- 异常处理
- 响应处理

### 9. 地理信息
- 距离计算
- 坐标转换
- 区域判断
- 地理编码

### 10. Excel处理
- 数据导出
- 数据导入
- 模板导出
- 格式处理

### 11. 日期处理
- 日期格式化
- 日期解析
- 日期计算
- 时区转换

### 12. 加密解密
- MD5加密
- AES加密
- RSA加密
- 数字签名

### 13. 压缩解压
- 文件压缩
- 文件解压
- 字符串压缩
- 流压缩

### 14. 编码解码
- Base64编码
- URL编码
- Unicode编码
- 字符集转换

### 15. 集合处理
- 集合判空
- 集合转换
- 集合分组
- 集合过滤

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

### 2. ID生成
```java
// 生成UUID
String uuid = IdUtils.uuid();

// 生成雪花ID
Long snowflakeId = IdUtils.snowflake();

// 生成订单号
String orderNo = IdUtils.orderNo();
```

### 3. Bean处理
```java
// 对象复制
BeanUtils.copyProperties(source, target);

// 对象转换
Target target = BeanUtils.convert(source, Target.class);

// 对象比较
boolean equals = BeanUtils.equals(source, target);
```

### 4. 文件操作
```java
// 文件上传
FileUtils.upload(file, path);

// 文件下载
FileUtils.download(url, path);

// 文件删除
FileUtils.delete(path);
```

### 5. 参数验证
```java
// 参数验证
Assert.notNull(value, "参数不能为空");

// 条件验证
Assert.isTrue(condition, "条件不满足");

// 状态验证
Assert.state(condition, "状态不正确");
```

### 6. 字符串处理
```java
// 字符串判空
StringUtils.isEmpty(str);

// 字符串转换
StringUtils.toCamelCase(str);

// 字符串格式化
StringUtils.format(template, args);
```

### 7. JSON处理
```java
// 对象转JSON
String json = JsonUtils.toJson(obj);

// JSON转对象
Object obj = JsonUtils.fromJson(json, Object.class);

// JSON路径
Object value = JsonPathUtils.getValue(json, "$.key");
```

### 8. 图片处理
```java
// 图片压缩
ImageUtils.compress(image, quality);

// 图片裁剪
ImageUtils.crop(image, x, y, width, height);

// 图片旋转
ImageUtils.rotate(image, angle);
```

### 9. HTTP请求
```java
// GET请求
String response = HttpUtils.get(url);

// POST请求
String response = HttpUtils.post(url, data);

// 文件上传
String response = HttpUtils.upload(url, file);
```

### 10. 地理信息
```java
// 计算距离
double distance = GeoUtils.distance(lat1, lng1, lat2, lng2);

// 坐标转换
Point point = GeoUtils.convert(lat, lng);

// 区域判断
boolean inArea = GeoUtils.inArea(point, area);
```

### 11. Excel处理
```java
// 导出Excel
ExcelUtils.export(data, path);

// 导入Excel
List<Object> data = ExcelUtils.import(path);

// 模板导出
ExcelUtils.exportTemplate(template, data, path);
```

### 12. 日期处理
```java
// 日期格式化
String date = DateUtils.format(date, pattern);

// 日期解析
Date date = DateUtils.parse(str, pattern);

// 日期计算
Date date = DateUtils.add(date, Calendar.DAY_OF_MONTH, 1);
```

### 13. 加密解密
```java
// MD5加密
String md5 = CryptoUtils.md5(str);

// AES加密
String aes = CryptoUtils.aes(str, key);

// RSA加密
String rsa = CryptoUtils.rsa(str, key);
```

### 14. 压缩解压
```java
// 压缩文件
CompressUtils.zip(source, target);

// 解压文件
CompressUtils.unzip(source, target);

// 压缩字符串
String compressed = CompressUtils.compress(str);
```

### 15. 编码解码
```java
// Base64编码
String base64 = CodecUtils.base64Encode(str);

// URL编码
String url = CodecUtils.urlEncode(str);

// Unicode编码
String unicode = CodecUtils.unicodeEncode(str);
```

### 16. 集合处理
```java
// 集合判空
CollUtils.isEmpty(collection);

// 集合转换
List<T> list = CollUtils.convert(collection, converter);

// 集合分组
Map<K, List<T>> map = CollUtils.group(collection, keyExtractor);
```

## 测试说明

### 1. 测试覆盖
- 测试用例总数：79个
- 测试覆盖率要求：80%以上
- 测试模块：
  - ID生成测试
  - Bean处理测试
  - 文件操作测试
  - 参数验证测试
  - 字符串处理测试
  - JSON处理测试
  - 图片处理测试
  - HTTP请求测试
  - 地理信息测试
  - Excel处理测试
  - 日期处理测试
  - 加密解密测试
  - 压缩解压测试
  - 编码解码测试
  - 集合处理测试

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