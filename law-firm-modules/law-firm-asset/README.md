# 资产管理模块 (Asset Module)

## 模块说明
资产管理模块是律师事务所管理系统的资产管理和维护模块，负责管理律所的固定资产、无形资产等各类资产的全生命周期。该模块提供了资产采购、使用、维护、处置等完整的管理功能，确保资产的合理利用和有效管理。

## 核心功能

### 1. 资产管理
- 资产分类管理
  - 固定资产
  - 无形资产
  - 低值易耗品
  - 软件资产
- 资产信息管理
  - 基础信息维护
  - 资产编码管理
  - 资产档案管理
  - 资产状态管理
- 资产财务管理
  - 资产价值核算
  - 折旧管理
  - 摊销管理
  - 减值管理

### 2. 资产操作
- 资产采购
  - 采购申请
  - 采购审批
  - 验收入库
  - 资产登记
- 资产领用
  - 领用申请
  - 审批流程
  - 领用记录
  - 归还管理
- 资产调拨
  - 部门间调拨
  - 网点间调拨
  - 调拨审批
  - 调拨记录

### 3. 资产维护
- 维修管理
  - 维修申请
  - 维修记录
  - 维修费用
  - 维修评估
- 保养管理
  - 保养计划
  - 保养执行
  - 保养记录
  - 保养评价
- 巡检管理
  - 巡检计划
  - 巡检执行
  - 巡检记录
  - 问题跟踪

### 4. 资产处置
- 报废管理
  - 报废申请
  - 报废审批
  - 报废处理
  - 记录归档
- 转让管理
  - 转让申请
  - 转让评估
  - 转让审批
  - 交接管理
- 清理管理
  - 清理计划
  - 清理执行
  - 清理记录
  - 资产核销

## 核心组件

### 1. 资产服务
- AssetService：资产服务接口
- CategoryService：分类服务
- FinanceService：财务服务
- CodeGeneratorService：编码服务
- ArchiveService：档案服务

### 2. 操作服务
- PurchaseService：采购服务
- RequisitionService：领用服务
- TransferService：调拨服务
- ApprovalService：审批服务
- RecordService：记录服务

### 3. 维护服务
- MaintenanceService：维护服务
- RepairService：维修服务
- InspectionService：巡检服务
- ProblemService：问题服务
- EvaluationService：评估服务

### 4. 处置服务
- DisposalService：处置服务
- ScrapService：报废服务
- TransferService：转让服务
- ClearanceService：清理服务
- VerificationService：核销服务

## 使用示例

### 1. 资产登记
```java
@Autowired
private AssetService assetService;

public AssetDTO registerAsset(AssetRegisterRequest request) {
    // 创建资产信息
    Asset asset = new Asset()
        .setAssetNo(generateAssetNo())
        .setName(request.getName())
        .setCategory(request.getCategory())
        .setSpecification(request.getSpecification())
        .setPrice(request.getPrice())
        .setPurchaseDate(request.getPurchaseDate())
        .setDepartment(request.getDepartmentId())
        .setManager(request.getManagerId())
        .setStatus(AssetStatusEnum.NORMAL);
    
    // 登记资产
    return assetService.registerAsset(asset);
}
```

### 2. 资产维修
```java
@Autowired
private RepairService repairService;

public RepairDTO createRepairRequest(RepairRequest request) {
    // 创建维修单
    RepairOrder order = new RepairOrder()
        .setOrderNo(generateOrderNo())
        .setAssetId(request.getAssetId())
        .setProblem(request.getProblem())
        .setUrgency(RepairUrgencyEnum.valueOf(request.getUrgency()))
        .setExpectedTime(request.getExpectedTime())
        .setApplicant(SecurityUtils.getCurrentUser().getId())
        .setRemark(request.getRemark());
    
    // 提交维修
    return repairService.createRepair(order);
}
```

### 3. 资产处置
```java
@Autowired
private DisposalService disposalService;

public DisposalDTO disposeAsset(DisposalRequest request) {
    // 创建处置单
    DisposalOrder order = new DisposalOrder()
        .setOrderNo(generateOrderNo())
        .setAssetIds(request.getAssetIds())
        .setType(DisposalTypeEnum.valueOf(request.getType()))
        .setReason(request.getReason())
        .setExpectedAmount(request.getExpectedAmount())
        .setApplicant(SecurityUtils.getCurrentUser().getId())
        .setAttachments(request.getAttachments());
    
    // 提交处置
    return disposalService.createDisposal(order);
}
```

## 配置说明

### 1. 资产配置
```yaml
asset:
  # 编号规则
  number:
    asset-prefix: AST
    repair-prefix: REP
    disposal-prefix: DIS
    
  # 分类配置
  category:
    max-level: 3
    enable-custom: true
    
  # 财务配置
  finance:
    depreciation-methods:
      - straight-line
      - double-declining
      - sum-of-years
```

### 2. 维护配置
```yaml
maintenance:
  # 维修配置
  repair:
    enable-auto-assign: true
    max-processing-time: 48h
    
  # 保养配置
  maintenance:
    reminder-days: 7
    auto-generate-plan: true
    
  # 巡检配置
  inspection:
    period: weekly
    generate-report: true
```

## 注意事项
1. 资产信息准确
2. 维护记录完整
3. 处置流程规范
4. 财务数据准确
5. 定期资产盘点 