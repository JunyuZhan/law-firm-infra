import os
import shutil

# 项目根目录
root_dir = r"D:\weidi\law-firm-infra"

# 模块路径
auth_migration_dir = os.path.join(root_dir, "law-firm-modules", "law-firm-auth", "src", "main", "resources", "db", "migration")
personnel_migration_dir = os.path.join(root_dir, "law-firm-modules", "law-firm-personnel", "src", "main", "resources", "db", "migration")

# 重命名迁移脚本
def rename_migrations():
    print("开始重命名迁移脚本...")
    
    # 重命名auth模块迁移脚本 (V1000 -> V2000)
    if os.path.exists(auth_migration_dir):
        print("处理auth模块...")
        for filename in os.listdir(auth_migration_dir):
            if filename.startswith("V1000"):
                new_filename = filename.replace("V1000", "V2000")
                old_path = os.path.join(auth_migration_dir, filename)
                new_path = os.path.join(auth_migration_dir, new_filename)
                shutil.move(old_path, new_path)
                print(f"  已重命名: {filename} -> {new_filename}")
            elif filename.startswith("V1001"):
                new_filename = filename.replace("V1001", "V2001")
                old_path = os.path.join(auth_migration_dir, filename)
                new_path = os.path.join(auth_migration_dir, new_filename)
                shutil.move(old_path, new_path)
                print(f"  已重命名: {filename} -> {new_filename}")
            elif filename.startswith("V1002"):
                new_filename = filename.replace("V1002", "V2002")
                old_path = os.path.join(auth_migration_dir, filename)
                new_path = os.path.join(auth_migration_dir, new_filename)
                shutil.move(old_path, new_path)
                print(f"  已重命名: {filename} -> {new_filename}")
            elif filename.startswith("V1003") or filename.startswith("V9003"):
                new_filename = "V2003__add_employee_constraint.sql"
                old_path = os.path.join(auth_migration_dir, filename)
                new_path = os.path.join(auth_migration_dir, new_filename)
                shutil.move(old_path, new_path)
                print(f"  已重命名: {filename} -> {new_filename}")
    
    # 重命名personnel模块迁移脚本 (V9000 -> V3000)
    if os.path.exists(personnel_migration_dir):
        print("处理personnel模块...")
        for filename in os.listdir(personnel_migration_dir):
            if filename.startswith("V9000"):
                new_filename = filename.replace("V9000", "V3000")
                old_path = os.path.join(personnel_migration_dir, filename)
                new_path = os.path.join(personnel_migration_dir, new_filename)
                shutil.move(old_path, new_path)
                print(f"  已重命名: {filename} -> {new_filename}")
            elif filename.startswith("V9001"):
                new_filename = filename.replace("V9001", "V3001")
                old_path = os.path.join(personnel_migration_dir, filename)
                new_path = os.path.join(personnel_migration_dir, new_filename)
                shutil.move(old_path, new_path)
                print(f"  已重命名: {filename} -> {new_filename}")
            elif filename.startswith("V9002"):
                new_filename = filename.replace("V9002", "V3002")
                old_path = os.path.join(personnel_migration_dir, filename)
                new_path = os.path.join(personnel_migration_dir, new_filename)
                shutil.move(old_path, new_path)
                print(f"  已重命名: {filename} -> {new_filename}")
            elif filename.startswith("V9003"):
                new_filename = filename.replace("V9003", "V3003")
                old_path = os.path.join(personnel_migration_dir, filename)
                new_path = os.path.join(personnel_migration_dir, new_filename)
                shutil.move(old_path, new_path)
                print(f"  已重命名: {filename} -> {new_filename}")
    
    print("迁移脚本重命名完成！")
    print("请记得清理Flyway历史记录，运行:")
    print("DROP TABLE IF EXISTS flyway_schema_history;")

if __name__ == "__main__":
    rename_migrations() 