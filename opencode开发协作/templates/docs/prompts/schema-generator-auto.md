你将执行“自动生成 AMis Schema”的完整流程。

输入：
- /spec/openapi.yaml（含 tags.x-menu、operation.x-permission、schema.x-ui/x-dict/x-mask）
- /spec/domain-model.md
- /ui-spec/field-mapping.md
- /ui-spec/custom-renderers.md
- /ui-spec/page-structure.md

任务：推断模块（按 tags 分组）→ 为每个模块生成 list/_form/detail → 自检修复（method/url/分页/响应/adaptor/权限点）

输出：以补丁格式
### FILE: <path>
<content>
