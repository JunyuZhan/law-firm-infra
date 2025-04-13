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

### 16. Servlet工具类
- **ServletUtils**: Servlet相关工具类
  - 请求参数获取
  - 响应数据写入
  - Cookie操作
  - Session操作

### 17. Spring工具类
- **SpringUtils**: Spring相关工具类
  - Bean获取
  - 环境配置获取
  - 属性值获取
  - 事件发布

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
├── collection
│   └── CollUtils.java
├── ServletUtils.java
├── SpringUtils.java
└── BaseUtils.java
```

## 使用示例

### 1. ID生成
```java
// 生成UUID
String uuid = IdUtils.uuid();

// 生成雪花ID
Long snowflakeId = IdUtils.snowflakeId();

// 生成订单号
String orderNo = IdUtils.orderNo();
```

### 2. Bean处理
```java
// 对象属性复制
UserDTO userDTO = new UserDTO();
BeanUtils.copyProperties(userEntity, userDTO);

// 对象类型转换
UserVO userVO = BeanUtils.convert(userDTO, UserVO.class);
```

### 3. 文件操作
```java
// 文件读取
String content = FileUtils.readFileToString(file);

// 文件写入
FileUtils.writeStringToFile(file, content);

// 文件压缩
FileUtils.compress(file, zipFile);
```

### 4. 参数验证
```java
// 非空检查
Assert.notNull(obj, "对象不能为空");

// 长度验证
Assert.length(str, 1, 10, "字符串长度必须在1-10之间");

// 范围检查
Assert.range(num, 1, 100, "数字必须在1-100之间");
```

### 5. JSON处理
```java
// 对象转JSON
String json = JsonUtils.toJson(obj);

// JSON转对象
User user = JsonUtils.fromJson(json, User.class);

// JSON格式化
String formatted = JsonUtils.format(json);
```

### 6. HTTP请求
```java
// GET请求
String response = HttpUtils.get(url, params);

// POST请求
String response = HttpUtils.post(url, params);

// 文件上传
String response = HttpUtils.upload(url, file);
```

### 7. 日期处理
```java
// 日期格式化
String dateStr = DateUtils.format(date, "yyyy-MM-dd");

// 日期解析
Date date = DateUtils.parse(dateStr, "yyyy-MM-dd");

// 日期计算
Date nextDay = DateUtils.addDays(date, 1);
```

### 8. 加密解密
```java
// MD5加密
String md5 = CryptoUtils.md5(str);

// AES加密
String encrypted = CryptoUtils.aesEncrypt(str, key);

// RSA加密
String encrypted = CryptoUtils.rsaEncrypt(str, publicKey);
```

## 注意事项

1. 工具类使用
   - 注意空值处理
   - 注意异常处理
   - 注意性能影响

2. 线程安全
   - 大部分工具类是线程安全的
   - 注意共享资源的使用
   - 避免并发问题

3. 性能优化
   - 合理使用缓存
   - 避免重复计算
   - 注意资源释放

## 常见问题

1. Q: 如何处理工具类的异常？
   A: 工具类通常抛出运行时异常，建议在调用处进行异常处理。

2. Q: 如何扩展工具类功能？
   A: 可以通过继承或组合的方式扩展工具类，注意保持向后兼容。

3. Q: 如何保证工具类的线程安全？
   A: 使用不可变对象或线程安全的数据结构，避免共享状态。

## 相关文档
- [工具类使用规范](../../docs/util-specification.md)
- [工具类测试说明](../../docs/util-test.md)
- [工具类性能优化](../../docs/util-performance.md) 