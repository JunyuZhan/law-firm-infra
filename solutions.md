# 解决Bean冲突问题

## 已解决的冲突

1. **CaseDocumentMapper冲突**
   - 问题：`com.lawfirm.model.cases.mapper.business.CaseDocumentMapper` 与 `com.lawfirm.model.document.mapper.CaseDocumentMapper` 冲突
   - 解决方案：将文档模块的 `CaseDocumentMapper` 重命名为 `DocumentCaseMapper`

2. **ContractMapper冲突**
   - 问题：`com.lawfirm.model.personnel.mapper.ContractMapper` 与 `com.lawfirm.model.contract.mapper.ContractMapper` 冲突
   - 解决方案：将人事模块的 `ContractMapper` 重命名为 `PersonnelContractMapper`

3. **ContactMapper冲突**
   - 问题：`com.lawfirm.model.personnel.mapper.ContactMapper` 与 `com.lawfirm.model.client.mapper.ContactMapper` 冲突
   - 解决方案：将人事模块的 `ContactMapper` 重命名为 `PersonnelContactMapper`

4. **MapperScan配置冲突**
   - 问题：案件模块中的 `@MapperScan("com.lawfirm.model.cases.mapper")` 与全局配置冲突
   - 解决方案：注释掉案件模块中的 `@MapperScan` 注解，统一使用全局配置

5. **Case类作为Java关键字的潜在问题**
   - 问题：`Case` 是Java关键字，可能在某些框架中导致问题
   - 解决方案：在MyBatis-Plus配置中添加 `use-java-type: true` 设置，允许使用关键字作为类型名

## 配置优化

1. 统一管理Mapper扫描包：
   ```yml
   mybatis-plus:
     mapper:
       packages: com.lawfirm.model.*.mapper
   ```

2. 优化类型别名配置：
   ```yml
   mybatis-plus:
     type-aliases-package: com.lawfirm.model.*.entity,com.lawfirm.model.*.entity.*
     type-aliases-super-type: com.lawfirm.common.data.entity.BaseEntity
   ```

3. 去除冗余的 `@MapperScan` 注解，使用全局配置避免重复扫描。

## 后续优化建议

1. 在每个Mapper接口上添加 `@Mapper` 注解，增强IDE的智能提示

2. 使用更具有明确性的命名约定，避免不同模块之间的命名冲突

3. 考虑在实体类名称上避免使用Java关键字（例如将 `Case` 改为 `LawCase` 或 `CaseEntity`）

4. 为项目添加更完善的日志记录，便于问题排查 