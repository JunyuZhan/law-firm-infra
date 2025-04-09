#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import os
import sys
import shutil
import argparse

# 项目根目录（自动获取）
root_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), ".."))

# 模块路径
auth_migration_dir = os.path.join(root_dir, "law-firm-modules", "law-firm-auth", "src", "main", "resources", "db", "migration")
personnel_migration_dir = os.path.join(root_dir, "law-firm-modules", "law-firm-personnel", "src", "main", "resources", "db", "migration")
system_migration_dir = os.path.join(root_dir, "law-firm-modules", "law-firm-system", "src", "main", "resources", "db", "migration")
client_migration_dir = os.path.join(root_dir, "law-firm-modules", "law-firm-client", "src", "main", "resources", "db", "migration")

# 所有模块的迁移目录
all_migration_dirs = {
    "auth": auth_migration_dir,
    "personnel": personnel_migration_dir,
    "system": system_migration_dir,
    "client": client_migration_dir
}

# 重命名迁移脚本（version -> new_version）
def rename_migrations(module, version_prefix, new_version_prefix):
    """
    将指定模块中的迁移脚本从一个版本前缀重命名为另一个版本前缀
    """
    print(f"开始重命名[{module}]模块迁移脚本: {version_prefix}xxx -> {new_version_prefix}xxx")
    
    migration_dir = all_migration_dirs.get(module)
    if not migration_dir or not os.path.exists(migration_dir):
        print(f"错误: [{module}]模块迁移目录不存在")
        return False
    
    renamed_count = 0
    for filename in os.listdir(migration_dir):
        if filename.startswith(version_prefix):
            # 提取版本号后缀
            version_suffix = filename[len(version_prefix):]
            # 创建新文件名
            new_filename = f"{new_version_prefix}{version_suffix}"
            # 构建文件路径
            old_path = os.path.join(migration_dir, filename)
            new_path = os.path.join(migration_dir, new_filename)
            # 重命名文件
            shutil.move(old_path, new_path)
            print(f"  已重命名: {filename} -> {new_filename}")
            renamed_count += 1
            
    print(f"重命名完成，共处理 {renamed_count} 个文件")
    return renamed_count > 0

# 交换两个模块的迁移脚本版本
def swap_migrations(module1, module2):
    """
    交换两个模块之间的迁移脚本版本
    """
    print(f"开始交换迁移版本 [{module1}] <-> [{module2}]")
    
    # 检查两个模块是否存在
    dir1 = all_migration_dirs.get(module1)
    dir2 = all_migration_dirs.get(module2)
    
    if not dir1 or not os.path.exists(dir1):
        print(f"错误: [{module1}]模块迁移目录不存在")
        return False
    
    if not dir2 or not os.path.exists(dir2):
        print(f"错误: [{module2}]模块迁移目录不存在")
        return False
    
    # 创建临时目录存放交换的文件
    temp_dir = os.path.join(root_dir, "temp_migration_swap")
    if not os.path.exists(temp_dir):
        os.makedirs(temp_dir)
    
    try:
        # 第1步: 将module1的迁移脚本移动到临时目录并重命名为TMP_
        print(f"步骤1: 将[{module1}]迁移脚本移动到临时目录")
        for filename in os.listdir(dir1):
            if filename.endswith(".sql"):
                # 保存原始版本号以便后续处理
                old_path = os.path.join(dir1, filename)
                temp_path = os.path.join(temp_dir, f"TMP_{filename}")
                shutil.copy2(old_path, temp_path)
        
        # 第2步: 将module2的迁移脚本移动到module1
        print(f"步骤2: 将[{module2}]迁移脚本移动到[{module1}]")
        for filename in os.listdir(dir2):
            if filename.endswith(".sql"):
                old_path = os.path.join(dir2, filename)
                new_path = os.path.join(dir1, filename)
                shutil.copy2(old_path, new_path)
        
        # 第3步: 将临时目录中的脚本移动到module2
        print(f"步骤3: 将临时目录迁移脚本移动到[{module2}]")
        for filename in os.listdir(temp_dir):
            if filename.startswith("TMP_"):
                old_path = os.path.join(temp_dir, filename)
                new_filename = filename[4:]  # 去掉TMP_前缀
                new_path = os.path.join(dir2, new_filename)
                shutil.copy2(old_path, new_path)
        
        print("交换完成!")
        return True
    
    except Exception as e:
        print(f"交换过程中发生错误: {str(e)}")
        return False
    
    finally:
        # 清理临时目录
        if os.path.exists(temp_dir):
            shutil.rmtree(temp_dir)

