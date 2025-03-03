# 工作流模型 Mapper 接口

本目录包含工作流模型的数据访问层接口，这些接口用于连接实体类与数据库表之间的映射关系。遵循MyBatis-Plus框架规范，实现了对工作流相关数据的高效访问。

## 主要接口

### ProcessMapper
- 对应实体类：`BaseProcess`
- 对应数据表：`workflow_process`
- 职责：处理流程基础数据的增删改查操作
- 常用场景：流程创建、状态更新、流程查询等

### TaskMapper
- 对应实体类：`ProcessTask`
- 对应数据表：`workflow_task`
- 职责：处理任务数据的增删改查操作
- 常用场景：任务分配、任务状态变更、待办任务查询等

### CommonProcessMapper
- 对应实体类：`CommonProcess`
- 对应数据表：`workflow_process`（通过继承BaseProcess）
- 职责：处理通用流程的增删改查操作
- 常用场景：通用流程的创建和管理

## 使用示例

```java
@Autowired
private ProcessMapper processMapper;

// 查询流程
BaseProcess process = processMapper.selectById(processId);

// 更新流程状态
process.setStatus(ProcessStatusEnum.RUNNING.getCode());
processMapper.updateById(process);

// 条件查询
List<BaseProcess> processes = processMapper.selectList(
    new LambdaQueryWrapper<BaseProcess>()
        .eq(BaseProcess::getBusinessType, "case")
        .eq(BaseProcess::getStatus, ProcessStatusEnum.PENDING.getCode())
);
```

## 开发规范

1. 这些Mapper接口都继承自MyBatis-Plus的BaseMapper，自带基础的CRUD操作
2. 如需添加复杂查询，可在Mapper接口中定义自定义方法，并在对应的XML文件中实现
3. 实体类与数据表的映射通过@TableName和@TableField注解完成
4. 添加新方法时，请同时更新单元测试以确保功能正常

## 扩展指南

如需扩展现有Mapper功能，请遵循以下步骤：

1. 在Mapper接口中定义新方法
   ```java
   List<BaseProcess> selectByBusinessTypeAndStatus(@Param("businessType") String businessType, 
                                                 @Param("status") Integer status);
   ```

2. 在resources/mapper目录下创建对应的XML文件（如ProcessMapper.xml）
   ```xml
   <select id="selectByBusinessTypeAndStatus" resultType="com.lawfirm.model.workflow.entity.base.BaseProcess">
       SELECT * FROM workflow_process 
       WHERE business_type = #{businessType} AND status = #{status}
   </select>
   ```

3. 在Service层中调用自定义方法

## 性能优化建议

1. 对于频繁访问的查询，考虑添加适当的索引
2. 复杂查询请使用分页功能减少数据传输量
3. 避免使用select *，只查询必要的字段
4. 大数据量场景下考虑使用批量操作方法

## 注意事项

1. 这些Mapper接口都继承自MyBatis-Plus的BaseMapper，自带基础的CRUD操作
2. 如需添加复杂查询，可在Mapper接口中定义自定义方法，并在对应的XML文件中实现
3. 实体类与数据表的映射通过@TableName和@TableField注解完成 