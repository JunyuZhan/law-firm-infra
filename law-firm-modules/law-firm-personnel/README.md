# 人事管理模块 (Personnel Module)

## 模块说明
人事管理模块是律师事务所管理系统的人力资源管理模块，负责管理律所的律师、员工等人员信息，包括人员档案、职称管理、考勤管理、绩效考核等功能。该模块为律所提供了完整的人力资源管理解决方案。

## 核心功能

### 1. 人员管理
- 律师信息管理
- 员工信息管理
- 人员档案管理
- 职称等级管理
- 执业资格管理

### 2. 考勤管理
- 考勤规则配置
- 考勤记录管理
- 请假管理
- 加班管理
- 统计分析

### 3. 绩效管理
- 绩效指标管理
- 绩效考核管理
- 绩效评估管理
- 绩效面谈管理
- 绩效报告

### 4. 薪酬管理
- 薪资结构管理
- 薪资计算规则
- 薪资发放管理
- 社保公积金
- 个税管理

## 核心组件

### 1. 人员服务
- PersonnelService：人员服务接口
- LawyerService：律师服务
- EmployeeService：员工服务
- QualificationService：资格服务
- ArchiveService：档案服务

### 2. 考勤服务
- AttendanceService：考勤服务
- LeaveService：请假服务
- OvertimeService：加班服务
- ShiftService：排班服务
- StatisticsService：统计服务

### 3. 绩效服务
- PerformanceService：绩效服务
- AssessmentService：考核服务
- EvaluationService：评估服务
- InterviewService：面谈服务
- ReportService：报告服务

### 4. 薪酬服务
- SalaryService：薪资服务
- PayrollService：工资单服务
- BenefitService：福利服务
- InsuranceService：社保服务
- TaxService：个税服务

## 使用示例

### 1. 添加律师
```java
@Autowired
private LawyerService lawyerService;

public LawyerDTO addLawyer(LawyerCreateRequest request) {
    // 创建律师信息
    Lawyer lawyer = new Lawyer()
        .setName(request.getName())
        .setLicenseNumber(request.getLicenseNumber())
        .setTitle(LawyerTitleEnum.SENIOR)
        .setDepartment(request.getDepartmentId())
        .setSpecialties(request.getSpecialties());
    
    // 保存律师信息
    return lawyerService.createLawyer(lawyer);
}
```

### 2. 记录考勤
```java
@Autowired
private AttendanceService attendanceService;

public void recordAttendance(AttendanceRequest request) {
    // 创建考勤记录
    Attendance attendance = new Attendance()
        .setEmployeeId(request.getEmployeeId())
        .setType(AttendanceTypeEnum.CHECK_IN)
        .setTime(LocalDateTime.now())
        .setLocation(request.getLocation())
        .setRemark(request.getRemark());
    
    // 保存考勤记录
    attendanceService.recordAttendance(attendance);
}
```

### 3. 绩效评估
```java
@Autowired
private PerformanceService performanceService;

public PerformanceResult evaluate(Long employeeId, PerformanceRequest request) {
    // 创建评估记录
    Performance performance = new Performance()
        .setEmployeeId(employeeId)
        .setPeriod(request.getPeriod())
        .setIndicators(request.getIndicators())
        .setScore(request.getScore())
        .setComments(request.getComments());
    
    // 执行评估
    return performanceService.evaluate(performance);
}
```

## 配置说明

### 1. 人员配置
```yaml
personnel:
  # 编号规则
  number:
    lawyer-prefix: L
    employee-prefix: E
    sequence-length: 4
    
  # 职称配置
  title:
    auto-promotion: false
    min-years: 3
    
  # 档案配置
  archive:
    storage-path: /personnel/archives
    backup-enabled: true
```

### 2. 考勤配置
```yaml
attendance:
  # 考勤规则
  rule:
    work-hours: 8
    flex-time: true
    grace-period: 15
    
  # 加班配置
  overtime:
    require-approval: true
    min-hours: 1
    max-hours: 4
```

## 注意事项
1. 人员信息安全
2. 考勤数据准确
3. 绩效评估公平
4. 薪资计算准确
5. 档案完整管理 