# 重置迁移版本
def reset_migrations():
    """
    为各个模块分配标准版本号
    """
    print("开始重置迁移版本...")
    
    # 定义各模块的版本前缀
    version_map = {
        "system": "V1000",    # 系统模块: V1000
        "auth": "V2000",      # 认证模块: V2000
        "personnel": "V3000", # 人员模块: V3000
        "client": "V4000",    # 客户模块: V4000
    }
    
    for module, target_prefix in version_map.items():
        dir_path = all_migration_dirs.get(module)
        if not dir_path or not os.path.exists(dir_path):
            print(f"警告: [{module}]模块迁移目录不存在，跳过")
            continue
        
        print(f"处理[{module}]模块迁移...")
        sql_files = [f for f in os.listdir(dir_path) if f.endswith(".sql")]
        
        # 如果目录为空，继续下一个
        if not sql_files:
            print(f"  [{module}]模块没有迁移文件，跳过")
            continue
        
        # 对文件进行排序
        sql_files.sort()
        
        # 根据排序后的顺序，重新分配版本号
        for i, filename in enumerate(sql_files):
            # 提取文件名中的描述部分（版本号后的部分）
            parts = filename.split("__", 1)
            if len(parts) < 2:
                print(f"  警告: 文件名格式不正确 {filename}，跳过")
                continue
            
            description = parts[1]
            # 创建新的文件名
            new_version = f"{target_prefix}{i+1:03d}"
            new_filename = f"{new_version}__{description}"
            
            # 重命名文件
            old_path = os.path.join(dir_path, filename)
            new_path = os.path.join(dir_path, new_filename)
            
            if filename != new_filename:
                shutil.move(old_path, new_path)
                print(f"  已重命名: {filename} -> {new_filename}")
    
    print("迁移版本重置完成！")
    print("请记得清理Flyway历史记录，运行:")
    print("DROP TABLE IF EXISTS flyway_schema_history;")
    return True

def main():
    parser = argparse.ArgumentParser(description="律师事务所管理系统 - 数据库迁移工具")
    
    # 添加子命令
    subparsers = parser.add_subparsers(dest="command", help="命令")
    
    # 重命名迁移版本命令
    rename_parser = subparsers.add_parser("rename", help="重命名迁移版本")
    rename_parser.add_argument("module", help="模块名称 (auth, personnel, system, client)")
    rename_parser.add_argument("old_prefix", help="原版本前缀 (e.g. V1000)")
    rename_parser.add_argument("new_prefix", help="新版本前缀 (e.g. V2000)")
    
    # 交换迁移版本命令
    swap_parser = subparsers.add_parser("swap", help="交换两个模块的迁移版本")
    swap_parser.add_argument("module1", help="第一个模块名称 (auth, personnel, system, client)")
    swap_parser.add_argument("module2", help="第二个模块名称 (auth, personnel, system, client)")
    
    # 重置迁移版本命令
    reset_parser = subparsers.add_parser("reset", help="重置所有模块的迁移版本")
    
    # 解析命令行参数
    args = parser.parse_args()
    
    # 处理命令
    if args.command == "rename":
        rename_migrations(args.module, args.old_prefix, args.new_prefix)
    elif args.command == "swap":
        swap_migrations(args.module1, args.module2)
    elif args.command == "reset":
        reset_migrations()
    else:
        parser.print_help()

if __name__ == "__main__":
    main() 