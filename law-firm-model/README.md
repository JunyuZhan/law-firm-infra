# 数据模型层 (Model Layer)

## 模块说明
数据模型层是系统的数据模型定义层,包含所有业务实体、数据传输对象、视图对象等。该层不包含任何业务逻辑,只负责数据的定义和转换。

## 目录结构
```
law-firm-model/
├── base-model/           # 基础模型
│   ├── entity/          # 基础实体类
│   ├── dto/             # 基础数据传输对象
│   ├── vo/              # 基础视图对象
│   ├── enums/           # 基础枚举类
│   ├── constants/       # 基础常量类
│   ├── query/           # 基础查询对象
│   ├── result/          # 基础结果对象
│   ├── service/         # 基础服务接口
│   ├── controller/      # 基础控制器
│   ├── tenant/          # 租户相关
│   └── support/         # 支持类
├── system-model/        # 系统模型
├── log-model/          # 日志模型
├── case-model/         # 案件模型
├── client-model/       # 客户模型
├── contract-model/     # 合同模型
├── document-model/     # 文档模型
├── finance-model/      # 财务模型
├── organization-model/ # 组织模型
├── personnel-model/    # 人员模型
├── auth-model/        # 认证模型
├── workflow-model/    # 工作流模型
├── storage-model/     # 存储模型
├── search-model/      # 搜索模型
├── message-model/     # 消息模型
├── knowledge-model/   # 知识模型
└── ai-model/         # AI模型
```

## 模块分类

### 1. 基础模块
- **base-model**: 基础模型,提供基础实体类、DTO、VO等
- **system-model**: 系统模型,包含系统配置、系统参数等
- **log-model**: 日志模型,包含日志记录、审计日志等

### 2. 业务模块
- **case-model**: 案件相关模型
- **client-model**: 客户相关模型
- **contract-model**: 合同相关模型
- **document-model**: 文档相关模型
- **finance-model**: 财务相关模型
- **organization-model**: 组织相关模型
- **personnel-model**: 人员相关模型

### 3. 功能模块
- **auth-model**: 认证相关模型
- **workflow-model**: 工作流相关模型
- **storage-model**: 存储相关模型
- **search-model**: 搜索相关模型
- **message-model**: 消息相关模型
- **knowledge-model**: 知识相关模型
- **ai-model**: AI相关模型

## 开发规范

### 1. 命名规范
- 类名: 使用大驼峰命名法,如`CaseEntity`
- 方法名: 使用小驼峰命名法,如`getCaseInfo`
- 变量名: 使用小驼峰命名法,如`caseId`
- 常量名: 使用全大写下划线分隔,如`CASE_STATUS`
- 包名: 使用全小写点分隔,如`com.lawfirm.case.model`

### 2. 目录规范
每个模块必须包含以下目录:
```
module/
├── entity/          # 实体类
├── dto/             # 数据传输对象
├── vo/              # 视图对象
├── enums/           # 枚举类
├── constants/       # 常量类
├── query/           # 查询对象
└── result/          # 结果对象
```

### 3. 类规范
- 实体类: 以Entity结尾,如`CaseEntity`
- DTO类: 以DTO结尾,如`CaseDTO`
- VO类: 以VO结尾,如`CaseVO`
- 枚举类: 以Enum结尾,如`CaseStatusEnum`
- 常量类: 以Constants结尾,如`CaseConstants`
- 查询类: 以Query结尾,如`CaseQuery`
- 结果类: 以Result结尾,如`CaseResult`

### 4. 字段规范
- 实体类字段:
  - id: 主键
  - createTime: 创建时间
  - updateTime: 更新时间
  - createBy: 创建人
  - updateBy: 更新人
  - deleted: 是否删除
  - version: 版本号
  - tenantId: 租户ID

- DTO字段:
  - 不包含实体类基础字段
  - 只包含业务字段
  - 字段名与实体类保持一致

- VO字段:
  - 不包含实体类基础字段
  - 只包含展示字段
  - 字段名与实体类保持一致

### 5. 注释规范
- 类注释:
```java
/**
 * 案件实体类
 *
 * @author xxx
 * @date 2024-03-18
 */
```

- 字段注释:
```java
/**
 * 案件ID
 */
private Long id;

/**
 * 案件名称
 */
private String name;
```

