# 知识管理模块

## 模块说明
知识管理模块提供律师事务所的知识库管理功能，支持文章、分类、标签等内容的管理，实现知识的沉淀和共享。

## 功能特性
1. 文章管理
   - 文章创建与编辑
   - 文章分类管理
   - 文章标签管理
   - 文章评论功能
   - 附件管理
   
2. 分类管理
   - 分类创建与编辑
   - 分类层级管理
   - 分类权限控制
   - 分类统计分析
   
3. 标签管理
   - 标签创建与编辑
   - 标签关联管理
   - 标签云展示
   - 标签统计分析
   
4. 评论管理
   - 评论发布
   - 评论审核
   - 评论回复
   - 评论通知

## 目录结构
```
knowledge-model/
├── entity/       # 实体类
│   ├── Article.java        # 文章实体
│   ├── Category.java       # 分类实体
│   ├── Tag.java           # 标签实体
│   ├── Comment.java       # 评论实体
│   └── Attachment.java    # 附件实体
├── enums/        # 枚举类
│   ├── ArticleTypeEnum.java   # 文章类型
│   ├── CategoryTypeEnum.java  # 分类类型
│   ├── ContentTypeEnum.java   # 内容类型
│   └── CommentStatusEnum.java # 评论状态
├── dto/          # 数据传输对象
│   ├── article/   # 文章相关
│   │   ├── ArticleCreateDTO.java # 文章创建
│   │   ├── ArticleUpdateDTO.java # 文章更新
│   │   └── ArticleQueryDTO.java  # 文章查询
│   └── category/  # 分类相关
│       ├── CategoryCreateDTO.java # 分类创建
│       └── CategoryQueryDTO.java  # 分类查询
├── vo/           # 视图对象
│   ├── ArticleVO.java     # 文章视图
│   ├── CategoryVO.java    # 分类视图
│   ├── TagVO.java        # 标签视图
│   ├── CommentVO.java    # 评论视图
│   └── AttachmentVO.java # 附件视图
└── service/      # 服务接口
    ├── ArticleService.java    # 文章服务
    ├── CategoryService.java   # 分类服务
    ├── TagService.java       # 标签服务
    ├── CommentService.java   # 评论服务
    └── AttachmentService.java # 附件服务
```

## 核心接口
1. ArticleService（继承BaseService<Article>）
   - 文章的CRUD操作
   - 文章分类管理
   - 文章标签管理
   - 文章评论管理
   - 文章统计分析
   - 相关文章推荐
   - 文章访问统计
   - 文章点赞/收藏
   
2. CategoryService（继承BaseService<Category>）
   - 分类的CRUD操作
   - 分类层级管理
   - 分类权限控制
   - 分类统计分析
   - 分类排序管理
   - 导航分类管理
   - 热门分类统计
   
3. TagService（继承BaseService<Tag>）
   - 标签的CRUD操作
   - 标签使用统计
   - 标签权重管理
   - 标签推荐管理
   - 相关标签分析
   - 文章标签关联
   
4. CommentService（继承BaseService<Comment>）
   - 评论的CRUD操作
   - 评论审核管理
   - 评论回复功能
   - 评论通知功能
   - 评论统计分析
   - 评论点赞功能
   
5. AttachmentService（继承BaseService<Attachment>）
   - 附件的CRUD操作
   - 附件上传下载
   - 附件预览功能
   - 附件权限控制
   - 附件统计分析
   - 存储空间管理

## 实体关系
1. Article（文章）
   - 一篇文章属于一个分类（Category）
   - 一篇文章可以有多个标签（Tag）
   - 一篇文章可以有多个评论（Comment）
   - 一篇文章可以有多个附件（Attachment）

2. Category（分类）
   - 一个分类可以有多篇文章（Article）
   - 一个分类可以有多个子分类（Category）
   - 一个分类只能有一个父分类（Category）

3. Tag（标签）
   - 一个标签可以关联多篇文章（Article）
   - 一篇文章可以有多个标签（Tag）

## 使用示例
```java
// 创建文章
ArticleCreateDTO createDTO = new ArticleCreateDTO()
    .setTitle("示例文章")
    .setContent("文章内容")
    .setCategoryId(1L)
    .setTags(Arrays.asList("标签1", "标签2"));
ArticleVO article = articleService.create(createDTO);

// 查询分类下的文章
CategoryQueryDTO queryDTO = new CategoryQueryDTO()
    .setCategoryId(1L)
    .setPage(1)
    .setSize(10);
Page<ArticleVO> articles = articleService.queryByCategoryId(queryDTO);
```

## 配置说明
```yaml
knowledge:
  article:
    # 文章配置
    max-title-length: 100
    max-content-length: 50000
    allow-comment: true
    
  category:
    # 分类配置
    max-level: 3
    allow-custom: true
    
  attachment:
    # 附件配置
    max-size: 10485760
    allowed-types: [".doc", ".docx", ".pdf"]
```

## 依赖说明
- Spring Boot 3.2.2
- Spring Data JPA
- MyBatis-Plus（通过base-model）
- Lombok

## 注意事项
1. 文章内容建议使用Markdown格式
2. 文章分类建议不超过三级
3. 标签数量建议控制在合理范围
4. 评论功能可以根据需要开启或关闭
5. 附件上传需要注意大小和类型限制 