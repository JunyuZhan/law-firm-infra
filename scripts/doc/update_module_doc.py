#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
自动更新模块文档和使用教程脚本
功能：
1. 扫描项目中的所有模块
2. 生成模块依赖关系图
3. 收集整合各业务模块的README文档
4. 生成完整的系统使用教程
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

# 业务模块前缀（用于识别业务模块）
BUSINESS_MODULE_PREFIX = "law-firm-"

# 文档输出路径
MODULE_DOC_PATH = ROOT_DIR / "docs" / "modules.md"
USER_GUIDE_PATH = ROOT_DIR / "docs" / "user-guide.md"
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
                    "description": description,
                    "is_business": name.startswith(BUSINESS_MODULE_PREFIX) or module_dir == "law-firm-modules"
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
                    
                    # 判断是否是业务模块（包含在law-firm-modules中或者名称以law-firm-开头）
                    is_business = (name.startswith(BUSINESS_MODULE_PREFIX) or 
                                  "law-firm-modules" in str(subdir))
                    
                    modules[name] = {
                        "path": str(subdir.relative_to(ROOT_DIR)),
                        "description": description,
                        "is_business": is_business
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

def collect_module_readme(module_name, module_info):
    """
    收集模块的README文档内容
    """
    module_path = ROOT_DIR / module_info["path"]
    readme_path = module_path / "README.md"
    
    if not readme_path.exists():
        return f"# {module_name}\n\n{module_info['description']}\n\n_（此模块暂无详细文档）_\n\n"
    
    with open(readme_path, "r", encoding="utf-8") as f:
        content = f.read()
    
    # 处理相对路径的图片和链接
    # 将相对路径替换为绝对路径，例如 ./images/diagram.png -> /path/to/module/images/diagram.png
    img_pattern = r'!\[(.*?)\]\(((?!http).*?)\)'
    link_pattern = r'\[(.*?)\]\(((?!http)(?!#).*?)\)'
    
    module_rel_path = module_info["path"].replace("\\", "/")
    content = re.sub(img_pattern, f'![\g<1>](/{module_rel_path}/\g<2>)', content)
    content = re.sub(link_pattern, f'[\g<1>](/{module_rel_path}/\g<2>)', content)
    
    # 如果README中没有一级标题，添加一个
    if not re.search(r'^# ', content, re.MULTILINE):
        content = f"# {module_name}\n\n{content}"
    
    return content

def generate_user_guide():
    """
    生成系统使用教程文档
    """
    modules = find_modules()
    
    # 创建文档内容
    content = "# 律师事务所管理系统使用教程\n\n"
    content += "本文档自动生成，包含了系统所有业务模块的使用说明。\n\n"
    content += "## 目录\n\n"
    
    # 添加目录
    business_modules = {name: info for name, info in modules.items() if info["is_business"]}
    for name, info in sorted(business_modules.items()):
        content += f"- [{name} - {info['description']}](#{name.lower().replace(' ', '-')})\n"
    
    content += "\n---\n\n"
    
    # 添加各模块的使用教程
    for name, info in sorted(business_modules.items()):
        module_content = collect_module_readme(name, info)
        content += f"{module_content}\n\n---\n\n"
    
    # 写入文件
    os.makedirs(os.path.dirname(USER_GUIDE_PATH), exist_ok=True)
    with open(USER_GUIDE_PATH, "w", encoding="utf-8") as f:
        f.write(content)
    
    print(f"✅ 系统使用教程已更新: {USER_GUIDE_PATH}")
    return True

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
    
    # 查找系统架构部分 (而不是之前的"模块结构"部分)
    module_section_pattern = r"(## 系统架构\n\n).*?((?:\n##|\Z))"
    module_match = re.search(module_section_pattern, content, re.DOTALL)
    
    if not module_match:
        print("⚠️ README.md中未找到'## 系统架构'部分")
        return False
    
    # 我们不修改README中的架构部分，因为它已经很详细
    # 我们只在文件末尾添加链接到新生成的文档
    
    # 检查是否已经有文档链接
    doc_link_pattern = r"\n## 文档链接"
    if re.search(doc_link_pattern, content):
        # 已有链接部分，尝试更新
        link_section_pattern = r"(## 文档链接\n\n)(.*?)(\n##|\Z)"
        link_section_match = re.search(link_section_pattern, content, re.DOTALL)
        
        if link_section_match:
            link_text = "## 文档链接\n\n"
            link_text += "- [系统使用教程](docs/user-guide.md) - 详细的系统使用说明\n"
            link_text += "- [模块文档](docs/modules.md) - 模块结构和依赖关系\n"
            
            updated_content = content[:link_section_match.start(1)] + link_text + content[link_section_match.end(2):]
            
            with open(README_PATH, "w", encoding="utf-8") as f:
                f.write(updated_content)
            
            print("✅ README.md中的文档链接已更新")
            return True
    
    # 没有找到链接部分，添加到文件末尾
    link_text = "\n\n## 文档链接\n\n"
    link_text += "- [系统使用教程](docs/user-guide.md) - 详细的系统使用说明\n"
    link_text += "- [模块文档](docs/modules.md) - 模块结构和依赖关系\n"
    
    updated_content = content + link_text
    
    with open(README_PATH, "w", encoding="utf-8") as f:
        f.write(updated_content)
    
    print(f"✅ README.md中已添加文档链接")
    return True

def main():
    """
    主函数
    """
    try:
        print("📝 开始更新系统文档...")
        
        # 生成模块文档
        success1 = generate_module_doc()
        
        # 生成系统使用教程
        success2 = generate_user_guide()
        
        # 更新README中的链接
        success3 = update_readme_modules()
        
        if success1 and success2 and success3:
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