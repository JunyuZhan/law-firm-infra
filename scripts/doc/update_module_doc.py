#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
自动更新模块文档脚本
功能：
1. 扫描项目中的所有模块
2. 生成模块依赖关系图
3. 更新README.md中的模块描述
"""

import os
import re
import sys
import json
from pathlib import Path
from collections import defaultdict

# 项目根目录（相对于脚本位置的父父目录）
ROOT_DIR = Path(os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__)))))

# 模块目录名称列表
MODULE_DIRS = [
    "law-firm-api",
    "law-firm-common",
    "law-firm-core",
    "law-firm-model",
    "law-firm-modules"
]

# 模块输出文件路径
MODULE_DOC_PATH = ROOT_DIR / "docs" / "modules.md"
README_PATH = ROOT_DIR / "README.md"

def find_modules():
    """
    扫描项目找到所有模块
    返回: 模块信息字典 {模块名: {path: 路径, description: 描述}}
    """
    modules = {}
    
    # 扫描主要模块目录
    for module_dir in MODULE_DIRS:
        dir_path = ROOT_DIR / module_dir
        if not dir_path.exists():
            continue
            
        # 从pom.xml获取模块描述
        pom_path = dir_path / "pom.xml"
        if pom_path.exists():
            with open(pom_path, "r", encoding="utf-8") as f:
                content = f.read()
                # 提取模块名称
                name_match = re.search(r"<artifactId>(.*?)</artifactId>", content)
                name = name_match.group(1) if name_match else module_dir
                
                # 提取模块描述
                desc_match = re.search(r"<description>(.*?)</description>", content)
                description = desc_match.group(1) if desc_match else f"{name}模块"
                
                modules[name] = {
                    "path": str(dir_path.relative_to(ROOT_DIR)),
                    "description": description
                }
        
        # 扫描子模块
        for subdir in dir_path.iterdir():
            if subdir.is_dir() and (subdir / "pom.xml").exists():
                sub_pom_path = subdir / "pom.xml"
                with open(sub_pom_path, "r", encoding="utf-8") as f:
                    content = f.read()
                    # 提取模块名称
                    name_match = re.search(r"<artifactId>(.*?)</artifactId>", content)
                    name = name_match.group(1) if name_match else subdir.name
                    
                    # 提取模块描述
                    desc_match = re.search(r"<description>(.*?)</description>", content)
                    description = desc_match.group(1) if desc_match else f"{name}模块"
                    
                    modules[name] = {
                        "path": str(subdir.relative_to(ROOT_DIR)),
                        "description": description
                    }
    
    return modules

def find_dependencies():
    """
    分析模块间的依赖关系
    返回: 依赖关系字典 {模块名: [依赖模块1, 依赖模块2, ...]}
    """
    dependencies = defaultdict(list)
    modules = find_modules()
    
    for module_name, module_info in modules.items():
        pom_path = ROOT_DIR / module_info["path"] / "pom.xml"
        if pom_path.exists():
            with open(pom_path, "r", encoding="utf-8") as f:
                content = f.read()
                # 查找所有依赖
                dep_pattern = r"<dependency>.*?<groupId>com\.lawfirm</groupId>.*?<artifactId>(.*?)</artifactId>.*?</dependency>"
                for dep in re.finditer(dep_pattern, content, re.DOTALL):
                    dep_name = dep.group(1)
                    if dep_name in modules and dep_name != module_name:
                        dependencies[module_name].append(dep_name)
    
    return dependencies

def generate_module_doc():
    """
    生成模块文档
    """
    modules = find_modules()
    dependencies = find_dependencies()
    
    # 创建文档内容
    content = "# 模块文档\n\n"
    content += "本文档自动生成，描述了项目中的各个模块及其依赖关系。\n\n"
    
    # 模块概览
    content += "## 模块概览\n\n"
    content += "| 模块名 | 路径 | 描述 |\n"
    content += "| ------ | ---- | ---- |\n"
    
    for name, info in sorted(modules.items()):
        content += f"| {name} | {info['path']} | {info['description']} |\n"
    
    # 依赖关系
    content += "\n## 模块依赖关系\n\n"
    
    for module, deps in sorted(dependencies.items()):
        if deps:
            content += f"### {module}\n\n"
            content += "依赖以下模块：\n\n"
            for dep in sorted(deps):
                content += f"- {dep}\n"
            content += "\n"
    
    # 写入文件
    os.makedirs(os.path.dirname(MODULE_DOC_PATH), exist_ok=True)
    with open(MODULE_DOC_PATH, "w", encoding="utf-8") as f:
        f.write(content)
    
    print(f"✅ 模块文档已更新: {MODULE_DOC_PATH}")
    return True

def update_readme_modules():
    """
    更新README.md中的模块描述部分
    """
    if not README_PATH.exists():
        print(f"⚠️ README.md不存在: {README_PATH}")
        return False
    
    modules = find_modules()
    
    with open(README_PATH, "r", encoding="utf-8") as f:
        content = f.read()
    
    # 查找模块部分
    module_section_pattern = r"(## 模块结构\n\n).*?((?:\n##|\Z))"
    module_match = re.search(module_section_pattern, content, re.DOTALL)
    
    if not module_match:
        print("⚠️ README.md中未找到'## 模块结构'部分")
        return False
    
    # 创建新的模块描述内容
    new_module_content = "## 模块结构\n\n"
    new_module_content += "项目包含以下主要模块：\n\n"
    
    for name, info in sorted(modules.items()):
        if "/" not in info["path"]:  # 仅包含顶级模块
            new_module_content += f"- **{name}**: {info['description']}\n"
    
    new_module_content += "\n详细的模块文档请参阅 [模块文档](docs/modules.md)。\n"
    
    # 替换内容
    updated_content = content[:module_match.start()] + new_module_content + content[module_match.end()-1:]
    
    with open(README_PATH, "w", encoding="utf-8") as f:
        f.write(updated_content)
    
    print(f"✅ README.md中的模块描述已更新")
    return True

def main():
    """
    主函数
    """
    try:
        print("📝 开始更新模块文档...")
        
        # 生成模块文档
        success1 = generate_module_doc()
        
        # 更新README中的模块描述
        success2 = update_readme_modules()
        
        if success1 and success2:
            print("✅ 文档更新完成!")
            return 0
        else:
            print("⚠️ 文档部分更新失败")
            return 1
    except Exception as e:
        print(f"❌ 文档更新出错: {str(e)}")
        return 1

if __name__ == "__main__":
    sys.exit(main()) 