# law-firm-analysis（数据分析模块）

## 模块定位
系统数据分析与决策支持，面向律所管理层、合伙人、律师等不同角色，提供多维度业务洞察和数据驱动决策能力。

---

## 当前主要能力与集成

- **案件分析**：支持按类型、时间区间等多维度统计案件数量。
- **服务注入**：
  - AI智能分析（core-ai，`AiService`）
  - 审计日志（log-model，`AuditService`）
  - 全文检索（search-model，`SearchService`）
  - 消息推送（core-message，`MessageSender`）
  - 文件存储（core-storage，`FileOperator`）
- **依赖模型**：analysis-model、case-model、log-model、search-model等。
- **自动配置**：支持Spring自动装配，Bean名称均带`analysis`前缀防冲突。

---

## 典型用法

- 在`AnalysisServiceImpl`中可直接调用AI、审计、检索、消息、存储等服务，灵活扩展分析、推理、日志、导出等场景。
- 支持自定义分析逻辑、报表API、权限隔离等。

---

## 功能规划（持续迭代中）

1. **业务数据分析**
   - 各类案件数量、类型、进展、结案率等统计
   - 客户来源、行业分布、活跃度分析
   - 合同签署量、履约情况统计
   - 收入、支出、利润等财务数据分析
2. **绩效分析**
   - 律师/团队业绩排行
   - 工时统计与利用率
   - 任务完成率、逾期率
   - 绩效考核与趋势
3. **财务分析**
   - 收入结构、费用结构分析
   - 账单回款周期、坏账率
   - 费用分摊与成本核算
   - 财务报表生成
4. **客户分析**
   - 客户生命周期价值（LTV）
   - 客户满意度与流失率
   - 客户风险等级分布
   - 客户贡献度排行
5. **案件类型与成功率分析**
   - 各类型案件的胜诉/和解/败诉率
   - 案件周期与结案效率
   - 典型案例分析
6. **趋势分析**
   - 业务量、收入、客户数等多维度趋势
   - 季度/年度同比、环比分析
   - 预测分析（如案件量、收入趋势预测）
7. **可视化报表**
   - 支持多维度自定义报表
   - 图表、数据大屏展示
   - 报表导出与分享
8. **分析模型与API**
   - 支持自定义分析模型配置
   - 对外提供统一的数据分析API和报表API
9. **权限与数据隔离**
   - 分析数据的权限控制
   - 多维度数据隔离（如按部门、团队、个人）

---

## 技术架构与扩展建议
- Spring Boot + MyBatis-Plus + 多模块依赖
- 支持AI、日志、检索、消息、存储等核心能力灵活扩展
- 推荐结合数据仓库、OLAP、可视化组件（如ECharts、DataV等）
- 支持大数据量分析与高性能查询
- 统一的分析API与报表API接口

---

## 依赖模块
- analysis-model、case-model、log-model、search-model、core-ai、core-message、core-storage等
- 其它业务模块：client、contract、finance、personnel、system、document、archive、knowledge等
