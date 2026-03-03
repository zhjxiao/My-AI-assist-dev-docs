# 字段 -> AMis 控件映射（生成规则）

## 基本类型
- string -> input-text
- number -> input-number
- boolean -> switch
- date-time -> input-datetime
- date -> input-date
- text/remark -> textarea

## 特殊命名
- *Id 且字段名包含 org -> org-selector
- *Ids 且字段名包含 group -> group-selector(multiple)
- *Status 或 enum 且存在 x-dict -> dict-select(dictCode=x-dict.code)
- assignee/handler/userIds -> user-picker(multiple)

## 列表页查询字段优先级（3~6 个）
keyword > name/title > status > orgId > dateRange > groupId
