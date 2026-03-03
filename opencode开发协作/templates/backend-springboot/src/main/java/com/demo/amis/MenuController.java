package com.demo.amis;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

  private final DemoStore store;

  public MenuController(DemoStore store) {
    this.store = store;
  }

  @GetMapping("/tree")
  public ApiResponse<List<DemoStore.MenuNode>> tree(@RequestParam(value="scene", required=false, defaultValue="SIDEBAR") String scene) {
    return ApiResponse.ok(store.menuTree());
  }

  @GetMapping("/{id}/actions")
  public ApiResponse<List<DemoStore.MenuAction>> getActions(@PathVariable("id") String id) {
    return ApiResponse.ok(store.menuActions.getOrDefault(id, new ArrayList<DemoStore.MenuAction>()));
  }

  @PutMapping("/{id}/actions")
  public ApiResponse<Object> setActions(@PathVariable("id") String id, @RequestBody Map<String,Object> body) {
    Object actionsObj = body.get("actions");
    List<DemoStore.MenuAction> list = new ArrayList<DemoStore.MenuAction>();
    if (actionsObj instanceof List) {
      for (Object o : (List<?>) actionsObj) {
        if (!(o instanceof Map)) continue;
        Map<?,?> m = (Map<?,?>) o;
        String key = String.valueOf(m.get("key"));
        String title = m.get("title")==null ? key : String.valueOf(m.get("title"));
        boolean enabled = m.get("enabled")==null ? true : Boolean.parseBoolean(String.valueOf(m.get("enabled")));
        list.add(new DemoStore.MenuAction(key, title, enabled));
      }
    }
    store.menuActions.put(id, list);
    return ApiResponse.ok(null);
  }

  @GetMapping("/{id}/field-policies")
  public ApiResponse<List<DemoStore.FieldPolicy>> getFieldPolicies(@PathVariable("id") String id) {
    return ApiResponse.ok(store.menuFieldPolicies.getOrDefault(id, new ArrayList<DemoStore.FieldPolicy>()));
  }

  @PutMapping("/{id}/field-policies")
  public ApiResponse<Object> setFieldPolicies(@PathVariable("id") String id, @RequestBody Map<String,Object> body) {
    Object policiesObj = body.get("policies");
    List<DemoStore.FieldPolicy> list = new ArrayList<DemoStore.FieldPolicy>();
    if (policiesObj instanceof List) {
      for (Object o : (List<?>) policiesObj) {
        if (!(o instanceof Map)) continue;
        Map<?,?> m = (Map<?,?>) o;
        DemoStore.FieldPolicy p = new DemoStore.FieldPolicy();
        p.resource = str(m.get("resource"));
        p.field = str(m.get("field"));
        p.target = str(m.get("target"));
        p.mode = str(m.get("mode"));
        p.when = str(m.get("when"));
        p.priority = intOrNull(m.get("priority"));
        p.maskType = str(m.get("maskType"));
        p.unmaskPermission = str(m.get("unmaskPermission"));
        list.add(p);
      }
    }
    store.menuFieldPolicies.put(id, list);
    return ApiResponse.ok(null);
  }

  @GetMapping("/{id}/action-policies")
  public ApiResponse<List<DemoStore.ActionPolicy>> getActionPolicies(@PathVariable("id") String id) {
    return ApiResponse.ok(store.menuActionPolicies.getOrDefault(id, new ArrayList<DemoStore.ActionPolicy>()));
  }

  @PutMapping("/{id}/action-policies")
  public ApiResponse<Object> setActionPolicies(@PathVariable("id") String id, @RequestBody Map<String,Object> body) {
    Object policiesObj = body.get("policies");
    List<DemoStore.ActionPolicy> list = new ArrayList<DemoStore.ActionPolicy>();
    if (policiesObj instanceof List) {
      for (Object o : (List<?>) policiesObj) {
        if (!(o instanceof Map)) continue;
        Map<?,?> m = (Map<?,?>) o;
        DemoStore.ActionPolicy p = new DemoStore.ActionPolicy();
        p.resource = str(m.get("resource"));
        p.actionKey = str(m.get("actionKey"));
        p.target = str(m.get("target"));
        p.mode = str(m.get("mode"));
        p.when = str(m.get("when"));
        p.priority = intOrNull(m.get("priority"));
        p.disabledReason = str(m.get("disabledReason"));
        p.confirmText = str(m.get("confirmText"));
        p.requireReason = boolOrNull(m.get("requireReason"));
        list.add(p);
      }
    }
    store.menuActionPolicies.put(id, list);
    return ApiResponse.ok(null);
  }

  private String str(Object o){ return o==null?null:String.valueOf(o); }
  private Integer intOrNull(Object o){ if(o==null) return null; try { return Integer.parseInt(String.valueOf(o)); } catch(Exception e){ return null; } }
  private Boolean boolOrNull(Object o){ if(o==null) return null; return Boolean.parseBoolean(String.valueOf(o)); }
}
