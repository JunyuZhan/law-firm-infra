import os
import codecs
import sys

def remove_bom(file_path):
    """移除文件中的BOM标记"""
    print(f"处理文件: {file_path}")
    
    # 读取文件内容
    with open(file_path, 'rb') as f:
        content = f.read()
    
    # 检查是否有BOM标记
    if content.startswith(codecs.BOM_UTF8):
        print(f"  发现BOM标记，正在移除...")
        # 移除BOM标记
        with open(file_path, 'wb') as f:
            f.write(content[len(codecs.BOM_UTF8):])
        print(f"  BOM标记已移除")
    else:
        print(f"  没有BOM标记")

def process_directory(directory):
    """处理目录中的所有Java文件"""
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith('.java'):
                file_path = os.path.join(root, file)
                remove_bom(file_path)

if __name__ == "__main__":
    source_dir = "law-firm-modules/law-firm-auth/src/main/java/com/lawfirm/auth"
    print(f"开始处理目录: {source_dir}")
    process_directory(source_dir)
    print("所有文件处理完成！") 