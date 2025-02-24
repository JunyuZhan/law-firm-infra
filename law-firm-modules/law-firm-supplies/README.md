# 办公用品管理模块 (Supplies Module)

## 模块说明
办公用品管理模块是律师事务所管理系统的办公用品管理和采购模块，负责管理律所的办公用品申请、采购、库存、领用等全流程。该模块提供了完整的办公用品生命周期管理，确保办公资源的合理使用和分配。

## 核心功能

### 1. 用品管理
- 用品分类管理
- 用品信息维护
- 用品编码管理
- 用品规格管理
- 供应商管理

### 2. 采购管理
- 采购申请管理
- 采购计划制定
- 采购审批流程
- 采购订单管理
- 采购验收入库

### 3. 库存管理
- 库存实时监控
- 库存预警管理
- 库存盘点管理
- 库存调拨管理
- 库存报表统计

### 4. 领用管理
- 领用申请管理
- 领用审批流程
- 领用记录跟踪
- 使用情况统计
- 部门配额管理

## 核心组件

### 1. 用品服务
- SuppliesService：用品服务接口
- CategoryService：分类服务
- SupplierService：供应商服务
- SpecificationService：规格服务
- CodeGeneratorService：编码生成服务

### 2. 采购服务
- PurchaseService：采购服务接口
- PurchaseRequestService：申请服务
- PurchaseOrderService：订单服务
- PurchaseApprovalService：审批服务
- PurchaseReceiveService：验收服务

### 3. 库存服务
- InventoryService：库存服务接口
- StockMonitorService：库存监控服务
- StockCheckService：盘点服务
- StockTransferService：调拨服务
- WarningService：预警服务

### 4. 领用服务
- RequisitionService：领用服务接口
- ApprovalService：审批服务
- RecordService：记录服务
- QuotaService：配额服务
- StatisticsService：统计服务

## 使用示例

### 1. 创建采购申请
```java
@Autowired
private PurchaseRequestService purchaseRequestService;

public PurchaseRequestDTO createRequest(PurchaseCreateRequest request) {
    // 创建采购申请
    PurchaseRequest purchaseRequest = new PurchaseRequest()
        .setRequestNo(generateRequestNo())
        .setRequestType(PurchaseTypeEnum.REGULAR)
        .setItems(request.getItems())
        .setTotalAmount(calculateAmount(request.getItems()))
        .setRequestBy(SecurityUtils.getCurrentUser().getId())
        .setDepartment(request.getDepartmentId())
        .setRemark(request.getRemark());
    
    // 提交申请
    return purchaseRequestService.createRequest(purchaseRequest);
}
```

### 2. 处理领用申请
```java
@Autowired
private RequisitionService requisitionService;

public void handleRequisition(Long requisitionId, RequisitionAction action) {
    // 获取领用申请
    Requisition requisition = requisitionService.getRequisition(requisitionId);
    
    // 处理申请
    switch (action) {
        case APPROVE -> requisitionService.approve(requisitionId);
        case REJECT -> requisitionService.reject(requisitionId, action.getReason());
        case CANCEL -> requisitionService.cancel(requisitionId);
    }
}
```

### 3. 库存盘点
```java
@Autowired
private StockCheckService stockCheckService;

public StockCheckResult checkStock(StockCheckRequest request) {
    // 创建盘点任务
    StockCheck stockCheck = new StockCheck()
        .setCheckNo(generateCheckNo())
        .setCheckType(CheckTypeEnum.FULL)
        .setWarehouse(request.getWarehouseId())
        .setChecker(SecurityUtils.getCurrentUser().getId())
        .setCheckTime(LocalDateTime.now());
    
    // 执行盘点
    return stockCheckService.executeCheck(stockCheck);
}
```

## 配置说明

### 1. 用品配置
```yaml
supplies:
  # 编号规则
  number:
    purchase-prefix: PUR
    requisition-prefix: REQ
    stock-prefix: STK
    
  # 分类配置
  category:
    max-level: 3
    enable-custom: true
    
  # 预警配置
  warning:
    check-interval: 1h
    email-notify: true
```

### 2. 审批配置
```yaml
approval:
  # 采购审批
  purchase:
    enable: true
    amount-threshold: 5000
    max-level: 3
    
  # 领用审批
  requisition:
    enable: true
    quota-check: true
    manager-approval: true
```

## 注意事项
1. 库存准确管理
2. 采购流程规范
3. 预算额度控制
4. 及时库存盘点
5. 定期数据统计 