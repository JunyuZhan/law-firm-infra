# 存储管理模块

## 模块说明
存储管理模块提供了律师事务所系统中文件存储相关的核心数据模型和服务接口定义。该模块主要包含文件存储、存储桶管理等功能的基础数据结构和服务定义。

## 目录结构
```
storage-model/
├── entity/
│   ├── base/             # 基础存储
│   │   └── BaseStorage.java    # 存储基类
│   ├── file/            # 文件存储
│   │   ├── FileObject.java    # 文件对象
│   │   └── FileInfo.java      # 文件信息
│   └── bucket/          # 存储桶
│       └── StorageBucket.java # 存储桶
├── enums/
│   ├── StorageTypeEnum.java   # 存储类型
│   ├── FileTypeEnum.java      # 文件类型
│   └── BucketTypeEnum.java    # 存储桶类型
├── dto/
│   ├── file/
│   │   ├── FileUploadDTO.java  # 文件上传
│   │   └── FileQueryDTO.java   # 文件查询
│   └── bucket/
│       ├── BucketCreateDTO.java # 存储桶创建
│       └── BucketQueryDTO.java  # 存储桶查询
├── vo/
│   ├── FileVO.java         # 文件视图
│   └── BucketVO.java       # 存储桶视图
└── service/
    ├── FileService.java    # 文件服务
    └── BucketService.java  # 存储桶服务
```

## 核心功能

### 1. 文件存储管理
- 文件上传：支持多种类型文件的上传和存储
- 文件下载：提供文件下载功能
- 文件预览：支持常见文件类型的在线预览
- 文件信息：管理文件的元数据信息
- 文件分类：支持文件的分类管理
- 文件权限：控制文件的访问权限

### 2. 存储桶管理
- 桶创建：支持创建不同类型的存储桶
- 桶配置：管理存储桶的配置信息
- 桶权限：控制存储桶的访问权限
- 桶策略：支持存储桶的生命周期策略
- 桶统计：提供存储桶使用情况统计

## 主要类说明

### 实体类
1. BaseStorage
   - 存储基类，定义存储的基本属性
   - 包含：存储ID、存储类型、创建时间等

2. FileObject
   - 文件对象，继承自BaseStorage
   - 包含：文件名、文件类型、文件大小等

3. FileInfo
   - 文件信息，包含文件的元数据
   - 包含：文件描述、文件标签、文件属性等

4. StorageBucket
   - 存储桶，继承自BaseStorage
   - 包含：桶名称、桶类型、桶配置等

### 枚举类
1. StorageTypeEnum
   - 定义存储类型：如本地存储、云存储等

2. FileTypeEnum
   - 定义文件类型：如文档、图片、视频等

3. BucketTypeEnum
   - 定义存储桶类型：如公共桶、私有桶等

### DTO类
1. FileUploadDTO
   - 文件上传数据传输对象
   - 包含上传文件所需的必要信息

2. FileQueryDTO
   - 文件查询数据传输对象
   - 包含查询文件所需的条件信息

3. BucketCreateDTO
   - 存储桶创建数据传输对象
   - 包含创建存储桶所需的必要信息

4. BucketQueryDTO
   - 存储桶查询数据传输对象
   - 包含查询存储桶所需的条件信息

### VO类
1. FileVO
   - 文件视图对象
   - 用于展示文件信息

2. BucketVO
   - 存储桶视图对象
   - 用于展示存储桶信息

### 服务接口
1. FileService
   - 提供文件相关的业务操作接口
   - 包含文件的上传、下载、查询等操作

2. BucketService
   - 提供存储桶相关的业务操作接口
   - 包含存储桶的创建、配置、查询等操作

## 使用说明
1. 引入依赖
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>storage-model</artifactId>
    <version>${project.version}</version>
</dependency>
```

2. 使用文件服务
```java
@Autowired
private FileService fileService;

// 上传文件
FileUploadDTO uploadDTO = new FileUploadDTO();
String fileId = fileService.uploadFile(uploadDTO);

// 下载文件
byte[] fileContent = fileService.downloadFile(fileId);
```

3. 使用存储桶服务
```java
@Autowired
private BucketService bucketService;

// 创建存储桶
BucketCreateDTO createDTO = new BucketCreateDTO();
Long bucketId = bucketService.createBucket(createDTO);

// 查询存储桶
BucketVO bucket = bucketService.getBucket(bucketId);
```

## 注意事项
1. 所有实体类都继承自BaseModel，确保基础字段的一致性
2. DTO类继承自BaseDTO，VO类继承自BaseVO
3. 遵循统一的命名规范和代码风格
4. 确保完整的单元测试覆盖
5. 注意文件上传的安全性和权限控制
6. 考虑存储容量和清理策略
7. 建议使用分布式存储方案
8. 重要文件建议做备份 

## 迁移记录
1. 2024-04-28: 完成从JPA到MyBatis Plus的迁移，移除了所有JPA注解，转换成对应的MyBatis Plus注解。迁移的实体类包括:
   - ChunkInfo.java
   - FileInfo.java 