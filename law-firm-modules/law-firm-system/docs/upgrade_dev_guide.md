# 系统升级开发指南

## 一、升级包开发规范

### 1.1 版本号规范
- 主版本号.次版本号.修订号
- 例如: 1.0.1
- 整体升级: 增加次版本号
- 补丁升级: 增加修订号

### 1.2 SQL脚本开发规范
```sql
-- 1. 命名规范
-- V{version}__{description}.sql
-- 例如: V1.0.1__add_user_table.sql

-- 2. 内容格式
-- 文件头注释
/*
 * 版本: 1.0.1
 * 描述: 添加用户表
 * 作者: xxx
 * 日期: 2024-01-24
 */

-- 具体SQL语句
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    ...
) COMMENT '用户表';

-- 3. 回滚脚本
-- R1.0.1__add_user_table.sql
DROP TABLE IF EXISTS sys_user;
```

### 1.3 文件更新规范
1. 新增文件
```plaintext
new/
├── modules
│   └── new_module
│       ├── src
│       │   └── main
│       │       └── java
│       │           └── com
│       │               └── lawfirm
│       │                   └── newmodule
│       └── pom.xml
```

2. 更新文件
```plaintext
update/
├── modules
│   └── existing_module
│       └── src
│           └── main
│               └── java
│               └── com
│                   └── lawfirm
│                       └── module
│                           └── updated_file.java
```

3. 删除文件列表(delete.txt)
```plaintext
modules/old_module/src/main/java/com/lawfirm/old/OldFile.java
modules/old_module/src/main/resources/old_config.yml
```

## 二、开发流程

### 2.1 创建升级包
1. 创建目录结构
2. 编写meta.json
3. 准备SQL脚本
4. 整理更新文件
5. 打包并测试

### 2.2 SQL脚本开发
1. 数据库变更
```sql
-- 1. 添加表
CREATE TABLE IF NOT EXISTS new_table (...);

-- 2. 修改表
ALTER TABLE existing_table ADD COLUMN new_column VARCHAR(50);

-- 3. 数据迁移
INSERT INTO new_table SELECT * FROM old_table;

-- 4. 删除表
DROP TABLE IF EXISTS old_table;
```

2. 数据更新
```sql
-- 1. 添加数据
INSERT INTO sys_dict(type, code, name) VALUES('status', '0', '正常');

-- 2. 更新数据
UPDATE sys_user SET status = '1' WHERE type = 'admin';

-- 3. 删除数据
DELETE FROM sys_log WHERE create_time < DATE_SUB(NOW(), INTERVAL 1 YEAR);
```

### 2.3 回滚脚本开发
1. 数据库回滚
```sql
-- 1. 删除新表
DROP TABLE IF EXISTS new_table;

-- 2. 删除新列
ALTER TABLE existing_table DROP COLUMN new_column;

-- 3. 还原数据
INSERT INTO old_table SELECT * FROM new_table;
```

2. 数据回滚
```sql
-- 1. 删除新增数据
DELETE FROM sys_dict WHERE type = 'status';

-- 2. 还原更新数据
UPDATE sys_user SET status = '0' WHERE type = 'admin';
```

## 三、测试规范

### 3.1 升级测试
1. 准备测试环境
2. 执行升级脚本
3. 验证数据正确性
4. 测试新功能
5. 检查系统日志

### 3.2 回滚测试
1. 执行回滚脚本
2. 验证数据还原
3. 检查系统功能
4. 确认日志记录

### 3.3 异常测试
1. 升级中断测试
2. 数据库异常测试
3. 文件操作异常测试
4. 并发升级测试

## 四、发布规范

### 4.1 升级包命名
```plaintext
# 整体升级包
upgrade_full_${version}.zip

# 补丁升级包
upgrade_patch_${version}_${sequence}.zip
```

### 4.2 版本说明
```markdown
# 版本 1.0.1

## 新增功能
1. 功能A
2. 功能B

## 修复问题
1. 问题A
2. 问题B

## 升级建议
1. 建议A
2. 建议B

## 注意事项
1. 注意A
2. 注意B
```

### 4.3 发布清单
1. 升级包文件
2. 版本说明文档
3. 升级指南
4. 测试报告

## 五、开发工具

### 5.1 推荐工具
1. 数据库工具
   - Navicat
   - DBeaver
   - MySQL Workbench

2. 开发工具
   - IntelliJ IDEA
   - Visual Studio Code
   - Eclipse

3. 版本控制
   - Git
   - SVN

4. 文档工具
   - Markdown编辑器
   - Office套件

### 5.2 常用命令
```bash
# 数据库备份
mysqldump -h host -u user -p database > backup.sql

# 数据库还原
mysql -h host -u user -p database < backup.sql

# 打包命令
zip -r upgrade_package.zip meta.json scripts files

# 解压命令
unzip upgrade_package.zip -d target_dir
``` 