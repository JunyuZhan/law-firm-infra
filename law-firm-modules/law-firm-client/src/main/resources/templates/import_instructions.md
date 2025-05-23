# 客户导入说明

## 导入格式支持
系统支持以下格式的客户数据导入：
- CSV格式（.csv文件）
- Excel格式（.xlsx或.xls文件）

## 必填字段说明
导入客户数据时，以下字段为必填项：
- **客户名称**：客户的姓名或公司名称，不能为空
- **客户类型**：填写"个人"或"企业"
- **联系电话**：客户的联系电话，用于通知和联系

## 字段说明
| 字段名称 | 说明 | 是否必填 | 示例值 |
| ------ | ---- | ------ | ------ |
| 客户名称 | 客户姓名或公司名称 | 是 | 张三 / 北京某某有限公司 |
| 客户类型 | 个人或企业 | 是 | 个人 / 企业 |
| 证件类型 | 1:身份证、2:护照、3:营业执照 | 否 | 1 |
| 证件号码 | 对应证件的编号 | 否 | 110101199001011234 |
| 联系电话 | 客户主要联系电话 | 是 | 13800138000 |
| 电子邮箱 | 客户邮箱地址 | 否 | example@example.com |
| 联系地址 | 客户联系地址 | 否 | 北京市朝阳区某街道1号 |
| 行业 | 客户所属行业 | 否 | 互联网 / 金融 / 制造业 |
| 规模 | 客户企业规模 | 否 | 小型 / 中型 / 大型 |
| 来源渠道 | 1:自主开发、2:同行推荐、3:客户介绍、4:网络推广、5:其他 | 否 | 3 |
| 联系人姓名 | 客户联系人姓名 | 否 | 李四 |
| 联系人电话 | 联系人电话 | 否 | 13900139000 |
| 联系人邮箱 | 联系人邮箱 | 否 | contact@example.com |
| 职位 | 联系人职位 | 否 | 法务总监 |
| 部门 | 联系人所在部门 | 否 | 法务部 |
| 备注 | 其他相关信息 | 否 | 客户特别要求... |

## 导入注意事项
1. 确保CSV文件使用UTF-8编码，避免中文乱码
2. Excel文件请使用.xlsx格式（Office 2007及以上版本）
3. 导入前请检查数据格式是否正确，特别是必填字段
4. 单次导入客户数量不超过1000条
5. 证件号码应确保唯一性，系统将根据证件号码进行查重
6. 客户编号由系统自动生成，无需填写

## 错误处理
导入过程中如遇以下情况将会生成错误报告：
- 必填字段为空
- 证件号码重复
- 数据格式不正确
- 字段值超出指定范围

导入完成后，系统将生成导入报告，显示成功和失败的记录数量及原因。