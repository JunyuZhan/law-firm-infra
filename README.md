# 律师事务所管理系统

## 项目简介
律师事务所管理系统是一套专为律所数字化转型打造的综合管理平台，采用Spring Boot分层多模块单体应用架构，涵盖案件、客户、合同、文档、财务、人事等核心业务，助力律所高效运营与智能管理。

## 产品亮点
- 全流程案件管理，支持团队协作与进度跟踪
- 客户与合同一体化管理，提升服务体验
- 文档集中存储与全文检索，资料安全高效
- 财务、账单、绩效一站式管理
- 灵活的权限体系，保障数据安全
- 支持本地与Docker一键部署，运维便捷
- 合规安全、智能分析、可扩展性强

## 系统架构
本系统采用分层多模块架构，主要包括：
- **依赖管理层**：统一依赖版本
- **通用模块层**：工具、核心、Web、缓存等
- **数据模型层**：领域数据结构
- **核心功能层**：AI、审计、消息、存储等
- **业务模块层**：认证、案件、合同等
- **API服务层**：统一REST API接口

![系统架构图](docs/arch.png)
*如无图片，可参考下表：*

| 层级         | 主要内容           |
|--------------|--------------------|
| 依赖管理层   | law-firm-dependencies：统一依赖版本管理 |
| 通用模块层   | law-firm-common：工具、核心、Web、缓存等 |
| 数据模型层   | law-firm-model：领域数据结构 |
| 核心功能层   | law-firm-core：AI、审计、消息、存储等 |
| 业务模块层   | law-firm-modules：认证、案件、合同等 |
| API服务层    | law-firm-api：统一REST API接口 |

## 主要功能模块
| 模块         | 主要功能           | 亮点/特色           |
|--------------|--------------------|---------------------|
| 认证与权限   | 登录、权限、角色管理   | 多级权限、单点登录、操作审计 |
| 案件管理     | 案件全生命周期、团队协作 | 阶段流转、进度跟踪、归档管理 |
| 客户管理     | 客户信息、关系、标签   | 客户画像、风险评估、分组管理 |
| 合同管理     | 合同起草、审批、提醒   | 模板复用、到期提醒、流程审批 |
| 文档管理     | 上传、检索、权限控制   | 全文检索、版本管理、在线预览 |
| 财务管理     | 账单、收付款、报表     | 多维统计、自动催收、成本核算 |
| 人事管理     | 档案、绩效、工时       | 绩效考核、培训管理、工时统计 |
| 知识库       | 法律知识、案例、模板   | 智能分类、全文检索、知识分享 |
| 数据分析     | 业务、绩效、趋势分析   | 可视化报表、预测分析         |

## 部署与运维

### 系统运行的硬件要求
| 环境类型   | CPU   | 内存   | 硬盘   | 网络         |
|------------|-------|--------|--------|--------------|
| 生产环境   | 4核+  | 16GB+  | 100GB+ SSD（建议独立数据盘） | 千兆以太网 |
| 开发/测试  | 2核   | 8GB    | 50GB   | 百兆及以上   |

### 部署方式
- 本地启动：双击`start.bat`（Windows）或运行`./start.sh`（Linux/Mac）
- Docker部署：参考`docker/README.md`，一条命令完成所有服务启动
- 详细部署步骤请查阅[Docker部署文档](docker/README.md)

### 主要端口
| 服务         | 端口号 | 说明           |
|--------------|--------|----------------|
| 系统入口     | 8080   | Web主入口      |
| MySQL        | 13306  | Docker外部端口 |
| Redis        | 6379   |                |
| MinIO API    | 9000   |                |
| MinIO控制台  | 9001   |                |

## 文档与支持
- [用户手册](docs/user-guide.md)
- [模块结构说明](docs/modules.md)
- [Docker部署文档](docker/README.md)
- 如有疑问请优先查阅上述文档

## 常见问题
- 启动失败：检查端口占用、依赖服务状态或查看日志
- 数据库连接异常：确认数据库服务已启动，端口配置正确
- 日志查看：本地`logs/`目录，Docker部署可用`docker-compose logs api`
- 如何升级：请关注[CHANGELOG.md](CHANGELOG.md)和官方发布说明，升级前请备份数据
- 更多问题请查阅用户手册和部署文档

---

## 联系我们
如需进一步技术支持、定制开发或商务合作，请联系项目维护团队：
- 邮箱：junyuzhan@yOutlook.com
- 官网：https://junyuzhan.com
- 或通过项目Issue区留言

## 开源协议

本项目采用 [GNU GPL v3.0](LICENSE) 开源协议，任何人可以自由使用、修改和分发本项目，但必须遵循 GPL v3.0 的相关条款，详情请见根目录 LICENSE 文件。
