#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import sys
import datetime
import re
import git

def get_git_changes():
    """获取Git变更信息"""
    repo = git.Repo(os.getcwd())
    changes = {}
    
    # 获取未提交的变更
    diff = repo.index.diff(None)
    for item in diff:
        module = get_module_name(item.a_path)
        if module:
            if module not in changes:
                changes[module] = []
            changes[module].append(f"修改文件: {item.a_path}")
            
    return changes

def get_module_name(file_path):
    """从文件路径获取模块名称"""
    if "law-firm-modules" not in file_path:
        return None
        
    parts = file_path.split(os.sep)
    try:
        module_index = parts.index("law-firm-modules")
        return parts[module_index + 1].replace("law-firm-", "")
    except:
        return None

def get_next_version(current_version):
    """获取下一个版本号"""
    major, minor, patch = current_version.split('.')
    return f"{major}.{minor}.{int(patch) + 1}"

def get_current_version(content):
    """获取当前版本号"""
    version_pattern = r'\|\s*([\d.]+)\s*\|'
    matches = re.findall(version_pattern, content)
    return matches[-1] if matches else "1.0.0"

def update_version_record(doc_path, changes):
    """更新版本记录"""
    try:
        with open(doc_path, 'r', encoding='utf-8') as f:
            content = f.read()
            
        current_version = get_current_version(content)
        next_version = get_next_version(current_version)
        
        # 格式化变更内容
        changes_str = "<br>".join(changes)
        
        # 获取当前用户
        repo = git.Repo(os.getcwd())
        author = repo.config_reader().get_value("user", "name")
        
        # 生成新的版本记录
        version_record = f"| {next_version} | {datetime.date.today()} | {changes_str} | {author} |"
        
        # 在版本记录表前插入新记录
        pattern = r'(\|\s*版本\s*\|\s*日期\s*\|\s*更新内容\s*\|\s*作者\s*\|[\s\S]*?)\Z'
        updated_content = re.sub(pattern, r'\1' + version_record + '\n', content)
        
        # 写入更新后的内容
        with open(doc_path, 'w', encoding='utf-8') as f:
            f.write(updated_content)
            
        print(f"已更新文档: {doc_path}")
        return True
    except Exception as e:
        print(f"更新文档失败: {doc_path}, 错误: {str(e)}")
        return False

def main():
    """主函数"""
    # 获取Git变更信息
    changes = get_git_changes()
    
    # 更新相关模块的文档
    for module, module_changes in changes.items():
        doc_path = f"docs/modules/{module}/README.md"
        if os.path.exists(doc_path):
            if update_version_record(doc_path, module_changes):
                print(f"模块 {module} 文档更新成功")
            else:
                print(f"模块 {module} 文档更新失败")
        else:
            print(f"模块 {module} 文档不存在: {doc_path}")

if __name__ == "__main__":
    main() 