- 方法注释:
```java
/**
 * 获取案件信息
 *
 * @param id 案件ID
 * @return 案件信息
 */
public CaseVO getCaseInfo(Long id);
```

### 6. 依赖规范
- 不依赖其他业务模块
- 只依赖基础模块
- 不依赖框架模块
- 不依赖持久层模块

### 7. 测试规范
- 单元测试覆盖率要求: 80%以上
- 必须包含以下测试:
  - 实体类测试
  - DTO测试
  - VO测试
  - 枚举类测试
  - 常量类测试
  - 查询类测试
  - 结果类测试

## 开发流程

### 1. 创建模块
```bash
# 创建模块目录
mkdir -p law-firm-model/new-module

# 创建pom.xml
cp law-firm-model/base-model/pom.xml law-firm-model/new-module/

# 创建目录结构
mkdir -p law-firm-model/new-module/src/main/java/com/lawfirm/new/model/{entity,dto,vo,enums,constants,query,result}
```

### 2. 开发实体类
```java
/**
 * 新模块实体类
 *
 * @author xxx
 * @date 2024-03-18
 */
@Data
@TableName("new_table")
public class NewEntity extends ModelBaseEntity {
    /**
     * 字段1
     */
    private String field1;

    /**
     * 字段2
     */
    private Integer field2;
}
```

### 3. 开发DTO
```java
/**
 * 新模块DTO
 *
 * @author xxx
 * @date 2024-03-18
 */
@Data
public class NewDTO extends BaseDTO {
    /**
     * 字段1
     */
    private String field1;

    /**
     * 字段2
     */
    private Integer field2;
}
```

### 4. 开发VO
```java
/**
 * 新模块VO
 *
 * @author xxx
 * @date 2024-03-18
 */
@Data
public class NewVO extends BaseVO {
    /**
     * 字段1
     */
    private String field1;

    /**
     * 字段2
     */
    private Integer field2;
}
```

### 5. 开发枚举类
```java
/**
 * 新模块状态枚举
 *
 * @author xxx
 * @date 2024-03-18
 */
public enum NewStatusEnum implements BaseEnum {
    DRAFT(0, "草稿"),
    PUBLISHED(1, "已发布");

    private final Integer code;
    private final String desc;

    NewStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
```

### 6. 开发常量类
```java
/**
 * 新模块常量类
 *
 * @author xxx
 * @date 2024-03-18
 */
public class NewConstants {
    /**
     * 最大长度
     */
    public static final int MAX_LENGTH = 100;

    /**
     * 最小长度
     */
    public static final int MIN_LENGTH = 1;
}
```

### 7. 开发查询类
```java
/**
 * 新模块查询类
 *
 * @author xxx
 * @date 2024-03-18
 */
@Data
public class NewQuery extends BaseQuery {
    /**
     * 字段1
     */
    private String field1;

    /**
     * 字段2
     */
    private Integer field2;
}
```

### 8. 开发结果类
```java
/**
 * 新模块结果类
 *
 * @author xxx
 * @date 2024-03-18
 */
@Data
public class NewResult extends BaseResult {
    /**
     * 字段1
     */
    private String field1;

    /**
     * 字段2
     */
    private Integer field2;
}
```

### 9. 编写测试
```java
/**
 * 新模块测试类
 *
 * @author xxx
 * @date 2024-03-18
 */
@SpringBootTest
public class NewTest {
    @Test
    public void testEntity() {
        NewEntity entity = new NewEntity();
        entity.setField1("test");
        entity.setField2(1);
        Assert.assertEquals("test", entity.getField1());
        Assert.assertEquals(1, entity.getField2());
    }

    @Test
    public void testDTO() {
        NewDTO dto = new NewDTO();
        dto.setField1("test");
        dto.setField2(1);
        Assert.assertEquals("test", dto.getField1());
        Assert.assertEquals(1, dto.getField2());
    }

    @Test
    public void testVO() {
        NewVO vo = new NewVO();
        vo.setField1("test");
        vo.setField2(1);
        Assert.assertEquals("test", vo.getField1());
        Assert.assertEquals(1, vo.getField2());
    }
}
```

## 更新日志
- 2024-03-18: 初始版本发布 