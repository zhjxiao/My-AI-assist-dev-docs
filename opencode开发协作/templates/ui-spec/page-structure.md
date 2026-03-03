# 页面结构约定（简版）
- list：query form + data-scope-filter（可选）+ crud + 操作列
- form：新增/编辑复用 _form.json
- detail：drawer/property 两列展示
- 所有 api 必须 adaptor：{code,message,data} -> {status,msg,data}
