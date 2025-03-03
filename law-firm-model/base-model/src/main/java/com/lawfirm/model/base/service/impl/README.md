# BaseServiceImpl使用说明

`BaseServiceImpl`是一个通用的服务实现基类，为基础CRUD操作提供了统一的实现。本类位于`base-model`模块，旨在为各个业务模块提供一致的数据访问方法。

## 使用方法

要使用`BaseServiceImpl`，您的服务实现类需要继承它并传入相应的泛型参数：

```java
@Service
public class YourServiceImpl extends BaseServiceImpl<YourMapper, YourEntity> implements YourService {
    
    // 构造函数注入
    public YourServiceImpl(YourMapper yourMapper) {
        super(yourMapper);
    }
    
    // 实现自定义业务方法...
}
```

## 泛型参数说明

- `M`: 继承自MyBatis Plus的BaseMapper接口的Mapper类型
- `T`: 实体类型

## 提供的方法

BaseServiceImpl提供了以下通用方法：

- `save(T entity)`: 保存单个实体
- `saveBatch(List<T> entities)`: 批量保存实体
- `update(T entity)`: 更新单个实体
- `updateBatch(List<T> entities)`: 批量更新实体
- `remove(Long id)`: 根据ID删除实体
- `removeBatch(List<Long> ids)`: 批量删除实体
- `getById(Long id)`: 根据ID查询实体
- `list(QueryWrapper<T> wrapper)`: 查询实体列表
- `page(Page<T> page, QueryWrapper<T> wrapper)`: 分页查询实体
- `count(QueryWrapper<T> wrapper)`: 统计实体数量
- `exists(QueryWrapper<T> wrapper)`: 检查实体是否存在

## 注意事项

1. 所有实体类都应该使用Long类型的主键ID
2. 所有的增删改操作都使用了@Transactional注解确保事务一致性
3. 该实现仅提供基础操作，复杂的业务逻辑应在子类中实现
4. 本类已从common-data模块移至base-model模块，以符合项目架构和依赖层级规范 