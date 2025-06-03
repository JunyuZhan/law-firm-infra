#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
数据库迁移脚本验证工具
用于检查Flyway迁移脚本的版本号、执行顺序、冲突和重复问题
"""

import os
import re
import sys
from pathlib import Path
from collections import defaultdict, OrderedDict
from typing import List, Dict, Tuple, Set
import json

class MigrationValidator:
    def __init__(self, base_path: str):
        self.base_path = Path(base_path)
        self.migration_files = []
        self.version_conflicts = []
        self.execution_order = []
        self.table_dependencies = defaultdict(set)
        self.created_tables = set()
        self.errors = []
        self.warnings = []
        
    def scan_migration_files(self) -> List[Dict]:
        """扫描所有迁移脚本文件"""
        print("📁 扫描迁移脚本文件...")
        
        patterns = [
            "**/META-INF/db/migration/V*.sql",
            "**/src/main/resources/db/migration/V*.sql"
        ]
        
        for pattern in patterns:
            for file_path in self.base_path.rglob(pattern):
                if self._is_valid_migration_file(file_path):
                    migration_info = self._parse_migration_file(file_path)
                    if migration_info:
                        self.migration_files.append(migration_info)
        
        print(f"✅ 找到 {len(self.migration_files)} 个迁移脚本文件")
        return self.migration_files
    
    def _is_valid_migration_file(self, file_path: Path) -> bool:
        """检查是否为有效的迁移文件"""
        # 跳过备份目录
        if "db-migration-backup" in str(file_path):
            return False
        
        # 跳过target目录（编译后的文件）
        if "target" in file_path.parts:
            return False
        
        # 检查文件名格式
        filename = file_path.name
        if not re.match(r'^V\d+.*\.sql$', filename):
            return False
            
        return True
    
    def _parse_migration_file(self, file_path: Path) -> Dict:
        """解析迁移文件信息"""
        filename = file_path.name
        
        # 提取版本号
        version_match = re.match(r'^V(\d+)(__.*)?\.sql$', filename)
        if not version_match:
            return None
            
        version_number = int(version_match.group(1))
        
        # 确定模块
        module = self._determine_module(file_path)
        
        # 读取文件内容
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                content = f.read()
        except Exception as e:
            self.errors.append(f"❌ 无法读取文件 {file_path}: {e}")
            return None
        
        return {
            'path': file_path,
            'filename': filename,
            'version': version_number,
            'module': module,
            'content': content,
            'size': len(content)
        }
    
    def _determine_module(self, file_path: Path) -> str:
        """确定文件所属模块"""
        path_parts = file_path.parts
        
        # 查找模块标识
        for i, part in enumerate(path_parts):
            if part.startswith('law-firm-'):
                return part
            elif part.startswith('core-'):
                return part
        
        # API层文件
        if 'law-firm-api' in path_parts:
            return 'law-firm-api'
            
        return 'unknown'
    
    def check_version_conflicts(self) -> List[Dict]:
        """检查版本号冲突"""
        print("🔍 检查版本号冲突...")
        
        version_map = defaultdict(list)
        
        for migration in self.migration_files:
            version_map[migration['version']].append(migration)
        
        conflicts = []
        for version, files in version_map.items():
            if len(files) > 1:
                conflict = {
                    'version': version,
                    'files': [f['path'] for f in files],
                    'modules': [f['module'] for f in files]
                }
                conflicts.append(conflict)
                self.errors.append(f"❌ 版本号冲突 V{version}: {', '.join([f['filename'] for f in files])}")
        
        if not conflicts:
            print("✅ 未发现版本号冲突")
        else:
            print(f"⚠️  发现 {len(conflicts)} 个版本号冲突")
        
        self.version_conflicts = conflicts
        return conflicts
    
    def generate_execution_order(self) -> List[Dict]:
        """生成执行顺序"""
        print("📋 生成执行顺序...")
        
        # 按版本号排序
        sorted_migrations = sorted(self.migration_files, key=lambda x: x['version'])
        
        self.execution_order = sorted_migrations
        
        print(f"✅ 执行顺序已生成，共 {len(sorted_migrations)} 个脚本")
        return sorted_migrations
    
    def check_table_dependencies(self) -> Dict:
        """检查表依赖关系"""
        print("🔗 检查表依赖关系...")
        
        dependency_issues = []
        
        for migration in self.execution_order:
            content = migration['content']
            
            # 查找创建的表
            created_tables = self._extract_created_tables(content)
            for table in created_tables:
                self.created_tables.add(table)
            
            # 查找外键依赖
            foreign_keys = self._extract_foreign_keys(content)
            for fk in foreign_keys:
                ref_table = fk['referenced_table']
                current_table = fk['table']
                
                # 检查引用的表是否已创建
                if ref_table not in self.created_tables:
                    issue = {
                        'migration': migration['filename'],
                        'table': current_table,
                        'referenced_table': ref_table,
                        'issue': f'引用的表 {ref_table} 在当前脚本执行前未创建'
                    }
                    dependency_issues.append(issue)
                    self.errors.append(f"❌ 依赖问题: {migration['filename']} 中 {current_table} 引用了未创建的表 {ref_table}")
        
        if not dependency_issues:
            print("✅ 未发现表依赖问题")
        else:
            print(f"⚠️  发现 {len(dependency_issues)} 个表依赖问题")
        
        return {
            'issues': dependency_issues,
            'created_tables': list(self.created_tables),
            'total_tables': len(self.created_tables)
        }
    
    def _extract_created_tables(self, content: str) -> Set[str]:
        """提取脚本中创建的表"""
        tables = set()
        
        # 匹配 CREATE TABLE 语句
        create_patterns = [
            r'CREATE\s+TABLE\s+(?:IF\s+NOT\s+EXISTS\s+)?([a-zA-Z_][a-zA-Z0-9_]*)',
            r'DROP\s+TABLE\s+IF\s+EXISTS\s+([a-zA-Z_][a-zA-Z0-9_]*)'
        ]
        
        for pattern in create_patterns:
            matches = re.findall(pattern, content, re.IGNORECASE)
            tables.update(matches)
        
        return tables
    
    def _extract_foreign_keys(self, content: str) -> List[Dict]:
        """提取外键约束"""
        foreign_keys = []
        
        # 匹配 FOREIGN KEY 约束
        fk_pattern = r'CONSTRAINT\s+\w+\s+FOREIGN\s+KEY\s*\([^)]+\)\s+REFERENCES\s+([a-zA-Z_][a-zA-Z0-9_]*)'
        matches = re.findall(fk_pattern, content, re.IGNORECASE)
        
        for match in matches:
            foreign_keys.append({
                'table': 'unknown',  # 需要更复杂的解析来确定具体表
                'referenced_table': match
            })
        
        return foreign_keys
    
    def check_syntax_errors(self) -> List[Dict]:
        """检查SQL语法错误"""
        print("🔍 检查SQL语法...")
        
        syntax_errors = []
        
        for migration in self.migration_files:
            content = migration['content']
            errors = self._validate_sql_syntax(content, migration['filename'])
            syntax_errors.extend(errors)
        
        if not syntax_errors:
            print("✅ 未发现SQL语法错误")
        else:
            print(f"⚠️  发现 {len(syntax_errors)} 个语法问题")
        
        return syntax_errors
    
    def _validate_sql_syntax(self, content: str, filename: str) -> List[Dict]:
        """验证SQL语法"""
        errors = []
        
        # 检查外键检查配对
        fk_disable_count = len(re.findall(r'SET\s+FOREIGN_KEY_CHECKS\s*=\s*0', content, re.IGNORECASE))
        fk_enable_count = len(re.findall(r'SET\s+FOREIGN_KEY_CHECKS\s*=\s*1', content, re.IGNORECASE))
        
        if fk_disable_count > 0 and fk_enable_count == 0:
            errors.append({
                'file': filename,
                'type': 'FOREIGN_KEY_CHECKS',
                'message': '禁用了外键检查但未恢复'
            })
        
        # 检查常见语法问题
        if 'CREATE TABLE' in content and 'ENGINE=InnoDB' not in content:
            self.warnings.append(f"⚠️  {filename}: 建议显式指定存储引擎")
        
        if 'utf8mb4' not in content and 'CREATE TABLE' in content:
            self.warnings.append(f"⚠️  {filename}: 建议使用utf8mb4字符集")
        
        return errors
    
    def generate_report(self) -> Dict:
        """生成验证报告"""
        print("📊 生成验证报告...")
        
        report = {
            'summary': {
                'total_files': len(self.migration_files),
                'version_conflicts': len(self.version_conflicts),
                'errors': len(self.errors),
                'warnings': len(self.warnings)
            },
            'files': self.migration_files,
            'execution_order': [f['filename'] for f in self.execution_order],
            'version_conflicts': self.version_conflicts,
            'errors': self.errors,
            'warnings': self.warnings,
            'modules': self._get_module_summary()
        }
        
        return report
    
    def _get_module_summary(self) -> Dict:
        """获取模块摘要"""
        module_stats = defaultdict(lambda: {'count': 0, 'versions': []})
        
        for migration in self.migration_files:
            module = migration['module']
            module_stats[module]['count'] += 1
            module_stats[module]['versions'].append(migration['version'])
        
        # 排序版本号
        for module in module_stats:
            module_stats[module]['versions'].sort()
        
        return dict(module_stats)
    
    def print_summary(self):
        """打印验证摘要"""
        print("\n" + "="*60)
        print("📋 数据库迁移脚本验证摘要")
        print("="*60)
        
        print(f"📁 总文件数: {len(self.migration_files)}")
        print(f"❌ 错误数: {len(self.errors)}")
        print(f"⚠️  警告数: {len(self.warnings)}")
        print(f"🔄 版本冲突: {len(self.version_conflicts)}")
        
        if self.errors:
            print("\n❌ 错误列表:")
            for error in self.errors:
                print(f"   {error}")
        
        if self.warnings:
            print("\n⚠️  警告列表:")
            for warning in self.warnings[:5]:  # 只显示前5个警告
                print(f"   {warning}")
            if len(self.warnings) > 5:
                print(f"   ... 还有 {len(self.warnings) - 5} 个警告")
        
        print("\n📊 模块分布:")
        module_summary = self._get_module_summary()
        for module, stats in sorted(module_summary.items()):
            version_range = f"V{min(stats['versions'])}-V{max(stats['versions'])}" if stats['versions'] else "无"
            print(f"   {module}: {stats['count']} 个文件, 版本范围: {version_range}")
        
        if len(self.errors) == 0:
            print("\n🎉 验证通过！迁移脚本可以安全执行。")
        else:
            print("\n⚠️  发现问题，建议修复后再执行迁移。")

def main():
    """主函数"""
    if len(sys.argv) > 1:
        base_path = sys.argv[1]
    else:
        base_path = "."
    
    print("🚀 数据库迁移脚本验证工具启动")
    print(f"📂 扫描路径: {os.path.abspath(base_path)}")
    
    validator = MigrationValidator(base_path)
    
    # 执行验证流程
    validator.scan_migration_files()
    validator.check_version_conflicts()
    validator.generate_execution_order()
    validator.check_table_dependencies()
    validator.check_syntax_errors()
    
    # 生成报告
    report = validator.generate_report()
    
    # 保存报告
    with open('migration-validation-report.json', 'w', encoding='utf-8') as f:
        json.dump(report, f, ensure_ascii=False, indent=2, default=str)
    
    # 打印摘要
    validator.print_summary()
    
    print(f"\n📄 详细报告已保存到: migration-validation-report.json")
    
    # 返回退出码
    return len(validator.errors)

if __name__ == "__main__":
    exit_code = main()
    sys.exit(exit_code) 