# 系统管理模块 (System Model)

## 模块说明
系统管理模块是律师事务所管理系统的系统配置和字典管理的数据模型定义模块，负责定义系统配置和数据字典相关的实体类、数据传输对象、视图对象和枚举类型。该模块是系统基础配置的数据基础。

## 目录结构
```
system-model/
├── entity/       
│   ├── SysConfig.java     # 系统配置
│   ├── SysDict.java       # 系统字典
│   └── SysDictItem.java   # 系统字典项
├── enums/        
│   ├── ConfigTypeEnum.java # 配置类型
│   └── DictTypeEnum.java   # 字典类型
├── dto/          
│   ├── config/
│   │   ├── ConfigCreateDTO.java # 配置创建
│   │   └── ConfigUpdateDTO.java # 配置更新
│   └── dict/
│       ├── DictCreateDTO.java   # 字典创建
│       ├── DictUpdateDTO.java   # 字典更新
│       ├── DictItemCreateDTO.java # 字典项创建
│       └── DictItemUpdateDTO.java # 字典项更新
├── vo/           
│   ├── ConfigVO.java      # 配置视图
│   ├── DictVO.java       # 字典视图
│   └── DictItemVO.java   # 字典项视图
└── service/      
    ├── ConfigService.java # 配置服务
    └── DictService.java   # 字典服务
```

## 核心功能

### 1. 系统配置管理
- 配置实体（SysConfig）
  - 配置名称
  - 配置键名
  - 配置值
  - 配置类型
  - 配置描述
  - 配置状态

### 2. 数据字典管理
- 字典实体（SysDict）
  - 字典名称
  - 字典编码
  - 字典类型
  - 字典描述
  - 字典状态

- 字典项实体（SysDictItem）
  - 字典ID（关联字典）
  - 字典项标签
  - 字典项值
  - 字典项描述
  - 是否默认
  - 颜色类型
  - CSS样式
  - 排序号
  - 状态

### 3. 系统属性
- 配置类型（ConfigTypeEnum）
  - 系统配置
  - 业务配置
  - 安全配置
  - 其他配置

- 字典类型（DictTypeEnum）
  - 系统字典
  - 业务字典
  - 状态字典
  - 其他字典

## 核心类说明

### 1. 实体类
- SysConfig：系统配置实体
  - 基本配置信息
  - 配置值管理
  - 配置类型
  - 状态管理

- SysDict：数据字典实体
  - 字典基本信息
  - 字典类型
  - 状态管理

- SysDictItem：字典项实体
  - 字典项信息
  - 展示属性
  - 样式配置
  - 状态管理

### 2. 数据传输对象
- config/：配置相关DTO
  - ConfigCreateDTO：配置创建对象
  - ConfigUpdateDTO：配置更新对象

- dict/：字典相关DTO
  - DictCreateDTO：字典创建对象
  - DictUpdateDTO：字典更新对象
  - DictItemCreateDTO：字典项创建对象
  - DictItemUpdateDTO：字典项更新对象

### 3. 视图对象
- ConfigVO：配置视图对象
  - 列表展示
  - 详情展示
  - 配置信息
  - 状态信息

- DictVO：字典视图对象
  - 列表展示
  - 详情展示
  - 字典项列表
  - 状态信息

- DictItemVO：字典项视图对象
  - 列表展示
  - 详情展示
  - 样式信息
  - 状态信息

## 使用示例

### 1. 创建系统配置
```java
SysConfig config = new SysConfig()
    .setConfigName("系统主题")
    .setConfigKey("system.theme")
    .setConfigValue("default")
    .setConfigType(ConfigTypeEnum.SYSTEM)
    .setStatus(StatusEnum.ENABLED);
```

### 2. 创建数据字典
```java
// 创建字典
SysDict dict = new SysDict()
    .setDictName("案件状态")
    .setDictCode("case_status")
    .setDictType(DictTypeEnum.BUSINESS)
    .setStatus(StatusEnum.ENABLED);

// 创建字典项
SysDictItem item = new SysDictItem()
    .setDictId(dict.getId())
    .setLabel("进行中")
    .setValue("PROCESSING")
    .setColorType("blue")
    .setIsDefault(1)
    .setSort(1)
    .setStatus(StatusEnum.ENABLED);
```

## 注意事项
1. 配置键名和字典编码必须唯一
2. 注意配置值和字典项的数据类型
3. 及时更新配置和字典数据
4. 重要变更需要记录日志
5. 关注数据安全和权限控制
6. 字典项必须关联到有效的字典
7. 注意字典项的排序和默认值设置 