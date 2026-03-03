package com.template.mf;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/menus")
public class MenuController {
  private final Store store;
  public MenuController(Store s){this.store=s;}
  @GetMapping("/tree")
  public ApiResponse<List<Store.MenuNode>> tree(@RequestParam(value="scene",required=false,defaultValue="SIDEBAR") String scene){
    return ApiResponse.ok(store.menuTree());
  }
  @GetMapping("/{id}/actions") public ApiResponse<List<Store.MenuAction>> actions(@PathVariable("id") String id){ return ApiResponse.ok(store.menuActions.getOrDefault(id,new ArrayList<>())); }
  @PutMapping("/{id}/actions") public ApiResponse<Object> setActions(@PathVariable("id") String id, @RequestBody Map<String,Object> body){ store.menuActions.put(id,new ArrayList<>()); return ApiResponse.ok(null); }
  @GetMapping("/{id}/field-policies") public ApiResponse<List<Store.FieldPolicy>> fps(@PathVariable("id") String id){ return ApiResponse.ok(store.menuFieldPolicies.getOrDefault(id,new ArrayList<>())); }
  @PutMapping("/{id}/field-policies") public ApiResponse<Object> setFps(@PathVariable("id") String id,@RequestBody Map<String,Object> body){ store.menuFieldPolicies.put(id,new ArrayList<>()); return ApiResponse.ok(null); }
  @GetMapping("/{id}/action-policies") public ApiResponse<List<Store.ActionPolicy>> aps(@PathVariable("id") String id){ return ApiResponse.ok(store.menuActionPolicies.getOrDefault(id,new ArrayList<>())); }
  @PutMapping("/{id}/action-policies") public ApiResponse<Object> setAps(@PathVariable("id") String id,@RequestBody Map<String,Object> body){ store.menuActionPolicies.put(id,new ArrayList<>()); return ApiResponse.ok(null); }
}
